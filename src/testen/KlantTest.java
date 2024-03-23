package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import domein.Adres;
import domein.gebruiker.Klant;

class KlantTest {

	Klant klant;
	
	static Stream<Arguments> geldigeData() {
		return Stream.of(
			Arguments.of("klant1@hotmail.com", "1234", "Bas Stokmans", new Adres("Land", "Stad", "1234", "Straat", "1"), "+32123456789"),
			Arguments.of("klant2@hotmail.com", "1234", "Tiemen Deroose", new Adres("Land", "Stad", "12345", "Straat", "20"), "+32123456789"));
	}
	static Stream<String> naam_fout() {
		return Stream.of("B4s", "T0 M_n", "=", "5325", "*\u00EB%\u20AC", "P/ter");
	}
	static Stream<String> contact_fout() {
	    return Stream.of("email@hotmail", "klant2@hotmail", "klant3@gmail.com", "klant4@hotmail.be", "@hotmail.com", "klant5hotmail.com");
	}
    static Stream<String> telefoonnummer_fout() {
        return Stream.of("+3201234567890", "+32 123 456 789", "+32(0)1234567890", "+32.123.456.7890", "+32123-4567890", "+32 12345678901");
    }

	
	@ParameterizedTest
	@MethodSource("geldigeData")
	void nieuweKlant_allesGeldig(String email, String wachtwoord, String naam, Adres adres, String telefoonnummer) {
		klant = new Klant(null, email, wachtwoord, naam, true, adres, telefoonnummer);
		
		assertEquals(naam, klant.getNaam());
		assertEquals(email, klant.getEmail());
		assertEquals(adres, klant.getAdres());
	}
	@ParameterizedTest
	@NullAndEmptySource
	@MethodSource("naam_fout")
	void nieuweKlant_naamOngeldig(String naam) {
		assertThrows(IllegalArgumentException.class, () -> new Klant(null, "klant1@hotmail.com", "1234", naam, true, new Adres("Land", "Stad", "1234", "Straat", "1"), "+32123456789")); 
	}
	
	@ParameterizedTest
	@NullSource
	void nieuweKlant_adresIsNull(Adres adres) {
		assertThrows(IllegalArgumentException.class, () -> new Klant(null, "klant1@hotmail.com", "1234", "Bas Stokmans", true, adres, "+32123456789")); 
	}
	@ParameterizedTest
	//@MethodSource("contact_fout")
	@NullAndEmptySource
	void nieuweKlant_contactOngeldig(String contact) {
	    assertThrows(IllegalArgumentException.class, () -> new Klant(null, contact, "1234", "Voorbeeld Klant", true, new Adres("Land", "Stad", "12345", "Straat", "20"), "+32123456789"));
	}
    @ParameterizedTest
    @MethodSource("telefoonnummer_fout")
    void nieuweKlant_telefoonnummerOngeldig(String telefoonnummer) {
        assertThrows(IllegalArgumentException.class, () -> new Klant(null, "klant1@hotmail.com", "1234", "Voorbeeld Klant", true, new Adres("Land", "Stad", "12345", "Straat", "20"), telefoonnummer));
    }
	

}
