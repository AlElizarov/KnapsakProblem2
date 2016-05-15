package model;

import java.util.Collections;

public class OneDirectSolver extends Solver {

	public OneDirectSolver(Task task) {
		super(task);
	}

	@Override
	public void createFirstSolution() {
		createTop(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return false;
			}
		});
		if (candidatesSolutions.size() > 0) {
			currentLeaderSolution = Collections.max(candidatesSolutions);
		} else {
			hasSolution = false;
		}
	}

	@Override
	protected void createRightSolution() {
		createTop(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx == currentLeaderSolution.getFix()
						|| currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

	@Override
	protected void createLeftSolution() {
		createTop(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx != currentLeaderSolution.getFix()
						&& currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

	@Override
	protected void createTop(Preparable preparable) {
		Solution solution = initializeSolution();
		calculateSolution(preparable, solution);
		addCandidate(solution);
	}

}
