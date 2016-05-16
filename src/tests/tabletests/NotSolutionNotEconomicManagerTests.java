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
import viewmodel.componentsmodels.tablemodelmanagers.ITableManager;

public class NotSolutionNotEconomicManagerTests {

	private final int VAR_COUNT = 4;
	private final int LIMITATION_COUNT = 2;
	private final int CRITERION_COUNT = 3;
	private TaskManager manager;
	private ITableManager tableManager;

	@Before
	public void setUp() {
		manager = TaskManager.getInstance();
		tableManager = new TableManager(manager);
		manager.setStartState();
	}

	@Test
	public void firstColumnEditable() {
		createNotEconomTask();

		assertTrue(tableManager.isCellEditable(0, 0));
	}

	@Test
	public void foursColumnEditable() {
		int lastTaskColumnIdx = VAR_COUNT - 1;

		createNotEconomTask();

		assertTrue(tableManager.isCellEditable(0, lastTaskColumnIdx));
	}

	@Test
	public void fifthColumnNotEditable() {
		int typeColumnIdx = VAR_COUNT;

		createNotEconomTask();

		assertFalse(tableManager.isCellEditable(0, typeColumnIdx));
	}

	@Test
	public void lastColumnCriterionRowNotEditable() {
		int lastColumnIdx = VAR_COUNT + 1;

		createNotEconomTask();

		assertFalse(tableManager.isCellEditable(0, lastColumnIdx));
	}

	@Test
	public void lastColumnLimitationRowEditable() {
		int lastColumnIdx = VAR_COUNT + 1;
		int firstRowForLimitations = CRITERION_COUNT;

		createNotEconomTask();

		assertTrue(tableManager.isCellEditable(firstRowForLimitations, lastColumnIdx));
	}

	@Test
	public void columnCountOnTable() {
		int expectColumnCount = VAR_COUNT + 2;

		createNotEconomTask();
		int actualColumnCount = tableManager.getColumnCount();

		assertEquals(expectColumnCount, actualColumnCount);
	}

	@Test
	public void rowCountOnTable() {
		int expectRowCount = LIMITATION_COUNT + CRITERION_COUNT;

		createNotEconomTask();
		int actualRowCount = tableManager.getRowCount();

		assertEquals(expectRowCount, actualRowCount);
	}

