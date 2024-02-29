package main;

import domein.*;
import gui.BestellingsSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class BestellingenSchermOpstart extends Application {
	
	@Override
    public void start(Stage stage) {
		Gebruiker gebruiker = new Gebruiker(1,null, 
				"mijn@email", "wachtwoord", "Bas", true, 
				new Adres("Belgie","Gent", "9000", "straat", "10"));
		BestellingController bc = new BestellingController(gebruiker);
        Scene scene = new Scene(new BestellingsSchermController(bc));
        stage.setScene(scene);
        stage.setTitle("B2B Portal");
        
         // The stage will not get smaller than its preferred (initial) size.
        stage.setOnShown((WindowEvent t) -> {
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        });
        stage.show();
    }

	public static void main(String... args) {
        Application.launch(BestellingenSchermOpstart.class, args);
    }
}
