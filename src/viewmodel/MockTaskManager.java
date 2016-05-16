package tests;

import viewmodel.TaskManager;

public class MockTaskManager extends TaskManager {

	protected MockTaskManager() {
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new MockTaskManager();
		}
		return instance;
	}

	@Override
	public void solveTask() {
		isTaskSolved = true;
		setChanged();
		notifyObservers();
	}

}
