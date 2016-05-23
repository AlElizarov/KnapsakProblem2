package viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Vector;

import model.BiDirectSolver;
import model.OneDirectSolver;
import model.Solver;
import model.Task;
import model.TaskParameters;
import view.components.KnapsakGuiSolver;
import db.TaskMapper;

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
	private String h;
	private String v;
	private int iteration;
	private int vertexDeleted;

	private TaskMapper mapper = new TaskMapper();
	private String authorName;
	private Date taskDate;
	private String note;
	private boolean canRewrite;
	private boolean isRead;

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
		} else {
			task = new Task(taskName, Integer.parseInt(varCount),
					Integer.parseInt(limitationCount),
					Integer.parseInt(criterionCount), isMax);
		}
		isTaskCreated = true;
		isRead = false;
		isTaskSolved = false;
		canRewrite = true;
		authorName = null;
		taskDate = null;
		note = null;
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
		if (solver.isEnd()) {
			solver = null;
			iteration = 0;
			return true;
		}
		return false;
	}

	public void solveTask() {
		solver.solve();
		h = solver.getH();
		v = solver.getV();
		iteration++;
		vertexDeleted = solver.vertexDeleted();
		task.setSolution(solver.getCurrentLeaderTop().getSolution());
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

	public String getSvertka() {
		return task.getSvertka().toString();
	}

	public String getH() {
		return h;
	}

	public String getV() {
		return v;
	}

	public String getIteraton() {
		return "" + iteration;
	}

	public int vertexDeleted() {
		return vertexDeleted;
	}

	public Vector<String> getAuthorsNames() {
		return mapper.getAuthorsNames();
	}

	public void save(String authorName, Date sqlTaskDate, boolean canRewrite,
			String note) throws SQLException {
		TaskParameters param = new TaskParameters();
		param.setEconom(isTaskEconom);
		param.setMax(isMax);
		param.setName(taskName);
		param.setAuthorName(authorName);
		this.authorName = authorName;
		param.setSqlDate(sqlTaskDate);
		taskDate = sqlTaskDate;
		param.setCanRewrite(canRewrite);
		this.canRewrite = canRewrite;
		param.setEconomMeaning(economText);
		param.setIsSolved(isTaskSolved);
		param.setNote(note);
		if (!note.equals("")) {
			this.note = note;
		}
		mapper.save(task, param);
		setChanged();
		notifyObservers();
	}

	public void addAuthor(String name, String surname, String futhername)
			throws SQLException {
		mapper.addAuthor(name, surname, futhername);
	}

	public void deleteAuthor(String author) throws SQLException {
		mapper.deleteAuthor(author);
	}

	public String getAuthor() {
		return authorName;
	}

	public String getDate() {
		if (taskDate != null) {
			return taskDate.toString();
		}
		return null;
	}

	public String getNote() {
		return note;
	}

	public void setCanRewrite(boolean canRewrite) {
		this.canRewrite = canRewrite;
	}

	public boolean isCanRewrite() {
		return canRewrite;
	}

	public Vector<String> readTasks() {
		return mapper.readTasks();
	}

	public Vector<String> readTasks(String author) {
		return mapper.readTasks(author);
	}

	public Vector<String> readTasks(String author, java.sql.Date sd,
			java.sql.Date fd) {
		return mapper.readTasks(author, sd, fd);
	}

	public Vector<String> readTasks(java.sql.Date sd, java.sql.Date fd) {
		return mapper.readTasks(sd, fd);
	}

	public void read(String taskData) throws SQLException {
		String[] data = taskData.split(";");
		for (int i = 0; i < data.length; i++) {
			data[i] = data[i].trim();
		}

		try {
			SimpleDateFormat format = new SimpleDateFormat();
			format.applyPattern("yyyy-MM-dd");
			try {
				taskDate = new Date(format.parse(data[2]).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			isTaskSolved = mapper.readIsSolved(data[0], data[1], taskDate);

			isMax = mapper.readIsMax(data[0], data[1], taskDate);
			isTaskEconom = mapper.readIsEconom(data[0], data[1], taskDate);

			if (isTaskEconom) {
				economText = mapper.readEconomText(data[0], data[1], taskDate);
			}
			else{
				economText = "";
			}
			varCount = String.valueOf(mapper.readVarCount(data[0], data[1],
					taskDate));
			criterionCount = String.valueOf(mapper.readCriterionCount(data[0],
					data[1], taskDate));
			limitationCount = String.valueOf(mapper.readLimitationCount(
					data[0], data[1], taskDate));

			if (isTaskEconom && !isTaskSolved) {
				task = new Task(taskName, Integer.parseInt(varCount),
						Integer.parseInt(limitationCount),
						Integer.parseInt(criterionCount), isMax, economText);
				mapper.readEconom(data[0], data[1], taskDate, task);
			}
			if (!isTaskEconom && isTaskSolved) {
				task = new Task(taskName, Integer.parseInt(varCount),
						Integer.parseInt(limitationCount),
						Integer.parseInt(criterionCount), isMax);
				mapper.readSolve(data[0], data[1], taskDate, task);
			}
			if (isTaskEconom && isTaskSolved) {
				task = new Task(taskName, Integer.parseInt(varCount),
						Integer.parseInt(limitationCount),
						Integer.parseInt(criterionCount), isMax, economText);
				mapper.readSolveEconom(data[0], data[1], taskDate, task);
			}
			if (!isTaskEconom && !isTaskSolved) {
				task = new Task(taskName, Integer.parseInt(varCount),
						Integer.parseInt(limitationCount),
						Integer.parseInt(criterionCount), isMax);
				mapper.read(data[0], data[1], taskDate, task);
			}

			isTaskCreated = true;

			canRewrite = mapper.readCanRewrite(data[0], data[1], taskDate);
			authorName = data[1];

			note = mapper.readNote(data[0], data[1], taskDate);
			taskName = data[0];

			isRead = true;

			setChanged();
			notifyObservers();
		} catch (SQLException e) {
			rollback();
			throw new SQLException();
		}
	}

	private void rollback() {
		isTaskCreated = false;
		isTaskSolved = false;
		isTaskEconom = false;
		canRewrite = true;
		authorName = null;
		taskDate = null;
		note = null;
		isRead = false;
	}

	public boolean isRead() {
		return isRead;
	}
	
	public Date getDate2(){
		return taskDate;
	}

}
