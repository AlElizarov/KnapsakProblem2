package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class ControlViewModel {
	
	protected TaskManager manager;
	private boolean isEconomMenuEnable = false;

	public ControlViewModel(TaskManager manager) {
		this.manager = manager;
	}
	
	public boolean isSaveAndSolutionEnable() {
		if (manager.isTaskFull()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEconomButtonEnabled() {
		return isEconomMenuEnable;
	}

}
