package testen;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.Adres;
import domein.Bestelling;
import domein.BetalingsStatus;
import domein.Klant;
import domein.OrderStatus;
import domein.Product;
import domein.Stock;
import service.bestelling.BestellingDaoJpa;
import service.bestelling.BestellingServiceDbImpl;

@ExtendWith(MockitoExtension.class)
class BestellingTest {

	Bestelling bestelling;
	
	@Mock
	private BestellingDaoJpa bestellingDaoJpaDummy;
	
	@InjectMocks
	private BestellingServiceDbImpl bestellingServiceDbImpl;
	
	static List<Klant> klanten = Arrays.asList(
			new Klant("Bas Stokmans","logobedrijf.png", "+32123456789","klant1@hotmail.com", new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Klant("Tiemen Deroose","logobedrijf.png", "+32123456789","klant2@hotmail.com", new Adres("Land", "Stad", "12345", "Straat", "20"))
	);
	static List<Product> producten = Arrays.asList(
			new Product("productA", 1000, Stock.STOCK, 500.0),
			new Product("productB", 50000, Stock.STOCK, 4.99),
			new Product("productC", 2100, Stock.STOCK, 19.99)
	);
	

	static Stream<Arguments> geldigeBestellingen(){
		return Stream.of(
			Arguments.of(1, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(2, LocalDate.now(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1)),
			Arguments.of(3, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(4, LocalDate.now(), OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, klanten.get(1))
		);
	}
	

	static Stream<Arguments> ongeldigeBestellingen(){
		//LocalDate.of(124, 5, 28) / LocalDate.of(124, 2, 30) / LocalDate.of(124, 12, 31)
		return Stream.of(
			//Ongeldig orderId
			Arguments.of(0, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(-1, LocalDate.now(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1)),
			Arguments.of(-97, LocalDate.now(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			
			//Ongeldige orderstatus
			Arguments.of(1, LocalDate.now(), null, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			
			//Ongeldige betalingsstatus
			Arguments.of(1, LocalDate.now(), OrderStatus.GELEVERD, null, klanten.get(0)),
			
			//Ongeldige klant
			Arguments.of(1, LocalDate.now(), OrderStatus.ONDERWEG, BetalingsStatus.NIET_BETAALD, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("geldigeBestellingen")
	void test_nieuweBestelling_geldigeWaarden(int orderId, LocalDate datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant) {
		this.bestelling = new Bestelling(orderId, datum, oStatus, bStatus, klant, producten);
		
		Assertions.assertEquals(orderId, this.bestelling.getOrderId());
		Assertions.assertEquals(datum, this.bestelling.getDatumGeplaatst());
		Assertions.assertEquals(oStatus, this.bestelling.getOrderStatus());
		Assertions.assertEquals(bStatus, this.bestelling.getBetalingsStatus());
		Assertions.assertEquals(klant.getName().get(), this.bestelling.getKlantName());
	}
	
	@ParameterizedTest
	@MethodSource("ongeldigeBestellingen")
	void test_nieuweBestelling_ongeldigeWaarden(int orderId, LocalDate datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Bestelling(orderId, datum, oStatus, bStatus, klant, producten));
	}
	
	//@ParameterizedTest
	@Test
	public void test_wijzigBestelling() {
		//TODO
		Assertions.assertEquals(1, 1);
	}
	
	@Test
	void berekenTotalBedrag() {
		//TODO
		Assertions.assertEquals(1, 1);
	}

}
