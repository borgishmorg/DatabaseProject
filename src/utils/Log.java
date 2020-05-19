package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static Log log = new Log();

    private BufferedWriter writer;

    private DateTimeFormatter dtf;


    public Log(){
        dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");  

        try{
            writer = new BufferedWriter(new FileWriter("log/log" + dtf.format(LocalDateTime.now()) +".log"));
        }catch(Exception e){
            error(e.getLocalizedMessage());
        }

        info("Logger had been started");
    }

    public void error(String s){
        String msg = dtf.format(LocalDateTime.now()) + ": ERROR : " + s + "\n";

        System.out.print(msg);
        try{
            writer.write(msg);
            writer.flush();
        }catch(Exception e){}
    }

    public void info(String s){
        String msg = dtf.format(LocalDateTime.now()) + ": INFO : " + s + "\n";

        System.out.print(msg);
        try{
            writer.write(msg);
            writer.flush();
        }catch(Exception e){}
    }

    public void debug(String s){
        String msg = dtf.format(LocalDateTime.now()) + ": DEBUG : " + s + "\n";

        System.out.print(msg);
        try{
            writer.write(msg);
            writer.flush();
        }catch(Exception e){}
    }
}