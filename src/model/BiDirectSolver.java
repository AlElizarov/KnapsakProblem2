package model;

import java.util.Collections;

public class BiDirectSolver extends Solver {

	private Task task;
	private OneDirectTask[] oneDirectTasks;

	public BiDirectSolver(Task task) {
		super(task);
		this.task = task;
		oneDirectTasks = new OneDirectTask[task.getLimitationCount()];
		for (int row = 0; row < task.getLimitationCount(); row++) {
			oneDirectTasks[row] = new OneDirectTask(task, row);
		}
	}

	@Override
	public void createFirstTop() {
		createBiDirectTop(new Preparable() {
			
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

	private void createSolutionOnTop(Solution[] solutions) {
		Solution solutionOnTop = createSolution();
		setSolutionAndH(solutions, solutionOnTop);
		setV(solutions, solutionOnTop);
		setFix(solutionOnTop);
		addCandidate(solutionOnTop);
	}

	private void setFix(Solution solutionOnTop) {
		int fixIdx = 0;
		int maxCost = 0;
		for(int i = 0; i < unsortVariableList.length; i++){
			if(unsortVariableList[i].getCost() > maxCost && !currentLeaderSolution.getConstVariables().contains(i)){
				fixIdx = i;
				maxCost = unsortVariableList[i].getCost();
			}
		}
		solutionOnTop.setFix(fixIdx);
	}

	private void setSolutionAndH(Solution[] solutions, Solution solutionOnTop) {
		int h = 0;
		out: for (int var = 0; var < unsortVariableList.length; var++) {
			for (int solution = 0; solution < solutions.length; solution++) {
				if (!solutions[solution].isVarTaken(var)) {
					continue out;
				}
			}
			solutionOnTop.setVariable(var);
			h += unsortVariableList[var].getCost();
		}
		solutionOnTop.setH(h);
	}

	private void setV(Solution[] solutions, Solution solutionOnTop) {
		int v = solutions[0].getV();
		for (int solution = 1; solution < solutions.length; solution++) {
			if (solutions[solution].getV() < v) {
				v = solutions[solution].getV();
			}
		}
		solutionOnTop.setV(v);
	}

	@Override
	protected void createRightTop() {
		createBiDirectTop(new Preparable() {
			
			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx == currentLeaderSolution.getFix()
						|| currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

	@Override
	protected void createLeftTop() {
		createBiDirectTop(new Preparable() {
			
			@Override
			public boolean prepare(int varIdx, int unsortNumber) {
				return varIdx != currentLeaderSolution.getFix()
						&& currentLeaderSolution.isVarTaken(unsortNumber);
			}
		});
	}

	private void createBiDirectTop(Preparable preparable) {
		Solution[] solutions = new Solution[oneDirectTasks.length];
		for (int row = 0; row < task.getLimitationCount(); row++) {
			setOneDirectTask(oneDirectTasks[row]);
			solutions[row] = createSolution();
			prepareToSolution(preparable, solutions[row]);
		}
		createSolutionOnTop(solutions);
	}

}
