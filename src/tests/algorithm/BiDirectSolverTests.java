package tests.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import model.BiDirectSolver;
import model.Task;
import model.Solution;

import org.junit.Before;
import org.junit.Test;

public class BiDirectSolverTests {

	private BiDirectSolver solver;
	private Task task;

	@Before
	public void setUp() {
		Integer[][] costs = { { 8, 3, 6, 5, 4 } };
		Integer[][] weights = { { 3, 4, 7, 2, 3 }, { 4, 6, 3, 1, 5 } };
		Integer[] limits = { 13, 12 };
		task = new Task("", 5, 2, 1, true);
		for (int row = 0; row < costs.length; row++) {
			for (int col = 0; col < costs[row].length; col++) {
				task.setValue(costs[row][col], row, col);
			}
		}
		for (int row = costs.length; row < weights.length + costs.length; row++) {
			for (int col = 0; col < weights[row - costs.length].length; col++) {
				task.setValue(weights[row - costs.length][col], row, col);
			}
			task.setValue(limits[row - costs.length], row, weights[row
					- costs.length].length);
		}
		solver = new BiDirectSolver(task);
	}

	@Test
	public void byDefaultSolverNotEnd() {
		solver.createFirstTop();
		assertFalse(solver.isEnd());
	}
	
	@Test
	public void getLeaderTopRound0() {
		solver.createFirstTop();
		Solution top = solver.getCurrentLeaderTop();

		assertTop(13, 21, top);
	}
	
	private void assertTop(int expectH, int expectV, Solution top) {
		int actualH = top.getH();
		int actualV = top.getV();

		assertEquals(expectH, actualH);
		assertEquals(expectV, actualV);
	}

}
