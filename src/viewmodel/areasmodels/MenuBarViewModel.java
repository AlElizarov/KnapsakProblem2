package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class MenuBarViewModel extends ControlViewModel {

	public MenuBarViewModel(TaskManager manager) {
		super(manager);
	}

	public boolean isTableMenuEnabled() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

}
