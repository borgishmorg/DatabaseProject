package gui.frames;

import javax.swing.*;

import database.Database;
import utils.Log;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class EditBreedFrame extends JFrame{
    
    private static final long serialVersionUID = 8514484897952271335L;
    
    private JTextField breedText;
    private JButton applyBreed;

    public EditBreedFrame(){
        super("Добавить породу");
                //TODO
        breedText = new JTextField();
        breedText.setColumns(20);    
                
        applyBreed = new JButton("Подтвердить");
        applyBreed.addActionListener(new ApplyBreedListener());

        add(breedText, BorderLayout.NORTH);
        add(applyBreed, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    class ApplyBreedListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = breedText.getText().substring(0, Math.min(50, breedText.getText().length()));
            try{
                Database.database.executeUpdate(
                    "INSERT INTO breed(name) "+
                    String.format("VALUES(\"%s\");", name)
                );
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                EditBreedFrame.this.dispose();
            }
        }
    }
}