package gui.frames;

import javax.swing.*;

import database.Database;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class AddGenderFrame extends JFrame{
    
    private static final long serialVersionUID = -8184339006281031618L;
    
    private JTextField genderText;
    private JButton applyGender;

    public AddGenderFrame(){
        super("Добавить пол");
        
        genderText = new JTextField();
        genderText.setColumns(20);    
                
        applyGender = new JButton("Подтвердить");
        applyGender.addActionListener(new ApplyGenderListener());

        add(genderText, BorderLayout.NORTH);
        add(applyGender, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyGenderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = genderText.getText().substring(0, Math.min(50, genderText.getText().length()));
            try{
                Database.database.executeUpdate(
                    "INSERT INTO gender(gender) "+
                    String.format("VALUES(\"%s\");", name)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                AddGenderFrame.this.dispose();
            }
        }
    }
}