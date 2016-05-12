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

	private void setStartState(String name, int variableCount,
			int limitationCount, int criterionCount, boolean isMax) {
		this.name = name;
		this.variableCount = variableCount;
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

	public List<List<Integer>> getCriterions() {
		return costs;
	}

	public List<List<Integer>> getLimitations() {
		return weights;
	}

	public List<Integer> getLimits() {
		return limits;
	}

	public List<String> getVarNames() {
		return varNames;
	}

	public List<String> getCritNames() {
		return critNames;
	}

	public List<String> getLimitNames() {
		return limitNames;
	}

	public List<String> getCritUnits() {
		return critUnits;
	}

	public List<String> getLimitUnits() {
		return limitUnits;
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
		if(variableCount > 1){
			deleteVariable();
			varNames.remove(varNames.size()-1);
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
			limits.remove(limits.size()-1);
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
}
