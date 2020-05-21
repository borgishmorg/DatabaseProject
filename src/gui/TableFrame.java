package gui;

import javax.swing.*;

public class TableFrame extends JInternalFrame{
    
    private static final long serialVersionUID = 3863347992736658739L;

    public TableFrame(String title, String column[], String data[][]) {
        super(title, true, true, true, true);
        JTable jt = new JTable(data,column); 
        jt.setEnabled(false);  
        jt.setAutoCreateRowSorter(true);     
		JScrollPane sp=new JScrollPane(jt);    
		add(sp);
    }

    public TableFrame(String column[], String data[][]) {
        this("", column, data);
    }
}