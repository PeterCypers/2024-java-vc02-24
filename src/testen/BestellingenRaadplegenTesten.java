package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Adres;
import domein.BestellingBeheerder;
import domein.Gebruiker;

public class BestellingenRaadplegenTesten {
	
	private BestellingBeheerder bb;
	
	@BeforeEach
	public void beforeEach() {
		Gebruiker g = new Gebruiker(9, null, "jan.jansens@hotmail.com", 
				"java", "Jan Jansens", true, 
				new Adres("Belgie", "Gent", "9000", "Veldstraat", "56"));
		this.bb = new BestellingBeheerder(g);
	}
	
	@Test
	public void test_BestellingenRaadplegen_CorrectVerloop() {
		Assertions.assertNotNull(bb.getBestellingen());
	}

}
