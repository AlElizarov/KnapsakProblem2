package tests.tabletests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import view.components.tablemanagers.TableManager;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class SolvedNotEconomManager {

	private final int VAR_COUNT = 4;
	private final int LIMITATION_COUNT = 3;
	private final int CRITERION_COUNT = 2;
	private ITableManager tableManager;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		tableManager = new TableManager(manager);
		manager.setStartState();
	}

	@Test
	public void getColumnCount() {
		int expectColumnCount = VAR_COUNT + 2 + 1;

		createEconomSolvedTask();
		int actualColumnCount = tableManager.getColumnCount();

		assertEquals(expectColumnCount, actualColumnCount);
	}

	@Test
	public void getRowCount() {
		int expectRowCount = CRITERION_COUNT + LIMITATION_COUNT + 1;

		createEconomSolvedTask();
		int actualRowCount = tableManager.getRowCount();

		assertEquals(expectRowCount, actualRowCount);
	}

	@Test
	public void getFirstRowHeight() {
		int expectFirstRowHeight = 20;

		createEconomSolvedTask();
		int actualFirstRowHeight = tableManager.getRowHeight(0);

		assertEquals(expectFirstRowHeight, actualFirstRowHeight);
	}

	@Test
	public void getLastRowHeight() {
		int expectLastRowHeight = 40;

		createEconomSolvedTask();
		int actualLastRowHeight = tableManager.getRowHeight(tableManager
				.getRowCount()-1);

		assertEquals(expectLastRowHeight, actualLastRowHeight);
	}
	
	@Test
	public void lastRowFirstColumnEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = 0;
		
		createEconomSolvedTask();
		
		assertTrue(tableManager.isCellEditable(row, col));
	}
	
	
	@Test
	public void lastRowlastVarColumnEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT-1;
		
		createEconomSolvedTask();
		
		assertTrue(tableManager.isCellEditable(row, col));
	}
	
	@Test
	public void lastRowlastColumnNotEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT+1;
		
		createEconomSolvedTask();
		
		assertFalse(tableManager.isCellEditable(row, col));
	}

	private void createEconomSolvedTask() {
		manager.setTaskData("4", "3", "2");
		manager.createTask();
		manager.solveTask();
	}

}
