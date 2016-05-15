package model;

import java.util.ArrayList;
import java.util.List;

public class OneDirectTask {

	private List<Integer> costs = new ArrayList<>();
	private List<Integer> weights = new ArrayList<>();
	private int limit;

	public OneDirectTask(Task task, int numberOfLimitation) {
		costs.addAll(task.getSvertka());
		weights.addAll(task.getLimitation(numberOfLimitation));
		limit = task.getLimit(numberOfLimitation);
	}

	public int getLimit() {
		return limit;
	}

	public int getVariableCount() {
		return weights.size();
	}

	public Object getCost(int col) {
		return costs.get(col);
	}

	public Object getWeight(int col) {
		return weights.get(col);
	}

}
