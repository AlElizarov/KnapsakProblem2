package tests.tabletests;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import view.components.tablemanagers.TableManager;
import viewmodel.MockTaskManager;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class SumColumnTests {

	private final int VAR_COUNT = 4;
	private final int LIMITATION_COUNT = 3;
	private final int CRITERION_COUNT = 2;
	private ITableManager tableManager;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = MockTaskManager.getMockInstance();
		tableManager = new TableManager(manager);
		manager.setStartState();
	}

	@Test
	public void whenTaskSolutionCorrectSumColumnGren() {
		Color expectColor = Color.GREEN;

		createNotEconomSolvedTask();
		Color actualColor = tableManager.getColumnColor(VAR_COUNT + 2);

		assertEquals(expectColor, actualColor);
	}

	private void fullTable() {
		for (int row = 0; row < LIMITATION_COUNT + CRITERION_COUNT; row++) {
			for (int col = 0; col < VAR_COUNT; col++) {
				tableManager.setValue(1, row, col);
			}
			if (row >= CRITERION_COUNT) {
				tableManager.setValue(1, row, VAR_COUNT + 1);
			}
		}
	}

	private void createNotEconomSolvedTask() {
		manager.setTaskData("4", "3", "2");
		manager.createTask();
		fullTable();
		manager.solveTask();
	}

}
