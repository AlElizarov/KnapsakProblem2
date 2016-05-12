package view.components;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class FrameClosingListener extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent event) {
		createClosingDialog(event);
	}

	private void createClosingDialog(WindowEvent event) {
		Object[] options = { "Yes", "No!" };
		int n = JOptionPane.showOptionDialog(event.getWindow(),
				"Close window?", "Confirmation", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (n == 0) {
			event.getWindow().setVisible(false);
			System.exit(0);
		}
	}

}
