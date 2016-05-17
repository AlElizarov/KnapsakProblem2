package view.areas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToolBar;

import view.components.ToolBarButton;
import view.frames.AbstractInteractiveInternalFrame;
import view.frames.Desktop;
import view.frames.NewTaskCreatingView;
import viewmodel.TaskManager;
import viewmodel.areasmodels.ToolBarViewModel;

//I'm not going to serialize that class
@SuppressWarnings("serial")
class ToolBarView extends JToolBar implements BindableArea {

	private JButton save = new ToolBarButton("save", "save task into database");
	private JButton read = new ToolBarButton("read", "read task from database");
	private JButton newTask = new ToolBarButton("new", "create new task");
	private JButton solve = new ToolBarButton("solution", "solve task");
	private JButton gen = new ToolBarButton("generation",
			"generate data for task");
	private JButton econom = new ToolBarButton("economics",
			"show econom intepretation of task");
	private Desktop desktop;
	private TaskManager manager;

	private ToolBarViewModel toolBarViewModel;

	public ToolBarView(Desktop desktop, TaskManager manager) {
		this.desktop = desktop;
		this.manager = manager;
		toolBarViewModel = new ToolBarViewModel(manager);
		setBackground(new Color(205, 190, 112));
		setFloatable(false);
		add(save);
		add(read);
		addSeparator();
		addNewTaskButton();
		addSolveButton();
		addGenButton();
		add(econom);
	}

	private void addGenButton() {
		add(gen);
		gen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				manager.genTaskData();
			}
		});
	}

	private void addSolveButton() {
		add(solve);
		solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				manager.solveTask();
			}
		});
	}

	private void addNewTaskButton() {
		add(newTask);
		newTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				desktop.setLayout(null);
				AbstractInteractiveInternalFrame iframe = new NewTaskCreatingView(
						desktop, manager);
				desktop.addIFrame(iframe);
				iframe.openingBind();
			}
		});
	}

	@Override
	public void bind() {
		save.setEnabled(toolBarViewModel.isSaveAndSolutionEnable());
		solve.setEnabled(toolBarViewModel.isSaveAndSolutionEnable());
		gen.setEnabled(toolBarViewModel.isGenButtonEnabled());
		econom.setEnabled(toolBarViewModel.isEconomButtonEnabled());
	}

}
