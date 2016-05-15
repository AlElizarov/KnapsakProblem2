package tests.algorithm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import model.OneDirectSolver;
import model.Task;
import model.Top;

import org.junit.Before;
import org.junit.Test;

public class OneDirectTests {

	private OneDirectSolver solver;
	private Task task;

	@Before
	public void setUp() {
		Integer[] costs = { 5, 6, 8, 4, 7 };
		Integer[] weights = { 3, 2, 3, 5, 3 };
		int limit = 10;
		task = new Task("", 5, 1, 1, true);
		for (int col = 0; col < costs.length; col++) {
			task.setValue(costs[col], 0, col);
		}
		for (int col = 0; col < weights.length; col++) {
			task.setValue(weights[col], 1, col);
		}
		task.setValue(limit, 1, 5);
		solver = new OneDirectSolver(task);
	}

	@Test
	public void byDefaultSolverNotEnd() {
		assertFalse(solver.isEnd());
	}

	@Test
	public void taskWithoutSolutions() {
		task.setValue(0, 1, 5);
		solver = new OneDirectSolver(task);
		
		assertFalse(solver.hasSolution());
	}
	
	@Test
	public void taskWithAbsoluteSolutions() {
		task.setValue(100, 1, 5);
		solver = new OneDirectSolver(task);
		solve();
		Top top = solver.getCurrentLeaderTop();
		
		assertTop(30, 30, top);
	}
	
	@Test
	public void taskWithOneVariableSolutions() {
		task.setValue(2, 1, 5);
		solver = new OneDirectSolver(task);
		solve();
		Top top = solver.getCurrentLeaderTop();
		
		assertTop(6, 6, top);
	}

	@Test
	public void getLeaderTopRound0() {
		Top top = solver.getCurrentLeaderTop();

		assertTop(21, 24, top);
	}

	@Test
	public void getLeaderTopRound1() {
		solver.solve();
		Top top = solver.getCurrentLeaderTop();

		assertTop(19, 23, top);
	}
	
	@Test
	public void getLeaderTopRound2() {
		solver.solve();
		solver.solve();
		Top top = solver.getCurrentLeaderTop();

		assertTop(18, 23, top);
	}
	
	@Test
	public void getSolution() {
		solve();
		Top top = solver.getCurrentLeaderTop();
		
		assertTop(21, 21, top);
	}

	private void solve() {
		while(!solver.isEnd()){
			solver.solve();
		}
	}

	private void assertTop(int expectH, int expectV, Top top) {
		int actualH = top.getH();
		int actualV = top.getV();

		assertEquals(expectH, actualH);
		assertEquals(expectV, actualV);
	}

}
