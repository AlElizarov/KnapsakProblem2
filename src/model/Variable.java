package model;

public class Variable implements Comparable<Variable>{

	private int cost;
	private int weight;
	private int number;

	public Variable(Object cost, Object weight, int number) {
		this.cost = (int) cost;
		this.weight = (int) weight;
		this.number = number;
	}

	@Override
	public int compareTo(Variable that) {
		if(this.cost*that.weight > this.weight*that.cost){
			return 1;
		}
		else if(this.cost*that.weight < this.weight*that.cost){
			return -1;
		}
		return 0;
	}

	public int getCost() {
		return cost;
	}

	public int getWeight() {
		return weight;
	}

	public int getNumber() {
		return number;
	}

	@Override
	public String toString() {
		return "Variable [cost=" + cost + ", weight=" + weight + ", number="
				+ number + "]";
	}

}
