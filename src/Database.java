import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private Connection connect;
    private Statement statement;
    private ResultSet resultSet;

    public Database() throws SQLException, ClassNotFoundException, FileNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Log.log.info("Connecting to database");
        connect = DriverManager.getConnection("jdbc:mysql://localhost/?"
        + "user=root&password=1234567890q&"
        + "serverTimezone=UTC");
        Log.log.info("Connected");
        statement = connect.createStatement();

        createDataBase();
        createTable();
    }

    private void createDataBase() throws SQLException{
        try{
            String sqlCreateDataBase = "CREATE DATABASE cats_club;";
            Log.log.info("Execute: "+sqlCreateDataBase);
            statement.executeUpdate(sqlCreateDataBase);
        }catch(SQLException e){
            if(e.getErrorCode() == 1007){
                Log.log.info(e.toString());
            }else{
                Log.log.error(e.toString());
                throw e;
            }
        }try{
            String sqlUseDataBase = "USE cats_club;";
            Log.log.info("Execute: "+sqlUseDataBase);
            statement.executeUpdate(sqlUseDataBase);
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
                Log.log.info("Execute: "+sql);
                statement.executeUpdate(sql);
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
}