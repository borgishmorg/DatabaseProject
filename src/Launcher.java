import gui.frames.AppFrame;
import database.Database;

class Launcher{
    public static void main(String[] args) {
        Database database = Database.database;
        AppFrame appFrame = AppFrame.appFrame;
        //This removes object not used warnings
        database.toString();
        appFrame.toString();
    }
}
