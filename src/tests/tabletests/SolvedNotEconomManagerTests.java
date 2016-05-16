package tests.tabletests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tests.MockTaskManager;
import view.components.tablemanagers.TableManager;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class SolvedNotEconomManagerTests {

	private final int VAR_COUNT = 4;
	private final int LIMITATION_COUNT = 3;
	private final int CRITERION_COUNT = 2;
	private ITableManager tableManager;
	private TaskManager manager;

	@Before
	public void setUp() {
		manager = MockTaskManager.getInstance();
		tableManager = new TableManager(manager);
		manager.setStartState();
	}

	@Test
	public void getColumnCount() {
		int expectColumnCount = VAR_COUNT + 2 + 1;

		createNotEconomSolvedTask();
		int actualColumnCount = tableManager.getColumnCount();

		assertEquals(expectColumnCount, actualColumnCount);
	}

	@Test
	public void getRowCount() {
		int expectRowCount = CRITERION_COUNT + LIMITATION_COUNT + 1;

		createNotEconomSolvedTask();
		int actualRowCount = tableManager.getRowCount();

		assertEquals(expectRowCount, actualRowCount);
	}

	@Test
	public void getFirstRowHeight() {
		int expectFirstRowHeight = 20;

		createNotEconomSolvedTask();
		int actualFirstRowHeight = tableManager.getRowHeight(0);

		assertEquals(expectFirstRowHeight, actualFirstRowHeight);
	}

	@Test
	public void getLastRowHeight() {
		int expectLastRowHeight = 40;

		createNotEconomSolvedTask();
		int actualLastRowHeight = tableManager.getRowHeight(tableManager
				.getRowCount()-1);

		assertEquals(expectLastRowHeight, actualLastRowHeight);
	}
	
	@Test
	public void lastRowFirstColumnEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = 0;
		
		createNotEconomSolvedTask();
		
		assertTrue(tableManager.isCellEditable(row, col));
	}
	
	
	@Test
	public void lastRowlastVarColumnEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT-1;
		
		createNotEconomSolvedTask();
		
		assertTrue(tableManager.isCellEditable(row, col));
	}
	
	@Test
	public void lastRowlastColumnNotEditable() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT+1;
		
		createNotEconomSolvedTask();
		
		assertFalse(tableManager.isCellEditable(row, col));
	}
	
	@Test
	public void lastRowFirstColumnGetValue() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = 0;
		
		createNotEconomSolvedTask();
		Object expectValue = manager.getSolutionVariable(col) ? 1 : 0;
		Object actualValue = tableManager.getValueAt(row, col);
		
		assertEquals(expectValue, actualValue);
	}
	
	@Test
	public void lastRowLastVarColumnGetValue() {
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT-1;
		
		createNotEconomSolvedTask();
		Object expectValue = manager.getSolutionVariable(col) ? 1 : 0;
		Object actualValue = tableManager.getValueAt(row, col);
		
		assertEquals(expectValue, actualValue);
	}
	
	@Test
	public void lastRowLastTypeColumnGetValue() {
		Object expectValue = null;
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT;
		
		createNotEconomSolvedTask();
		Object actualValue = tableManager.getValueAt(row, col);
		
		assertEquals(expectValue, actualValue);
	}
	
	@Test
	public void lastRowLastColumnGetValue() {
		Object expectValue = null;
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = VAR_COUNT+1;
		
		createNotEconomSolvedTask();
		Object actualValue = tableManager.getValueAt(row, col);
		
		assertEquals(expectValue, actualValue);
	}
	
	@Test
	public void setSolutionVariable(){
		Object expectValue = 1;
		int row = LIMITATION_COUNT+CRITERION_COUNT;
		int col = 0;
		
		createNotEconomSolvedTask();
		tableManager.setValue(1, row, col);
		Object actualValue = tableManager.getValueAt(row, col);
		
		assertEquals(expectValue, actualValue);
	}

	private void createNotEconomSolvedTask() {
		manager.setTaskData("4", "3", "2");
		manager.createTask();
		manager.solveTask();
	}

}
