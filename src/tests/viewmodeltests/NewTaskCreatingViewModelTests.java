package tests.viewmodeltests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import viewmodel.TaskManager;
import viewmodel.framesmodels.NewTaskCreatinngViewModel;

public class NewTaskCreatingViewModelTests {

	private NewTaskCreatinngViewModel newTaskCreatingViewModel;
	private TaskManager taskManager;

	@Before
	public void setUp() {
		taskManager = TaskManager.getInstance();
		newTaskCreatingViewModel = new NewTaskCreatinngViewModel(
				taskManager);
		taskManager.setStartState();
	}

	@Test
	public void byDefaulEconomFieldDisabled() {
		assertFalse(newTaskCreatingViewModel.isEconomFieldEnabled());
	}

	@Test
	public void selectEconomCheckBox() {
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);

		assertTrue(newTaskCreatingViewModel.isEconomFieldEnabled());
	}

	@Test
	public void deselectEconomCheckBox() {
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);
		newTaskCreatingViewModel.setEconomCheckBoxSelected(false);

		assertFalse(newTaskCreatingViewModel.isEconomFieldEnabled());
	}

	@Test
	public void byDefaultEconomCheckBoxNotSelected() {
		assertFalse(newTaskCreatingViewModel.isEconomCheckBoxSelected());
	}

	@Test
	public void selectMax() {
		newTaskCreatingViewModel.setMinSelected();
		newTaskCreatingViewModel.setMaxSelected();
		boolean isMinSelected = newTaskCreatingViewModel.isMinSelected();
		boolean isMaxSelected = newTaskCreatingViewModel.isMaxSelected();

		assertNotSame(isMinSelected, isMaxSelected);
	}

	@Test
	public void byDefaultMaxButtonIsSelected() {
		assertTrue(newTaskCreatingViewModel.isMaxSelected());
	}

	@Test
	public void byDefaultMinButtonIsNotSelected() {
		assertFalse(newTaskCreatingViewModel.isMinSelected());
	}

	@Test
	public void byDefaultOkButtonIsDisabled() {
		assertFalse(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenInputCorrectOkButtonIsEnabled() {
		setCorrectTaskParameters();

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenInputCorrectAfterIncorrectOkButtonIsEnabled() {
		newTaskCreatingViewModel.getTaskNameHandler().setText(".");
		setCorrectTaskParameters();

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenInputIncorrectOkButtonIsDisabled() {
		newTaskCreatingViewModel.getVarCountHandler().setText("11a");

		assertFalse(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenInputIncorrectAfterCorrectOkButtonIsDisabled() {
		newTaskCreatingViewModel.getTaskNameHandler().setText("aa");
		newTaskCreatingViewModel.getVarCountHandler().setText("11");
		newTaskCreatingViewModel.getVarCountHandler().setText("11a");

		assertFalse(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenInputCorrectAfterInCorrectOkButtonIsDisabled() {
		newTaskCreatingViewModel.getTaskNameHandler().setText("aa;");
		setCorrectTaskParameters();

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenEconomCheckButtonTypedOkButtonIsDisabled() {
		setCorrectTaskParameters();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);

		assertFalse(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenEconomMeaningInputOkButtonIsEnabled() {
		setCorrectTaskParameters();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);
		newTaskCreatingViewModel.getEconomTextHandler().setText("dresses");

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenEconomCheckButtonUnTypedOkButtonIsEnabled() {
		setCorrectTaskParameters();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);
		newTaskCreatingViewModel.setEconomCheckBoxSelected(false);

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenIncorrectInputBeforeEconomCheckBoxUnTypedOkButtonIsDisabled() {
		setCorrectTaskParameters();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);
		newTaskCreatingViewModel.getTaskNameHandler().setText(".");
		newTaskCreatingViewModel.setEconomCheckBoxSelected(false);

		assertFalse(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenEconomCheckButtonUnTypedAndEconomTextInCorrectOkButtonIsEnabled() {
		createInCorrectEconomMeaning();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(false);

		assertTrue(newTaskCreatingViewModel.isOkButtonEnabled());
	}

	@Test
	public void whenEconomCheckButtonUntypedEconomTextClear() {
		String expectEconomText = "";

		createInCorrectEconomMeaning();
		taskManager.setTaskData("11", "3", "2");
		newTaskCreatingViewModel.setEconomText("dr.");
		newTaskCreatingViewModel.createTask();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(false);

		String actualEconomText = newTaskCreatingViewModel.getEconomText();
		assertEquals(expectEconomText, actualEconomText);
	}

	private void setCorrectTaskParameters() {
		newTaskCreatingViewModel.getTaskNameHandler().setText("aa");
		newTaskCreatingViewModel.getVarCountHandler().setText("11");
		newTaskCreatingViewModel.getLimitCountHandler().setText("3");
		newTaskCreatingViewModel.getCriterionCountHandler().setText("2");
	}

	private void createInCorrectEconomMeaning() {
		setCorrectTaskParameters();
		newTaskCreatingViewModel.setEconomCheckBoxSelected(true);
		newTaskCreatingViewModel.getEconomTextHandler().setText(".");
	}

}