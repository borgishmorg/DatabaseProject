package gui;

import java.awt.event.*;

import javax.swing.*;

import gui.frames.AddBreedFrame;
import gui.frames.AddCatFrame;
import gui.frames.AddCityFrame;
import gui.frames.AddExhibitionFrame;
import gui.frames.AddGenderFrame;
import gui.frames.AddPersonFrame;

public class AddMenu extends JMenu{

    private static final long serialVersionUID = -8815980336375255542L;

    public AddMenu(){
        super("Добавить...");
		
        JMenuItem menuItem = new JMenuItem("Добавить кота");
		menuItem.addActionListener(new AddCatMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Добавить выставку");
		menuItem.addActionListener(new AddExhibitionMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Добавить участие в выставке");
		menuItem.addActionListener(new AddParticipationMenuItemListener());
        add(menuItem);
        
        addSeparator();
        
		menuItem = new JMenuItem("Добавить человека");
		menuItem.addActionListener(new AddPersonMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Добавить город");
		menuItem.addActionListener(new AddCityMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Добавить породу");
		menuItem.addActionListener(new AddBreedMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Добавить пол");
		menuItem.addActionListener(new AddGenderMenuItemListener());
		add(menuItem);
    }
  
	class AddCatMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new AddCatFrame();
		}
    }    
    
	class AddExhibitionMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new AddExhibitionFrame();
		}
	}    
    
	class AddParticipationMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}    
    
    class AddPersonMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new AddPersonFrame();
        }
    }

    class AddCityMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new AddCityFrame();
        }
    }
    
	class AddBreedMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
			new AddBreedFrame();
		}
	}    
    
    class AddGenderMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new AddGenderFrame();
        }
    }    
}