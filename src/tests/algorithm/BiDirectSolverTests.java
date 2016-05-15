package tests.algorithm;

import static org.junit.Assert.*;
import model.BiDirectSolver;
import model.OneDirectSolver;
import model.Task;

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
		task = new Task("", 5, 1, 1, true);
		for (int row = 0; row < costs.length; row++) {
			for (int col = 0; col < costs[row].length; col++) {
				task.setValue(costs[row][col], row, col);
			}
		}
		for (int row = costs.length; row < weights.length + costs.length; row++) {
			for (int col = 0; col < weights[row].length; col++) {
				task.setValue(weights[row - costs.length][col], row, col);
			}
			task.setValue(limits[row - costs.length], row, weights[row].length);
		}
		solver = new BiDirectSolver(task);
	}

	@Test
	public void byDefaultSolverNotEnd() {
		assertFalse(solver.isEnd());
	}

}
