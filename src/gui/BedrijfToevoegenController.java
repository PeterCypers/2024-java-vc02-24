package gui;
import java.io.IOException;

import domein.Bedrijf;
import domein.BedrijfBuilder;
import domein.BedrijfController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class BedrijfToevoegenController extends VBox {


    @FXML
    private Button btnToevoegen;

    @FXML
    private Label lbError;

    @FXML
    private TextField txfBTWNummer;

    @FXML
    private TextField txfEmailadres;
    
    @FXML
    private TextField txfTelefoonNr;

    @FXML
    private TextField txfKlantEmailadres;

    @FXML
    private TextField txfKlantNaam;

    @FXML
    private TextField txfKlantWachtwoord;

    @FXML
    private TextField txfLand;

    @FXML
    private TextField txfLeverancierEmailadres;

    @FXML
    private TextField txfLeverancierNaam;

    @FXML
    private TextField txfLeverancierWachtwoord;

    @FXML
    private TextField txfLogo;

    @FXML
    private TextField txfNaam;

    @FXML
    private TextField txfPostcode;

    @FXML
    private TextField txfSector;

    @FXML
    private TextField txfStad;

    @FXML
    private TextField txfStraat;

    @FXML
    private TextField txfStraatnummer;
    
    private BedrijfController bc;
    
    @FXML
    void toevoegen(ActionEvent event) {
    	try {
        	Bedrijf nieuwBedrijf = maakBedrijf();
	    	bc.voegBedrijfToe(nieuwBedrijf);
	    	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Bedrijf werd succesvol toegevoegd");
			alert.setHeaderText(String.format("Het nieuwe bedrijf \"%s\" werd succesvol toegevoegd", nieuwBedrijf.getNaam()));
			alert.showAndWait();
	    	
			Window window = this.getScene().getWindow();
			window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    	} catch(IllegalArgumentException iae) {
    		lbError.setText(iae.getMessage());
    	} catch(Exception e) {
    		lbError.setText("Bedrijfsnaam is al in gebruik");
    		e.printStackTrace();
    	}
    }
    
    public BedrijfToevoegenController(BedrijfController bc) {
    	this.bc = bc;
    	buildGui();
    }
    
    private void buildGui() {
    	Stage stage = new Stage();
    	Scene scene = new Scene(this);
    	stage.setScene(scene);
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BedrijfToevoegen.fxml"));
    	loader.setRoot(this);
    	loader.setController(this);
    	
        try {
        	loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    	
    	stage.setScene(scene);
    	stage.show();
	}
    
    private Bedrijf maakBedrijf() {
    	BedrijfBuilder bedrijfBuilder = new BedrijfBuilder();
    	
    	bedrijfBuilder
    		.setNaam(txfNaam.getText())
    		.setLogo(txfLogo.getText())
            .setSector(txfSector.getText())
            .setLand(txfLand.getText())
            .setStad(txfStad.getText())
            .setPostcode(txfPostcode.getText())
            .setStraat(txfStraat.getText())
            .setStraatNummer(txfStraatnummer.getText())
            .setEmailadres(txfEmailadres.getText())
            .setTelefoonNr(txfTelefoonNr.getText())
            .setBtwNr(txfBTWNummer.getText());
            
        bedrijfBuilder
            .setLgNaam(txfLeverancierNaam.getText())
            .setLgEmailadres(txfLeverancierEmailadres.getText())
            .setLgWachtwoord(txfLeverancierWachtwoord.getText())
            .setLgLand(txfLand.getText())
            .setLgStad(txfStad.getText())
            .setLgPostcode(txfPostcode.getText())
            .setLgStraat(txfStraat.getText())
            .setLgStraatNummer(txfStraatnummer.getText());
            
        bedrijfBuilder
            .setKgNaam(txfKlantNaam.getText())
            .setKgEmailadres(txfKlantEmailadres.getText())
            .setKgWachtwoord(txfKlantWachtwoord.getText())
            .setKgLand(txfLand.getText())
            .setKgStad(txfStad.getText())
            .setKgPostcode(txfPostcode.getText())
            .setKgStraat(txfStraat.getText())
            .setKgStraatNummer(txfStraatnummer.getText());
        
        bedrijfBuilder
        	.setKNaam(txfNaam.getText())
        	.setKLogoPad(txfLogo.getText())
        	.setKEmailadres(txfKlantEmailadres.getText())
        	.setKTelefoonNr(txfTelefoonNr.getText())
            .setKLand(txfLand.getText())
            .setKStad(txfStad.getText())
            .setKPostcode(txfPostcode.getText())
            .setKStraat(txfStraat.getText())
            .setKStraatNummer(txfStraatnummer.getText());
    	
    	return bedrijfBuilder.build();
    }
}

