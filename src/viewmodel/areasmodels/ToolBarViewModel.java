package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class ToolBarViewModel {

	private boolean isSaveButtonEnabled = false;
	private boolean isSolutionButtonEnabled = false;
	private boolean isEconomButtonEnabled = false;
	private TaskManager manager;

	public ToolBarViewModel(TaskManager sharedViewModel) {
		this.manager = sharedViewModel;
	}

	public boolean isSaveButtonEnabled() {
		return isSaveButtonEnabled;
	}

	public boolean isSolutionButtonEnabled() {
		return isSolutionButtonEnabled;
	}

	public boolean isGenButtonEnabled() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

	public boolean isEconomButtonEnabled() {
		return isEconomButtonEnabled;
	}

	public boolean isSaveAndSolutionEnable() {
		if (manager.isTaskFull()) {
			return true;
		} else {
			return false;
		}
	}

}
