package view.frames;

import javax.swing.JLabel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class AboutProgrammView extends AbstractDecoratedInternalFrame {

	public AboutProgrammView(Desktop desktop) {
		super("About", desktop, 400, 200, 150, 150);
	}

	@Override
	protected void fullPanel() {
		panel.add(new JLabel("<html><h3>Program name: Knapsak problem"
				+ "</h3><br><h3>Author: Alexander Elizarov</h3></html>"));
	}

}
