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
	public void createTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
		
		assertTrue(manager.isTaskCreated());
	}

	@Test
	public void createNotEconomTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
		
		assertFalse(manager.isTaskEconom());
	}

	@Test
	public void createEconomTask() {
		manager.setTaskData("4", "2", "3");
		manager.setEconomText("dresses");
		manager.createTask();
		
		assertTrue(manager.isTaskEconom());
	}

}
