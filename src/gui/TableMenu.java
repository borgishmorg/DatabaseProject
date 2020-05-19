package gui;

import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

import database.Database;
import utils.*;

public class TableMenu extends JMenu{

    private static final long serialVersionUID = -8815980336375255542L;

    public TableMenu(){
        super("Табцицы");
		
        JMenuItem menuItem = new JMenuItem("Коты");
		menuItem.addActionListener(new CatMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Выставки");
		menuItem.addActionListener(new ExhibitionMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Участие в выставке");
		menuItem.addActionListener(new ParticipationMenuItemListener());
        add(menuItem);
        
        addSeparator();
        
		menuItem = new JMenuItem("Люди");
		menuItem.addActionListener(new PersonMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Города");
		menuItem.addActionListener(new CityMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Порода");
		menuItem.addActionListener(new BreedMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Пол");
		menuItem.addActionListener(new GenderMenuItemListener());
		add(menuItem);
    }
  
	class CatMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            Log.log.debug("Cat menu item clicked");
		}
    }    
    
	class ExhibitionMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Log.log.debug("Exhibition menu item clicked");
		}
	}    
    
	class ParticipationMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Log.log.debug("Participation menu item clicked");
		}
	}    
    
    class PersonMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.log.debug("Person menu item clicked");
            try{
                String column[] = {"person_id", "Человек"};
                String dataColumn[] = {"person_id", "person"};
                String data[][] = Database.database.getDataFromSelect("person", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Люди", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }

    class CityMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.log.debug("City menu item clicked");
            try{
                String column[] = {"city_id", "Город"};
                String dataColumn[] = {"city_id", "city"};
                String data[][] = Database.database.getDataFromSelect("city", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Города", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }
    
	class BreedMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
            Log.log.debug("Breed menu item clicked");
            try{
                String column[] = {"breed_id", "Порода"};
                String dataColumn[] = {"breed_id", "breed"};
                String data[][] = Database.database.getDataFromSelect("breed", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Породы", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
    class GenderMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.log.debug("Gender menu item clicked");
            try{
                String column[] = {"gender_id", "Пол"};
                String dataColumn[] = {"gender_id", "gender"};
                String data[][] = Database.database.getDataFromSelect("gender", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Пол", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }    
}