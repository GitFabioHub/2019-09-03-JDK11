/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...\n");
    	try {
    		int NPassi=Integer.parseInt(txtPassi.getText());
    		String porzione=boxPorzioni.getValue();
        	if(porzione==null) {
        		txtResult.appendText("Seleziona porzione");
        		return ;
        	}

    		List<String> l=new LinkedList<>(this.model.trovaPercorso(porzione, NPassi));
    		if(!l.isEmpty()) {
    			txtResult.appendText(String.format("Cammino trovato,peso:#%d\n", this.model.getPesoTot()));
    		for(String s:l) {
    			
    			txtResult.appendText(s+"\n");
    		}
    	}}catch(NumberFormatException e ){
    		
    		txtResult.appendText("Inserisci un numero!!!");
    		
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("\n");
    	String porzione=boxPorzioni.getValue();
    	if(porzione==null) {
    		txtResult.appendText("Seleziona porzione");
    		return;
    	}
    	List<Adiacenza>res=new LinkedList<>(this.model.getConnessi(porzione));
    	for(Adiacenza a :res) {
    		txtResult.appendText(a.getP2()+" #"+a.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	int calorie;
    	try {
    		calorie=Integer.parseInt(txtCalorie.getText());
    		this.model.creaGrafo(calorie);
    		txtResult.appendText(String.format("GRAFO CREATO!\n#%d vertici #%d archi", this.model.getNumVertici(),this.model.getNumArchi()));
        	boxPorzioni.getItems().addAll(this.model.getGrafo().vertexSet());
    	}catch(NumberFormatException e){
    		
    		txtResult.appendText("ERRORE: inserisci un numero!!");
    		}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
