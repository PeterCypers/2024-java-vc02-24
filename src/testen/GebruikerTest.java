package testen;

//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.AanmeldController;
import domein.Adres;
import domein.Gebruiker;
import domein.Rol;
import jakarta.persistence.EntityNotFoundException;
import service.gebruiker.GebruikerDaoJpa;
import service.gebruiker.GebruikerServiceDbImpl;

@ExtendWith(MockitoExtension.class)
public class GebruikerTest {
	
	@Mock
	private GebruikerDaoJpa gebruikerDaoJpaMock;
	
	@InjectMocks
	private GebruikerServiceDbImpl gebruikerServiceDbImpl;
	
	private AanmeldController ac;
	
	@BeforeEach
	public void beforeEach() {
		this.ac = new AanmeldController();
	}
	
	/********/
	/*DOMEIN*/
	/********/
	
    private static Stream<String[]> gegevensOngeldigeGebruikers() {
    	return Stream.of(
    			new String[]{"vijfde@hotmail.com","654",null},
    			new String[]{"vijfde@hotmail.com",null,"Jos"},
    			new String[]{null,"654","Jos"},
    			new String[]{"vijfde@hotmail.com","654",""},
    			new String[]{"vijfde@hotmail.com","","Jos"},
    			new String[]{"","654","Jos"},
    			new String[]{"vijfde@hotmail","654","Jos"},
    			new String[]{"vijfde@hotmail.com","65 4","Jos"}
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
		assertThrows(EntityNotFoundException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
	}
	
	@Test
	public void test_GebruikerAanmelden_GebruikerNietActief() {
		String emailadres = "vijfde@hotmail.com";
		String wachtwoord = "654";
		
		assertThrows(EntityNotFoundException.class, () -> ac.meldGebruikerAan(emailadres, wachtwoord));
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
	
	/*********/
	/*SERVICE*/
	/*********/
	
	static List<Gebruiker> gebruikers = Arrays.asList(
			new Gebruiker(1, Rol.LEVERANCIER, "eerste@hotmail.com", "1234", "Jasper Vandenbroucke", true, new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Gebruiker(2, Rol.LEVERANCIER, "tweede@hotmail.com", "1234", "Tiemen Deroose", true, new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Gebruiker(3, Rol.LEVERANCIER, "derde@hotmail.com", "1234", "Mohisha Van Damme", true, new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Gebruiker(4, Rol.LEVERANCIER, "vierde@hotmail.com", "1234", "Peter Cypers", true, new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Gebruiker(5, Rol.LEVERANCIER, "vijfde@hotmail.com", "1234", "Bas Stokmans", true, new Adres("Land", "Stad", "1234", "Straat", "1"))
	);
	
	static Stream<Arguments> onbekendeLogin() {
		return Stream.of(
				Arguments.of("eerste", "1234", true, Rol.LEVERANCIER),
				Arguments.of("#123@gmail.com", "123", true, Rol.ADMINISTRATOR)
		);
	}
	
	static Stream<Arguments> inactieveLogin(){
		return Stream.of(
				Arguments.of("eerste@hotmail.com", "1234", false, Rol.LEVERANCIER),
				Arguments.of("tweede@hotmail.com", "1234", false, Rol.ADMINISTRATOR)
		);
	}
	
	static Stream<Arguments> klantLogin(){
		return Stream.of(
				Arguments.of("derde@hotmail.com", "1234", true, Rol.KLANT)
		);
	}
	
	@Test
	public void test_getGebruikers() {
		Mockito.when(gebruikerDaoJpaMock.findAll()).thenReturn(gebruikers);
		
		List<Gebruiker> lijst = gebruikerServiceDbImpl.getGebruikers();
		assertTrue(lijst.size() > 0);
		
		Mockito.verify(gebruikerDaoJpaMock).findAll();
	}
	
	@ParameterizedTest
	@MethodSource("onbekendeLogin")
	public void test_meldGebruikerAan_onbenkendeLogin(String email, String wachtwoord, boolean isActief, Rol rol) {
		Mockito.when(gebruikerDaoJpaMock.meldAan(email, wachtwoord)).thenThrow(EntityNotFoundException.class);
		
		assertThrows(EntityNotFoundException.class, () -> gebruikerServiceDbImpl.meldGebruikerAan(email, wachtwoord));
	}
	
	@ParameterizedTest
	@MethodSource("inactieveLogin")
	public void test_meldGebruikerAan_inactieveGebruiker(String email, String wachtwoord, boolean isActief, Rol rol) {
		Mockito.when(gebruikerDaoJpaMock.meldAan(email, wachtwoord)).thenReturn(
				new Gebruiker(1, rol, email, wachtwoord, "Test persoon", isActief, 
						new Adres("Land", "Stad", "1234", "Straat", "1"))
		);
		
		assertThrows(IllegalArgumentException.class, () -> gebruikerServiceDbImpl.meldGebruikerAan(email, wachtwoord));
	}
	
	@ParameterizedTest
	@MethodSource("klantLogin")
	public void test_meldGebruikerAan_klantLogin(String email, String wachtwoord, boolean isActief, Rol rol) {
		Mockito.when(gebruikerDaoJpaMock.meldAan(email, wachtwoord)).thenReturn(
				new Gebruiker(1, rol, email, wachtwoord, "Test persoon", isActief, 
						new Adres("Land", "Stad", "1234", "Straat", "1"))
		);
		
		assertThrows(IllegalArgumentException.class, () -> gebruikerServiceDbImpl.meldGebruikerAan(email, wachtwoord));
	}
	
}

