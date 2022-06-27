/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Model;
import it.polito.tdp.food.model.Simulatore;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	Graph<Food, DefaultWeightedEdge> grafo;
	private Model model;
	boolean grafoCreato = false;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtPorzioni"
    private TextField txtPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalorie"
    private Button btnCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="boxFood"
    private ComboBox<Food> boxFood; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	String n = txtPorzioni.getText();
    	int porzioni = 0;
    	try {
    		porzioni = Integer.parseInt(n);
    		this.model.creaGrafo(porzioni);
    		
    		this.grafo = this.model.getGrafo();
    		List<Food> food = new ArrayList<>(this.grafo.vertexSet());
    		this.boxFood.getItems().clear();
    		this.boxFood.getItems().addAll(food);
    		
    		txtResult.setText("GRAFO CREATO\n");
    		txtResult.appendText("# VERICI "+this.grafo.vertexSet().size());
    		txtResult.appendText("\n# ARCHI "+this.grafo.edgeSet().size());
    		
    		this.grafoCreato = true;
    		
    	}catch(NumberFormatException e) {
    		e.printStackTrace();
    		txtResult.setText("DEVI INSERIRE UN NUMERO ");
    	}
    }
    
    @FXML
    void doCalorie(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Analisi calorie...");
    	
    	if(!this.grafoCreato) {
    		txtResult.setText("devi prima creare il grafo\n");
    		
    		
    	}else {
    		Food scelto = this.boxFood.getValue();
    		if(scelto== null) {
    			txtResult.setText("per favore seleziona un cubo dalla tendina\n");
    		}else {
    			
    			List<Food> vicini = new ArrayList<>(Graphs.neighborListOf(this.grafo, scelto));
    			if(vicini.size()!=0) {
    				 //interrogo il dao
    				List<Adiacenza> congiunti = this.model.getAdiacenzeCalorieCongiunte(scelto);
    				Collections.sort(congiunti);
    				int i=1;
    				for(Adiacenza a : congiunti) {
    					if(!a.getF1().equals(scelto))
    						txtResult.appendText("\n"+a.getF1()+" "+a.getCalorie());
    					else
    						txtResult.appendText("\n"+a.getF2()+" "+a.getCalorie());
    					i++;
    					if(i == 5)
    						break;
    				}
    				

					
    				
    				
    			}else {
    				txtResult.setText("il cbo selezionato non ha cibi congiunti");
    			}
    		}
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Simulazione...");
    	
    	if(this.grafoCreato) {
    		Food scelto = this.boxFood.getValue();
    		String n = txtK.getText();
    		int k = Integer.parseInt(n);
    		Simulatore simulatore = new Simulatore(grafo, scelto, k);
    		simulatore.inizializza();
    		simulatore.run();
    		
    		int totCibi = simulatore.getTotCibi();
    		double durata = simulatore.getDurata();
    		
    		txtResult.setText("NUMERO CIBI: "+totCibi+"\nDURATA : "+durata+" minuti");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtPorzioni != null : "fx:id=\"txtPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCalorie != null : "fx:id=\"btnCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxFood != null : "fx:id=\"boxFood\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
