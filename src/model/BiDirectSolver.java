package model;

import java.util.Collections;

public class BiDirectSolver extends Solver {

	private Task task;
	private OneDirectTask[] oneDirectTasks;

	public BiDirectSolver(Task task) {
		super(task);
		this.task = task;
		oneDirectTasks = new OneDirectTask[task.getLimitationCount()];
		for (int row = 0; row < task.getLimitationCount(); row++) {
			oneDirectTasks[row] = new OneDirectTask(task, row);
		}
	}

	@Override
	public void createFirstTop() {
		for (int row = 0; row < task.getLimitationCount(); row++) {
			setOneDirectTask(oneDirectTasks[row]);
		}
		
	}

	@Override
	protected void createRightTop() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createLeftTop() {
		// TODO Auto-generated method stub

	}

}
