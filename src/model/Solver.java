package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Solver {

	protected Variable[] sortVariableList;
	protected Variable[] unsortVariableList;
	protected Solution currentLeaderSolution = new Solution();
	protected int leftLimit;
	protected List<Solution> candidatesSolutions = new ArrayList<>();
	protected boolean hasSolution = true;
	protected int limit;
	protected ArrayList<Boolean> sol;

	public Solver(Task task) {
		setOneDirectTask(new OneDirectTask(task, 0));
	}

	public void solve() {
		createLeftTop();
		createRightTop();

		candidatesSolutions.remove(currentLeaderSolution);
		if (candidatesSolutions.size() > 0) {
			currentLeaderSolution = Collections.max(candidatesSolutions);
		}
	}

	protected void setOneDirectTask(OneDirectTask oneDirectTask) {
		createSortListOfVariable(oneDirectTask);
		limit = oneDirectTask.getLimit();
		setLeftLimit();
	}

	public abstract void createFirstTop();

	protected void createTop(int currentH, int currentV, Solution top) {
		setHAndVAndFix(currentH, currentV, top);
		if (isTopCandidate(top)) {
			candidatesSolutions.add(top);
		}
		setLeftLimit();
	}

	protected abstract void createRightTop();

	protected abstract void createLeftTop();

	private void createSortListOfVariable(OneDirectTask oneDirectTask) {
		sortVariableList = new Variable[oneDirectTask.getVariableCount()];
		unsortVariableList = new Variable[oneDirectTask.getVariableCount()];
		for (int col = 0; col < sortVariableList.length; col++) {
			Object cost = oneDirectTask.getCost(col);
			Object weight = oneDirectTask.getWeight(col);
			sortVariableList[col] = new Variable(cost, weight, col);
			unsortVariableList[col] = new Variable(cost, weight, col);
		}
		Arrays.sort(sortVariableList, Collections.reverseOrder());
	}

	protected void setLeftLimit() {
		leftLimit = limit;
	}

	protected boolean isTopCandidate(Solution top) {
		return top.getV() >= currentLeaderSolution.getH() && top.getV() > 0;
	}

	protected void setHAndVAndFix(int currentH, int currentV, Solution top) {
		for (int i = 0; i < sortVariableList.length; i++) {
			if (currentLeaderSolution.getConstVariables().contains(i)) {
				//sol.set(i, currentLeaderSolution.getSolution().get(i));
				continue;
			}
			int cost = unsortVariableList[sortVariableList[i].getNumber()]
					.getCost();
			int weight = unsortVariableList[sortVariableList[i].getNumber()]
					.getWeight();
			if (weight <= leftLimit) {
				currentH += cost;
				currentV += cost;
				sol.set(sortVariableList[i].getNumber(), true);
			} else if (leftLimit > 0) {
				top.setFix(i);
				currentV += ((double) leftLimit / weight) * cost;
			}
			leftLimit -= weight;
		}
		top.setH(currentH);
		top.setV(currentV);
		top.setSolution(sol);
		System.out.print(top.getSolution()+"   ");
		System.out.println(top);
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

}
