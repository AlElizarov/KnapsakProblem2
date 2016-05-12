package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class SimpleCenterViewModel {

	private boolean isSplitForTableAndSolutionOneTouchExpandeble = true;
	private boolean isSplitForTableAndSolutionContinouslyLayout = true;
	private int splitForTableAndSolutionDividerSize = 0;
	private TaskManager manager;

	public SimpleCenterViewModel(TaskManager manager) {
		this.manager = manager;
	}

	public boolean isSplitForTableAndSolutionOneTouchExpandable() {
		return isSplitForTableAndSolutionOneTouchExpandeble;
	}

	public boolean isSplitForTableAndSolutionContinuoslyLayout() {
		return isSplitForTableAndSolutionContinouslyLayout;
	}

	public int getSplitForTableAndSolutionDivivderSize() {
		return splitForTableAndSolutionDividerSize;
	}

	public boolean isPanelForTableVisible() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

}
