package testen;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import domein.Adres;
import domein.KlantBeheerder;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import service.klant.KlantService;

public class KlantBeheerderTest {
	
	private KlantService klantenServiceMock;
	private KlantBeheerder klantenBeheerder;
	private Leverancier gebruiker;
	
	@BeforeEach
	void setup() {
		klantenServiceMock = mock(KlantService.class);
		gebruiker = new Leverancier(null, "mike@gmail.com", "1234", "Mike", true);
		trainMock(); //dit moet gebeuren voor constructie KlantBeheerder()
		klantenBeheerder = new KlantBeheerder(gebruiker, klantenServiceMock);
	}
	
	@Test
	void test_getKlanten() {
		assertEquals(3, klantenBeheerder.getKlanten().size());
	}
	
	@Test
	void test_getKlanten_isSorted() {
		String klantNaam1 = helpGetKlantNaam(0);
		String klantNaam2 = helpGetKlantNaam(1);
		String klantNaam3 = helpGetKlantNaam(2);
		assertTrue(klantNaam1.compareTo(klantNaam2) <= 0);
		assertTrue(klantNaam2.compareTo(klantNaam3) <= 0);
		
	}
	
	String helpGetKlantNaam(int index) {
		return klantenBeheerder.getKlanten().get(index).gebruikersnaamProperty().get();
	}
	
	/**
	 * <ul>
	 * <li>no filter: expect all customers
	 * <li>filter on duplicate name: expect 2
	 * <li>filter on singular name: expect 1
	 * <li>filter nonexistant name: expect 0
	 * </ul>
	 */
	static Stream<Arguments> filterCombinaties() {
		return Stream.of(
				Arguments.of(null, 3),
				Arguments.of("Jake", 2),
				Arguments.of("Michel", 1),
				Arguments.of("Lisa", 0));
	}
	
	//will not get tested: many layers of object constructions required
	//refer to populateDB Client contains list<Order> contains list<Product>
	/*@ParameterizedTest
	@MethodSource("filterCombinaties")
	void test_changeFilter_listIsFiltered(String klantNaam, int expectedListSize) {
		klantenBeheerder.changeFilter(klantNaam);
		assertEquals(expectedListSize, klantenBeheerder.getKlanten().size());
	}*/
	
	
	private void trainMock() {
		List<Klant> klanten = Arrays.asList(
    			new Klant(null, "michel@outlook.be", "1234", "Michel", true, new Adres("Belgium", "Brussels", "1000", "Kerkstraat", "1"), "+32974178174"),
				new Klant(null, "jake@gmail.com", "1234", "Jake", true, new Adres("United States", "New York", "10001", "Broadway",	"20"), "(212)912-0384"),
				new Klant(null, "jake@outlook.com", "1234", "Jake", true, new Adres("United States", "New York", "10001", "Broadway",	"20"), "(212)912-0444"));
		
		when(klantenServiceMock.getKlanten(any(Gebruiker.class))).thenReturn(klanten);
	}
	
}
