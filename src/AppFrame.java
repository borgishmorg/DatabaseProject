import javax.swing.*;

public class AppFrame extends JFrame{

	private static final long serialVersionUID = 146571341364742862L;

    public AppFrame() {
        super("Клуб любителей кошечек");

		createMenuBar();

		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(new AddMenu());

		this.setJMenuBar(menuBar);
	}
}