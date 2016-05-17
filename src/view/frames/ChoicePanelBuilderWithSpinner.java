package view.frames;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.miginfocom.swing.MigLayout;
import view.components.MySpinner;

public class ChoicePanelBuilderWithSpinner {

	private ChoicePanelBuilder builder;

	public ChoicePanelBuilderWithSpinner(JRadioButton firstButton,
			JRadioButton secondButton, String title) {
		builder = new ChoicePanelBuilder(firstButton, secondButton, title);
	}

	public JPanel addTypePanel() {
		JPanel typePanel = builder.addTypePanel();
		typePanel.setLayout(new MigLayout());
		Object[] spinnerData = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9 };
		typePanel.add(new JPanel(), "wrap");
		typePanel.add(new JLabel("coefficient"));
		typePanel.add(new MySpinner(spinnerData));
		return typePanel;
	}
}
