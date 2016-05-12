package view.areas;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import viewmodel.TaskManager;
import viewmodel.areasmodels.InfoPanelViewModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class InfoPanelView extends JPanel implements BindableArea{

	private JLabel infoLabel = new JLabel();
	private JTextField taskNameFiled = new JTextField(10);
	private JTextField economMeaningField = new JTextField(10);
	private InfoPanelViewModel infoPanelViewModel;

	public InfoPanelView(TaskManager manager) {
		infoPanelViewModel = new InfoPanelViewModel(manager);
		setLayout(new MigLayout());
		add(infoLabel, "wrap");
		JLabel taskNameNote = new JLabel("Task name:");
		add(taskNameNote, "wrap");
		JLabel economMeaningNote = new JLabel("Econom meaning:");
		add(taskNameFiled, "wrap");
		add(economMeaningNote, "wrap");
		add(economMeaningField);
		setBackground(Color.WHITE);
	}

	@Override
	public void bind() {
		infoLabel.setText(infoPanelViewModel.getInfoText());
		taskNameFiled.setEnabled(infoPanelViewModel.isTaskNameFieldEnabled());
		taskNameFiled.setText(infoPanelViewModel.getTaskName());
		economMeaningField.setEnabled(infoPanelViewModel.isTaskEconom());
		economMeaningField.setText(infoPanelViewModel.getEconomText());
	}

}
