package tests.tasktests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import model.Task;

import org.junit.Test;

public class TaskTests {

	private final int VAR_COUNT = 4;
	private final int LIMITATION_COUNT = 2;
	private final int CRITERION_COUNT = 3;

	@Test
	public void getName() {
		String expectedName = "first Task";

		Task task = createNotEconomTask();
		String actualTaskName = task.getName();

		assertEquals(expectedName, actualTaskName);
	}

	@Test
	public void isMax() {
		Task task = createNotEconomTask();

		assertTrue(task.isMax());
	}

	@Test
	public void economMeaning() {
		String expectEconomText = "dresses";

		Task task = createEconomTask();
		String actualEconomText = task.getEconomMeaning();

		assertEquals(expectEconomText, actualEconomText);
	}

	@Test
	public void varCount() {
		int expectVarCount = VAR_COUNT;

		Task task = createNotEconomTask();
		int actualVarCount = task.getVariableCount();

		assertEquals(expectVarCount, actualVarCount);
	}

	@Test
	public void limitCount() {
		int expectVarCount = LIMITATION_COUNT;

		Task task = createNotEconomTask();
		int actualVarCount = task.getLimitationCount();

		assertEquals(expectVarCount, actualVarCount);
	}

	@Test
	public void criterionCount() {
		int expectVarCount = CRITERION_COUNT;

		Task task = createNotEconomTask();
		int actualVarCount = task.getCriterionCount();

		assertEquals(expectVarCount, actualVarCount);
	}

	@Test
	public void addCriterionToNotEconomTask() {
		int expectCritCount = CRITERION_COUNT + 1;

		Task task = createNotEconomTask();
		task.addCriterion();
		int actualCritCount = task.getCriterionCount();

		assertEquals(expectCritCount, actualCritCount);
	}

	@Test
	public void deleteCriterionfromNotEconomTask() {
		int expectCritCount = CRITERION_COUNT - 1;

		Task task = createNotEconomTask();
		task.deleteCriterion();
		int actualCritCount = task.getCriterionCount();

		assertEquals(expectCritCount, actualCritCount);
	}

	@Test
	public void deleteLastCriterionfromNotEconomTask() {
		int expectCritCount = 1;

		Task task = createNotEconomTask();
		task.deleteCriterion();
		task.deleteCriterion();
		task.deleteCriterion();
		int actualCritCount = task.getCriterionCount();

		assertEquals(expectCritCount, actualCritCount);
	}

	@Test
	public void addLimitationToNotEconomTask() {
		int expectLimitCount = LIMITATION_COUNT + 1;

		Task task = createNotEconomTask();
		task.addLimitation();
		int actualLimitCount = task.getLimitationCount();

		assertEquals(expectLimitCount, actualLimitCount);
	}

	@Test
	public void deleteLimitationFromNotEconomTask() {
		int expectLimitCount = LIMITATION_COUNT - 1;

		Task task = createNotEconomTask();
		task.deleteLimitation();
		int actualLimitCount = task.getLimitationCount();

		assertEquals(expectLimitCount, actualLimitCount);
	}

	@Test
	public void addNotEconomVariable() {
		int expectVarCount = VAR_COUNT + 1;

		Task task = createNotEconomTask();
		task.addVariable();
		int actualVarCount = task.getVariableCount();

		assertEquals(expectVarCount, actualVarCount);
	}

	@Test
	public void deleteNotEconomVariable() {
		int expectVarCount = VAR_COUNT - 1;

		Task task = createNotEconomTask();
		task.deleteVariable();
		int actualVarCount = task.getVariableCount();

		assertEquals(expectVarCount, actualVarCount);
	}
	
	public void generation(){
		Task task = createNotEconomTask();
		task.genData(1, 1, 100, 100, 0.5);
		
		assertGenData(task);
	}

	private void assertGenData(Task task) {
		for(int row = 0; row < task.getCriterionCount() + task.getLimitationCount(); row++){
			for(int col = 0; col < task.getVariableCount(); col++){
				assertTrue((int)task.getValue(row, col) >= 1);
				assertTrue((int)task.getValue(row, col) <= 100);
			}
		}
	}

	private Task createNotEconomTask() {
		Task task = new Task("first Task", VAR_COUNT, LIMITATION_COUNT,
				CRITERION_COUNT, true);
		return task;
	}

	private Task createEconomTask() {
		Task task = new Task("first Task", VAR_COUNT, LIMITATION_COUNT,
				CRITERION_COUNT, true, "dresses");
		return task;
	}

}
