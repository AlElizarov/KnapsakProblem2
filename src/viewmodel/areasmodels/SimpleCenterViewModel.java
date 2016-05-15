package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class SimpleCenterViewModel {

	private boolean isSplitForTableAndSolutionOneTouchExpandeble = true;
	private boolean isSplitForTableAndSolutionContinouslyLayout = true;
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
		if (manager.isTaskSolved()) {
			return 5;
		}
		return 0;
	}

	public boolean isPanelForTableVisible() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

	public boolean isPanelForSolutionVisible() {
		if (manager.isTaskSolved()) {
			return true;
		}
		return false;
	}

	public double getResizeWeight() {
		if (manager.isTaskSolved()) {
			return 0.7;
		}
		return 0;
	}

}
