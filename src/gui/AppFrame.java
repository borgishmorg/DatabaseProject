package gui;

import javax.swing.*;

public class AppFrame extends JFrame{

	private static final long serialVersionUID = 146571341364742862L;

	public static final AppFrame appFrame = new AppFrame();

	private JDesktopPane desktopPane;

    private AppFrame() {
        super("Клуб любителей кошечек");

		createMenuBar();
		
		desktopPane = new JDesktopPane();
		this.add(desktopPane);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(new TableMenu());
		menuBar.add(new AddMenu());

		this.setJMenuBar(menuBar);
	}

	public void addInternalFrame(JInternalFrame frame){
		frame.pack();
		frame.setVisible(true);
		desktopPane.add(frame);
	}
}