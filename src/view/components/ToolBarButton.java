package view.components;

import java.awt.Color;

import javax.swing.JButton;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class ToolBarButton extends JButton {

	public ToolBarButton(String title, String tooltip) {
		super(title);
		setToolTipText(tooltip);
		setBackground(new Color(205, 190, 112));
		setFocusable(false);
	}

}
