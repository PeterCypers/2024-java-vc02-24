open module Java_vc02_24 {
		//FX
		requires javafx.base;
		requires javafx.controls;
		requires javafx.fxml;
		requires javafx.graphics;
		//Persistence
		requires jakarta.persistence;
		requires java.sql;
		requires java.instrument;
		//Unit tests
		requires org.junit.jupiter.api;
		requires org.mockito.junit.jupiter;
		requires org.mockito;
		requires org.junit.jupiter.params;
}