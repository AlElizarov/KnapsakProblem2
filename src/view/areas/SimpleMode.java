package view.areas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;

import net.miginfocom.swing.MigLayout;
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
	private JPanel panelForSolution;
	private JLabel labelForSverka;
	private JTextPane textPaneForSolution;

	public SimpleMode(TaskManager manager) {
		super(manager);
		centerViewModel = new SimpleCenterViewModel(manager);
		panelForTable = new JPanel(new BorderLayout());
		panelForTable.add(createTable());
		panelForSolution = new JPanel(new BorderLayout());
		createSolutionPanel();
		setTopComponent(panelForTable);
		setBottomComponent(panelForSolution);
	}

	private void createSolutionPanel() {
		JPanel panelForButtonAndSvertka = new JPanel(new BorderLayout());

		JPanel buttonPanel = new JPanel(new MigLayout());
		JButton start = new JButton("start");
		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.execute();
			}
		});
		JButton stop = new JButton("stop");
		stop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.cancel();
			}
		});
		buttonPanel.add(start, "sg 1");
		buttonPanel.add(stop, "sg 1");
		buttonPanel.setBackground(new Color(205, 190, 112));
		panelForButtonAndSvertka.add(buttonPanel, BorderLayout.NORTH);

		labelForSverka = new JLabel("sver");
		panelForButtonAndSvertka.add(labelForSverka, BorderLayout.CENTER);

		panelForSolution.add(panelForButtonAndSvertka, BorderLayout.NORTH);

		textPaneForSolution = new JTextPane();
		textPaneForSolution.setContentType("text/html");
		panelForSolution.add(textPaneForSolution, BorderLayout.CENTER);
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
		panelForSolution
				.setVisible(centerViewModel.isPanelForSolutionVisible());
		setContinuousLayout(centerViewModel
				.isSplitForTableAndSolutionContinuoslyLayout());
		setOneTouchExpandable(centerViewModel
				.isSplitForTableAndSolutionOneTouchExpandable());
		setDividerSize(centerViewModel
				.getSplitForTableAndSolutionDivivderSize());
		setResizeWeight(centerViewModel.getResizeWeight());
		setDividerLocation(centerViewModel.getResizeWeight());
	}

}
