package main;

import domein.AanmeldController;
import domein.BetalingController;
import gui.AanmeldSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			AanmeldController ac = new AanmeldController();
			
			/*NEW*/
			BetalingController bc = new BetalingController();
			
			Scene scene = new Scene(new AanmeldSchermController(ac));
			primaryStage.setScene(scene);
			primaryStage.setTitle("B2B Portal");
			primaryStage.setResizable(false);
			primaryStage.show();
			/*NEW*/
			System.out.println("Verwerken betalingen (StartUpGUI)");
			bc.verwerkBetalingen();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
