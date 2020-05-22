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

public class AddExhibitionFrame extends JFrame {

    private static final long serialVersionUID = -2075839617065501118L;

    private JTextField titleField;
    private JComboBox<String> cityComboBox;
    private JDatePicker datePicker;
    private JButton applyButton;

    public AddExhibitionFrame() {
        super("Добавить выставку");

        titleField = new JTextField(20);

        try {
            cityComboBox = new SQLSelectComboBox("city", "title");
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        datePicker = new JDatePicker();

        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(titleField);
        add(cityComboBox);
        add(datePicker);
        add(applyButton);

        pack();
        setVisible(true);
    }

    class ApplyButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText().substring(0, Math.min(50, titleField.getText().length()));
            int city_id = cityComboBox.getSelectedIndex() + 1;
            String exhibition_date;
            try{
                GregorianCalendar calendar = (GregorianCalendar) datePicker.getModel().getValue();
                exhibition_date = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            }catch(Exception exception){
                exhibition_date = "NULL";
                Log.log.error(exception.toString());
            }
                        
            try{
                Database.database.executeUpdate(
                    "INSERT INTO exhibition(title, city_id, exhibition_date) "+
                    String.format("VALUES(\"%s\", \"%d\", \"%s\");", title, city_id, exhibition_date)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                AddExhibitionFrame.this.dispose();
            }
        }
    } 
}