package view.frames;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import view.components.GenTable;
import view.components.GenTableRenderer;
import viewmodel.TaskManager;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class GenerationAreaView extends AbstractInteractiveInternalFrame {

	public GenerationAreaView(Desktop desktop, TaskManager manager) {
		super("Generation", desktop, 400, 500, 100, 100, manager);
	}

	@Override
	protected void createOkAction() {
		manager.genTaskData();
	}

	@Override
	public void openingBind() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void fullPanel() {
		JRadioButton onlyEmpty = new JRadioButton(
				"<html>Generate only<br>empty cells");
		JRadioButton all = new JRadioButton("<html>Generate<br>all cells");
		panel.add(new ChoicePanelBuilder(onlyEmpty, all, "Generation type")
				.addTypePanel(), "wrap");
		createMargin();
		panel.add(createGenTable(), "wrap");
		createMargin();
		JRadioButton withCondition = new JRadioButton(
				"<html>Generate<br>with Condition");
		JRadioButton withoutCondition = new JRadioButton(
				"<html>Generate<br>randomly");
		panel.add(new ChoicePanelBuilderWithSpinner(withoutCondition,
				withCondition, "Generation").addTypePanel());
		createMargin();
	}

	private void createMargin() {
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
	}

	private Component createGenTable() {
		GenTable genTable = new GenTable();
		genTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		genTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		genTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		genTable.setDefaultRenderer(Object.class, new GenTableRenderer());
		return genTable;
	}
}
