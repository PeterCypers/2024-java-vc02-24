package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.Adres;

class AdresTest{

	Adres adres;
	
	@ParameterizedTest
	@CsvSource({
		"Belgie,Gent,9000,Valentin Vaerwyckweg,1",
		"Verenigde Staten,New York,9000,Valentin Vaerwyckweg,1"
	})
	void nieuwAdres_allesGeldig(String land, String stad, String postcode, String straat, String straatNr) {
	  
	    Adres adres = new Adres(land, stad, postcode, straat, straatNr);
	    
	    assertEquals(adres.getLand(), land);
	    assertEquals(adres.getStad(), stad);
	    assertEquals(adres.getPostcode(), postcode);
	    assertEquals(adres.getStraat(), straat);
	    assertEquals(adres.getStraatNr(), straatNr);
	}
	@ParameterizedTest
	@NullAndEmptySource
	void nieuwAdres_landOngeldig(String land) {
	    assertThrows(IllegalArgumentException.class, () -> new Adres(land, "Gent", "9000", "Valentin Vaerwyckweg", "1"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"^$%,stad", "1234984Stad"})
	void nieuwAdres_stadOngeldig(String stad) {
		assertThrows(IllegalArgumentException.class, () -> new Adres("Belgi\u00EB", stad, "9000", "Valentin Vaerwyckweg", "1"));
	}
	@ParameterizedTest
	@NullAndEmptySource
	void nieuwAdres_postcodeLeeg(String postcode) {
		assertThrows(IllegalArgumentException.class, () -> new Adres("Belgi\u00EB", "Gent", postcode, "Valentin Vaerwyckweg", "1"));
	}
	@ParameterizedTest
	@NullAndEmptySource
	void nieuwAdres_straatLeeg(String straat) {
	    assertThrows(IllegalArgumentException.class, () -> new Adres("Belgi\u00EB", "Gent", "9000", straat, "1"));
	}
	
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"^$%,straat", "S", "7894612straat"})
	void nieuwAdres_straatnummerOngeldig(String straatnummer) {
	    assertThrows(IllegalArgumentException.class, () -> new Adres("Belgi\u00EB", "Gent", "9000", "Valentin Vaerwyckweg", straatnummer));
	}


	

}
