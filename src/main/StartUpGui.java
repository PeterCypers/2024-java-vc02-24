package main;

import domein.AanmeldController;
import gui.AanmeldSchermController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUpGui extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			//DomeinController dc = new DomeinController();
			AanmeldController ac = new AanmeldController();
			
			Scene scene = new Scene(new AanmeldSchermController(ac));
			
//			primaryStage.setTitle("TipCalculator");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
