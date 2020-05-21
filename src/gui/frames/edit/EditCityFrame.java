package gui.frames.edit;

import javax.swing.*;

import database.Database;
import gui.components.*;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EditCityFrame extends JFrame{
    
    private static final long serialVersionUID = -8184339006281031618L;
    
    private JComboBox<String> cityComboBox;
    private JTextField cityText;
    private JButton applyCity;

    public EditCityFrame(){
        super("Изменить город");
        
        try{
            cityComboBox = new SQLSelectComboBox("city", "title");
            cityComboBox.setSelectedIndex(-1);
            cityComboBox.addActionListener(new CityComboBoxListener());
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        cityText = new JTextField();
        cityText.setColumns(20);    
                
        applyCity = new JButton("Подтвердить");
        applyCity.addActionListener(new ApplyCityListener());

        add(cityComboBox, BorderLayout.NORTH);
        add(cityText, BorderLayout.CENTER);
        add(applyCity, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyCityListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String city = cityText.getText().substring(0, Math.min(50, cityText.getText().length()));
            int city_id = cityComboBox.getSelectedIndex() + 1;
            if(city_id == 0)
                return;
            try{
                Database.database.executeUpdate(
                    "UPDATE city "+
                    String.format("SET title=\"%s\" ", city) +
                    "WHERE city_id=" + city_id + " ;"
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditCityFrame.this.dispose();
            }
        }
    }

    class CityComboBoxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int city_id = cityComboBox.getSelectedIndex();
            String city;
            try{
                city = Database.database.getColumnFromSelect("city", "title", 1)[city_id];
            }catch(SQLException exception){
                Log.log.error(exception.toString());
                EditCityFrame.this.dispose();
                return;
            }
            cityText.setText(city);
        }
    }
}