package it.polito.tdp.food.model;

public class Event implements Comparable <Event>{
	public enum EventType{
		INIZIO_PREPARAZIONE,
		FINE_PREPARAZIONE
	}
	
	private Food food;
	private Stazione stazione;
	private EventType type;
	private double time;
	public Event(Food food, Stazione stazione, EventType type, double time) {
		super();
		this.food = food;
		this.stazione = stazione;
		this.type = type;
		this.time = time;
	}
	public Food getFood() {
		return food;
	}
	public Stazione getStazione() {
		return stazione;
	}
	public EventType getType() {
		return type;
	}
	public double getTime() {
		return time;
	}
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return (int)(this.time-o.time);
	}
	
	
}
