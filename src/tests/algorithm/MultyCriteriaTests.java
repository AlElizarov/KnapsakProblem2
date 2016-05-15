package tests.algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import model.BiDirectSolver;
import model.Task;

import org.junit.Before;
import org.junit.Test;

public class MultyCriteriaTests {

	private BiDirectSolver solver;
	private Task task;

	@Before
	public void setUp() {
		Integer[][] costs = { { 3, 5, 7, 11, 6 }, { 8, 2, 6, 5, 9 },
				{ 10, 4, 7, 8, 6 }, { 9, 5, 8, 7, 4 } };
		Integer[][] weights = { { 10, 12, 18, 9, 13 }, { 28, 19, 11, 13, 14 },
				{ 22, 9, 19, 11, 8 } };
		Integer[] limits = { 50, 70, 60 };
		task = new Task("", 5, 3, 4, true);
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
	public void getSvertka() {
		ArrayList<Integer> expectSvertka = new ArrayList<>();
		expectSvertka.add(7);
		expectSvertka.add(4);
		expectSvertka.add(7);
		expectSvertka.add(7);
		expectSvertka.add(6);

		ArrayList<Integer> actualSvertka = task.calculateSvertka();

		assertEquals(expectSvertka, actualSvertka);
	}

}
