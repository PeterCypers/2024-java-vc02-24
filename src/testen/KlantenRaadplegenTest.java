package testen;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.Adres;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import service.klant.KlantDaoJpa;
import service.klant.KlantServiceDbImpl;

@ExtendWith(MockitoExtension.class)
class KlantenRaadplegenTest {

	@Mock
	private KlantDaoJpa klantDaoJpa;
	
	@InjectMocks
	private KlantServiceDbImpl klantServiceDbImpl;
	
	static List<Klant> klanten = Arrays.asList(
			new Klant(null, "klant1@hotmail.com", "1234", "Bas Stokmans", true, new Adres("Land", "Stad", "1234", "Straat", "1"), "+32123456789"),
			new Klant(null, "klant2@hotmail.com", "1234", "Tiemen Deroose", true, new Adres("Land", "Stad", "12345", "Straat", "20"), "+32123456789")
	);
	
	static Leverancier gebruiker = new Leverancier(null, "jasper.vandenbroucke@hotmail.com", "1234", "Jasper Vandenbroucke", true);
	
	@Test
	public void test_raadplegenKlanten() {
		Mockito.when(klantDaoJpa.vindPerLeverancier(gebruiker)).thenReturn(klanten);

		List<Klant> klanten = klantServiceDbImpl.getKlanten(gebruiker);

		Assertions.assertTrue(klanten.size() != 0);

		Assertions.assertEquals("Bas Stokmans", klanten.get(0).gebruikersnaamProperty().get());
		Assertions.assertEquals("Tiemen Deroose", klanten.get(1).gebruikersnaamProperty().get());

		Mockito.verify(klantDaoJpa).vindPerLeverancier(gebruiker);
	}
}
