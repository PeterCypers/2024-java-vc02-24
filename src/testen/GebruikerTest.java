package testen;

//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import domein.AanmeldController;
import domein.Adres;
import domein.Gebruiker;
import domein.Rol;

public class GebruikerTest {
	
	private AanmeldController ac;
	
	@BeforeEach
	public void beforeEach() {
		this.ac = new AanmeldController();
	}
	
    private static Stream<String[]> gegevensOngeldigeGebruikers() {
    	return Stream.of(
    			new String[]{"vijfde@hotmail.com","654",null},
    			new String[]{"vijfde@hotmail.com",null,"Jos"},
    			new String[]{null,"654","Jos"},
    			new String[]{"vijfde@hotmail.com","654",""},
    			new String[]{"vijfde@hotmail.com","","Jos"},
    			new String[]{"","654","Jos"},
    			new String[]{"vijfde@hotmail","654","Jos"},
    			new String[]{"vijfde@hotmail.com","65 4","Jos"},
    			new String[]{"vijfde@hotmail.com","654","Jo s"}
    	);
    }
	
    private static Stream<String[]> gegevensOnbekendeGebruikers() {
        return Stream.of(
        		new String[]{null,null},
        		new String[]{"emailadres",null},
        		new String[]{null,"paswoord123"},
        		new String[]{"",""},
        		new String[]{"emailadres",""},
                new String[]{"", "paswoord123"},
                new String[]{"onbekende@mail.com", "paswoord123"},
                new String[]{"emailadres", "paswoord"},
                new String[]{"123", "456"}
        );
    }
    
	@ParameterizedTest
    @MethodSource("gegevensOngeldigeGebruikers")
	public void test_GebruikerAanmaken_verkeerdeGegevens(String email, String wachtwoord, String naam) {
		assertThrows(IllegalArgumentException.class, 
				() -> new Gebruiker(
						5, 
						Rol.LEVERANCIER, 
						email, 
						wachtwoord,
						naam, 
						true, 
						new Adres("Netherlands","Rotterdam","3011","Wolfshoek","7")
				));
	}
	
	@ParameterizedTest
	@MethodSource("gegevensOnbekendeGebruikers")
	public void test_GebruikerAanmelden_OngeldigeGegevens(String emailadres, String wachtwoord) {
		assertThrows(NoSuchElementException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
	}
	
	@Test
	public void test_GebruikerAanmelden_GebruikerNietActief() {
		String emailadres = "vijfde@hotmail.com";
		String wachtwoord = "654";
		
		assertThrows(IllegalArgumentException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
	}
	
	@Test
	public void test_GebruikerAanmelden_GebruikerIsKlant() {
		String klantEmailadres = "vierde@hotmail.com";
		String klantWachtwoord = "321";
		
		assertThrows(IllegalArgumentException.class, () -> ac.meldGebruikerAan(klantEmailadres, klantWachtwoord));
	}
	
	@ParameterizedTest
	@CsvSource({
		"eerste@hotmail.com,1234",
		"tweede@hotmail.com,password",
		"derde@hotmail.com,123abc"
	})
	public void test_GebruikerAanmelden_GeldigeGegevens(String emailadres, String wachtwoord) {
		Gebruiker gebruiker = ac.meldGebruikerAan(emailadres, wachtwoord);
		assertNotNull(gebruiker);
	}
}

