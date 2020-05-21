package gui.frames.edit;

import javax.swing.*;

import database.Database;
import gui.components.*;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EditGenderFrame extends JFrame{
    
    private static final long serialVersionUID = -8184339006281031618L;
    
    private JComboBox<String> genderComboBox;
    private JTextField genderText;
    private JButton applyGender;

    public EditGenderFrame(){
        super("Изменить пол");
        
        try{
            genderComboBox = new SQLSelectComboBox("gender", "gender");
            genderComboBox.setSelectedIndex(-1);
            genderComboBox.addActionListener(new GenderComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        genderText = new JTextField();
        genderText.setColumns(20);    
                
        applyGender = new JButton("Подтвердить");
        applyGender.addActionListener(new ApplyGenderListener());

        add(genderComboBox, BorderLayout.NORTH);
        add(genderText, BorderLayout.CENTER);
        add(applyGender, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyGenderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String gender = genderText.getText().substring(0, Math.min(50, genderText.getText().length()));
            int gender_id = genderComboBox.getSelectedIndex() + 1;
            if(gender_id == 0)
                return;
            try{
                Database.database.executeUpdate(
                    "UPDATE gender "+
                    String.format("SET gender=\"%s\" ", gender) +
                    "WHERE gender_id=" + gender_id + " ;"
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditGenderFrame.this.dispose();
            }
        }
    }

    class GenderComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int gender_id = genderComboBox.getSelectedIndex();
            String gender;
            try{
                gender = Database.database.getColumnFromSelect("gender", "gender", 1)[gender_id];
            }catch(SQLException exception){
                Log.log.error(exception.toString());
                EditGenderFrame.this.dispose();
                return;
            }
            genderText.setText(gender);
        }
    }
}