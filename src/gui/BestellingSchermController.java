package gui;

import java.io.IOException;

import domein.Gebruiker;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class BestellingSchermController extends Pane {
	public BestellingSchermController(Gebruiker gebruiker) {
    	buildGui();
	}
	
    private void buildGui() {
    	FXMLLoader loader = new FXMLLoader(this.getClass().getResource("BestellingScherm.fxml"));
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
}