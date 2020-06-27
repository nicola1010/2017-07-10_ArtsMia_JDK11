package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.ArtObjectPeso;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<Integer> boxLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	
    	txtResult.clear();
    	this.model.creaGrafo();
    	txtResult.appendText(this.model.creaSoluzione());

    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	
    	int id;
    	try {
    		id=Integer.parseInt(this.txtObjectId.getText());
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("SCRIVI UN NUMERO INTERO");
    		return;
    	}
    	
    	if(this.model.verificaObj(id)==false)
    	{
    		txtResult.appendText("NON ESSITE NESSUN OGGETTO CON QUESTO ID");
    		return;
    	}
    	
    	this.txtResult.clear();
    	int vicini=this.model.calcolaComponenteConnessa(Integer.parseInt(this.txtObjectId.getText()));
    	this.txtResult.appendText("VERTICE "+id+" CONNESSO A "+vicini+" OBJECTS \n");
    	for(int i=2; i<=vicini; i++)
    	this.boxLUN.getItems().add(i);
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {
    	
    	if(this.boxLUN.getValue()==null) {
    		this.txtResult.appendText("Seleziona un numero dalla tendina!!");
    		return;
    	}
    	int id;
    	try {
    		id=Integer.parseInt(this.txtObjectId.getText());
    		
    	}catch(NumberFormatException e) {
    		txtResult.appendText("SCRIVI UN NUMERO INTERO");
    		return;
    	}
    	
    	if(this.model.verificaObj(id)==false)
    	{
    		txtResult.appendText("NON ESSITE NESSUN OGGETTO CON QUESTO ID");
    		return;
    	}
    	
    	
    	
    	this.model.cercaOttimo(this.boxLUN.getValue(), Integer.parseInt(this.txtObjectId.getText()));
    	this.txtResult.appendText("BEST PESO TROVATO: "+this.model.getBest()+"\n");
    	
    	for(ArtObjectPeso a: this.model.getCamminoOttimo()) {
    		this.txtResult.appendText(""+a.getA().getName()+ " "+a.getPeso()+"\n");
    	}
    	
    	

    }

    @FXML
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
