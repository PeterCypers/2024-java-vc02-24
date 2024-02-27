package test;

//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.AanmeldController;
import domein.Gebruiker;

public class GebruikerTest {
	
	private AanmeldController ac;
	
	@BeforeEach
	public void beforeEach() {
		this.ac = new AanmeldController();
	}
	
	@ParameterizedTest
	@CsvSource({
		"onbekende@mail.com,paswoord123", 
		"emailadres,paswoord",
		"123,456"
	})
	@NullAndEmptySource
	public void test_GebruikerAanmelden_OngeldigeGegevens(String emailadres, String wachtwoord) {
		assertThrows(IllegalArgumentException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
	}
	
	// TODO: gebruik geldige, inactieve gebruiker
	@Test
	public void test_GebruikerAanmelden_GebruikerNietActief() {
		String emailadres = "";
		String wachtwoord = "";
		
		assertThrows(IllegalArgumentException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
	}
	
	// TODO: gebruik geldige gebruiker met rol = klant
	@Test
	public void test_GebruikerAanmelden_GebruikerIsKlant() {
		String klantEmailadres = "";
		String klantWachtwoord = "";
		
		assertThrows(IllegalArgumentException.class, () -> ac.meldGebruikerAan(klantEmailadres, klantWachtwoord));
	}
	
	// TODO: geldige gebruikers toevoegen na aanmaken db
	@ParameterizedTest
	@CsvSource({
		",",
		",",
		","
	})
	public void test_GebruikerAanmelden_GeldigeGegevens(String emailadres, String wachtwoord) {
		Gebruiker gebruiker = ac.meldGebruikerAan(emailadres, wachtwoord);
		assertNotNull(gebruiker);
	}
}

