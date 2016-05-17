package view.frames;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;

import net.miginfocom.swing.MigLayout;
import view.components.GenTable;
import view.components.GenTableRenderer;
import view.components.MySpinner;
import viewmodel.TaskManager;
import viewmodel.framesmodels.GenerationViewModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
public class GenerationAreaView extends AbstractInteractiveInternalFrame {

	private GenerationViewModel viewModel;
	private JTable genTable;
	private JSpinner spinner;
	private JRadioButton withoutCondition;
	private JRadioButton all;

	public GenerationAreaView(Desktop desktop, TaskManager manager) {
		super("Generation", desktop, 400, 500, 100, 100, manager);
		viewModel = new GenerationViewModel(manager);
	}

	@Override
	protected void createOkAction() {
		viewModel.setUpperBoundCosts(Integer.parseInt(genTable.getModel()
				.getValueAt(1, 2).toString()));
		viewModel.setUpperBoundWeights(Integer.parseInt(genTable.getModel()
				.getValueAt(2, 2).toString()));
		viewModel.setLowerBoundCosts(Integer.parseInt(genTable.getModel()
				.getValueAt(1, 1).toString()));
		viewModel.setLowerBoundWeights(Integer.parseInt(genTable.getModel()
				.getValueAt(2, 1).toString()));
		viewModel.setPartOfUpperBoundForLimits((double) spinner.getValue());
		viewModel.generate();
		GenerationAreaView.this.dispose();
	}

	@Override
	public void openingBind() {
		spinner.setEnabled(!viewModel.isRandomlyButtonEnable());
	}

	@Override
	protected void fullPanel() {
		addGenerationChoiceMode();
		createMargin();

		addTable();
		createMargin();

		addRandomOrConditionGenChoie();
		createMargin();
	}

	private void addRandomOrConditionGenChoie() {
		JRadioButton withCondition = new JRadioButton(
				"<html>Generate<br>with Condition");
		withoutCondition = new JRadioButton("<html>Generate<br>randomly");
		withoutCondition.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewModel.setRandomlyButtonEnable(withoutCondition.isSelected());
				openingBind();
			}
		});
		withCondition.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewModel.setRandomlyButtonEnable(withoutCondition.isSelected());
				openingBind();
			}
		});
		Object[] spinnerData = { 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9 };
		spinner = new MySpinner(spinnerData);
		JPanel typePanel = new ChoicePanelBuilder(withoutCondition,
				withCondition, "Generation").addTypePanel();
		typePanel.setLayout(new MigLayout());
		typePanel.add(new JPanel(), "wrap");
		typePanel.add(new JLabel("coefficient"));
		typePanel.add(spinner);
		panel.add(typePanel);
	}

	private void addTable() {
		panel.add(createGenTable(), "wrap");
	}

	private void addGenerationChoiceMode() {
		JRadioButton onlyEmpty = new JRadioButton(
				"<html>Generate only<br>empty cells");
		onlyEmpty.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewModel.setAllButtonEnable(all.isSelected());
			}
		});
		all = new JRadioButton("<html>Generate<br>all cells");
		all.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				viewModel.setAllButtonEnable(all.isSelected());
			}
		});
		panel.add(new ChoicePanelBuilder(all, onlyEmpty, "Generation type")
				.addTypePanel(), "wrap");
	}

	private void createMargin() {
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
		panel.add(new JPanel(), "wrap");
	}

	private Component createGenTable() {
		genTable = new GenTable();
		genTable.getColumnModel().getColumn(0).setPreferredWidth(150);
		genTable.getColumnModel().getColumn(1).setPreferredWidth(100);
		genTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		genTable.setDefaultRenderer(Object.class, new GenTableRenderer());
		return genTable;
	}
}
