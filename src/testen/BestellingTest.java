package testen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import domein.Adres;
import domein.Bestelling;
import domein.BetalingsStatus;
import domein.Klant;
import domein.OrderStatus;
import domein.Product;
import domein.Stock;

class BestellingTest {

	Bestelling bestelling;
	static List<Klant> klanten = Arrays.asList(
			new Klant("Bas Stokmans","logobedrijf.png", "+32123456789","klant1@hotmail.com", new Adres("Land", "Stad", "1234", "Straat", "1")),
			new Klant("Tiemen Deroose","logobedrijf.png", "+32123456789","klant2@hotmail.com", new Adres("Land", "Stad", "12345", "Straat", "20"))
	);
	static List<Product> producten = Arrays.asList(
			new Product("productA", 1000, Stock.STOCK, 500.0),
			new Product("productB", 50000, Stock.STOCK, 4.99),
			new Product("productC", 2100, Stock.STOCK, 19.99)
	);
	
	@SuppressWarnings("deprecation")
	static Stream<Arguments> geldigeBestellingen(){
		return Stream.of(
			Arguments.of(1, new Date(124, 3, 17), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(2, new Date(124, 5, 28), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1)),
			Arguments.of(3, new Date(124, 2, 30), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(4, new Date(124, 12, 31), OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, klanten.get(1))
		);
	}
	
	@SuppressWarnings("deprecation")
	static Stream<Arguments> ongeldigeBestellingen(){
		return Stream.of(
			//Ongeldig orderId
			Arguments.of(0, new Date(), OrderStatus.GELEVERD, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			Arguments.of(-1, new Date(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(1)),
			Arguments.of(-97, new Date(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			
			//Ongeldige orderstatus
			Arguments.of(1, new Date(), null, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			
			//Ongeldige betalingsstatus
			Arguments.of(1, new Date(), OrderStatus.GELEVERD, null, klanten.get(0)),
			
			//Ongeldige klant
			Arguments.of(1, new Date(), OrderStatus.ONDERWEG, BetalingsStatus.NIET_BETAALD, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("geldigeBestellingen")
	void test_nieuweBestelling_geldigeWaarden(int orderId, Date datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant) {
		this.bestelling = new Bestelling(orderId, datum, oStatus, bStatus, klant, producten);
		
		Assertions.assertEquals(orderId, this.bestelling.getOrderId());
		Assertions.assertEquals(datum, this.bestelling.getDatumGeplaatst());
		Assertions.assertEquals(oStatus, this.bestelling.getOrderStatus());
		Assertions.assertEquals(bStatus, this.bestelling.getBetalingsStatus());
		Assertions.assertEquals(klant.getName(), this.bestelling.getKlantName());
	}
	
	@ParameterizedTest
	@MethodSource("ongeldigeBestellingen")
	void test_nieuweBestelling_ongeldigeWaarden(int orderId, Date datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Bestelling(orderId, datum, oStatus, bStatus, klant, producten));
	}

}
