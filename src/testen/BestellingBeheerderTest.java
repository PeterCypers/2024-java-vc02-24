package testen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import domein.Adres;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.BestellingBeheerder;
import domein.BetalingsStatus;
import domein.LeverMethode;
import domein.OrderStatus;
import domein.Product;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
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
    
    /**
     * <ul>
     * <li>all null : no filtering
     * <li>not-null value(s) are retaining a list containing matches with the given value(s)
     * <li>final value == expected list-size
     * </ul>
     */
	static Stream<Arguments> filterCombinaties() {
		return Stream.of(
			Arguments.of(null, OrderStatus.filter, BetalingsStatus.filter, null, null, 2), //no-filter -> 2
			Arguments.of(LocalDate.now().minusMonths(3), OrderStatus.filter, BetalingsStatus.filter, null, null, 1), //date -> 1
			Arguments.of(LocalDate.now().minusMonths(5), OrderStatus.filter, BetalingsStatus.filter, null, null, 0), //date -> 0
			Arguments.of(null, OrderStatus.GELEVERD, BetalingsStatus.filter, null, null, 1), //OrderStatus
			Arguments.of(null, OrderStatus.GEREGISTREERD, BetalingsStatus.filter, null, null, 1),
			Arguments.of(null, OrderStatus.ONDERWEG, BetalingsStatus.filter, null, null, 0),
			Arguments.of(null, OrderStatus.filter, BetalingsStatus.BETAALD, null, null, 2), //BetalingsStatus
			Arguments.of(null, OrderStatus.filter, BetalingsStatus.NIET_BETAALD, null, null, 0),
			Arguments.of(null, OrderStatus.GELEVERD, BetalingsStatus.BETAALD, null, null, 1), //combi OrderStatus + BetalingsStatus
			Arguments.of(null, OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, null, null, 1),
			Arguments.of(null, OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, null, null, 0),
			Arguments.of(LocalDate.now().minusMonths(3),OrderStatus.GELEVERD, BetalingsStatus.BETAALD, null, null, 1), //combi date + OrderStatus + BetalingsStatus
			Arguments.of(LocalDate.now().minusDays(1),OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, null, null, 1),
			Arguments.of(LocalDate.now().minusDays(1),OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, null, null, 0));
	}
    
    /**
     * the filter can be checked by comparing the size of the returned list
     * to the amount of matching values in the mock data <code>trainMock</code>
     * the list of Orders can be size= [0,1,2]
     */
	@ParameterizedTest
	@MethodSource("filterCombinaties")
    void changeFilter_listIsFiltered(LocalDate fd, OrderStatus fos, BetalingsStatus fbs, String fv, Klant klant, int expectedListSize) {
		helperChangeFilter(fd, fos, fbs, fv, klant);
		Assertions.assertEquals(expectedListSize, bestellingBeheerder.getBestellingen().size());
    }
    
    private void helperChangeFilter(LocalDate filterDate, OrderStatus filterOrderStatus, BetalingsStatus filterBetalingsStatus, String filterValue, Klant klant) {
    	bestellingBeheerder.changeFilter(filterDate, filterOrderStatus, filterBetalingsStatus, filterValue, klant);
    }
    
    private void trainMock() {
    	List<Klant> klanten = Arrays.asList(
    			new Klant(null, "michel@outlook.be", "1234", "Michel", true, new Adres("Belgium", "Brussels", "1000", "Kerkstraat", "1"), "+32974178174"),
				new Klant(null, "jake@gmail.com", "1234", "Jake", true, new Adres("United States",	"New York",  "10001", 	"Broadway",	 				"20"), "(212)912-0384"));
    	
    	List<Product> producten = List.of(
				new Product("Stella Artois Lager, 12 Pack 11.2 fl. oz. Bottles", 			1000, 39.99, LeverMethode.STOCK),
				new Product("Stella Artois Lager, 24 Pack 11.2 fl. oz. Cans",				900, 59.99, LeverMethode.STOCK),
				new Product("Stella Artois Premium Lager Beer, 24-11.2 fl. oz. Bottles", 	500, 52.49, LeverMethode.STOCK),
				new Product("Stella Artois Premium Lager Beer, 3 Pack 25 fl. oz. Cans", 	500, 18.99, LeverMethode.STOCK)
    	);
    	
		List<Bestelling> bestellingen = List.of(
				new Bestelling(49001, LocalDate.now().minusMonths(3),OrderStatus.GELEVERD, BetalingsStatus.BETAALD, 
						klanten.get(0), gebruiker,
						Arrays.asList(
								new BesteldProduct(producten.get(0), 80),
								new BesteldProduct(producten.get(1), 140),
								new BesteldProduct(producten.get(2), 20),
								new BesteldProduct(producten.get(3), 30)
						),
						LocalDate.now().plusDays(29)
				),
				new Bestelling(49002, LocalDate.now().minusDays(1),OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, 
						klanten.get(1), gebruiker,
						Arrays.asList(
								new BesteldProduct(producten.get(0), 100),
								new BesteldProduct(producten.get(1), 220)
						),
						LocalDate.now().plusDays(10)
				)
		);
		when(bestellingServiceMock.getBestellingen(any(Gebruiker.class))).thenReturn(bestellingen);
    }
    
}
