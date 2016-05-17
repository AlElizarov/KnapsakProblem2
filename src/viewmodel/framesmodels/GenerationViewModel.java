package viewmodel.framesmodels;

import viewmodel.TaskManager;

public class GenerationViewModel {

	private int upperBoundCosts;
	private int upperBoundWeights;
	private int lowerBoundCosts;
	private int lowerBoundWeights;
	private double genCoeff;
	private TaskManager manager;
	private boolean isAllButtonEnable = true;
	private boolean isRandomlyButtonEnable = true;

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
		if (isRandomlyButtonEnable) {
			return 0.5;
		}
		return genCoeff;
	}

	public void setPartOfUpperBoundForLimits(double partOfUpperBoundForLimits) {
		this.genCoeff = partOfUpperBoundForLimits;
	}

	public void generate() {
		double coeff = getPartOfUpperBoundForLimits();
		if (isAllButtonEnable) {
			manager.genTaskData(lowerBoundCosts, lowerBoundWeights,
					upperBoundCosts, upperBoundWeights, coeff);
		} else {
			manager.genEmptyData(lowerBoundCosts, lowerBoundWeights,
					upperBoundCosts, upperBoundWeights, coeff);
		}
	}

	public boolean isAllButtonEnable() {
		return isAllButtonEnable;
	}

	public boolean isRandomlyButtonEnable() {
		return isRandomlyButtonEnable;
	}

	public void setAllButtonEnable(boolean isAllButtonEnable) {
		this.isAllButtonEnable = isAllButtonEnable;
	}

	public void setRandomlyButtonEnable(boolean isRandomlyButtonEnable) {
		this.isRandomlyButtonEnable = isRandomlyButtonEnable;
	}

}
