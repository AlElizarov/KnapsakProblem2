package view.frames;

import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class ChoicePanelBuilder {

	private JRadioButton firstButton;
	private JRadioButton secondButton;
	private String title;

	public ChoicePanelBuilder(JRadioButton firstButton,
			JRadioButton secondButton, String title) {
		this.firstButton = firstButton;
		this.secondButton = secondButton;
		this.title = title;
	}

	private void createButtonGroup() {
		ButtonGroup gr = new ButtonGroup();
		gr.add(firstButton);
		gr.add(secondButton);
	}

	public JPanel addTypePanel() {
		createButtonGroup();
		JPanel typePanel = new JPanel();
		typePanel.setBorder(createTitledBorder());
		firstButton.setSelected(true);
		typePanel.add(firstButton);
		typePanel.add(secondButton);
		return typePanel;
	}

	private Border createTitledBorder() {
		TitledBorder tb = new TitledBorder(new LineBorder(Color.BLACK, 1));
		tb.setTitle(title);
		tb.setTitleJustification(TitledBorder.CENTER);
		return tb;
	}

}
