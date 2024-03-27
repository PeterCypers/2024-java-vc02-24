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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private PasswordField pwdFieldKlant;

    @FXML
    private TextField txfLand;

    @FXML
    private TextField txfLeverancierEmailadres;

    @FXML
    private TextField txfLeverancierNaam;

    @FXML
    private PasswordField pwdFieldLeverancier;

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
    
    @FXML
    private TextField txfRekeningnummer;
    
    private BedrijfController bc;
    BedrijfBuilder bedrijfBuilder;
    
    @FXML
    void toevoegen(ActionEvent event) {
    	try {
        	Bedrijf nieuwBedrijf = bedrijfBuilder.build();
	    	bc.voegBedrijfToe(nieuwBedrijf);
	    	
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Bedrijf werd succesvol toegevoegd");
			alert.setHeaderText(String.format("Het nieuwe bedrijf \"%s\" werd succesvol toegevoegd", nieuwBedrijf.getNaam()));
			alert.showAndWait();
	    	
			Window window = this.getScene().getWindow();
			window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    	} catch(IllegalArgumentException iae) {
    		if (lbError.getText() == "") lbError.setText(iae.getMessage());
    	} catch(Exception e) {
    		lbError.setText("Bedrijfsnaam is al in gebruik");
    		e.printStackTrace();
    	}
    }
    
    public BedrijfToevoegenController(BedrijfController bc) {
    	this.bc = bc;
    	this.bedrijfBuilder = new BedrijfBuilder();
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
        
        voegFocusListenersToe();
    	
    	stage.setScene(scene);
    	stage.show();
	}
    
    private void voegFocusListenersToe() {
    	// bedrijf
    	txfNaam.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setNaam(txfNaam.getText())); } 
    	});
    	txfLogo.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setLogo(txfLogo.getText())); } 
    	});
    	txfSector.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setSector(txfSector.getText())); } 
    	});
    	txfEmailadres.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setEmailadres(txfEmailadres.getText())); } 
    	});
    	txfBTWNummer.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setBtwNr(txfBTWNummer.getText())); } 
    	});
    	txfRekeningnummer.focusedProperty().addListener((obs, oldVal, newVal) -> {
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setRekeningnummer(txfRekeningnummer.getText())); }
    	});
    	
    	// leverancier
    	txfLeverancierNaam.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setLNaam(txfLeverancierNaam.getText())); } 
    	});
    	txfLeverancierEmailadres.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setLEmailadres(txfLeverancierEmailadres.getText())); } 
    	});
    	pwdFieldLeverancier.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setLWachtwoord(pwdFieldLeverancier.getText())); } 
    	});
    	
    	// klant
    	txfKlantNaam.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setKNaam(txfKlantNaam.getText())); } 
    	});
    	txfKlantEmailadres.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setKEmailadres(txfKlantEmailadres.getText())); } 
    	});
    	pwdFieldKlant.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { setVeld(() -> bedrijfBuilder.setKWachtwoord(pwdFieldKlant.getText())); } 
    	});
    	
    	// shared
    	txfLand.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
	    			bedrijfBuilder.setLand(txfLand.getText());
	    			bedrijfBuilder.setKLand(txfLand.getText());
	    		});
    		}
    	});
    	txfStad.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
    				bedrijfBuilder.setStad(txfStad.getText());
        			bedrijfBuilder.setKStad(txfStad.getText());
	    		});
    		}
    	});
    	txfPostcode.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
    				bedrijfBuilder.setPostcode(txfPostcode.getText());
    				bedrijfBuilder.setKPostcode(txfPostcode.getText());
	    		});
    		}
    	});
    	txfStraat.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
    				bedrijfBuilder.setStraat(txfStraat.getText());
    				bedrijfBuilder.setKStraat(txfStraat.getText());
	    		});
    		}
    	});
    	txfStraatnummer.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
    				bedrijfBuilder.setStraatNummer(txfStraatnummer.getText());
    				bedrijfBuilder.setKStraatNummer(txfStraatnummer.getText());
	    		});
    		}
    	});
    	txfTelefoonNr.focusedProperty().addListener((obs, oldVal, newVal) -> { 
    		if (!newVal) { 
    			setVeld(() -> {
        			bedrijfBuilder.setTelefoonNr(txfTelefoonNr.getText());
        			bedrijfBuilder.setKTelefoonNr(txfTelefoonNr.getText());
	    		});
    		}
    	});
    	
    	lbError.setText("");
    }
    
    private void setVeld(Runnable builderSetter) {
    	try {
        	builderSetter.run();
        	lbError.setText("");
    	} catch(IllegalArgumentException iae) {
    		lbError.setText(iae.getMessage());
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

