package gui.frames;

import java.sql.SQLException;

import java.awt.event.*;

import javax.swing.*;

import database.Database;
import utils.Log;

public class EditParticipationFrame extends JFrame{

    private static final long serialVersionUID = 51646545611L;
    
    private JComboBox<String> catComboBox;
    private JComboBox<String> exhibitionComboBox;
    private JTextField placeTextField;
    private JButton applyButton;

    public EditParticipationFrame(){
        super("Добавить участие");
        //TODO
        try{
            catComboBox = new JComboBox<>(Database.database.getColumnFromSelect("cat", new String[]{"name"}, 1));
            exhibitionComboBox = new JComboBox<>(Database.database.getColumnFromSelect("exhibition", new String[]{"title"}, 1));
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }
        placeTextField = new JTextField(20);
        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

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
                    "INSERT INTO participation(cat_id, exhibition_id, place) "+
                    String.format("VALUES(%d, %d, %s);", cat_id, exhibition_id, place)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditParticipationFrame.this.dispose();
            }
        }
    } 
}