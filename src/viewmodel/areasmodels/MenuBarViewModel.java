package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class MenuBarViewModel {

	private boolean isEconomMenuEnable = false;
	private TaskManager manager;

	public MenuBarViewModel(TaskManager manager) {
		this.manager = manager;
	}

	public boolean isSaveAndSolutionEnable() {
		if (manager.isTaskFull()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTableMenuEnabled() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}
	
	public boolean isEconomButtonEnabled() {
		return isEconomMenuEnable;
	}

}
