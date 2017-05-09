/**
 * Sample Skeleton for 'Country.fxml' Controller Class
 */

package it.polito.tdp.country;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.country.model.Country;
import it.polito.tdp.country.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class CountryController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxPartenza"
    private ComboBox<Country> boxPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="btnRaggiungibili"
    private Button btnRaggiungibili; // Value injected by FXMLLoader

    @FXML // fx:id="boxDestinazione"
    private ComboBox<Country> boxDestinazione; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtresult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handlePercorso(ActionEvent event) {
    	
    	Country destinazione = boxDestinazione.getValue() ;
    	
    	List<Country> percorso = model.getPercorso(destinazione) ;
    	
    	txtResult.appendText(percorso.toString()+"\n");


    }

    @FXML
    void handleRaggiungibili(ActionEvent event) {
    	Country partenza = boxPartenza.getValue();
    	if(partenza == null){
    		txtResult.appendText("Errore: devi selezionare lo stato di partenza\n");
    	}
    	
    	List<Country> raggiungibili = model.getRaggiungibili(partenza);
    	
    	//stampo a video la lista
    	txtResult.appendText(raggiungibili.toString());
    	
    	//popolo la tendina destinazione
    	boxDestinazione.getItems().clear();  //prima la pulisco
    	boxDestinazione.getItems().addAll(raggiungibili);
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'Country.fxml'.";
        assert btnRaggiungibili != null : "fx:id=\"btnRaggiungibili\" was not injected: check your FXML file 'Country.fxml'.";
        assert boxDestinazione != null : "fx:id=\"boxDestinazione\" was not injected: check your FXML file 'Country.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Country.fxml'.";
        assert txtResult != null : "fx:id=\"txtresult\" was not injected: check your FXML file 'Country.fxml'.";

    }
    
  // le inizializzazioni che riguardano il database non possono essere svolte in inizialize() ma in setModel()
    public void setModel(Model model){
    	this.model = model;
    	
    	//riempi la prima tendina con l'elenco completo delle nazioni
    	boxPartenza.getItems().addAll(model.getCountries());
    	
    }
}
