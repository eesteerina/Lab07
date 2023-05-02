/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutages;
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
    	
    	Nerc n = this.cmbNerc.getValue();
    	
    	int anniMax;
    	String a = this.txtYears.getText();
    	int oreMax;
    	String o = this.txtHours.getText();
    	
    	try {
    		anniMax = Integer.parseInt(a);
    		oreMax = Integer.parseInt(o);
    		
    		List<PowerOutages> worstCase = new ArrayList<PowerOutages>(this.model.trovaWorstCase(n, anniMax, oreMax));
    		
    		this.txtResult.appendText("Tot people affected: "+this.model.getWorstPers() + "\n");
    		this.txtResult.appendText("Tot hours of outage: "+this.model.getOre() + "\n");
    		
    		for(PowerOutages po : worstCase) {
    			this.txtResult.appendText(po.toString() + "\n");
    		}
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		this.txtResult.appendText("Inserire un intervallo valido di anni o ore massimi");
    	}
    	
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
    	this.cmbNerc.getItems().addAll(this.model.getNercList());
    }
}
