package gui.frames.edit;

import java.awt.event.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.*;

import org.jdatepicker.JDatePicker;

import database.Database;
import gui.SQLSelectComboBox;
import utils.Log;

public class EditExhibitionFrame extends JFrame {

    private static final long serialVersionUID = -2075839617065501118L;

    private JComboBox<String> exhibitionComboBox;
    private JTextField titleField;
    private JComboBox<String> cityComboBox;
    private JDatePicker datePicker;
    private JButton applyButton;

    public EditExhibitionFrame() {
        super("Изменить выставку");
        
        try{
            exhibitionComboBox = new SQLSelectComboBox("exhibition", "title");
            exhibitionComboBox.setSelectedIndex(-1);
            exhibitionComboBox.addActionListener(new ExhibitionComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        titleField = new JTextField(20);

        try {
            cityComboBox = new JComboBox<>(Database.database.getColumnFromSelect("city", "title", 1));
        } catch (SQLException exception) {
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        datePicker = new JDatePicker();

        applyButton = new JButton("Подтвердить");
        applyButton.addActionListener(new ApplyButtonActionListener());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(exhibitionComboBox);
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
            int exhibition_id = exhibitionComboBox.getSelectedIndex() + 1;
            if(exhibition_id == 0)
                return;
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
                    "UPDATE exhibition SET "+
                    String.format("title=\"%s\", city_id=%d, exhibition_date=\"%s\" ", title, city_id, exhibition_date) +
                    String.format("WHERE exhibition_id=%d ;", exhibition_id)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditExhibitionFrame.this.dispose();
            }
        }
    } 

    class ExhibitionComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int exhibition_id = exhibitionComboBox.getSelectedIndex();
            String title;
            int city_id;
            Date exhibition_date;
            try{
                title = Database.database.getColumnFromSelect("exhibition", "title", 1)[exhibition_id];
                city_id = Integer.parseInt(
                    Database.database.getColumnFromSelect("exhibition", "city_id", 1)[exhibition_id]
                    );
                exhibition_date = new SimpleDateFormat("yyyy-MM-dd").parse(
                    Database.database.getColumnFromSelect("exhibition", "exhibition_date", 1)[exhibition_id]
                    );
            }catch(Exception exception){
                Log.log.error(exception.toString());
                EditExhibitionFrame.this.dispose();
                return;
            }
            titleField.setText(title);
            cityComboBox.setSelectedIndex(city_id-1);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(exhibition_date);

            datePicker.getModel().setYear(cal.get(Calendar.YEAR));
            datePicker.getModel().setMonth(cal.get(Calendar.MONTH));
            datePicker.getModel().setDay(cal.get(Calendar.DAY_OF_MONTH));
            datePicker.getModel().setSelected(true);
        }
    }
}