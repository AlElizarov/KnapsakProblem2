package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OneDirectSolver extends Solver {

	public OneDirectSolver(Task task) {
		super(task);
	}

	@Override
	public void createFirstTop() {
		Solution solution = new Solution();
		createTop(0, 0, solution);
		if (candidatesSolutions.size() > 0) {
			currentLeaderSolution = Collections.max(candidatesSolutions);
		} else {
			hasSolution = false;
		}
	}

	@Override
	protected void createRightTop() {
		Solution solution = new Solution(currentLeaderSolution.getConstVariables());
		int h = 0;
		int v = 0;
		int weight = 0;
		int cost = 0;
		for (int i = 0; i < currentLeaderSolution.getConstVariables().size(); i++) {
			Variable constVariable = sortVariableList[currentLeaderSolution
					.getConstVariables().get(i)];
			weight = constVariable.getWeight();
			cost = constVariable.getCost();
			if (weight > leftLimit) {
				return;
			}
			leftLimit -= weight;
			h += cost;
			v += cost;
		}
		createTop(h, v, solution);
	}

	@Override
	protected void createLeftTop() {
		Solution solution = new Solution(currentLeaderSolution.getConstVariables());
		createTop(0, 0, solution);
	}

}
