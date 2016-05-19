package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class ControlViewModel {
	
	protected TaskManager manager;

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
		if(manager.isTaskSolved() && manager.isTaskEconom()){
			return true;
		}
		return false;
	}

}
