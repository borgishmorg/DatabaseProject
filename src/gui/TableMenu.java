package gui;

import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;

import database.Database;
import utils.*;

public class TableMenu extends JMenu{

    private static final long serialVersionUID = -8815980336375255542L;

    public TableMenu(){
        super("Таблицы");
        
        addMenuItems();
        addSeparator();
        addRawMenuItems();
    }
    
    public void addMenuItems(){
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

    public void addRawMenuItems(){
        JMenu rawTablesMenu = new JMenu("Raw таблицы");
        JMenuItem menuItem = new JMenuItem("Коты (raw)");
        menuItem.addActionListener(new CatMenuRawItemListener());
        rawTablesMenu.add(menuItem);
        menuItem = new JMenuItem("Выставки (raw)");
        menuItem.addActionListener(new ExhibitionMenuRawItemListener());
        rawTablesMenu.add(menuItem);
        menuItem = new JMenuItem("Участие в выставке (raw)");
        menuItem.addActionListener(new ParticipationMenuRawItemListener());
        rawTablesMenu.add(menuItem);
        rawTablesMenu.addSeparator();
        menuItem = new JMenuItem("Люди (raw)");
        menuItem.addActionListener(new PersonMenuRawItemListener());
        rawTablesMenu.add(menuItem);
        menuItem = new JMenuItem("Города (raw)");
        menuItem.addActionListener(new CityMenuRawItemListener());
        rawTablesMenu.add(menuItem);
		menuItem = new JMenuItem("Порода (raw)");
		menuItem.addActionListener(new BreedMenuRawItemListener());
		rawTablesMenu.add(menuItem);
        menuItem = new JMenuItem("Пол (raw)");
        menuItem.addActionListener(new GenderMenuRawItemListener());
        rawTablesMenu.add(menuItem);
        add(rawTablesMenu);
    }

	class CatMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Имя", "День рождения", "Порода", "Хозяин", "Пол", "Отец", "Мать"};
                
                ResultSet rs = Database.database.executeQuery(
                    "SELECT cat.name AS t1, cat.birthday AS t2, breed.name AS t3, person.name AS t4, gender AS t5, cf.name AS t6, cm.name AS t7 " + 
                    "FROM cat" + 
                    "   INNER JOIN breed ON cat.breed_id = breed.breed_id " + 
                    "   INNER JOIN person ON cat.person_id = person.person_id " +
                    "   INNER JOIN gender ON cat.gender_id = gender.gender_id " + 
                    "   LEFT JOIN cat cf ON cat.father_id = cf.cat_id " +
                    "   LEFT JOIN cat cm ON cat.mother_id = cm.cat_id; "
                );

                String data[][] = Database.getDataFromResultSet(rs);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Кошки", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
    }    

	class CatMenuRawItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"cat_id", "Имя", "День рождения", "breed_id", "person_id", "gender_id", "father_id", "mother_id"};
                String dataColumn[] = {"cat_id", "name", "birthday", "breed_id", "person_id", "gender_id", "father_id", "mother_id"};
                String data[][] = Database.database.getDataFromSelect("cat", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Кошки (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
    }    
    
	class ExhibitionMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Название", "Город", "Дата выставки"};

                ResultSet rs = Database.database.executeQuery(
                    "SELECT exhibition.title AS t1, city.title AS t2, exhibition_date " + 
                    "FROM exhibition" + 
                    "     INNER JOIN city ON exhibition.city_id = city.city_id;"
                );

                String data[][] = Database.getDataFromResultSet(rs);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Выставки", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}     
    
	class ExhibitionMenuRawItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"exhibition_id", "Название", "city_id", "Дата выставки"};
                String dataColumn[] = {"exhibition_id", "title", "city_id", "exhibition_date"};
                String data[][] = Database.database.getDataFromSelect("exhibition", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Выставки (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
	class ParticipationMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Имя", "Выставка", "Место"};

                ResultSet rs = Database.database.executeQuery(
                    "SELECT name, title, place " + 
                    "FROM participation" + 
                    "     INNER JOIN cat ON participation.cat_id = cat.cat_id "+
                    "     INNER JOIN exhibition ON participation.exhibition_id = exhibition.exhibition_id;"
                );

                String data[][] = Database.getDataFromResultSet(rs);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Участие в саревнованиях", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
	class ParticipationMenuRawItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"participation_id", "cat_id", "exhibition_id", "Место"};
                String dataColumn[] = {"participation_id", "cat_id", "exhibition_id", "place"};
                String data[][] = Database.database.getDataFromSelect("participation", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Участие в саревнованиях (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
    class PersonMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Человек"};
                String dataColumn[] = {"name"};
                String data[][] = Database.database.getDataFromSelect("person", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Люди", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }
    
    class PersonMenuRawItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"person_id", "Человек"};
                String dataColumn[] = {"person_id", "name"};
                String data[][] = Database.database.getDataFromSelect("person", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Люди (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }

    class CityMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Город"};
                String dataColumn[] = {"title"};
                String data[][] = Database.database.getDataFromSelect("city", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Города", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }

    class CityMenuRawItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"city_id", "Город"};
                String dataColumn[] = {"city_id", "title"};
                String data[][] = Database.database.getDataFromSelect("city", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Города (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }
    
	class BreedMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Порода"};
                String dataColumn[] = {"name"};
                String data[][] = Database.database.getDataFromSelect("breed", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Породы", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}  
    
	class BreedMenuRawItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"breed_id", "Порода"};
                String dataColumn[] = {"breed_id", "name"};
                String data[][] = Database.database.getDataFromSelect("breed", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Породы (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
		}
	}    
    
    class GenderMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"Пол"};
                String dataColumn[] = {"gender"};
                String data[][] = Database.database.getDataFromSelect("gender", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Пол", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    } 
    
    class GenderMenuRawItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                String column[] = {"gender_id", "Пол"};
                String dataColumn[] = {"gender_id", "gender"};
                String data[][] = Database.database.getDataFromSelect("gender", dataColumn);
                
                AppFrame.appFrame.addInternalFrame(new TableFrame("Таблица: Пол (raw)", column, data));
            }catch(Exception exception){
                Log.log.error(exception.toString());
            }
        }
    }    
}