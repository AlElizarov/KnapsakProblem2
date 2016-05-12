package tests.tabletests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.components.tablemanagers.TableManager;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.KnapsakProblemTableModel;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class KnapsakTableModelTests {

	private KnapsakProblemTableModel model;
	private final String VAR_COUNT = "4";
	private final String LIMITATION_COUNT = "2";
	private final String CRITERION_COUNT = "3";
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		ITableManager tableManager = new TableManager(manager);
		model = new KnapsakProblemTableModel(tableManager);
		manager.setStartState();
	}

	@Test
	public void getValueAtCostsCellAfterSet() {
		Object expectValue = 2;

		createTask();
		model.setValueAt("2", 0, 0);
		Object actualValue = model.getValueAt(0, 0);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void whenInputEmptyStringNothingHappened() {
		Object expected = null;

		createTask();
		model.setValueAt("", 0, 0);
		Object actual = model.getValueAt(0, 0);
		
		assertEquals(expected, actual);
	}

	@Test
	public void columnCount() {
		int expectCount = 6;

		createTask();
		int actualCount = model.getColumnCount();

		assertEquals(expectCount, actualCount);
	}

	private void createTask() {
		manager.setTaskData(VAR_COUNT, LIMITATION_COUNT, CRITERION_COUNT);
		manager.createTask();
	}
}
