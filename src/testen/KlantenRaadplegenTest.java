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
import domein.Gebruiker;
import domein.Klant;
import domein.OrderStatus;
import domein.Product;
import domein.Rol;
import domein.Stock;
import service.klant.KlantDaoJpa;
import service.klant.KlantServiceDbImpl;

@ExtendWith(MockitoExtension.class)
class KlantenRaadplegenTest {

	@Mock
	private KlantDaoJpa klantDaoJpa;
	
	@InjectMocks
	private KlantServiceDbImpl klantServiceDbImpl;
	
	static List<Klant> klanten = Arrays.asList(
			new Klant("Bas Stokmans","logobedrijf.png", "+32123456789","klant1@hotmail.com", new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Klant("Tiemen Deroose","logobedrijf.png", "+32123456789","klant2@hotmail.com", new Adres("Land", "Stad", "12345", "Straat", "20"))
	);
	
	static List<Product> producten = Arrays.asList(
			new Product("productA", 1000, Stock.STOCK, 500.0),
			new Product("productB", 50000, Stock.STOCK, 4.99),
			new Product("productC", 2100, Stock.STOCK, 19.99)
	);
	
	static List<Bestelling> bestellingen = Arrays.asList(
			new Bestelling(1, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0), producten),
			new Bestelling(2, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0), producten),
			new Bestelling(3, LocalDate.now(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1), producten),
			new Bestelling(4, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(0), producten),
			new Bestelling(5, LocalDate.now(), OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, klanten.get(1), producten),
			new Bestelling(6, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(1), producten),
			new Bestelling(7, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD, klanten.get(0), producten)
	);
	
	private Gebruiker gebruiker = new Gebruiker(1, Rol.LEVERANCIER, "jasper.vandenbroucke@hotmail.com", "1234", 
			"Jasper Vandenbroucke", true, new Adres("België", "Gent", "9000", "Veldstraat", "56"));
	
	@ParameterizedTest
	@CsvSource({"Bas Stokmans", "Tiemen Deroose"})
	public void test_raadplegenKlanten(String verwachteNaam) {
		Mockito.when(klantDaoJpa.vindPerLeverancier(gebruiker)).thenReturn(klanten);
		
		List<Klant> klanten = klantServiceDbImpl.getKlanten(gebruiker);
		Assertions.assertTrue(klanten.size() != 0);
		Assertions.assertEquals(verwachteNaam, klanten.get(0).getName());
		Assertions.assertEquals(verwachteNaam, klanten.get(1).getName());
		
		Mockito.verify(klantDaoJpa).vindPerLeverancier(gebruiker);
	}
}
