package tests.viewmodeltests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;

public class TaskManagerTests {

	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		manager.setStartState();
	}

	@Test
	public void byDefaultTaskIsNotCreated() {
		assertFalse(manager.isTaskCreated());
	}

	@Test
	public void testTaskCreated() {
		createTask();

		assertTrue(manager.isTaskCreated());
	}

	@Test
	public void testNotEconomTask() {
		createTask();

		assertFalse(manager.isTaskEconom());
	}

	private void createTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
	}

	@Test
	public void testEconomTask() {
		manager.setTaskData("4", "2", "3");
		manager.setEconomText("dresses");
		manager.createTask();

		assertTrue(manager.isTaskEconom());
	}

	@Test
	public void testTaskGenerate() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
		manager.genTaskData();

		assertTrue(manager.isTaskFull());
	}

}
