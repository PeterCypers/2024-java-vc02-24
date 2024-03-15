package gui;

import java.io.IOException;
import java.util.NoSuchElementException;

import domein.AanmeldController;
import domein.BestellingController;
import domein.Gebruiker;
import domein.GebruikerHolder;
import jakarta.persistence.EntityNotFoundException;
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

    	System.out.printf("test%nEmail: %s | Wachtwoord: %s%n", txfEmail.getText(), pwdField.getText());
    	
    	try {
    		meldAan(txfEmail.getText(), pwdField.getText());
    		Gebruiker aangemeldeGebruiker = GebruikerHolder.getInstance();
    		System.out.printf("%s gevonden met naam: %s", aangemeldeGebruiker.getClass().getSimpleName(), 
    				aangemeldeGebruiker.getNaam());
    		toonVolgendScherm();
    	} catch (EntityNotFoundException enfe) {
    		System.out.println("Gebruiker niet gevonden, controleer de email en wachtwoord combinatie");
    		lblError.setText("Gebruiker niet gevonden, controleer de email en wachtwoord combinatie");
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
    /** method: ac.meldGebruikerAan(x,y) instantiates singleton GebruikerHolder
     * 
     * @param emailadres = testfield input
     * @param wachtwoord = passwordfield input
     * @return GebruikerHolder instance of Gebruiker
     */
    private void meldAan(String emailadres, String wachtwoord) {
    	ac.meldGebruikerAan(emailadres, wachtwoord);
    }
    
    private void toonVolgendScherm() {
    	switch (GebruikerHolder.getInstance().getRol()) {		
    	case LEVERANCIER -> {
    		HoofdSchermController hoofdScherm = new HoofdSchermController(); //veranderd als bc gebruiker bij houd
    		Stage stage = (Stage) this.getScene().getWindow();
    		stage.setResizable(true);
    		stage.setScene(new Scene(hoofdScherm));
		}
    	case ADMINISTRATOR -> {
    		AdminSchermController adminScherm = new AdminSchermController();
    		Stage stage = (Stage) this.getScene().getWindow();
    		stage.setResizable(true);
    		stage.setScene(new Scene(adminScherm));
    	}
		default -> throw new IllegalArgumentException("Unexpected value: " + GebruikerHolder.getInstance().getRol());
		}
    	
    }
    
}
