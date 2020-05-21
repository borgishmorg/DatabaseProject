package gui.frames.edit;

import java.sql.SQLException;

import java.awt.event.*;

import javax.swing.*;

import database.Database;
import gui.components.*;
import utils.Log;

public class EditParticipationFrame extends JFrame{

    private static final long serialVersionUID = 51646545611L;
    
    private JComboBox<String> participationComboBox;
    private JComboBox<String> catComboBox;
    private JComboBox<String> exhibitionComboBox;
    private JTextField placeTextField;
    private JButton applyButton;

    public EditParticipationFrame(){
        super("Изменить участие");
        try{
            participationComboBox = new SQLSelectComboBox(
                "participation p " +
                "LEFT JOIN cat ON cat.cat_id = p.cat_id " +
                "LEFT JOIN exhibition ex ON ex.exhibition_id = p.exhibition_id ",
                "CONCAT(name, \" - \", title)");
            catComboBox = new SQLSelectComboBox("cat", "name");
            exhibitionComboBox = new SQLSelectComboBox("exhibition", "title");
            participationComboBox.setSelectedIndex(-1);
            participationComboBox.addActionListener(new ParticipationComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }
        placeTextField = new JTextField(20);
        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(participationComboBox);
        add(catComboBox);
        add(exhibitionComboBox);
        add(placeTextField);
        add(applyButton);

        pack();
        setVisible(true);
    }

    class ApplyButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int participation_id = participationComboBox.getSelectedIndex() + 1;
            if(participation_id == 0)
                return;
            int cat_id = catComboBox.getSelectedIndex() + 1;
            int exhibition_id = exhibitionComboBox.getSelectedIndex() + 1;
            String place = "NULL";

            try{
                place = "" + Integer.parseInt(placeTextField.getText());
            }catch(NumberFormatException exception){
                Log.log.error(exception.toString());
            }

            try{
                Database.database.executeUpdate(
                    "UPDATE participation SET "+
                    String.format("cat_id=%d, exhibition_id=%d, place=%s ", cat_id, exhibition_id, place)+
                    String.format("WHERE participation_id=%d;", participation_id)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditParticipationFrame.this.dispose();
            }
        }
    } 

    class ParticipationComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int participation_id = participationComboBox.getSelectedIndex();
            int cat_id;
            int exhibition_id;
            String place;
            try{
                cat_id = Integer.parseInt(Database.database.getColumnFromSelect("participation", "cat_id", 1)[participation_id]);
                exhibition_id = Integer.parseInt(Database.database.getColumnFromSelect("participation", "exhibition_id", 1)[participation_id]);
                place = Database.database.getColumnFromSelect("participation", "place", 1)[participation_id];
            }catch(Exception exception){
                Log.log.error(exception.toString());
                EditParticipationFrame.this.dispose();
                return;
            }
            
            catComboBox.setSelectedIndex(cat_id-1);
            exhibitionComboBox.setSelectedIndex(exhibition_id-1);
            try{
                placeTextField.setText("" + Integer.parseInt(place));
            }catch(NumberFormatException exception){
                placeTextField.setText("");
            }
        }
    }
}