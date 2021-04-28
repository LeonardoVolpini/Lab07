/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	Nerc nerc= this.cmbNerc.getValue();
    	if (nerc==null) {
    		this.txtResult.setText("Selezionare un Nerc");
    		return;
    	}
    	
    	String anni= this.txtYears.getText();
    	if (anni.isEmpty()) {
    		this.txtResult.setText("Inserire un numero massimo di anni");
    		return;
    	}
    	int years;
    	try {
    		years= Integer.parseInt(anni);
    	} catch(NumberFormatException e){
    		this.txtResult.setText("Inserire un valore numerico come anni");
    		return;
    	}
    	
    	String ore= this.txtHours.getText();
    	if (ore.isEmpty()) {
    		this.txtResult.setText("Inserire un numero massimo di ore");
    		return;
    	}
    	long hours;
    	try {
    		hours= Long.parseLong(ore);
    	} catch (NumberFormatException e) {
    		this.txtResult.setText("Inserire un valore numerico come ore");
    		return;
    	}
    	
    	Set<PowerOutage> result=this.model.trovaWorstCase(nerc.getId(), years, hours);
    	String s="";
    	for (PowerOutage p: result) {
    		s+=p.toString();
    	}
    	this.txtResult.setText(s);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbNerc.getItems().addAll(model.getNercList());
    }
}
