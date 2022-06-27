package it.polito.tdp.food.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;

public class Simulatore {

	//DATI IN INGRESSO
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Food scelto;
	private int k; //NUMERO STAZIONI
	private Model model;
	
	//DATI IN USCITA
	private double  durata; //SI POTREBBE USARE ANCHE DURATION.OFMINUTES
	private int totCibi;
	
	//STATO DEL MONDO
	private int stazioniOccuate;
	List<Food> cucinati;
	List<Stazione> stazioni;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;

	public Simulatore(Graph<Food, DefaultWeightedEdge> grafo, Food scelto, int k) {
		super();
		this.grafo = grafo;
		this.scelto = scelto;
		this.k = k;
	}
	
	public void inizializza() {
		this.totCibi = 0;
		//this.durata = Duration.ofMinutes(0);
		this.durata = 0.0;
		this.stazioniOccuate = 0;
		this.cucinati = new ArrayList<>();
		this.queue = new PriorityQueue<>();
		this.model = new Model();
		
		this.stazioni = new ArrayList<>();
		for(int i=0; i<k; i++) {
			stazioni.add(new Stazione(null, true));
		}
		
		//SCELGO I PRIMI DA LAVORARE
		//List<Food> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, scelto));
		List<CibiConnessi> vicini = this.model.getCibiConnessi(scelto); //LISTA GIA ORDINATA DI CIBI DA LAVORARE
		
		for(int i=0; i<this.k && i<vicini.size(); i++) {
			if(!cucinati.contains(vicini.get(i).getFood())) {
				
				Food daLavorare = vicini.get(i).getFood();
				double durata = vicini.get(i).getCalorie();
				
				stazioni.get(i).setLibera(false);
				stazioni.get(i).setFood(daLavorare);
				
				Event e = new Event(daLavorare, stazioni.get(i), EventType.INIZIO_PREPARAZIONE, durata);
				queue.add(e);
				cucinati.add(daLavorare);
				this.stazioniOccuate++;
			}
		}
		
		
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			ProcessEvent(e);
		}
	}
	public void ProcessEvent(Event e) {
		switch(e.getType()) {
		
		case FINE_PREPARAZIONE:
			//prendo il food da cui scelgere il prossimo
			Food precedente = e.getFood();
			//libero la stazione
			e.getStazione().setFood(null);
			e.getStazione().setLibera(true);
			
			//aggiorno i dati 
			this.durata += e.getTime();
			this.totCibi++;
			this.stazioniOccuate--;
			
			//scelto il prossimo food da cucinare
			CibiConnessi connesso = null;
			for(CibiConnessi c : this.model.getCibiConnessi(precedente)){
				if(!this.cucinati.contains(c.getFood())) {
					connesso = c;
					break;
				}
			}
			if(connesso != null) {
				
				Food prossimo = connesso.getFood();
				double durata = connesso.getCalorie();
				
				Event nuovoEvento = new Event(prossimo, e.getStazione(), EventType.INIZIO_PREPARAZIONE, durata);
				
				this.queue.add(nuovoEvento);
				cucinati.add(prossimo);
			}
			
			break;
		
		case INIZIO_PREPARAZIONE: 
			
			this.stazioniOccuate++;
			e.getStazione().setFood(e.getFood());
			e.getStazione().setLibera(false);
			Event fine = new Event(e.getFood(), e.getStazione(), EventType.FINE_PREPARAZIONE, e.getTime());
			
			this.queue.add(fine);
			
			
			break;
		}
	}

	public double getDurata() {
		return durata;
	}

	public int getTotCibi() {
		return totCibi;
	}
	
	
}
