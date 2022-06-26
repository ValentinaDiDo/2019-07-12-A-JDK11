package it.polito.tdp.food.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulatore {

	//DATI IN INGRESSO
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Food scelto;
	private int k; //NUMERO STAZIONI
	
	//DATI IN USCITA
	private Duration durata;
	private int totCibi;
	
	//STATO DEL MONDO
	private int stazioniOccuate;
	
	//CODA DEGLI EVENTI
	List<Food> daCucinare;

	public Simulatore(Graph<Food, DefaultWeightedEdge> grafo, Food scelto, int k) {
		super();
		this.grafo = grafo;
		this.scelto = scelto;
		this.k = k;
	}
	
	public void inizializza() {
		this.totCibi = 0;
		this.durata = Duration.ofMinutes(0);
		this.stazioniOccuate = 0;
		this.daCucinare = new ArrayList<>(this.grafo.vertexSet());
	}
	
	public void run() {
		
	}
}
