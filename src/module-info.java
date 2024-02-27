module delaware_vc02 {
//	exports test;
//	exports gui;
	//exports main;
//	exports domein;

//	requires java.desktop;
//	requires javafx.base;
//	requires javafx.controls;
//	requires javafx.fxml;
//	requires javafx.graphics;
//	requires junit;
//	requires org.junit.jupiter.api;
//	requires org.junit.jupiter.params;
	
	
//	opens main to javafx.fxml, javafx.graphics;
//	opens gui to javafx.fxml, javafx.graphics;
	
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.junit.jupiter.api;
	requires org.junit.jupiter.params;
	
	opens main to javafx.fxml, javafx.graphics;
	opens gui to javafx.fxml, javafx.graphics;
	
}