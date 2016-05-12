package view.areas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.components.KnapsakProblemTable;
import view.components.RowNumberTable;
import view.components.tablemanagers.TableManager;
import view.components.tablemanagers.renderers.TableRenderer;
import viewmodel.TaskManager;
import viewmodel.areasmodels.SimpleCenterViewModel;
import viewmodel.componentsmodels.KnapsakProblemTableModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class SimpleMode extends CenterViewMode {

	private JPanel panelForTable;
	private SimpleCenterViewModel centerViewModel;
	private JTable table;
	private TableManager tableManager;

	public SimpleMode(TaskManager manager) {
		super(manager);
		centerViewModel = new SimpleCenterViewModel(manager);
		panelForTable = new JPanel(new BorderLayout());
		panelForTable.add(createTable());
		setTopComponent(panelForTable);
	}

	private Component createTable() {
		tableManager = new TableManager(manager);
		table = new KnapsakProblemTable(tableManager);
		table.setDefaultRenderer(Object.class, new TableRenderer());
		RowNumberTable rowTable = new RowNumberTable(table, tableManager);
		JScrollPane scrollForTable = new JScrollPane(table);
		scrollForTable.setRowHeaderView(rowTable);
		scrollForTable
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollForTable
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollForTable.setBorder(null);
		scrollForTable.getViewport().setBackground(Color.white);
		return scrollForTable;
	}

	@Override
	public void bind() {
		table.setModel(new KnapsakProblemTableModel(tableManager));
		panelForTable.setVisible(centerViewModel.isPanelForTableVisible());
		setContinuousLayout(centerViewModel
				.isSplitForTableAndSolutionContinuoslyLayout());
		setOneTouchExpandable(centerViewModel
				.isSplitForTableAndSolutionOneTouchExpandable());
		setDividerSize(centerViewModel
				.getSplitForTableAndSolutionDivivderSize());
	}

}
