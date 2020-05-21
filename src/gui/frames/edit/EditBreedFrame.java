package gui.frames.edit;

import javax.swing.*;

import database.Database;
import gui.SQLSelectComboBox;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EditBreedFrame extends JFrame{
    
    private static final long serialVersionUID = -8184339006281031618L;
    
    private JComboBox<String> breedComboBox;
    private JTextField breedText;
    private JButton applyBreed;

    public EditBreedFrame(){
        super("Изменить породу");
        
        try{
            breedComboBox = new SQLSelectComboBox("breed", "name");
            breedComboBox.setSelectedIndex(-1);
            breedComboBox.addActionListener(new BreedComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        breedText = new JTextField();
        breedText.setColumns(20);    
                
        applyBreed = new JButton("Подтвердить");
        applyBreed.addActionListener(new ApplyBreedListener());

        add(breedComboBox, BorderLayout.NORTH);
        add(breedText, BorderLayout.CENTER);
        add(applyBreed, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyBreedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String breed = breedText.getText().substring(0, Math.min(50, breedText.getText().length()));
            int breed_id = breedComboBox.getSelectedIndex() + 1;
            if(breed_id == 0)
                return;
            try{
                Database.database.executeUpdate(
                    "UPDATE breed "+
                    String.format("SET name=\"%s\" ", breed) +
                    "WHERE breed_id=" + breed_id + " ;"
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditBreedFrame.this.dispose();
            }
        }
    }

    class BreedComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int breed_id = breedComboBox.getSelectedIndex();
            String breed;
            try{
                breed = Database.database.getColumnFromSelect("breed", "name", 1)[breed_id];
            }catch(SQLException exception){
                Log.log.error(exception.toString());
                EditBreedFrame.this.dispose();
                return;
            }
            breedText.setText(breed);
        }
    }
}