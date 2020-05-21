package gui.frames.edit;

import javax.swing.*;

import database.Database;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EditPersonFrame extends JFrame{
    
    private static final long serialVersionUID = 8514484897952271335L;
    
    private JComboBox<String> personComboBox;
    private JTextField personText;
    private JButton applyPerson;

    public EditPersonFrame(){
        super("Изменить человека");
        
        try{
            personComboBox = new JComboBox<>(Database.database.getColumnFromSelect("person", new String[]{"name"}, 1));
            personComboBox.setSelectedIndex(-1);
            personComboBox.addActionListener(new PersonComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        personText = new JTextField();
        personText.setColumns(20);    
                
        applyPerson = new JButton("Подтвердить");
        applyPerson.addActionListener(new ApplyPersonListener());

        add(personComboBox, BorderLayout.NORTH);
        add(personText, BorderLayout.CENTER);
        add(applyPerson, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyPersonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String person = personText.getText().substring(0, Math.min(50, personText.getText().length()));
            int person_id = personComboBox.getSelectedIndex() + 1;
            if(person_id == 0)
                return;
            try{
                Database.database.executeUpdate(
                    "UPDATE person "+
                    String.format("SET name=\"%s\" ", person) +
                    "WHERE person_id=" + person_id + " ;"
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditPersonFrame.this.dispose();
            }
        }
    }

    class PersonComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int person_id = personComboBox.getSelectedIndex();
            String person;
            try{
                person = Database.database.getColumnFromSelect("person", new String[]{"name"}, 1)[person_id];
            }catch(SQLException exception){
                Log.log.error(exception.toString());
                EditPersonFrame.this.dispose();
                return;
            }
            personText.setText(person);
        }
    }
}