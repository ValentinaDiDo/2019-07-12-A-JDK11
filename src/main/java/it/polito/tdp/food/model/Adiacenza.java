package it.polito.tdp.food.model;

public class Adiacenza implements Comparable<Adiacenza>{
	private Food f1;
	private Food f2;
	private double calorie;
	public Adiacenza(Food f1, Food f2, double calorie) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.calorie = calorie;
	}
	public Food getF1() {
		return f1;
	}
	public Food getF2() {
		return f2;
	}
	public double getCalorie() {
		return calorie;
	}
	@Override
	public int compareTo(Adiacenza o) {
		// TODO Auto-generated method stub
		return (int)(o.calorie-this.calorie);
	}
	
	
	
}
