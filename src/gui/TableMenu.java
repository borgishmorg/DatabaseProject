package gui;

import java.awt.event.*;

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
            try{
                String column[] = {"cat_id", "Имя", "День рождения", "breed_id", "person_id", "gender_id", "father_id", "mother_id"};
                String dataColumn[] = {"cat_id", "name", "birthday", "breed_id", "person_id", "gender_id", "father_id", "mother_id"};
                String data[][] = Database.database.getDataFromSelect("cat", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Кошки", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
    }    
    
	class ExhibitionMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"exhibition_id", "Название", "city_id", "Дата выставки"};
                String dataColumn[] = {"exhibition_id", "title", "city_id", "exhibition_date"};
                String data[][] = Database.database.getDataFromSelect("exhibition", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Выставки", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
	class ParticipationMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"participation_id", "cat_id", "exhibition_id", "Место"};
                String dataColumn[] = {"participation_id", "cat_id", "exhibition_id", "place"};
                String data[][] = Database.database.getDataFromSelect("participation", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Участие в саревнованиях", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
    class PersonMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"person_id", "Человек"};
                String dataColumn[] = {"person_id", "name"};
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
            try{
                String column[] = {"city_id", "Город"};
                String dataColumn[] = {"city_id", "title"};
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
            try{
                String column[] = {"breed_id", "Порода"};
                String dataColumn[] = {"breed_id", "name"};
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