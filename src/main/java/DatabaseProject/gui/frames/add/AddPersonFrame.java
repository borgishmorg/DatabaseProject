package DatabaseProject.gui.frames.add;

import javax.swing.*;

import DatabaseProject.database.Database;
import DatabaseProject.utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddPersonFrame extends JFrame{
    
    private static final long serialVersionUID = 8514484897952271335L;
    
    private JTextField personText;
    private JButton applyPerson;

    public AddPersonFrame(){
        super("Добавить человека");
        
        personText = new JTextField();
        personText.setColumns(20);    
                
        applyPerson = new JButton("Подтвердить");
        applyPerson.addActionListener(new ApplyPersonListener());

        add(personText, BorderLayout.NORTH);
        add(applyPerson, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyPersonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = personText.getText().substring(0, Math.min(50, personText.getText().length()));
            try{
                Database.database.executeUpdate(
                    "INSERT INTO person(name) "+
                    String.format("VALUES(\"%s\");", name)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                AddPersonFrame.this.dispose();
            }
        }
    }
}