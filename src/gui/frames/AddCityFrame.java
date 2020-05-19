package gui.frames;

import javax.swing.*;

import database.Database;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddCityFrame extends JFrame{

    private static final long serialVersionUID = 2574410537120380131L;
    
    private JTextField cityText;
    private JButton applyCity;

    public AddCityFrame(){
        super("Добавить город");
        
        cityText = new JTextField();
        cityText.setColumns(20);    
                
        applyCity = new JButton("Подтвердить");
        applyCity.addActionListener(new ApplyCityListener());

        add(cityText, BorderLayout.NORTH);
        add(applyCity, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyCityListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = cityText.getText().substring(0, Math.min(50, cityText.getText().length()));
            try{
                Database.database.executeUpdate(
                    "INSERT INTO city(title) "+
                    String.format("VALUES(\"%s\");", name)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                AddCityFrame.this.dispose();
            }
        }
    }
}