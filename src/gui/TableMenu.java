package gui;

import java.awt.event.*;

import javax.swing.*;

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
            String column[] = {"Column 1", "Column 2", "Column 3"};
            String data[][] = {
                {"1", "2", "3"},
                {"1", "2", "3"},
                {"1", "2", "3"},
            };
            AppFrame.appFrame.addInternalFrame(new TableFrame(column, data));
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
        }
    }

    class CityMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.log.debug("City menu item clicked");
        }
    }
    
	class BreedMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
            Log.log.debug("Breed menu item clicked");
		}
	}    
    
    class GenderMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Log.log.debug("Gender menu item clicked");
        }
    }    
}