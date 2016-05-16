package viewmodel;


public class MockTaskManager extends TaskManager {

	protected MockTaskManager() {
	}

	public static MockTaskManager getMockInstance() {
		if (mockInstance == null) {
			mockInstance = new MockTaskManager();
		}
		return mockInstance;
	}

	@Override
	public void solveTask() {
		isTaskSolved = true;
		setChanged();
		notifyObservers();
	}

}
