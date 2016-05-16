package model;


public class OneDirectSolver extends Solver {

	public OneDirectSolver(Task task) {
		super(task);
	}

	@Override
	protected void createFirstSolution() {
		createTop(new Preparable() {

			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return false;
			}
		});
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
