package viewmodel;

import java.util.List;
import java.util.Observable;

import model.Task;

public class TaskManager extends Observable {

	private static TaskManager instance;

	private Task task;
	private boolean isTaskCreated = false;
	private boolean isTaskEconom = false;

	private String varCount;
	private String taskName;
	private String limitationCount;
	private String criterionCount;
	private String economText;
	private boolean isMax = false;

	private boolean isTaskFull;

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		return instance;
	}

	protected TaskManager() {
	}

	public void setStartState() {
		task = null;
		isTaskCreated = false;
		isTaskEconom = false;
		varCount = null;
		taskName = null;
		limitationCount = null;
		criterionCount = null;
		economText = null;
		isTaskFull = false;
	}

	public boolean isTaskCreated() {
		return isTaskCreated;
	}

	public void createTask() {
		if (isTaskEconom) {
			task = new Task(taskName, Integer.parseInt(varCount),
					Integer.parseInt(limitationCount),
					Integer.parseInt(criterionCount), isMax, economText);
		}
		task = new Task(taskName, Integer.parseInt(varCount),
				Integer.parseInt(limitationCount),
				Integer.parseInt(criterionCount), isMax);
		isTaskCreated = true;
		isTaskFull = false;
		setChanged();
		notifyObservers();
	}

	public void setVarCount(String varCount) {
		this.varCount = varCount;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setLimitationCount(String limitationCount) {
		this.limitationCount = limitationCount;
	}

	public int getLimitationCount() {
		return task.getLimitationCount();
	}

	public void setCriterionCount(String criterionCount) {
		this.criterionCount = criterionCount;
	}

	public int getCriterionCount() {
		return task.getCriterionCount();
	}

	public void setEconomText(String economText) {
		this.economText = economText;
		if (economText.equals("")) {
			isTaskEconom = false;
		} else {
			isTaskEconom = true;
		}
	}

	public String getEconomText() {
		return economText;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

	public boolean isMax() {
		return task.isMax();
	}

	public boolean isTaskEconom() {
		return isTaskEconom;
	}

	public void setTaskData(String var, String lim, String crit) {
		setTaskName("first task");
		setVarCount(var);
		setLimitationCount(lim);
		setCriterionCount(crit);
		setMax(true);
	}

	public Task getTask() {
		return task;
	}

	public int getVariableCount() {
		return task.getVariableCount();
	}

	public List<List<Integer>> getCriterions() {
		return task.getCriterions();
	}

	public List<List<Integer>> getLimitations() {
		return task.getLimitations();
	}

	public List<Integer> getLimits() {
		return task.getLimits();
	}

	public List<String> getVarNames() {
		return task.getVarNames();
	}

	public List<String> getCritNames() {
		return task.getCritNames();
	}

	public List<String> getLimitNames() {
		return task.getLimitNames();
	}

	public List<String> getCritUnits() {
		return task.getCritUnits();
	}

	public List<String> getLimitUnits() {
		return task.getLimitUnits();
	}

	public void addCriterion() {
		if (isTaskEconom) {
			task.addEconomCriterion();
		} else {
			task.addCriterion();
		}
		isTaskFull = false;
		setChanged();
		notifyObservers();
	}

	public void deleteCrit() {
		if (isTaskEconom) {
			task.deleteEconomCriterion();
		} else {
			task.deleteCriterion();
		}
		setChanged();
		notifyObservers();
	}

	public void deleteVariable() {
		if (isTaskEconom) {
			task.deleteEconomVariable();
		} else {
			task.deleteVariable();
		}
		setChanged();
		notifyObservers();
	}

	public void addVariable() {
		if (isTaskEconom) {
			task.addEconomVariable();
		} else {
			task.addVariable();
		}
		isTaskFull = false;
		setChanged();
		notifyObservers();
	}

	public void deleteLimitation() {
		if (isTaskEconom) {
			task.deleteEconomLimitation();
		} else {
			task.deleteLimitation();
		}
		setChanged();
		notifyObservers();
	}

	public void addLimitation() {
		if (isTaskEconom) {
			task.addEconomLimitation();
		} else {
			task.addLimitation();
		}
		isTaskFull = false;
		setChanged();
		notifyObservers();
	}

	public void onFullEvent() {
		isTaskFull = true;
		setChanged();
		notifyObservers();
	}

	public boolean isTaskFull() {
		return isTaskFull;
	}

	public void setTaskFull(boolean isFull) {
		isTaskFull = isFull;
	}

}
