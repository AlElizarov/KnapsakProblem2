package viewmodel.areasmodels;

import viewmodel.TaskManager;

public class InfoPanelViewModel {

	private TaskManager manager;

	public InfoPanelViewModel(TaskManager manager) {
		this.manager = manager;
	}

	public String getInfoText() {
		if (manager.isTaskCreated()) {
			return "<html><h3>Information</h3>"
					+ "<hr>"
					+ (manager.getCriterionCount() > 1 ? "Multicriteria"
							: "One-criterion")
					+ " problem<br>"
					+ " of "
					+ (manager.getLimitationCount() > 1 ? "multidimensional"
							: "onedimensional") + " knapsack<br><br>"
					+ "Variables count: " + manager.getVariableCount()
					+ "<br>Limitations count: " + manager.getLimitationCount()
					+ "<br>Criterions count: " + manager.getCriterionCount()
					+ "<br> Criterions type: "
					+ (manager.isMax() ? "maximum" : "minimum")
					+ "<br><br></html>";
		}
		return "<html><h3>Information</h3>" + "<hr>No tasks:<ul>"
				+ "<li>Create new task</li>"
				+ "<li>Read task from database</li>" + "</ul>" + "</html>";
	}

	public boolean isTaskNameFieldEnabled() {
		if (manager.isTaskCreated()) {
			return true;
		}
		return false;
	}

	public String getTaskName() {
		return manager.getTaskName();
	}

	public boolean isTaskEconom() {
		return manager.isTaskEconom();
	}

	public String getEconomText() {
		return manager.getEconomText();
	}

}
