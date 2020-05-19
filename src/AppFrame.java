import javax.swing.*;

import java.awt.*;


public class AppFrame extends JFrame{

	private static final long serialVersionUID = 146571341364742862L;

	public static final AppFrame appFrame = new AppFrame();

	private JDesktopPane desktopPane;

    private AppFrame() {
        super("Клуб любителей кошечек");

		createMenuBar();
		//setLayout(new FlowLayout());
		
		//JDesktopPane desktop = new JDesktopPane();
		/*         
		JTable jt=new JTable(data,column);    
		jt.setBounds(30,40,200,300);   
		jt.setEnabled(false);       
		JScrollPane sp=new JScrollPane(jt);    
		add(sp);   */       

		/*JInternalFrame in = new JInternalFrame("InternalFrame", true, false, true, true);
        in.setVisible(true);  
		desktop.add(in);

		JInternalFrame in2 = new JInternalFrame("InternalFrame", true, false, true, true); 
        in2.setTitle("InternalFrame2"); 
        in2.setVisible(true);  
		desktop.add(in2);
		
		this.add(desktop, BorderLayot.);
		*/

		/*JInternalFrame intFrame = new JInternalFrame("Our Frame", true, true, true, true);
		desktopPane.add(intFrame);
		intFrame.pack();
		intFrame.setVisible(true);*/
		
		desktopPane = new JDesktopPane();
		this.add(desktopPane);
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private void createMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(new AddMenu());

		this.setJMenuBar(menuBar);
	}

	public void addInternalFrame(JInternalFrame frame){
		frame.pack();
		frame.setVisible(true);
		desktopPane.add(frame);
	}
}