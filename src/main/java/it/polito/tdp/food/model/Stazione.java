package it.polito.tdp.food.model;

public class Stazione {
	private Food food;
	private boolean libera;
	public Stazione(Food food, boolean libera) {
		super();
		this.food = food;
		this.libera = libera;
	}
	public Food getFood() {
		return food;
	}
	public void setFood(Food food) {
		this.food = food;
	}
	public boolean isLibera() {
		return libera;
	}
	public void setLibera(boolean libera) {
		this.libera = libera;
	}
	
	
}
