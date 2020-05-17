import java.awt.*;

import javax.swing.*;

import mdlaf.*;
import mdlaf.utils.*;
import mdlaf.animation.*;

public class AppFrame extends JFrame{

    private static final long serialVersionUID = 146571341364742862L;

    public AppFrame() {
        super("Material Design UI for Swing by atharva washimkar");

        try {
			UIManager.setLookAndFeel (new MaterialLookAndFeel ());
		}
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace ();
		}

		//this.setMinimumSize (new Dimension (600, 400));

		JButton button = new JButton ("PRESS ME");
		//button.setMaximumSize (new Dimension (200, 200));

		JPanel content = new JPanel ();
		content.add (button);
		this.add (content, BorderLayout.CENTER);

		// on hover, button will change to a light gray
		MaterialUIMovement.add (button, MaterialColors.GRAY_100);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.pack ();
		this.setVisible (true);
    }
}