package view.frames;

import javax.swing.JInternalFrame;
import javax.swing.plaf.basic.BasicInternalFrameUI;

// I'm not going to serialize that class
@SuppressWarnings("serial")
public class MainInternalFrameView extends JInternalFrame {

	public MainInternalFrameView() {
		((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
		setResizable(false);
		setBorder(null);
		setVisible(true);
	}

}
