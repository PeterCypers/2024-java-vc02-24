package main;

import domein.Adres;
import domein.Gebruiker;
import domein.Rol;
import gui.HoofdSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class HoofdSchermOpstart extends Application{
	
	@Override
    public void start(Stage stage) {
		Gebruiker gebruiker = new Gebruiker(1, Rol.LEVERANCIER, 
				"eerste@hotmail.com", "wachtwoord", "Bas", true, 
				new Adres("Belgie","Gent", "9000", "straat", "10"));
		Scene scene = new Scene(new HoofdSchermController(gebruiker));
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
        Application.launch(HoofdSchermOpstart.class, args);
    }
}
