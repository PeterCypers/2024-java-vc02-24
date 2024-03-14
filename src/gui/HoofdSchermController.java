package gui;

import java.io.IOException;

import domein.Gebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class HoofdSchermController extends BorderPane{

    @FXML
    private Label lbNaam;

    @FXML
    private Button btnOverzichtBestellingen;
    
    @FXML
    private Button btnOverzichtKlanten;

    @FXML
    private BorderPane borderpane;
    
    @FXML
    private VBox vbox;

    @FXML
    private Label lbError;

    @FXML
    void overzichtBestellingen(ActionEvent event) {
    	vbox.getChildren().clear();
    	BestellingsScherm controller = new BestellingsScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }

    @FXML
    void overzichtKlanten(ActionEvent event) {
    	vbox.getChildren().clear();
    	KlantenScherm controller = new KlantenScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }
    
    
    Gebruiker gebruiker;

	public HoofdSchermController(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdScherm.fxml"));
        loader.setRoot(this);
    	loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        lbNaam.setText(gebruiker.getNaam());
	}
	
	public Gebruiker getGebruiker() {
		return gebruiker;
	}
    
}