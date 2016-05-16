package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Solver {

	private Variable[] sortVariableList;
	protected Variable[] unsortVariableList;
	protected Solution currentLeaderSolution = new Solution();
	private int leftLimit;
	protected List<Solution> candidatesSolutions = new ArrayList<>();
	protected boolean hasSolution = false;
	private int limit;
	private int currentMaxH;
	protected Task task;

	public Solver(Task task) {
		this.task = task;
		setOneDirectTask(new OneDirectTask(task, 0));
		out: for (int col = 0; col < task.getVariableCount(); col++) {
			for (int row = task.getCriterionCount(); row < task
					.getCriterionCount() + task.getLimitationCount(); row++) {
				if ((int) task.getValue(row, col) > (int) task.getValue(row,
						task.getVariableCount())) {
					continue out;
				}
			}
			hasSolution = true;
			break;
		}
		createFirstSolution();
		if (candidatesSolutions.size() > 0) {
			currentLeaderSolution = Collections.max(candidatesSolutions);
		}
	}

	public void solve() {
		createLeftSolution();
		createRightSolution();

		candidatesSolutions.remove(currentLeaderSolution);
		currentLeaderSolution = Collections.max(candidatesSolutions);
		deleteNonPerspectiveTops();
	}

	private void deleteNonPerspectiveTops() {
		for (int candidate = 0; candidate < candidatesSolutions.size(); candidate++) {
			if (candidatesSolutions.get(candidate).getV() < currentMaxH) {
				candidatesSolutions.remove(candidate);
				candidate--;
			}
		}
	}

	protected void setOneDirectTask(OneDirectTask oneDirectTask) {
		createSortListOfVariable(oneDirectTask);
		limit = oneDirectTask.getLimit();
		setLeftLimit();
	}

	protected void addCandidate(Solution solution) {
		if (isTopCandidate(solution)) {
			candidatesSolutions.add(solution);
			if (solution.getH() > currentMaxH) {
				currentMaxH = solution.getH();
			}
		}
	}

	protected abstract void createTop(Preparable preparable);

	protected abstract void createRightSolution();

	protected abstract void createLeftSolution();

	protected abstract void createFirstSolution();

	private void createSortListOfVariable(OneDirectTask oneDirectTask) {
		sortVariableList = new Variable[oneDirectTask.getVariableCount()];
		unsortVariableList = new Variable[oneDirectTask.getVariableCount()];
		for (int col = 0; col < unsortVariableList.length; col++) {
			Object cost = oneDirectTask.getCost(col);
			Object weight = oneDirectTask.getWeight(col);
			sortVariableList[col] = new Variable(cost, weight, col);
			unsortVariableList[col] = new Variable(cost, weight, col);
		}
		Arrays.sort(sortVariableList, Collections.reverseOrder());
	}

	private void setLeftLimit() {
		leftLimit = limit;
	}

	private boolean isTopCandidate(Solution solution) {
		return solution.getV() >= currentLeaderSolution.getH()
				&& solution.getV() > 0;
	}

	private void setHAndVAndFix(int currentH, Solution solution) {
		int currentV = currentH;
		for (int var = 0; var < unsortVariableList.length; var++) {
			if (currentLeaderSolution.getConstVariables().contains(
					sortVariableList[var].getNumber())) {
				continue;
			}
			Variable currentVariable = unsortVariableList[sortVariableList[var]
					.getNumber()];
			int cost = currentVariable.getCost();
			int weight = currentVariable.getWeight();
			if (weight <= leftLimit) {
				currentH += cost;
				currentV += cost;
				solution.setVariable(sortVariableList[var].getNumber());
			} else if (leftLimit > 0) {
				solution.setFix(sortVariableList[var].getNumber());
				currentV += ((double) leftLimit / weight) * cost;
			}
			leftLimit -= weight;
		}
		solution.setH(currentH);
		solution.setV(currentV);
	}

	public boolean isEnd() {
		if (currentLeaderSolution.getV() == currentLeaderSolution.getH()) {
			return true;
		}
		return false;
	}

	public Solution getCurrentLeaderTop() {
		return currentLeaderSolution;
	}

	public boolean hasSolution() {
		return hasSolution;
	}

	protected Solution initializeSolution() {
		Solution solution = new Solution(
				currentLeaderSolution.getConstVariables());
		solution.initializeSolution(sortVariableList.length);
		return solution;
	}

	protected void calculateSolution(Preparable preparable, Solution solution) {
		int weight = 0;
		int cost = 0;
		for (int var = 0; var < currentLeaderSolution.getConstVariables()
				.size(); var++) {
			int currentConstVariableIdx = currentLeaderSolution
					.getConstVariables().get(var);
			int unsortNumber = unsortVariableList[currentConstVariableIdx]
					.getNumber();
			if (preparable.prepare(currentConstVariableIdx, unsortNumber)) {
				solution.setVariable(unsortNumber);
				Variable constVariable = unsortVariableList[unsortNumber];
				weight = constVariable.getWeight();
				cost += constVariable.getCost();
				if (weight > leftLimit) {
					setLeftLimit();
					return;
				}
				leftLimit -= weight;
			}
		}
		setHAndVAndFix(cost, solution);
		setLeftLimit();
	}

}
