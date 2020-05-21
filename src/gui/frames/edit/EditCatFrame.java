package gui.frames.edit;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import database.Database;
import utils.Log;

public class EditCatFrame extends JFrame {

    private static final long serialVersionUID = 14561L;

    private JTextField nameField;
    private JDatePicker datePicker;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> personComboBox;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> fatherComboBox;
    private JComboBox<String> motherComboBox;
    private JButton applyButton;
    
    private String[] fatherIds;
    private String[] motherIds;

    public EditCatFrame() {
        super("Добавить кисоньку");
        //TODO
        nameField = new JTextField(20);
        datePicker = new JDatePicker();

        try {
            breedComboBox = new JComboBox<>(Database.database.getColumnFromSelect("breed", new String[]{"name"}, 1));
            personComboBox = new JComboBox<>(Database.database.getColumnFromSelect("person", new String[]{"name"}, 1));
            genderComboBox = new JComboBox<>(Database.database.getColumnFromSelect("gender", new String[]{"gender"}, 1));

            fatherComboBox = new JComboBox<>(Database.database.getColumnFromSelect("cat", new String[]{"name"}, " WHERE gender_id = 1 ", 1));
            fatherIds = Database.database.getColumnFromSelect("cat", new String[]{"cat_id"}, " WHERE gender_id = 1 ", 1);
            fatherComboBox.setSelectedIndex(-1);
            motherComboBox = new JComboBox<>(Database.database.getColumnFromSelect("cat", new String[]{"name"}, " WHERE gender_id = 2 ", 1));
            motherIds = Database.database.getColumnFromSelect("cat", new String[]{"cat_id"}, " WHERE gender_id = 2 ", 1);
            motherComboBox.setSelectedIndex(-1);
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(nameField);
        add(datePicker);
        add(breedComboBox);
        add(personComboBox);
        add(genderComboBox);
        add(fatherComboBox);
        add(motherComboBox);
        add(applyButton);

        pack();
        setVisible(true);
    }

    class ApplyButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().substring(0, Math.min(50, nameField.getText().length()));
            String birthday;
            try{
                GregorianCalendar calendar = (GregorianCalendar) datePicker.getModel().getValue();
                birthday = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            }catch(Exception exception){
                birthday = "NULL";
                Log.log.error(exception.toString());
            }

            int breed_id = breedComboBox.getSelectedIndex() + 1;
            int person_id = personComboBox.getSelectedIndex() + 1;
            int gender_id = genderComboBox.getSelectedIndex() + 1;
            int father_id = 0;
            if(fatherComboBox.getSelectedIndex() != -1)
                father_id = Integer.parseInt(fatherIds[fatherComboBox.getSelectedIndex()]);
            int mother_id = 0;
            if(motherComboBox.getSelectedIndex() != -1)
                mother_id = Integer.parseInt(motherIds[motherComboBox.getSelectedIndex()]);
            
            try{
                Database.database.executeUpdate(
                    "INSERT INTO cat(name, birthday, breed_id, person_id, gender_id, father_id, mother_id) "+
                    String.format("VALUES(\"%s\", \"%s\", %d, %d, %d, %d, %d);", name, birthday, breed_id, person_id, gender_id, father_id, mother_id)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditCatFrame.this.dispose();
            }
        }
    } 
}