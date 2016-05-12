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
	public void addCriterionToEconomTask() {
		int expectUnitCount = CRITERION_COUNT + 1;

		Task task = createNotEconomTask();
		task.addEconomCriterion();
		int actualUnitCount = task.getCritUnits().size();

		assertEquals(expectUnitCount, actualUnitCount);
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
	public void deleteCriterionfromEconomTask() {
		int expectUnitCount = CRITERION_COUNT - 1;

		Task task = createEconomTask();
		task.deleteEconomCriterion();
		int actualUnitCount = task.getCritUnits().size();

		assertEquals(expectUnitCount, actualUnitCount);
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
	public void addLimitationToEconomTask() {
		int expectLimitUnitCount = LIMITATION_COUNT + 1;

		Task task = createNotEconomTask();
		task.addEconomLimitation();
		int actualLimitUnitCount = task.getLimitUnits().size();

		assertEquals(expectLimitUnitCount, actualLimitUnitCount);
	}
	
	@Test
	public void deleteLimitationFromEconomTask() {
		int expectLimitCount = LIMITATION_COUNT - 1;

		Task task = createNotEconomTask();
		task.deleteEconomLimitation();
		int actualLimitCount = task.getLimitUnits().size();

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

	@Test
	public void addEconomVariable() {
		int expectVarNameCount = VAR_COUNT + 1;

		Task task = createEconomTask();
		task.addEconomVariable();
		int actualVarNameCount = task.getVarNames().size();

		assertEquals(expectVarNameCount, actualVarNameCount);
	}
	
	@Test
	public void deleteEconomVariable() {
		int expectVarNameCount = VAR_COUNT - 1;

		Task task = createEconomTask();
		task.deleteEconomVariable();
		int actualVarNameCount = task.getVarNames().size();

		assertEquals(expectVarNameCount, actualVarNameCount);
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
