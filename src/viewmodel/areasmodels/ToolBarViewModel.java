package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class ToolBarViewModel extends ControlViewModel{

	public ToolBarViewModel(TaskManager manager) {
		super(manager);
	}

	public boolean isGenButtonEnabled() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

}
