package tests.tabletests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import view.components.tablemanagers.TableManager;
import viewmodel.TaskManager;
import viewmodel.componentsmodels.TooBigNumberException;
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class NotSolutionEconomicManagerTests {

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
	public void firstRowFirstColIsNotEditable() {
		int row = 0;
		int col = 0;

		createEconomTask();

		assertFalse(tableManager.isCellEditable(row, col));
	}

	@Test
	public void firstRowSecondColIsNotEditable() {
		int row = 0;
		int col = 1;

		createEconomTask();

		assertFalse(tableManager.isCellEditable(row, col));
	}

	@Test
	public void firstRowLastColIsNotEditable() {
		int row = 0;
		int col = VAR_COUNT + 3;

		createEconomTask();

		assertFalse(tableManager.isCellEditable(row, col));
	}

	@Test
	public void firstRowTypeColIsNotEditable() {
		int row = 0;
		int col = VAR_COUNT + 3;

		createEconomTask();

		assertFalse(tableManager.isCellEditable(row, col));
	}

	@Test
	public void firstRowDataCellIsEditable() {
		int row = 0;
		int col = 2;

		createEconomTask();

		assertTrue(tableManager.isCellEditable(row, col));
	}

	@Test
	public void firstColSecondRowIsEditable() {
		int row = 1;
		int col = 0;

		createEconomTask();

		assertTrue(tableManager.isCellEditable(row, col));
	}

	@Test
	public void columnCountOnTable() {
		int expectColumnCount = VAR_COUNT + 4;

		createEconomTask();
		int actualColumnCount = tableManager.getColumnCount();

		assertEquals(expectColumnCount, actualColumnCount);
	}

	@Test
	public void rowCountOnTable() {
		int expectRowCount = LIMITATION_COUNT + CRITERION_COUNT + 1;

		createEconomTask();
		int actualRowCount = tableManager.getRowCount();

		assertEquals(expectRowCount, actualRowCount);
	}

	@Test
	public void getValue() {
		Object expectValue = null;
		int row = 0;
		int col = 0;

		createEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtCostsCellAfterSetValue() {
		Object expectValue = 2;
		int row = 1;
		int col = 2;

		createEconomTask();
		tableManager.setValue(2, row, col);
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtWeightsCellAfterSetValue() {
		Object expectValue = 2;
		int row = CRITERION_COUNT + 1;
		int col = 2;

		createEconomTask();
		tableManager.setValue(2, row, col);
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtLimitsCellAfterSetValue() {
		Object expectValue = 2;
		int row = CRITERION_COUNT;
		int col = VAR_COUNT + 1;

		createEconomTask();
		tableManager.setValue(2, row, col);
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnCriterionRow() {
		Object expectValue = "-->";
		int row = 1;
		int col = VAR_COUNT + 2;

		createEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnLimitationRowMax() {
		Object expectValue = "<=";
		int row = CRITERION_COUNT + 1;
		int col = VAR_COUNT + 2;

		createEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnLimitationRowMin() {
		Object expectValue = ">=";
		int row = CRITERION_COUNT + 1;
		int col = VAR_COUNT + 2;

		manager.setTaskData("4", "3", "2");
		manager.setEconomText("dresses");
		manager.setMax(false);
		manager.createTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertEquals(expectValue, actualValue);
	}

	@Test
	public void setVarName() {
		Object expectName = "gaz";

		createEconomTask();
		tableManager.setValue("gaz", 0, 2);
		Object actualName = tableManager.getValueAt(0, 2);

		assertEquals(expectName, actualName);
	}

	@Test
	public void setCritName() {
		Object expectName = "benefit";

		createEconomTask();
		tableManager.setValue("benefit", 1, 0);
		Object actualName = tableManager.getValueAt(1, 0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void setLimitName() {
		Object expectName = "benefit";

		createEconomTask();
		tableManager.setValue("benefit", 3, 0);
		Object actualName = tableManager.getValueAt(3, 0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void setCritUnit() {
		Object expectUnit = "$";

		createEconomTask();
		tableManager.setValue("$", 1, 1);
		Object actualUnit = tableManager.getValueAt(1, 1);

		assertEquals(expectUnit, actualUnit);
	}

	@Test
	public void nameColumn() {
		String expectName = "Name";

		createEconomTask();
		String actualName = tableManager.getColumnName(0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void unitColumn() {
		String expectName = "Unit";

		createEconomTask();
		String actualName = tableManager.getColumnName(1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstVariableColumnName() {
		String expectName = "X1";

		createEconomTask();
		String actualName = tableManager.getColumnName(2);

		assertEquals(expectName, actualName);
	}

	@Test
	public void lastVariableColumnName() {
		String expectName = "X4";

		createEconomTask();
		String actualName = tableManager.getColumnName(
				VAR_COUNT + 1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void typeColumnName() {
		String expectName = "Type";

		createEconomTask();
		String actualName = tableManager.getColumnName(
				VAR_COUNT + 2);

		assertEquals(expectName, actualName);
	}

	@Test
	public void lastColumnName() {
		String expectName = "B";

		createEconomTask();
		String actualName = tableManager.getColumnName(
				VAR_COUNT + 3);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstRowHeight() {
		int expectHeight = 40;

		createEconomTask();
		int actualHeight = tableManager.getRowHeight(0);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void secondRowHeight() {
		int expectHeight = 20;

		createEconomTask();
		int actualHeight = tableManager.getRowHeight(1);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void lastRowHeight() {
		int expectHeight = 20;

		createEconomTask();
		int actualHeight = tableManager.getRowHeight(CRITERION_COUNT
				+ LIMITATION_COUNT);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void firstRowNameEconom() {
		String expectName = "Economic";

		createEconomTask();
		String actualName = tableManager.getRowName(0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstCritRowNameF1() {
		String expectName = "F1";

		createEconomTask();
		String actualName = tableManager.getRowName(1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstLimitRowNameF1() {
		String expectName = "Y1";

		createEconomTask();
		String actualName = tableManager.getRowName(CRITERION_COUNT + 1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstCritRowColor() {
		Color expectColor = Color.LIGHT_GRAY;

		createEconomTask();
		Color actualColor = tableManager.getUnFocusRow(1);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstLimitRowColor() {
		Color expectColor = Color.GRAY;

		createEconomTask();
		Color actualColor = tableManager.getUnFocusRow(CRITERION_COUNT + 1);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstRowColor() {
		Color expectColor = Color.YELLOW;

		createEconomTask();
		Color actualColor = tableManager.getUnFocusRow(0);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstCritRowFocusColor() {
		Color expectColor = new Color(230, 230, 250);

		createEconomTask();
		Color actualColor = tableManager.getFocusRow(1);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstLimitRowFocusColor() {
		Color expectColor = new Color(112, 128, 144);

		createEconomTask();
		Color actualColor = tableManager.getFocusRow(CRITERION_COUNT + 1);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstRowFocusColor() {
		Color expectColor = new Color(255, 215, 0);

		createEconomTask();
		Color actualColor = tableManager.getFocusRow(0);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void byDefaultTableIsNotFull() {
		createEconomTask();

		assertFalse(tableManager.isFull());
	}

	@Test
	public void whenInputNotAllCellTableIsNotFull() {
		createEconomTask();
		tableManager.setValue(1, 1, 2);

		assertFalse(tableManager.isFull());
	}

	@Test
	public void whenInputAllCellTablIsFull() {
		createEconomTask();
		fullTable();

		assertTrue(tableManager.isFull());
	}
	
	private void fullTable() {
		for(int col = 2; col < manager.getVariableCount()+2; col++){
			tableManager.setValue("ss", 0, col);
		}
		
		for (int row = 1; row < LIMITATION_COUNT + CRITERION_COUNT + 1; row++) {
			for (int col = 2; col < VAR_COUNT + 2; col++) {
				tableManager.setValue(1, row, col);
			}
			if(row > CRITERION_COUNT){
				tableManager.setValue(1, row, VAR_COUNT + 3);
			}
			tableManager.setValue("ss", row, 0);
		}
	}
	
	@Test
	public void getValueAtCritNamesCellAfterSolution() {
		Object expectValue = "ee";
		int row = 1;
		int col = 0;

		createEconomTask();
		tableManager.setValue("ee", row, col);
		manager.solveTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test(expected = TooBigNumberException.class)
	public void setTooBigNumber() {
		createEconomTask();
		tableManager.setValue("1222267687622222222222", 1, 2);
	}

	private void createEconomTask() {
		manager.setTaskData("4", "3", "2");
		manager.setEconomText("dresses");
		manager.createTask();
	}

}
