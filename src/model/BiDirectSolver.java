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
		Solution[] solutions = new Solution[oneDirectTasks.length];
		for (int row = 0; row < task.getLimitationCount(); row++) {
			setOneDirectTask(oneDirectTasks[row]);
			solutions[row] = createSolution();
			createTop(0, solutions[row]);
		}
		createSolutionOnTop(solutions);
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
		int fixIdx = 0;
		for(int var = 0; var < sortVariableList.length; var++){
			if(currentLeaderSolution.getConstVariables().contains(var)){
				fixIdx++;
			}
			else{
				break;
			}
		}
		solutionOnTop.setFix(fixIdx);
		System.out.println(fixIdx);
		if (isTopCandidate(solutionOnTop)) {
			candidatesSolutions.add(solutionOnTop);
		}
	}

	private void setSolutionAndH(Solution[] solutions, Solution solutionOnTop) {
		int h = 0;
		out: for(int var = 0; var < sortVariableList.length; var++){
			for(int solution = 0; solution < solutions.length; solution++){
				if(!solutions[solution].isVarTaken(var)){
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
		for(int solution = 1; solution < solutions.length; solution++){
			if(solutions[solution].getV() < v){
				v = solutions[solution].getV();
			}
		}
		solutionOnTop.setV(v);
	}

	@Override
	protected void createRightTop() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createLeftTop() {
		// TODO Auto-generated method stub

	}

}
