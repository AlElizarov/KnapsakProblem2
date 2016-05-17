package view.frames;

import handle.FieldAndLabelArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import viewmodel.TaskManager;
import viewmodel.framesmodels.NewTaskCreatinngViewModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class NewTaskCreatingView extends AbstractInteractiveInternalFrame {

	private JRadioButton max;
	private JRadioButton min;
	private JCheckBox isEconom;
	private NewTaskCreatinngViewModel newTaskCreatinngViewModel;

	private FieldAndLabelArea taskNameArea;
	private FieldAndLabelArea variableCountArea;
	private FieldAndLabelArea limitationCountArea;
	private FieldAndLabelArea criterionCountArea;
	private FieldAndLabelArea economMeaningArea;

	public NewTaskCreatingView(Desktop desktop, TaskManager manager) {
		super("New task", desktop, 400, 500, 100, 100, manager);
	}

	@Override
	protected void fullPanel() {
		newTaskCreatinngViewModel = new NewTaskCreatinngViewModel(manager);
		addTextFields();
		max = new JRadioButton("max");
		min = new JRadioButton("min");
		panel.add(new ChoicePanelBuilder(max, min, "Type").addTypePanel(),
				"wrap");
		addEconomMeaning();
	}

	@Override
	protected void createOkAction() {
		backBind();
		newTaskCreatinngViewModel.createTask();
		NewTaskCreatingView.this.dispose();
	}

	private void addEconomMeaning() {
		isEconom = new JCheckBox();
		panel.add(new JLabel("Do you want to add econom meaning?"), "wrap");
		panel.add(isEconom, "wrap");
		isEconom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openingBind();
				economMeaningArea.bind();
				economMeaningArea.getField().requestFocus();
			}
		});
		addEconomField();
	}

	private void addTextFields() {
		addTaskNameField();
		addVarCountField();
		addLimitCountField();
		addCriterionCountField();
	}

	private void addCriterionCountField() {
		criterionCountArea = new FieldAndLabelArea(
				newTaskCreatinngViewModel.getCriterionCountHandler(),
				"Critarion count:");
		panel.add(criterionCountArea, "wrap");
		criterionCountArea.getField().addKeyListener(
				new MyKeyListener(criterionCountArea));
	}

	private void addLimitCountField() {
		limitationCountArea = new FieldAndLabelArea(
				newTaskCreatinngViewModel.getLimitCountHandler(),
				"Limitation count:");
		panel.add(limitationCountArea, "wrap");
		limitationCountArea.getField().addKeyListener(
				new MyKeyListener(limitationCountArea));
	}

	private void addVarCountField() {
		variableCountArea = new FieldAndLabelArea(
				newTaskCreatinngViewModel.getVarCountHandler(),
				"Variable count:");
		panel.add(variableCountArea, "wrap");
		variableCountArea.getField().addKeyListener(
				new MyKeyListener(variableCountArea));
	}

	private void addTaskNameField() {
		taskNameArea = new FieldAndLabelArea(
				newTaskCreatinngViewModel.getTaskNameHandler(), "Task name:");
		panel.add(taskNameArea, "wrap");
		taskNameArea.getField().addKeyListener(new MyKeyListener(taskNameArea));
	}

	private void addEconomField() {
		economMeaningArea = new FieldAndLabelArea(
				newTaskCreatinngViewModel.getEconomTextHandler(),
				"Econom meaning:");
		panel.add(economMeaningArea, "wrap");
		economMeaningArea.getField().addKeyListener(
				new MyKeyListener(economMeaningArea));
	}

	@Override
	public void openingBind() {
		bindEconomParameters();
		bindOkButton();
		max.setSelected(newTaskCreatinngViewModel.isMaxSelected());
		min.setSelected(newTaskCreatinngViewModel.isMinSelected());
		isEconom.setSelected(newTaskCreatinngViewModel
				.isEconomCheckBoxSelected());
	}

	private void bindOkButton() {
		ok.setEnabled(newTaskCreatinngViewModel.isOkButtonEnabled());
	}

	private void bindEconomParameters() {
		newTaskCreatinngViewModel.setEconomCheckBoxSelected(isEconom
				.isSelected());
		economMeaningArea.getField().setEnabled(
				newTaskCreatinngViewModel.isEconomFieldEnabled());
		economMeaningArea.getField().setText(
				newTaskCreatinngViewModel.getEconomText());

	}

	private void backBind() {
		newTaskCreatinngViewModel.setTaskName(taskNameArea.getText());
		newTaskCreatinngViewModel.setVarCount(variableCountArea.getText());
		newTaskCreatinngViewModel.setLimitationCount(limitationCountArea
				.getText());
		newTaskCreatinngViewModel.setCriterionCount(criterionCountArea
				.getText());
		newTaskCreatinngViewModel.setType(max.isSelected());
		newTaskCreatinngViewModel.setEconomText(economMeaningArea.getText());
	}

	private class MyKeyListener extends KeyAdapter {

		private FieldAndLabelArea area;

		public MyKeyListener(FieldAndLabelArea area) {
			this.area = area;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			area.backBind();
			area.bind();
			bindOkButton();
		}

	}

}
