public class App {

    private Database database;

    private AppFrame appFrame;

    public App(){
        try {
            database = new Database();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        appFrame = new AppFrame();
    }
}