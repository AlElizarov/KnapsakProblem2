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

	protected void createTop(int currentH, Solution soluton) {
		setHAndVAndFix(currentH, soluton);
		if (isTopCandidate(soluton)) {
			candidatesSolutions.add(soluton);
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

	protected boolean isTopCandidate(Solution soluton) {
		return soluton.getV() >= currentLeaderSolution.getH()
				&& soluton.getV() > 0;
	}

	protected void setHAndVAndFix(int currentH, Solution soluton) {
		int currentV = currentH;
		for (int var = 0; var < sortVariableList.length; var++) {
			if (currentLeaderSolution.getConstVariables().contains(var)) {
				continue;
			}
			Variable currentVariable = unsortVariableList[sortVariableList[var]
					.getNumber()];
			int cost = currentVariable.getCost();
			int weight = currentVariable.getWeight();
			if (weight <= leftLimit) {
				currentH += cost;
				currentV += cost;
				soluton.setVariable(sortVariableList[var].getNumber());
			} else if (leftLimit > 0) {
				soluton.setFix(var);
				currentV += ((double) leftLimit / weight) * cost;
			}
			leftLimit -= weight;
		}
		soluton.setH(currentH);
		soluton.setV(currentV);
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
	
	protected Solution createSolution() {
		Solution solution = new Solution(
				currentLeaderSolution.getConstVariables());
		solution.initializeSolution(sortVariableList.length);
		return solution;
	}

}