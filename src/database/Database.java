package database;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import utils.*;

public class Database {

    public static final Database database = new Database();

    private Connection connect;
    private Statement statement;

    private Database() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Log.log.info("Connecting to database");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/?"
            + "user=root&password=1234567890q&"
            + "serverTimezone=UTC");
            Log.log.info("Connected");
            statement = connect.createStatement();
            
            createDataBase();
            createTable();
        }catch (Exception e) {
            Log.log.error(e.toString());
            Log.log.error(e.getStackTrace().toString());
            System.exit(1);
        }
    }

    private void createDataBase() throws SQLException{
        try{
            String sqlCreateDataBase = "CREATE DATABASE cats_club;";
            executeUpdate(sqlCreateDataBase);
        }catch(SQLException e){
            if(e.getErrorCode() == 1007){
                Log.log.info(e.toString());
            }else{
                Log.log.error(e.toString());
                throw e;
            }
        }try{
            String sqlUseDataBase = "USE cats_club;";
            executeUpdate(sqlUseDataBase);
        }catch(SQLException e){
            Log.log.error(e.toString());
            throw e;
        }
    }

    private void createTable() throws SQLException, FileNotFoundException{
        Log.log.info("Creating tables");
        String data = "";
        try{
            BufferedReader reader = new BufferedReader(new FileReader("sql/create_tables.sql"));
            for (Object line : reader.lines().toArray())
                data += line.toString() + " ";
            reader.close();
        }catch(FileNotFoundException e){
            Log.log.error(e.toString());
            throw e;
        }catch(IOException e){
            Log.log.error(e.toString());
        }

        String[] sqls = data.split(";");
        
        for(String sql : sqls){
            sql = sql.strip();
            if (sql.length() == 0)
                continue;
            try{
                executeUpdate(sql);
            }catch(SQLException e){
                if(e.getErrorCode() == 1065 || e.getErrorCode() == 1050){
                    Log.log.info(e.toString());
                }else{
                    Log.log.error(e.toString());
                    throw e;
                }
            }
        }
        Log.log.info("Created");
        
    }
    
    public int executeUpdate(String sql) throws SQLException{
        Log.log.info("Execute: "+sql);
        return statement.executeUpdate(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException{
        Log.log.info("Execute: "+sql);
        return statement.executeQuery(sql);
    }

    public String[][] getDataFromSelect(String table, String label) throws SQLException{            
        ResultSet rs = Database.database.executeQuery("SELECT " + label +" FROM "+ table +";");
        return getDataFromResultSet(rs);
    }

    public String[][] getDataFromSelect(String table, String label, String where) throws SQLException{            
        ResultSet rs = Database.database.executeQuery("SELECT " + label + " FROM "+ table + " WHERE " + where +" ;");
        return getDataFromResultSet(rs);
    }

    public String[] getColumnFromSelect(String table, String label, int column) throws SQLException{
        String[][] data = getDataFromSelect(table, label);
        String[] items = new String[data.length];
        
        for (int i = 0; i < items.length; i++)
            items[i] = data[i][column-1];
        
        return items;
    }
        
    public String[] getColumnFromSelect(String table, String label, String where, int column) throws SQLException{
        String[][] data = getDataFromSelect(table, label, where);
        String[] items = new String[data.length];
        
        for (int i = 0; i < items.length; i++)
            items[i] = data[i][column-1];
        
        return items;
    }
    
    public static String[][] getDataFromResultSet(ResultSet rs) throws SQLException{
        String data[][] = {};
        ArrayList<String[]> list = new ArrayList<>();
        
        while(rs.next()){
            String arr[] = {};
            ArrayList<String> arrlist = new ArrayList<>();
            
            for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                arrlist.add(rs.getString(i));
            
            list.add(arrlist.toArray(arr));
        }
        
        return list.toArray(data);
    }
    
    public static String[] getColumnFromResultSet(ResultSet rs, int column) throws SQLException{
        String[][] data = getDataFromResultSet(rs);
        String[] items = new String[data.length];
    
        for (int i = 0; i < items.length; i++)
            items[i] = data[i][column-1];
    
        return items;
    }
}