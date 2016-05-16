package model;

import java.util.ArrayList;
import java.util.List;

public class Task {

	private String name;
	private int variableCount;
	private int limitationCount;
	private int criterionCount;
	private boolean isMax;
	private String economMeaning;
	private List<List<Integer>> costs;
	private List<List<Integer>> weights;
	private List<Integer> limits;
	private List<String> varNames;
	private List<String> critNames;
	private List<String> limitNames;
	private List<String> critUnits;
	private List<String> limitUnits;
	private List<Integer> svertka;
	private boolean[] discretSolution;

	public Task(String name, int variableCount, int limitationCount,
			int criterionCount, boolean isMax) {
		setStartState(name, variableCount, limitationCount, criterionCount,
				isMax);
	}

	public Task(String name, int variableCount, int limitationCount,
			int criterionCount, boolean isMax, String economMeaning) {
		setStartState(name, variableCount, limitationCount, criterionCount,
				isMax);
		this.economMeaning = economMeaning;
	}

	public List<Integer> getSvertka() {
		if (costs.size() == 1) {
			return costs.get(0);
		}
		calculateSvertka();
		return svertka;
	}

	List<Integer> getLimitation(int numberOfLimitation) {
		return weights.get(numberOfLimitation);
	}

	int getLimit(int numberOfLimit) {
		return limits.get(numberOfLimit);
	}

	private void setStartState(String name, int variableCount,
			int limitationCount, int criterionCount, boolean isMax) {
		this.name = name;
		this.variableCount = variableCount;
		discretSolution = new boolean[variableCount];
		this.limitationCount = limitationCount;
		this.criterionCount = criterionCount;
		this.isMax = isMax;
		costs = new ArrayList<>();
		weights = new ArrayList<>();
		limits = new ArrayList<>();
		varNames = new ArrayList<>();
		critNames = new ArrayList<>();
		limitNames = new ArrayList<>();
		critUnits = new ArrayList<>();
		limitUnits = new ArrayList<>();
		initiateCosts();
		initiateWeights();
		initiateLimits();
		initiateVarNames();
		initiateCritNamesAndUnits();
		initiateLimitNamesAndUnits();
	}

	private void initiateLimitNamesAndUnits() {
		for (int i = 0; i < limitationCount; i++) {
			limitNames.add(null);
			limitUnits.add("$");
		}
	}

	private void initiateCritNamesAndUnits() {
		for (int i = 0; i < criterionCount; i++) {
			critNames.add(null);
			critUnits.add("$");
		}
	}

	private void initiateVarNames() {
		for (int i = 0; i < variableCount; i++) {
			varNames.add(null);
		}
	}

	private void initiateLimits() {
		for (int i = 0; i < limitationCount; i++) {
			limits.add(null);
		}
	}

	private void initiateWeights() {
		List<Integer> tmp;
		for (int i = 0; i < criterionCount; i++) {
			tmp = new ArrayList<>();
			for (int j = 0; j < variableCount; j++) {
				tmp.add(null);
			}
			costs.add(i, tmp);
		}
	}

	public void initiateCosts() {
		List<Integer> tmp;
		for (int i = 0; i < limitationCount; i++) {
			tmp = new ArrayList<>();
			for (int j = 0; j < variableCount; j++) {
				tmp.add(null);
			}
			weights.add(i, tmp);
		}
	}

	public int getVariableCount() {
		return variableCount;
	}

	public int getLimitationCount() {
		return limitationCount;
	}

	public int getCriterionCount() {
		return criterionCount;
	}

	public boolean isMax() {
		return isMax;
	}

	public String getEconomMeaning() {
		return economMeaning;
	}

	public String getName() {
		return name;
	}

	public void addCriterion() {
		List<Integer> tmp = new ArrayList<>();
		for (int i = 0; i < variableCount; i++) {
			tmp.add(null);
		}
		costs.add(tmp);
		criterionCount++;
	}

	public void addEconomCriterion() {
		addCriterion();
		critNames.add(null);
		critUnits.add("$");
	}

	public void deleteCriterion() {
		if (criterionCount > 1) {
			costs.remove(costs.size() - 1);
			criterionCount--;
		}
	}

	public void deleteEconomCriterion() {
		if (criterionCount > 1) {
			deleteCriterion();
			critNames.remove(critNames.size() - 1);
			critUnits.remove(critUnits.size() - 1);
		}
	}

	public void addVariable() {
		for (int crit = 0; crit < criterionCount; crit++) {
			costs.get(crit).add(null);
		}
		for (int limit = 0; limit < limitationCount; limit++) {
			weights.get(limit).add(null);
		}
		variableCount++;
	}

