package tests.algorithm;

import static org.junit.Assert.*;
import model.KnapsakProblemSolver;
import model.Task;

import org.junit.Before;
import org.junit.Test;

public class SolverTests {
	
	private KnapsakProblemSolver solver;
	private Task task;
	
	@Before
	public void setUp(){
		
		solver = new KnapsakProblemSolver(task);
	}

	@Test
	public void solverTest() {
		fail("Not yet implemented");
	}

}
