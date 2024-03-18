package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import domein.Adres;
import domein.Bestelling;
import domein.BestellingBeheerder;
import domein.BetalingsStatus;
import domein.OrderStatus;
import domein.Product;
import domein.Stock;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import domein.gebruiker.Rol;
import service.bestelling.BestellingService;

public class BestellingBeheerderTest {
	
	private BestellingService bestellingServiceMock;
    private BestellingBeheerder bestellingBeheerder;
    private Leverancier gebruiker;
		
	@BeforeEach
	void setup() {
		bestellingServiceMock = mock(BestellingService.class);
		gebruiker = new Leverancier(null, "mike@gmail.com", "1234", "Mike", true);
		trainMock(); //dit moet gebeuren voor constructie BestellingBeheerder()
		bestellingBeheerder = new BestellingBeheerder(gebruiker, bestellingServiceMock);
		
	}
	
    @Test
    void test_getBestellingen() {
    	Assertions.assertEquals(2, bestellingBeheerder.getBestellingen().size());
    }
    
    //TODO: verdere testing mogelijk
    
    private void trainMock() {
    	List<Klant> klanten = Arrays.asList(
    			new Klant(null, "michel@outlook.be", "1234", "Michel", true, new Adres("Belgium", "Brussels", "1000", "Kerkstraat", "1"), "+32974178174"),
				new Klant(null, "jake@gmail.com", "1234", "Jake", true, new Adres("United States",	"New York",  "10001", 	"Broadway",	 				"20"), "(212)912-0384"));
    	
		List<Bestelling> bestellingen =  List.of(
				new Bestelling(49001, LocalDate.now().minusMonths(3),OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
						klanten.get(0), gebruiker,
						Arrays.asList(new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 80, Stock.STOCK, 39.99),
								new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans", 140, Stock.STOCK, 59.99),
								new Product("Stella Artois Premium Lager Beer, 24-11.2 fl. oz. Bottles", 20, Stock.STOCK, 52.49),
								new Product("Stella Artois Premium Lager Beer, 3 Pack 25 fl. oz. Cans", 30, Stock.STOCK, 18.99)
						)
				),
				new Bestelling(49002, LocalDate.now().minusDays(1),OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, 
						klanten.get(1), gebruiker,
						Arrays.asList(new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 100, Stock.STOCK, 39.99),
								new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans", 220, Stock.STOCK, 59.99)
						)
				)
		);
		when(bestellingServiceMock.getBestellingen(any(Gebruiker.class))).thenReturn(bestellingen);
    }
}
