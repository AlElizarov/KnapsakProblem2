package tests.viewmodeltests.areasmodelstests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;
import viewmodel.areasmodels.MenuBarViewModel;

public class MenuBarViewModelTests {

	private MenuBarViewModel menuBarViewModel;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		menuBarViewModel = new MenuBarViewModel(manager);
		manager.setStartState();
	}

	@Test
	public void byDefaultTableMenuIsDisabled() {
		assertFalse(menuBarViewModel.isTableMenuEnabled());
	}

	@Test
	public void whenTaskCreatedTableMenuisEnabled() {
		createTask();

		assertTrue(menuBarViewModel.isTableMenuEnabled());
	}

	private void createTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
	}

}
