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
		sol = new ArrayList<>();
		for (int i = 0; i < sortVariableList.length; i++) {
			sol.add(false);
		}
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
		sol = new ArrayList<>();
		for (int i = 0; i < sortVariableList.length; i++) {
			sol.add(false);
		}
		Solution solution = new Solution(
				currentLeaderSolution.getConstVariables());
		int h = 0;
		int v = 0;
		int weight = 0;
		int cost = 0;
		for (int i = 0; i < currentLeaderSolution.getConstVariables().size(); i++) {
			if (currentLeaderSolution.getConstVariables().get(i) == currentLeaderSolution.getFix()
					|| currentLeaderSolution.getSolution().get(sortVariableList[currentLeaderSolution.getConstVariables().get(i)].getNumber())) {
				sol.set(sortVariableList[currentLeaderSolution.getConstVariables().get(i)].getNumber(), true);
				Variable constVariable = unsortVariableList[sortVariableList[currentLeaderSolution
						.getConstVariables().get(i)].getNumber()];
				weight = constVariable.getWeight();
				cost = constVariable.getCost();
				if (weight > leftLimit) {
					setLeftLimit();
					return;
				}
				leftLimit -= weight;
				h += cost;
				v += cost;
			}
		}
		createTop(h, v, solution);
	}

	@Override
	protected void createLeftTop() {
		sol = new ArrayList<>();
		for (int i = 0; i < sortVariableList.length; i++) {
			sol.add(false);
		}
		int h = 0;
		int v = 0;
		int weight = 0;
		int cost = 0;
		for (int i = 0; i < currentLeaderSolution.getConstVariables().size(); i++) {
			if (currentLeaderSolution.getConstVariables().get(i) != currentLeaderSolution.getFix()
					&& currentLeaderSolution.getSolution().get(sortVariableList[currentLeaderSolution.getConstVariables().get(i)].getNumber())) {
				sol.set(sortVariableList[currentLeaderSolution.getConstVariables().get(i)].getNumber(), true);
				Variable constVariable = unsortVariableList[sortVariableList[currentLeaderSolution
						.getConstVariables().get(i)].getNumber()];
				weight = constVariable.getWeight();
				cost = constVariable.getCost();
				if (weight > leftLimit) {
					setLeftLimit();
					return;
				}
				leftLimit -= weight;
				h += cost;
				v += cost;
			}
		}
		Solution solution = new Solution(
				currentLeaderSolution.getConstVariables());
		createTop(h, v, solution);
	}

}
