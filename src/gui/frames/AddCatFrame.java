package gui.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import database.Database;
import utils.Log;

public class AddCatFrame extends JFrame {

    private static final long serialVersionUID = 14561L;

    private JTextField nameField;
    private JDatePicker datePicker;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> personComboBox;
    private JComboBox<String> genderComboBox;
    //TODO add father and mother selection
    private JButton applyButton;

    public AddCatFrame() {
        super("Добавить кисоньку");

        nameField = new JTextField(20);

        datePicker = new JDatePicker();

        try {
            String[] label = {"name"};
            String table = "breed" ;

            breedComboBox = new JComboBox<>(Database.database.getColumnFromSelect(table, label, 1));
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        try {
            String[] label = {"name"};
            String table = "person" ;

            personComboBox = new JComboBox<>(Database.database.getColumnFromSelect(table, label, 1));
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        try {
            String[] label = {"gender"};
            String table = "gender" ;

            genderComboBox = new JComboBox<>(Database.database.getColumnFromSelect(table, label, 1));
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
            int person_id = breedComboBox.getSelectedIndex() + 1;
            int gender_id = genderComboBox.getSelectedIndex() + 1;
                        
            try{
                Database.database.executeUpdate(
                    "INSERT INTO cat(name, birthday, breed_id, person_id, gender_id) "+
                    String.format("VALUES(\"%s\", \"%s\", %d, %d, %d);", name, birthday, breed_id, person_id, gender_id)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                AddCatFrame.this.dispose();
            }
        }
    } 
}