package gui;

import java.io.IOException;

import domein.gebruiker.GebruikerHolder;
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
    	btnOverzichtKlanten.getStyleClass().clear();
    	btnOverzichtBestellingen.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	BestellingsScherm controller = new BestellingsScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }

    @FXML
    void overzichtKlanten(ActionEvent event) {
    	btnOverzichtBestellingen.getStyleClass().clear();
    	btnOverzichtKlanten.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	KlantenScherm controller = new KlantenScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }
    
	public HoofdSchermController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("HoofdScherm.fxml"));
        loader.setRoot(this);
    	loader.setController(this);
        try {
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        lbNaam.setText(GebruikerHolder.getInstance().getNaam());
	}
    
}