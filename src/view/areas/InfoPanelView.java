package view.areas;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import viewmodel.TaskManager;
import viewmodel.areasmodels.InfoPanelViewModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class InfoPanelView extends JPanel implements BindableArea {

	private JLabel infoLabel = new JLabel();
	private JTextField taskNameField = new JTextField(10);
	private JTextField economMeaningField = new JTextField(10);
	private InfoPanelViewModel infoPanelViewModel;

	public InfoPanelView(TaskManager manager) {
		infoPanelViewModel = new InfoPanelViewModel(manager);
		setLayout(new MigLayout());
		add(infoLabel, "wrap");
		JLabel taskNameNote = new JLabel("Task name:");
		add(taskNameNote, "wrap");
		JLabel economMeaningNote = new JLabel("Econom meaning:");
		add(taskNameField, "wrap");
		taskNameField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (!infoPanelViewModel.setTaskName(taskNameField.getText())) {
					taskNameField.setText(infoPanelViewModel.getTaskName());
				}
			}

		});
		add(economMeaningNote, "wrap");
		add(economMeaningField);
		economMeaningField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (!infoPanelViewModel.setEconomMeaning(economMeaningField.getText())) {
					economMeaningField.setText(infoPanelViewModel.getEconomText());
				}
			}

		});
		setBackground(Color.WHITE);
	}

	@Override
	public void bind() {
		infoLabel.setText(infoPanelViewModel.getInfoText());
		taskNameField.setEnabled(infoPanelViewModel.isTaskNameFieldEnabled());
		taskNameField.setText(infoPanelViewModel.getTaskName());
		economMeaningField.setEnabled(infoPanelViewModel.isTaskEconom());
		economMeaningField.setText(infoPanelViewModel.getEconomText());
	}

}