	@Test
	public void getValue() {
		Object expectValue = null;
		int row = 0;
		int col = 0;

		createNotEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtCostsCellAfterSetValue() {
		Object expectValue = 2;
		int row = 0;
		int col = 0;

		createNotEconomTask();
		tableManager.setValue(2, row, col);
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtWeightsCellAfterSetValue() {
		Object expectValue = 2;
		int row = CRITERION_COUNT;
		int col = 0;

		createNotEconomTask();
		tableManager.setValue(2, row, col);
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtLimitsCellAfterSetValue() {
		Object expectValue = 2;
		int row = CRITERION_COUNT;

		createNotEconomTask();
		tableManager.setValue(2, row, VAR_COUNT + 1);
		Object actualValue = tableManager.getValueAt(row, VAR_COUNT + 1);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnCriterionRow() {
		Object expectValue = "-->";
		int row = 0;
		int col = VAR_COUNT;

		createNotEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnLimitationRowMax() {
		Object expectValue = "<=";
		int row = CRITERION_COUNT;
		int col = VAR_COUNT;

		createNotEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertSame(expectValue, actualValue);
	}

	@Test
	public void getValueAtTypeColumnLimitationRowMin() {
		Object expectValue = ">=";
		int row = CRITERION_COUNT;
		int col = VAR_COUNT;

		manager.setTaskData("4", "2", "3");
		manager.setMax(false);
		manager.createTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertEquals(expectValue, actualValue);
	}

	@Test
	public void getValueAtLastCriterionRow() {
		Object expectValue = null;
		int row = 0;
		int col = VAR_COUNT + 1;

		createNotEconomTask();
		Object actualValue = tableManager.getValueAt(row, col);

		assertEquals(expectValue, actualValue);
	}

	@Test
	public void rewriteValue() {
		int expectValue = 5;

		createNotEconomTask();
		tableManager.setValue(3, 2, 2);
		tableManager.setValue(5, 2, 2);
		Object actualValue = tableManager.getValueAt(2, 2);

		assertEquals(expectValue, actualValue);
	}

	@Test
	public void setTwoValues() {
		int expectValue = 3;

		createNotEconomTask();
		tableManager.setValue(3, 2, 2);
		tableManager.setValue(5, 1, 1);
		Object actualValue = tableManager.getValueAt(2, 2);

		assertEquals(expectValue, actualValue);
	}

	@Test
	public void firstColumnName() {
		String expectName = "X1";

		createNotEconomTask();
		String actualName = tableManager.getColumnName(0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void lastVariableColumnName() {
		String expectName = "X4";

		createNotEconomTask();
		String actualName = tableManager.getColumnName(VAR_COUNT - 1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void typeColumnName() {
		String expectName = "Type";

		createNotEconomTask();
		String actualName = tableManager.getColumnName(VAR_COUNT);

		assertEquals(expectName, actualName);
	}

	@Test
	public void lastColumnName() {
		String expectName = "B";

		createNotEconomTask();
		String actualName = tableManager.getColumnName(VAR_COUNT + 1);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstRowHeight() {
		int expectHeight = 20;

		createNotEconomTask();
		int actualHeight = tableManager.getRowHeight(0);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void secondRowHeight() {
		int expectHeight = 20;

		createNotEconomTask();
		int actualHeight = tableManager.getRowHeight(1);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void lastRowHeight() {
		int expectHeight = 20;

		createNotEconomTask();
		int actualHeight = tableManager.getRowHeight(CRITERION_COUNT
				+ LIMITATION_COUNT-1);

		assertEquals(expectHeight, actualHeight);
	}

	@Test
	public void firstCritRowNameF1() {
		String expectName = "F1";

		createNotEconomTask();
		String actualName = tableManager.getRowName(0);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstLimitRowNameF1() {
		String expectName = "Y1";

		createNotEconomTask();
		String actualName = tableManager.getRowName(CRITERION_COUNT);

		assertEquals(expectName, actualName);
	}

	@Test
	public void firstCritRowColor() {
		Color expectColor = Color.LIGHT_GRAY;

		createNotEconomTask();
		Color actualColor = tableManager.getUnFocusRow(0);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstLimitRowColor() {
		Color expectColor = Color.GRAY;

		createNotEconomTask();
		Color actualColor = tableManager.getUnFocusRow(CRITERION_COUNT);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstCritRowFocusColor() {
		Color expectColor = new Color(230, 230, 250);

		createNotEconomTask();
		Color actualColor = tableManager.getFocusRow(0);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void firstLimitRowFocusColor() {
		Color expectColor = new Color(112, 128, 144);

		createNotEconomTask();
		Color actualColor = tableManager.getFocusRow(CRITERION_COUNT);

		assertEquals(expectColor, actualColor);
	}

	@Test
	public void byDefaultTableIsNotFull() {
		createNotEconomTask();

		assertFalse(tableManager.isFull());
	}

	@Test
	public void whenInputNotAllCellTableIsNotFull() {
		createNotEconomTask();
		tableManager.setValue(1, 0, 0);

		assertFalse(tableManager.isFull());
	}

	@Test
	public void whenInputAllCellTablIsFull() {
		createNotEconomTask();
		for (int row = 0; row < LIMITATION_COUNT + CRITERION_COUNT; row++) {
			for (int col = 0; col < VAR_COUNT; col++) {
				tableManager.setValue(1, row, col);
			}
		}
		for (int row = CRITERION_COUNT; row < LIMITATION_COUNT
				+ CRITERION_COUNT; row++) {
			tableManager.setValue(1, row, VAR_COUNT + 1);
		}

		assertTrue(tableManager.isFull());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void incorrectRowIndex(){
		createNotEconomTask();
		tableManager.isCellEditable(CRITERION_COUNT+LIMITATION_COUNT, 0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void incorrectColumnIndex(){
		createNotEconomTask();
		tableManager.isCellEditable(0, VAR_COUNT+2);
	}

	private void createNotEconomTask() {
		manager.setTaskData("4", "2", "3");
		manager.createTask();
	}
}
