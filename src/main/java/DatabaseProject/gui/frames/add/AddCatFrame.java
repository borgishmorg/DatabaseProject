package DatabaseProject.gui.frames.add;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import DatabaseProject.database.Database;
import DatabaseProject.gui.components.*;
import DatabaseProject.utils.Log;

public class AddCatFrame extends JFrame {

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

    public AddCatFrame() {
        super("Добавить кисоньку");

        nameField = new JTextField(20);
        datePicker = new JDatePicker();

        try {
            breedComboBox = new SQLSelectComboBox("breed", "name");
            personComboBox = new SQLSelectComboBox("person", "name");
            genderComboBox = new SQLSelectComboBox("gender", "gender");
            fatherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 1");
            motherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 2");
            fatherComboBox.setSelectedIndex(-1);
            motherComboBox.setSelectedIndex(-1);
            fatherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 1", 1);
            motherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 2", 1);
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
                AddCatFrame.this.dispose();
            }
        }
    } 
}