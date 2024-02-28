package testen;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;

import domein.Adres;
import domein.Klant;

class KlantTest {

	Klant klant;
	
	static Stream<Arguments> geldigeData() {
		return Stream.of(
			Arguments.of("Bas Stokmans","klant1@hotmail.com", new Adres("Land", "Stad", "1234", "Straat", "1")),
			Arguments.of("Tiemen Deroose","klant2@hotmail.com", new Adres("Land", "Stad", "12345", "Straat", "20")));
	}
	static Stream<String> naam_fout() {
		return Stream.of("B4s", "T0 M_n", "=", "5325", "*¨%£", "P/ter");
	}
	static Stream<String> contact_fout() {
	    return Stream.of("email@hotmail", "klant2@hotmail", "klant3@gmail.com", "klant4@hotmail.be", "@hotmail.com", "klant5hotmail.com");
	}
	
	@ParameterizedTest
	@MethodSource("geldigeData")
	void nieuweKlant_allesGeldig(String naam, String contact, Adres adres) {
		klant = new Klant(naam, contact, adres);
		
		assertEquals(naam, klant.getNaam().getValue());
		assertEquals(contact, klant.getContactgegevens());
		assertEquals(adres, klant.getAdres());
	}
	@ParameterizedTest
	@NullAndEmptySource
	@MethodSource("naam_fout")
	void nieuweKlant_naamOngeldig(String naam) {
		assertThrows(IllegalArgumentException.class, () -> new Klant(naam, "klant1@hotmail.com", new Adres("Land", "Stad", "1234", "Straat", "1"))); 
	}
	
	@ParameterizedTest
	@NullSource
	void nieuweKlant_adresIsNull(Adres adres) {
		assertThrows(IllegalArgumentException.class, () -> new Klant("Bas Stokmans","klant1@hotmail.com", adres)); 
	}
	@ParameterizedTest
	@MethodSource("contact_fout")
	void nieuweKlant_contactOngeldig(String contact) {
	    assertThrows(IllegalArgumentException.class, () -> new Klant("Voorbeeld Klant", contact, new Adres("Land", "Stad", "12345", "Straat", "20")));
	}
	

}
