package view.areas;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JToolBar;

import view.components.ToolBarButton;
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

	private ToolBarViewModel toolBarViewModel;

	public ToolBarView(TaskManager manager) {
		toolBarViewModel = new ToolBarViewModel(manager);
		setBackground(new Color(205, 190, 112));
		setFloatable(false);
		add(save);
		add(read);
		addSeparator();
		add(newTask);
		add(solve);
		add(gen);
		add(econom);
	}

	@Override
	public void bind() {
		save.setEnabled(toolBarViewModel.isSaveAndSolutionEnable());
		solve.setEnabled(toolBarViewModel.isSaveAndSolutionEnable());
		gen.setEnabled(toolBarViewModel.isGenButtonEnabled());
		econom.setEnabled(toolBarViewModel.isEconomButtonEnabled());
	}

}
