package gui.menu;

import javax.swing.*;

import gui.frames.search.SearchCatFrame;

import java.awt.event.*;

public class SearchMenu extends JMenu{

    private static final long serialVersionUID = -6861630768572603436L;
    
    public SearchMenu(){
        super("Найти...");

        JMenuItem menuItem = new JMenuItem("Найти кота");
        menuItem.addActionListener(new SearchCatMenuitemListener());
        add(menuItem);
    }

    class SearchCatMenuitemListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new SearchCatFrame();
        }
    }
}