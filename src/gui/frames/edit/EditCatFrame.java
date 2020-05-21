package gui.frames.edit;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import database.Database;
import gui.SQLSelectComboBox;
import utils.Log;

public class EditCatFrame extends JFrame {

    private static final long serialVersionUID = 14561L;

    private JComboBox<String> catComboBox;
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
        super("Изменить кисоньку");
        
        nameField = new JTextField(20);
        datePicker = new JDatePicker();

        try {
            catComboBox = new JComboBox<>(Database.database.getColumnFromSelect("cat", "name", 1));
            catComboBox.addActionListener(new CatComboBoxListener());
            breedComboBox = new SQLSelectComboBox("breed", "name");
            personComboBox = new SQLSelectComboBox("person", "name");
            genderComboBox = new SQLSelectComboBox("gender", "gender");
            fatherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 1");
            motherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 2");
            catComboBox.setSelectedIndex(-1);
            fatherComboBox.setSelectedIndex(-1);
            motherComboBox.setSelectedIndex(-1);
            fatherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 1 ", 1);
            motherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 2 ", 1);
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(catComboBox);
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
            int cat_id = catComboBox.getSelectedIndex() + 1;
            if(cat_id == 0)
                return;
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
                    "UPDATE cat SET "+
                    String.format("name=\"%s\", birthday=\"%s\", breed_id=%d, person_id=%d, gender_id=%d, father_id=%d, mother_id=%d ", name, birthday, breed_id, person_id, gender_id, father_id, mother_id)+
                    String.format("WHERE cat_id=%d ;", cat_id)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditCatFrame.this.dispose();
            }
        }
    } 

    class CatComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int cat_id = catComboBox.getSelectedIndex();
            String name;
            Date birthday;
            int breed_id;
            int person_id;
            int gender_id;
            int father_id;
            int mother_id;

            try{
                name = Database.database.getColumnFromSelect("cat", "name", 1)[cat_id];
                birthday = new SimpleDateFormat("yyyy-MM-dd").parse(Database.database.getColumnFromSelect("cat", "birthday", 1)[cat_id]);
                breed_id = Integer.parseInt(Database.database.getColumnFromSelect("cat", "breed_id", 1)[cat_id]);
                person_id = Integer.parseInt(Database.database.getColumnFromSelect("cat", "person_id", 1)[cat_id]);
                gender_id = Integer.parseInt(Database.database.getColumnFromSelect("cat", "gender_id", 1)[cat_id]);
                father_id = Integer.parseInt(Database.database.getColumnFromSelect("cat", "father_id", 1)[cat_id]);
                mother_id =  Integer.parseInt(Database.database.getColumnFromSelect("cat", "mother_id", 1)[cat_id]);
            }catch(Exception exception){
                Log.log.error(exception.toString());
                EditCatFrame.this.dispose();
                return;
            }

            nameField.setText(name);
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthday);
            datePicker.getModel().setYear(cal.get(Calendar.YEAR));
            datePicker.getModel().setMonth(cal.get(Calendar.MONTH));
            datePicker.getModel().setDay(cal.get(Calendar.DAY_OF_MONTH));
            datePicker.getModel().setSelected(true);
            breedComboBox.setSelectedIndex(breed_id-1);
            personComboBox.setSelectedIndex(person_id-1);
            genderComboBox.setSelectedIndex(gender_id-1);
            fatherComboBox.setSelectedIndex(Arrays.asList(fatherIds).indexOf(father_id+""));
            motherComboBox.setSelectedIndex(Arrays.asList(motherIds).indexOf(mother_id+""));
        } 
    }
}