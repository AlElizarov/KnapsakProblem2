package tests.viewmodeltests.areasmodelstests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;
import viewmodel.areasmodels.ToolBarViewModel;

public class ToolBarViewModelTests {

	private ToolBarViewModel toolBarViewModel;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		toolBarViewModel = new ToolBarViewModel(manager);
		manager.setStartState();
	}

	@Test
	public void byDefaultGenButtonIsDisabled() {
		assertFalse(toolBarViewModel.isGenButtonEnabled());
	}

	@Test
	public void whenTaskCreatedGenButtonIsEnabled() {
		createTask();

		assertTrue(toolBarViewModel.isGenButtonEnabled());
	}

	private void createTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
	}

}
