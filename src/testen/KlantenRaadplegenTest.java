package testen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.Adres;
import domein.Bestelling;
import domein.BetalingsStatus;
import domein.OrderStatus;
import domein.Product;
import domein.Stock;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import domein.gebruiker.Rol;
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
	
	static List<Product> producten = Arrays.asList(
			new Product("productA", 1000, Stock.STOCK, 500.0),
			new Product("productB", 50000, Stock.STOCK, 4.99),
			new Product("productC", 2100, Stock.STOCK, 19.99)
	);
	
	static List<Bestelling> bestellingen = Arrays.asList(
			new Bestelling(1, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0), gebruiker, producten),
			new Bestelling(2, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0), gebruiker, producten),
			new Bestelling(3, LocalDate.now(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1), gebruiker, producten),
			new Bestelling(4, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(0), gebruiker, producten),
			new Bestelling(5, LocalDate.now(), OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, klanten.get(1), gebruiker, producten),
			new Bestelling(6, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(1), gebruiker, producten),
			new Bestelling(7, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD, klanten.get(0), gebruiker, producten)
	);
	
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
