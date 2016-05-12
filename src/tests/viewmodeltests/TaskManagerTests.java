package tests.viewmodeltests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;

public class TaskManagerTests {

	private TaskManager sharedViewModel;

	@Before
	public void setUp() {
		sharedViewModel = TaskManager.getInstance();
		sharedViewModel.setStartState();
	}

	@Test
	public void byDefaultTaskIsNotCreated() {
		assertFalse(sharedViewModel.isTaskCreated());
	}
	
	@Test
	public void createTask() {
		sharedViewModel.setTaskData("4", "2", "3");
		sharedViewModel.createTask();
		
		assertTrue(sharedViewModel.isTaskCreated());
	}

	@Test
	public void createNotEconomTask() {
		sharedViewModel.setTaskData("4", "2", "3");
		sharedViewModel.createTask();
		
		assertFalse(sharedViewModel.isTaskEconom());
	}

	@Test
	public void createEconomTask() {
		sharedViewModel.setTaskData("4", "2", "3");
		sharedViewModel.setEconomText("dresses");
		sharedViewModel.createTask();
		
		assertTrue(sharedViewModel.isTaskEconom());
	}

}
