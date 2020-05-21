package gui;

import java.sql.SQLException;

import javax.swing.JComboBox;

import database.Database;

public class SQLSelectComboBox extends JComboBox<String>{
    
    private static final long serialVersionUID = -3657147488511995219L;

    public SQLSelectComboBox(String table, String label) throws SQLException{
        super(Database.database.getColumnFromSelect(table, label, 1));
    }

    public SQLSelectComboBox(String table, String label, String where) throws SQLException{
        super(Database.database.getColumnFromSelect(table, label, where, 1));
    }
}