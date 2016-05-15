package model;

import java.util.Collections;

public class OneDirectSolver extends Solver {

	public OneDirectSolver(Task task) {
		super(task);
	}

	@Override
	public void createFirstTop() {
		Solution solution = createSolution();
		createTop(0, solution);
		addCandidate(solution);
		if (candidatesSolutions.size() > 0) {
			currentLeaderSolution = Collections.max(candidatesSolutions);
		} else {
			hasSolution = false;
		}
	}

	@Override
	protected void createRightTop() {
		prepareToSolution(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx == currentLeaderSolution.getFix()
						|| currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

	private void prepareToSolution(Preparable preparable) {
		Solution solution = createSolution();
		int weight = 0;
		int cost = 0;
		for (int var = 0; var < currentLeaderSolution.getConstVariables()
				.size(); var++) {
			int currentConstVariableIdx = currentLeaderSolution
					.getConstVariables().get(var);
			int unsortNumber = sortVariableList[currentConstVariableIdx]
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
		createTop(cost, solution);
		addCandidate(solution);
	}

	@Override
	protected void createLeftTop() {
		prepareToSolution(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx != currentLeaderSolution.getFix()
						&& currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

}
