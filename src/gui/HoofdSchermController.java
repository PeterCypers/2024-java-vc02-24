package gui;

import java.io.IOException;

import domein.AanmeldController;
import domein.gebruiker.GebruikerHolder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HoofdSchermController extends BorderPane{

	@FXML
    private Menu mbName;

    @FXML
    private Button btnOverzichtBestellingen;
    
    @FXML
    private Button btnOverzichtKlanten;
    
    @FXML
    private Button btnBetaalmethodes;

    @FXML
    private BorderPane borderpane;
    
    @FXML
    private VBox vbox;

    @FXML
    private Label lbError;
    
    @FXML
    private MenuItem miLogout;
    
    
    @FXML
    void logoutAction(ActionEvent event) {
        AanmeldController ac = new AanmeldController(); 
        AanmeldSchermController aanmeldSchermController = new AanmeldSchermController(ac);

        Scene scene = new Scene(aanmeldSchermController);

        Stage stage = (Stage) this.getScene().getWindow();
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setResizable(false);
    }

    @FXML
    void overzichtBestellingen(ActionEvent event) {
    	btnOverzichtKlanten.getStyleClass().clear();
    	btnBetaalmethodes.getStyleClass().clear();
    	btnOverzichtBestellingen.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	BestellingsScherm controller = new BestellingsScherm();
    	vbox.getChildren().add(controller.geefNode());
    }

    @FXML
    void overzichtKlanten(ActionEvent event) {
    	btnOverzichtBestellingen.getStyleClass().clear();
    	btnBetaalmethodes.getStyleClass().clear();
    	btnOverzichtKlanten.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	KlantenScherm controller = new KlantenScherm();
    	vbox.getChildren().add(controller.geefNode());
    }
    
    @FXML
    void betaalmethodesBeheren(ActionEvent event) {
    	btnOverzichtBestellingen.getStyleClass().clear();
    	btnOverzichtKlanten.getStyleClass().clear();
    	btnBetaalmethodes.getStyleClass().add("button-selected");
    	vbox.getChildren().clear();
    	BetaalmethodScherm controller = new BetaalmethodScherm();
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
        
        mbName.setText(GebruikerHolder.getInstance().getNaam());
	}
    
}