package viewmodel;

import java.util.Observable;

import view.components.KnapsakGuiSolver;
import model.BiDirectSolver;
import model.OneDirectSolver;
import model.Solver;
import model.Task;

public class TaskManager extends Observable {

	protected static TaskManager instance;
	protected static MockTaskManager mockInstance;

	private Task task;
	private boolean isTaskCreated = false;
	private boolean isTaskEconom = false;

	private String varCount;
	private String taskName;
	private String limitationCount;
	private String criterionCount;
	private String economText;
	private boolean isMax = false;

	protected boolean isTaskSolved;
	private Solver solver;
	private KnapsakGuiSolver<Object, Object> guiSolver;

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
		isTaskSolved = false;
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
		isTaskSolved = false;
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

	public void addCriterion() {
		if (isTaskEconom) {
			task.addEconomCriterion();
		} else {
			task.addCriterion();
		}
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
		setChanged();
		notifyObservers();
	}

	public void onFullEvent() {
		setChanged();
		notifyObservers();
	}

	public boolean isTaskFull() {
		if (!isTaskCreated) {
			return false;
		}
		if (isTaskEconom) {
			return isFull() && isEconomFull();
		}
		return isFull();
	}

	public void setValue(int intValue, int row, int col) {
		task.setValue(intValue, row, col);
	}

	public void setEconomValue(Object value, int row, int col) {
		task.setEconomValue(value, row, col);
	}

	public Object getValue(int row, int col) {
		return task.getValue(row, col);
	}

	public Object getEconomValue(int row, int col) {
		return task.getEconomValue(row, col);
	}

	private boolean isFull() {
		return task.isFull();
	}

	private boolean isEconomFull() {
		return task.isEconomFull();
	}

	public boolean isTaskSolved() {
		return isTaskSolved;
	}

	public boolean isEnd() {
		if (solver == null) {
			solver = createSolver();
		}
		if(solver.isEnd()){
			solver = null;
			return true;
		}
		return false;
	}

	public void solveTask() {
		solver.solve();
		task.setSolution(solver.getCurrentLeaderTop().getSolution());
		setTaskSolved();
	}

	public void setTaskSolved() {
		isTaskSolved = true;
		setChanged();
		notifyObservers();
	}

	private Solver createSolver() {
		Solver solver = null;
		if (task.getLimitationCount() > 1) {
			solver = new BiDirectSolver(task);
		} else {
			solver = new OneDirectSolver(task);
		}
		return solver;
	}

	public boolean getSolutionVariable(int col) {
		return task.getSolutionVariable(col);
	}

	public void setSolutionVariable(boolean value, int col) {
		task.setSolutionVariable(value, col);
	}

	public long getSum(int row) {
		return task.getSum(row);
	}

	public boolean allSumsOk() {
		return task.allSumsOk();
	}

	public boolean sumOk(int row) {
		return task.sumOk(row);
	}

	public void genTaskData(int lowerBoundCosts, int lowerBoundWeights,
			int upperBoundCosts, int upperBoundWeights, double coeff) {
		task.genData(lowerBoundCosts, lowerBoundWeights, upperBoundCosts,
				upperBoundWeights, coeff);
		setChanged();
		notifyObservers();
	}

	public void genEmptyData(int lowerBoundCosts, int lowerBoundWeights,
			int upperBoundCosts, int upperBoundWeights, double coeff) {
		task.genEmptyData(lowerBoundCosts, lowerBoundWeights, upperBoundCosts,
				upperBoundWeights, coeff);
		setChanged();
		notifyObservers();
	}

	private void createNewGuiSolver() {
		guiSolver = new KnapsakGuiSolver<>(this);
	}

	public void execute() {
		createNewGuiSolver();
		guiSolver.execute();
	}

	public void cancel() {
		guiSolver.cancel(true);
	}

}
