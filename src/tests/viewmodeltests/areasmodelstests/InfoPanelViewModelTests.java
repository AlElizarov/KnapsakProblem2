package tests.viewmodeltests.areasmodelstests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;
import viewmodel.areasmodels.InfoPanelViewModel;

public class InfoPanelViewModelTests {

	private InfoPanelViewModel infoPanelViewModel;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		infoPanelViewModel = new InfoPanelViewModel(manager);
		manager.setStartState();
	}

	@Test
	public void testDefaultInfoText() {
		String expectInfoText = "<html><h3>Information</h3>"
				+ "<hr>No tasks:<ul>" + "<li>Create new task</li>"
				+ "<li>Read task from database</li>" + "</ul>" + "</html>";

		String actualInfoText = infoPanelViewModel.getInfoText();

		assertEquals(expectInfoText, actualInfoText);
	}

	@Test
	public void setInfoTextAfterCreatingMultiTask() {
		String expectInfoText = "<html><h3>Information</h3>" + "<hr>"
				+ "Multicriteria problem<br>"
				+ " of multidimensional knapsack<br><br>" + "Variables count: "
				+ 4 + "<br>Limitations count: " + 2 + "<br>Criterions count: "
				+ 3 + "<br> Criterions type: " + "maximum<br><br>" + "</html>";

		manager.setTaskData("4", "2", "3");
		manager.createTask();
		String actualInfoText = infoPanelViewModel.getInfoText();

		assertEquals(expectInfoText, actualInfoText);
	}

	@Test
	public void setInfoTextAfterTaskCreatingOneCriterionTask() {
		String expectInfoText = "<html><h3>Information</h3>" + "<hr>"
				+ "One-criterion problem<br>"
				+ " of multidimensional knapsack<br><br>" + "Variables count: "
				+ 4 + "<br>Limitations count: " + 2 + "<br>Criterions count: "
				+ 1 + "<br> Criterions type: " + "maximum<br><br>" + "</html>";

		manager.setTaskData("4", "2", "1");
		manager.createTask();
		String actualInfoText = infoPanelViewModel.getInfoText();

		assertEquals(expectInfoText, actualInfoText);
	}

	@Test
	public void setInfoTextAfterTaskCreatingOneLimitationTask() {
		String expectInfoText = "<html><h3>Information</h3>" + "<hr>"
				+ "Multicriteria problem<br>"
				+ " of onedimensional knapsack<br><br>" + "Variables count: "
				+ 4 + "<br>Limitations count: " + 1 + "<br>Criterions count: "
				+ 3 + "<br> Criterions type: " + "maximum<br><br>" + "</html>";

		manager.setTaskData("4", "1", "3");
		manager.createTask();
		String actualInfoText = infoPanelViewModel.getInfoText();

		assertEquals(expectInfoText, actualInfoText);
	}

	@Test
	public void setInfoTextAfterTaskCreatingOneLimitationAndOneCriterionTask() {
		String expectInfoText = "<html><h3>Information</h3>" + "<hr>"
				+ "One-criterion problem<br>"
				+ " of onedimensional knapsack<br><br>" + "Variables count: "
				+ 4 + "<br>Limitations count: " + 1 + "<br>Criterions count: "
				+ 1 + "<br> Criterions type: " + "maximum<br><br>" + "</html>";

		manager.setTaskData("4", "1", "1");
		manager.createTask();
		String actualInfoText = infoPanelViewModel.getInfoText();

		assertEquals(expectInfoText, actualInfoText);
	}

	@Test
	public void byDefaultTaskNameFieldDisabled() {
		assertFalse(infoPanelViewModel.isTaskNameFieldEnabled());
	}

	@Test
	public void byDefaultEconomMeaningFieldDisabled() {
		assertFalse(infoPanelViewModel.isTaskEconom());
	}

	@Test
	public void whenTaskCreatingTaskNameFieldEnabled() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();

		assertTrue(infoPanelViewModel.isTaskNameFieldEnabled());
	}

	@Test
	public void whenNotEconomTaskEconomFieldDisabled() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();

		assertFalse(infoPanelViewModel.isTaskEconom());
	}

	@Test
	public void whenEconomTaskCreatingEconomMeaningFieldEnabled() {
		manager.setTaskData("4", "2", "3");
		manager.setEconomText("dresses");
		manager.createTask();

		assertTrue(infoPanelViewModel.isTaskNameFieldEnabled());
	}

}