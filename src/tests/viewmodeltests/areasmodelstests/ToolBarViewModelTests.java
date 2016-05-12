package tests.viewmodeltests.areasmodelstests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import view.components.tablemanagers.TableManager;
import viewmodel.TaskManager;
import viewmodel.areasmodels.ToolBarViewModel;
import viewmodel.componentsmodels.KnapsakProblemTableModel;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class ToolBarViewModelTests {

	private ToolBarViewModel toolBarViewModel;
	private TaskManager manager;
	private ITableManager tableManager;
	private KnapsakProblemTableModel tableModel;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		toolBarViewModel = new ToolBarViewModel(manager);
		tableManager = new TableManager(manager);
		tableModel = new KnapsakProblemTableModel(tableManager);
		manager.setStartState();
	}

	@Test
	public void byDefaultSaveAndSolutionDisabled() {
		assertFalse(isSaveAndSolutionEnable());
	}

	@Test
	public void byDefaultGenButtonIsDisabled() {
		assertFalse(toolBarViewModel.isGenButtonEnabled());
	}

	@Test
	public void byDefaultEconomButtonIsDisabled() {
		assertFalse(toolBarViewModel.isEconomButtonEnabled());
	}

	@Test
	public void whenTaskCreatedGenButtonIsEnabled() {
		manager.setTaskData("4", "3", "2");
		manager.createTask();

		assertTrue(toolBarViewModel.isGenButtonEnabled());
	}

	@Test
	public void whenTableFullSaveAndSolutionEnable() {
		createTask();
		fullTable();

		assertTrue(isSaveAndSolutionEnable());
	}

	@Test
	public void whenTaskCreatedAfterTableFullSaveAndSolutionDisable() {
		createTask();
		fullTable();
		createTask();

		assertFalse(isSaveAndSolutionEnable());
	}

	@Test
	public void whenLimitationAddAfterTableFullSaveAndSolutionDisable() {
		createTask();
		fullTable();
		manager.addLimitation();

		assertFalse(isSaveAndSolutionEnable());
	}

	@Test
	public void whenCriterionAddAfterTableFullSaveAndSolutionDisable() {
		createTask();
		fullTable();
		manager.addCriterion();

		assertFalse(isSaveAndSolutionEnable());
	}

	@Test
	public void whenVariableAddAfterTableFullSaveAndSolutionDisable() {
		createTask();
		fullTable();
		manager.addVariable();

		assertFalse(isSaveAndSolutionEnable());
	}

	@Test
	public void whenLimitationDeleteAfterLimitationAddSaveAndSolutionEnable() {
		createTask();
		fullTable();
		manager.addLimitation();
		manager.deleteLimitation();

		assertTrue(isSaveAndSolutionEnable());
	}

	private void fullTable() {
		for (int row = 0; row < manager.getLimitationCount()
				+ manager.getCriterionCount(); row++) {
			for (int col = 0; col < manager.getVariableCount(); col++) {
				tableModel.setValueAt(1, row, col);
			}
		}
		for (int row = manager.getCriterionCount(); row < manager
				.getLimitationCount() + manager.getCriterionCount(); row++) {
			tableModel.setValueAt(1, row, manager.getVariableCount() + 1);
		}
	}

	private boolean isSaveAndSolutionEnable() {
		return toolBarViewModel.isSaveAndSolutionEnable();
	}

	private void createTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
	}

}
