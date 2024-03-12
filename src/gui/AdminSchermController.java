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

public class AdminSchermController extends BorderPane {

    @FXML
    private Label lbNaam;

    @FXML
    private Label lbBeheerbedrijf;

    @FXML
    private Button btnBeheerBedrijf;

    @FXML
    private VBox vbox;

    @FXML
    private Label lbError;

    @FXML
    void beheerBedrijf(ActionEvent event) {
    	vbox.getChildren().clear();
    	BedrijvenScherm controller = new BedrijvenScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }
    
    Gebruiker gebruiker;

	public AdminSchermController(Gebruiker gebruiker) {
		this.gebruiker = gebruiker;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScherm.fxml"));
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
