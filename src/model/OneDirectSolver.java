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
		Solution solution = createSolution();
		prepareToSolution(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx == currentLeaderSolution.getFix()
						|| currentLeaderSolution.isVarTaken(unsortNumber);
			}
		}, solution);
		addCandidate(solution);
	}

	@Override
	protected void createLeftTop() {
		Solution solution = createSolution();
		prepareToSolution(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx != currentLeaderSolution.getFix()
						&& currentLeaderSolution.isVarTaken(unsortNumber);
			}
		}, solution);
		addCandidate(solution);
	}

}
