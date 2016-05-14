package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OneDirectSolver {

	private Task task;
	private Variable[] sortVariableList;
	private Top currentLeaderTop = new Top();
	private int leftLimit;
	private List<Top> candidatesTop = new ArrayList<>();
	private boolean hasSolution = true;

	public OneDirectSolver(Task task) {
		this.task = task;
		createSortListOfVariable();
		setLeftLimit();
		createFirstTop();
	}

	private void createSortListOfVariable() {
		sortVariableList = new Variable[task.getVariableCount()];
		for (int col = 0; col < sortVariableList.length; col++) {
			Object cost = task.getValue(0, col);
			Object weight = task.getValue(1, col);
			sortVariableList[col] = new Variable(cost, weight, col);
		}
		Arrays.sort(sortVariableList, Collections.reverseOrder());
	}

	private void setLeftLimit() {
		leftLimit = (int) task.getValue(task.getCriterionCount(),
				task.getVariableCount());
	}

	public boolean isEnd() {
		if (currentLeaderTop.getV() == currentLeaderTop.getH()) {
			return true;
		}
		return false;
	}

	public Top getCurrentLeaderTop() {
		return currentLeaderTop;
	}

	public void solve() {
		createLeftTop();
		createRightTop();

		candidatesTop.remove(currentLeaderTop);
		if (candidatesTop.size() > 0) {
			currentLeaderTop = Collections.max(candidatesTop);
		}
	}

	private void createTop(int currentH, int currentV, Top top) {
		setHAndVAndFix(currentH, currentV, top);
		if (isTopCandidate(top)) {
			candidatesTop.add(top);
		}
		setLeftLimit();
	}

	private boolean isTopCandidate(Top top) {
		return top.getV() >= currentLeaderTop.getH() && top.getV() > 0;
	}

	private void setHAndVAndFix(int currentH, int currentV, Top top) {
		for (int i = 0; i < sortVariableList.length; i++) {
			if (currentLeaderTop.getConstVariables().contains(i)) {
				continue;
			}
			int cost = sortVariableList[i].getCost();
			int weight = sortVariableList[i].getWeight();
			if (weight <= leftLimit) {
				currentH += cost;
				currentV += cost;
			} else if (leftLimit > 0) {
				top.setFix(i);
				currentV += ((double) leftLimit / weight) * cost;
			}
			leftLimit -= weight;
		}
		top.setH(currentH);
		top.setV(currentV);
	}

	private void createFirstTop() {
		Top top = new Top();
		createTop(0, 0, top);
		if (candidatesTop.size() > 0) {
			currentLeaderTop = Collections.max(candidatesTop);
		} else {
			hasSolution = false;
		}
	}

	private void createRightTop() {
		Top top = new Top(currentLeaderTop.getConstVariables());
		int h = 0;
		int v = 0;
		int weight = 0;
		int cost = 0;
		for (int i = 0; i < currentLeaderTop.getConstVariables().size(); i++) {
			Variable constVariable = sortVariableList[currentLeaderTop
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
		createTop(h, v, top);
	}

	private void createLeftTop() {
		Top top = new Top(currentLeaderTop.getConstVariables());
		createTop(0, 0, top);
	}

	public boolean hasSolution() {
		return hasSolution;
	}

}
