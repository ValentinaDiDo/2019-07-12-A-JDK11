package it.polito.tdp.food.model;

public class CibiConnessi implements Comparable<CibiConnessi>{
	private Food food;
	private double Calorie;
	public CibiConnessi(Food food, double calorie) {
		super();
		this.food = food;
		Calorie = calorie;
	}
	public Food getFood() {
		return food;
	}
	public double getCalorie() {
		return Calorie;
	}
	@Override
	public int compareTo(CibiConnessi o) {
		// TODO Auto-generated method stub
		return (int)(o.Calorie-this.Calorie);
	}
	
	
}
