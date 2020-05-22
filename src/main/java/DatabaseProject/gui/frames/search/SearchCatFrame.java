package DatabaseProject.gui.frames.search;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

import DatabaseProject.database.Database;
import DatabaseProject.gui.components.SQLSelectComboBox;
import DatabaseProject.gui.frames.AppFrame;
import DatabaseProject.gui.frames.TableFrame;
import DatabaseProject.utils.Log;

public class SearchCatFrame extends JFrame {

    private static final long serialVersionUID = 1581484581468168L;
    
    
    private JComboBox<String> genderComboBox;
    private JComboBox<String> breedComboBox;
    private JComboBox<String> personComboBox;
    private JComboBox<String> exhibitionComboBox;
    private JTextField        placeTextField;
    private JComboBox<String> fatherComboBox;
    private JComboBox<String> motherComboBox;
    private JButton applyButton;
    
    private String[] fatherIds;
    private String[] motherIds;

    public SearchCatFrame(){
        super("Найти кисоньку");

        try{
            genderComboBox = new SQLSelectComboBox("gender", "gender");
            breedComboBox = new SQLSelectComboBox("breed", "name");
            personComboBox = new SQLSelectComboBox("person", "name");
            exhibitionComboBox = new SQLSelectComboBox("exhibition", "title");
            placeTextField = new JTextField(20);
            fatherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 1");
            motherComboBox = new SQLSelectComboBox("cat", "name", "gender_id = 2");
            applyButton = new JButton("Найти");

            genderComboBox.setSelectedIndex(-1);
            breedComboBox.setSelectedIndex(-1);
            personComboBox.setSelectedIndex(-1);
            exhibitionComboBox.setSelectedIndex(-1);
            fatherComboBox.setSelectedIndex(-1);
            motherComboBox.setSelectedIndex(-1);

            fatherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 1", 1);
            motherIds = Database.database.getColumnFromSelect("cat", "cat_id", "gender_id = 2", 1);
        }catch(SQLException exception){
            Log.log.error(exception.toString());
            dispose();
            return;
        }

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        addComboBox("Пол:", genderComboBox);
        addComboBox("Порода:", breedComboBox);
        addComboBox("Человек:", personComboBox);
        addComboBox("Выставка:", exhibitionComboBox);     
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel jLabel = new JLabel("Место:");
        jLabel.setPreferredSize(new Dimension(70, -1));
        panel.add(jLabel);
        panel.add(placeTextField);
        add(panel);   
        addComboBox("Отец:", fatherComboBox);        
        addComboBox("Мать:", motherComboBox);
        add(applyButton);
        applyButton.addActionListener(new SearchButtonListener());

        pack();
        setVisible(true);
    }

    private void addComboBox(String label, JComboBox<String> comboBox){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel jLabel = new JLabel(label);
        jLabel.setPreferredSize(new Dimension(70, -1));
        panel.add(jLabel);
        panel.add(comboBox);
        JButton button = new JButton("Сбросить");
        button.addActionListener(new DropButtonListener(comboBox));
        panel.add(button);
        add(panel);
    }

    private class DropButtonListener implements ActionListener{

        private JComboBox<String> target;

        public DropButtonListener(JComboBox<String> target){
            this.target = target;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.setSelectedIndex(-1);
        }
    }

    private class SearchButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<String> whereList = new ArrayList<>();
            if(genderComboBox.getSelectedIndex() != -1)
                whereList.add("gender_id="+(genderComboBox.getSelectedIndex()+1));
            if(breedComboBox.getSelectedIndex() != -1)
                whereList.add("breed_id="+(breedComboBox.getSelectedIndex()+1));
            if(personComboBox.getSelectedIndex() != -1)
                whereList.add("person_id="+(personComboBox.getSelectedIndex()+1));
            if(exhibitionComboBox.getSelectedIndex() != -1)
                whereList.add("exhibition_id="+(exhibitionComboBox.getSelectedIndex()+1));
            if(fatherComboBox.getSelectedIndex() != -1)
                whereList.add("father_id="+(fatherIds[fatherComboBox.getSelectedIndex()]));
            if(motherComboBox.getSelectedIndex() != -1)
                whereList.add("mother_id="+(motherIds[motherComboBox.getSelectedIndex()]));
            if(!placeTextField.getText().equals(""))
                whereList.add("place="+placeTextField.getText());
            
            String sql = "";
            sql += "SELECT cat.cat_id ";
            sql += "FROM cat ";
            sql += "LEFT JOIN participation ON cat.cat_id = participation.cat_id ";

            if(whereList.size() != 0){
                sql += "WHERE ";
                sql += String.join(" AND ", whereList);
            }

            sql += " GROUP BY cat.cat_id ";
            
            String megaSQL = "";
            megaSQL += "SELECT cat.name, birthday, person.name ";
            megaSQL += "FROM cat LEFT JOIN person ON cat.person_id = person.person_id ";
            megaSQL += "WHERE cat_id IN (" + sql + " ); ";

            try{

                String[][] data = Database.getDataFromResultSet(Database.database.executeQuery(megaSQL));
                String[] column = {"Кот", "Дата рождения", " Человек"};
                
                JInternalFrame frame = new TableFrame("Результаты поиска", column, data);
                AppFrame.appFrame.addInternalFrame(frame);
            }catch(SQLException exception){
                Log.log.error(exception.toString());
            }finally{
                SearchCatFrame.this.dispose();
            }
        }
    }
}