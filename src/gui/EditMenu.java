package gui;

import java.awt.event.*;

import javax.swing.*;

import gui.frames.*;

public class EditMenu extends JMenu{

    private static final long serialVersionUID = -8815980336375255542L;

    public EditMenu(){
        super("Изменить...");
		
        JMenuItem menuItem = new JMenuItem("Изменить кота");
		menuItem.addActionListener(new EditCatMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Изменить выставку");
		menuItem.addActionListener(new EditExhibitionMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Изменить участие в выставке");
		menuItem.addActionListener(new EditParticipationMenuItemListener());
        add(menuItem);
        
        addSeparator();
        
		menuItem = new JMenuItem("Изменить человека");
		menuItem.addActionListener(new EditPersonMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Изменить город");
		menuItem.addActionListener(new EditCityMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Изменить породу");
		menuItem.addActionListener(new EditBreedMenuItemListener());
		add(menuItem);
		menuItem = new JMenuItem("Изменить пол");
		menuItem.addActionListener(new EditGenderMenuItemListener());
		add(menuItem);
    }
  
	class EditCatMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new EditCatFrame();
		}
    }    
    
	class EditExhibitionMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new EditExhibitionFrame();
		}
	}    
    
	class EditParticipationMenuItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new EditParticipationFrame();
		}
	}    
    
    class EditPersonMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new EditPersonFrame();
        }
    }

    class EditCityMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new EditCityFrame();
        }
    }
    
	class EditBreedMenuItemListener implements ActionListener{
        @Override
		public void actionPerformed(ActionEvent e) {
			new EditBreedFrame();
		}
	}    
    
    class EditGenderMenuItemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
			new EditGenderFrame();
        }
    }    
}