	public void deleteVariable() {
		if (variableCount > 1) {
			for (int crit = 0; crit < criterionCount; crit++) {
				costs.get(crit).remove(variableCount - 1);
			}
			for (int limit = 0; limit < limitationCount; limit++) {
				weights.get(limit).remove(variableCount - 1);
			}
		}
		variableCount--;
	}

	public void deleteEconomVariable() {
		if (variableCount > 1) {
			deleteVariable();
			varNames.remove(varNames.size() - 1);
		}
	}

	public void addEconomVariable() {
		addVariable();
		varNames.add(null);
	}

	public void addLimitation() {
		List<Integer> tmp = new ArrayList<>();
		for (int i = 0; i < variableCount; i++) {
			tmp.add(null);
		}
		weights.add(tmp);
		limits.add(null);
		limitationCount++;
	}

	public void deleteLimitation() {
		if (limitationCount > 1) {
			weights.remove(weights.size() - 1);
			limits.remove(limits.size() - 1);
			limitationCount--;
		}
	}

	public void addEconomLimitation() {
		addLimitation();
		limitNames.add(null);
		limitUnits.add("$");
	}

	public void deleteEconomLimitation() {
		if (limitationCount > 1) {
			deleteLimitation();
			limitNames.remove(limitNames.size() - 1);
			limitUnits.remove(limitUnits.size() - 1);
		}
	}

	public void setValue(int intValue, int row, int col) {
		if (col == variableCount) {
			limits.set(row - criterionCount, intValue);
			return;
		}
		if (row < criterionCount) {
			costs.get(row).set(col, intValue);
			return;
		}
		weights.get(row - criterionCount).set(col, intValue);
	}

	public void setEconomValue(Object value, int row, int col) {
		if (row == 0) {
			varNames.set(col, value.toString());
			return;
		}
		if (col == 0) {
			setNames(value, row - 1);
			return;
		}
		setUnits(value, row - 1);
	}

	private void setUnits(Object value, int row) {
		if (row < criterionCount) {
			critUnits.set(row, value.toString());
		} else {
			limitUnits.set(row - criterionCount, value.toString());
		}
	}

	private void setNames(Object value, int row) {
		if (row < criterionCount) {
			critNames.set(row, value.toString());
		} else {
			limitNames.set(row - criterionCount, value.toString());
		}
	}

	public Object getValue(int row, int col) {
		if (col == variableCount) {
			return limits.get(row - criterionCount);
		}
		if (row < criterionCount) {
			return costs.get(row).get(col);
		}
		return weights.get(row - criterionCount).get(col);
	}

	public Object getEconomValue(int row, int col) {
		if (row == 0) {
			return varNames.get(col);
		}
		if (col == 0) {
			return getNames(row - 1);
		}

		if (col == 1) {
			return getUnits(row - 1);
		}
		return null;
	}

	private Object getUnits(int row) {
		if (row < criterionCount) {
			return critUnits.get(row);
		}
		if (row >= criterionCount) {
			return limitUnits.get(row - criterionCount);
		}
		return null;
	}

	private Object getNames(int row) {
		if (row < criterionCount) {
			return critNames.get(row);
		}
		if (row >= criterionCount) {
			return limitNames.get(row - criterionCount);
		}
		return null;
	}

	public boolean isFull() {
		return isCriterionsFull() && isLimitationFull() && isLimitsFull();
	}

	private boolean isLimitsFull() {
		return !limits.contains(null);
	}

	private boolean isLimitationFull() {
		for (int row = 0; row < limitationCount; row++) {
			if (weights.get(row).contains(null)) {
				return false;
			}
		}
		return true;
	}

	private boolean isCriterionsFull() {
		for (int row = 0; row < criterionCount; row++) {
			if (costs.get(row).contains(null)) {
				return false;
			}
		}
		return true;
	}

	public boolean isEconomFull() {
		return isCritNamesFull() && isLimitationNamesFull() && isVarNamesFull();
	}

	private boolean isVarNamesFull() {
		return !varNames.contains(null);
	}

	private boolean isLimitationNamesFull() {
		return !limitNames.contains(null);
	}

	private boolean isCritNamesFull() {
		return !critNames.contains(null);
	}

	public void calculateSvertka() {
		svertka = new ArrayList<>();
		for (int i = 0; i < variableCount; i++) {
			double tmpX = 0;
			for (int j = 0; j < criterionCount; j++) {
				tmpX += costs.get(j).get(i) * 0.25;
			}
			svertka.add((int) tmpX);
		}
	}

	public boolean getSolutionVariable(int col) {
		return discretSolution[col];
	}

	public void setSolution(boolean[] solution) {
		discretSolution = solution;
	}

	public void setSolutionVariable(boolean value, int col) {
		discretSolution[col] = value;
	}
}
