package gui;

import java.io.IOException;

import domein.AanmeldController;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
    private Button btnlogout;
    
    @FXML
    void logoutAction(ActionEvent event) {
        
        AanmeldController ac = new AanmeldController(); 
        AanmeldSchermController aanmeldSchermController = new AanmeldSchermController(ac);

        
        Scene scene = new Scene(aanmeldSchermController);

        
        Stage stage = (Stage) btnlogout.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    void beheerBedrijf(ActionEvent event) {
    	btnBeheerBedrijf.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	BedrijvenScherm controller = new BedrijvenScherm(this);
    	vbox.getChildren().add(controller.geefNode());
    }

	public AdminSchermController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminScherm.fxml"));
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
