package it.polito.tdp.food.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	FoodDao dao =new FoodDao();
	Graph<Food, DefaultWeightedEdge> grafo;
	Map<Integer, Food> mFood;
	public void creaGrafo(int porzioni) {
	
		List<Food> food = this.dao.getFoodPortions(porzioni);
		mFood = new HashMap<>();
		for(Food f : food) {
			mFood.put(f.getFood_code(), f);
		}
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, food);
		
		List<Adiacenza> adiacenze  = this.dao.getAdiacenze(mFood);
		
		for(Adiacenza a  : adiacenze) {
			Graphs.addEdge(this.grafo, a.getF1(), a.getF2(), a.getCalorie());
		}
		
		System.out.println("GRAFO CREATO ");
		System.out.println("VERTICI "+this.grafo.vertexSet().size());
		System.out.println("ARCHI "+this.grafo.edgeSet().size());
	}

	public Graph<Food, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	
	public List<Adiacenza> getAdiacenzeCalorieCongiunte(Food f){
		return this.dao.getAdiacenzeCalorieCongiunte(f, this.mFood);
	}
	public List<CibiConnessi> getCibiConnessi(Food f){
		List<CibiConnessi> connessi = this.dao.getCibiConnessi(f, mFood);
		Collections.sort(connessi);
		return connessi;
	}
}
