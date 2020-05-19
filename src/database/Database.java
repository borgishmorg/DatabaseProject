package database;

import java.io.*;
import java.sql.*;

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
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader("sql/create_tables.sql"));
        }catch(FileNotFoundException e){
            Log.log.error(e.toString());
            throw e;
        }

        String data = "";
        for (Object line : reader.lines().toArray()) {
            data += line.toString() + "\n";
        } 

        String[] sqls = data.replace('\n', ' ').strip().split(";");
        
        for(String sql : sqls){
            sql = sql.strip();
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
        try{
            reader.close();
        }catch(IOException e){
            Log.log.error(e.toString());
        }
    }
    
    public int executeUpdate(String sql) throws SQLException{
        Log.log.info("Execute: "+sql);
        return statement.executeUpdate(sql);
    }

    public ResultSet executeQuery(String sql) throws SQLException{
        Log.log.info("Execute: "+sql);
        return statement.executeQuery(sql);
    }
}