package gui;

import java.io.IOException;
import java.util.NoSuchElementException;

import domein.AanmeldController;
import domein.BestellingController;
import domein.Gebruiker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AanmeldSchermController extends Pane {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txfEmail;

    //txField vervangen door wachtwoord field (verborgen inputs)
    @FXML
    private PasswordField pwdField;
    
    @FXML
    private Label lblError;

    
    @FXML
    void btnLoginOnAction(ActionEvent event) {
    	//TODO meldAan returns Gebruiker, vermoed transitie naar volgend scherm en neemt gebruiker mee
    	System.out.printf("test%nEmail: %s | Wachtwoord: %s%n", txfEmail.getText(), pwdField.getText());
    	
    	try {
    		Gebruiker g = meldAan(txfEmail.getText(), pwdField.getText());
    		System.out.printf("%s gevonden met naam: %s", g.getClass().getSimpleName(), g.getNaam());
    		toonVolgendScherm(g);
    	} catch (NoSuchElementException nsee) {
    		System.out.println("Gebruiker niet gevonden, controleer of email/wachtwoord juist zijn.");
    		lblError.setText("Gebruiker niet gevonden, controleer of email/wachtwoord juist zijn.");
    	} catch (IllegalArgumentException iae) {
    		lblError.setText(iae.getMessage());
    	}
    }
    
    @FXML
    void pwdFieldClearErrorOnClick(MouseEvent event) {
    	lblError.setText("");
    }

    @FXML
    void txfEmailClearErrorOnClick(MouseEvent event) {
    	lblError.setText("");
    }
    
    private final AanmeldController ac;
    

    public AanmeldSchermController(AanmeldController ac) {
        this.ac = ac;
        buildGui();
    }
    
    private void buildGui() {
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("AanmeldScherm.fxml"));
    	loader.setController(this);
    	loader.setRoot(this);
    	try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("Het scherm kan niet getoond worden");
		}
    }
    
    private Gebruiker meldAan(String emailadres, String wachtwoord) {
    	return ac.meldGebruikerAan(emailadres, wachtwoord);
    }
    
    private void toonVolgendScherm(Gebruiker gebruiker) {
    	BestellingController bc = new BestellingController(gebruiker);
    	BestellingsSchermController bestellingSchermController = new BestellingsSchermController(bc);
		Stage stage = (Stage) this.getScene().getWindow();
		stage.setScene(new Scene(bestellingSchermController));
    }
    
}
