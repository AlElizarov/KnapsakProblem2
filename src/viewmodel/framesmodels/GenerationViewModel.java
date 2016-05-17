package viewmodel.framesmodels;

import viewmodel.TaskManager;

public class GenerationViewModel {

	private int upperBoundCosts = 100;
	private int upperBoundWeights = 100;
	private int lowerBoundCosts = 100;
	private int lowerBoundWeights = 100;
	private double partOfUpperBoundForLimits = 0.5;
	private TaskManager manager;

	public GenerationViewModel(TaskManager manager) {
		this.manager = manager;
	}

	public int getUpperBoundCosts() {
		return upperBoundCosts;
	}

	public void setUpperBoundCosts(int upperBoundCosts) {
		this.upperBoundCosts = upperBoundCosts;
	}

	public int getUpperBoundWeights() {
		return upperBoundWeights;
	}

	public void setUpperBoundWeights(int upperBoundWeights) {
		this.upperBoundWeights = upperBoundWeights;
	}

	public int getLowerBoundCosts() {
		return lowerBoundCosts;
	}

	public void setLowerBoundCosts(int lowerBoundCosts) {
		this.lowerBoundCosts = lowerBoundCosts;
	}

	public int getLowerBoundWeights() {
		return lowerBoundWeights;
	}

	public void setLowerBoundWeights(int lowerBoundWeights) {
		this.lowerBoundWeights = lowerBoundWeights;
	}

	public double getPartOfUpperBoundForLimits() {
		return partOfUpperBoundForLimits;
	}

	public void setPartOfUpperBoundForLimits(double partOfUpperBoundForLimits) {
		this.partOfUpperBoundForLimits = partOfUpperBoundForLimits;
	}

	public void generate() {
		manager.genTaskData(lowerBoundCosts, lowerBoundWeights,
				upperBoundCosts, upperBoundWeights);
	}

}
