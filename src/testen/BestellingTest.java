package testen;

import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domein.Adres;
import domein.Bedrijf;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.Betaalmethode;
import domein.BetalingsStatus;
import domein.LeverMethode;
import domein.OrderStatus;
import domein.Product;
import domein.gebruiker.Klant;
import service.bestelling.BestellingDaoJpa;
import service.bestelling.BestellingServiceDbImpl;

@ExtendWith(MockitoExtension.class)
class BestellingTest {

	Bestelling bestelling;
	
	@BeforeEach
	void before() {
		this.bestelling = new Bestelling(1, LocalDate.now(), OrderStatus.VERWERKT, BetalingsStatus.FACTUUR_VERZONDEN, klanten.get(0), null, besteldeProducten,LocalDate.now().plusDays(10));
	}
	
	
	@Mock
	private BestellingDaoJpa bestellingDaoJpaDummy;
	
	@InjectMocks
	private BestellingServiceDbImpl bestellingServiceDbImpl;

	static List<Bedrijf> bedrijven = Arrays.asList(
			new Bedrijf("Stella Artois", "https://logodix.com/logo/2066282.png", "Brewers",
					new Adres("Belgium","Brussels","1000","Kerkstraat","1"), List.of(Betaalmethode.APPLE_PAY, Betaalmethode.BANCONTACT), "BE16154215421625", "mark@outlook.be", "+32974178174", "BE197248342B38", true),
			new Bedrijf("Hewlett-Packard", "https://logodix.com/logo/4934.png", "Technology Hardware, Storage & Peripherals",
					new Adres("United States","New York","10001","Broadway","20"), List.of(Betaalmethode.APPLE_PAY, Betaalmethode.BANCONTACT), "317265174 - 97135791278174529377", "mike@gmail.com", "(212)912-0384", "749196976", true)
			);
	
	static List<Klant> klanten = Arrays.asList(
			new Klant(bedrijven.get(0),"klant1@hotmail.com","1234","Bas Stokmans", true, new Adres("Land", "Stad", "1234", "Straat", "1"), "+32123456789"),
			new Klant(bedrijven.get(1),"klant2@hotmail.com","1234","Tiemen Deroose", true, new Adres("Land", "Stad", "12345", "Straat", "20"), "+32123456789")
	);
	static List<Product> producten = Arrays.asList(
			new Product("productA", 3000, 500.0, LeverMethode.STOCK, ""),
			new Product("productB", 2000, 4.99, LeverMethode.STOCK, ""),
			new Product("productC", 1000, 19.99, LeverMethode.STOCK, "")
	);
	static List<BesteldProduct> besteldeProducten = Arrays.asList(
			new BesteldProduct(producten.get(0), 1000),
			new BesteldProduct(producten.get(1), 50000),
			new BesteldProduct(producten.get(2), 2100)
	);
	

	static Stream<Arguments> geldigeBestellingen(){
		return Stream.of(
			Arguments.of(1, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.ONVERWERKT, klanten.get(0), "Bas Stokmans", "Stella Artois"),
			Arguments.of(2, LocalDate.now(), OrderStatus.VERZONDEN, BetalingsStatus.BETAALD, klanten.get(1), "Tiemen Deroose", "Hewlett-Packard"),
			Arguments.of(3, LocalDate.now(), OrderStatus.VERWERKT, BetalingsStatus.FACTUUR_VERZONDEN, klanten.get(0), "Bas Stokmans", "Stella Artois"),
			Arguments.of(4, LocalDate.now(), OrderStatus.GEPLAATST, BetalingsStatus.BETAALD, klanten.get(1), "Tiemen Deroose", "Hewlett-Packard")
		);
	}
	

	static Stream<Arguments> ongeldigeBestellingen(){
		return Stream.of(
			//Ongeldig orderId
			Arguments.of(0, LocalDate.now(), OrderStatus.GELEVERD, BetalingsStatus.ONVERWERKT, klanten.get(0)),
			Arguments.of(-1, LocalDate.now(), OrderStatus.UIT_VOOR_LEVERING, BetalingsStatus.BETAALD, klanten.get(1)),
			Arguments.of(-97, LocalDate.now(), OrderStatus.VERWERKT, BetalingsStatus.FACTUUR_VERZONDEN, klanten.get(0)),
			
			//Ongeldige orderstatus
			Arguments.of(1, LocalDate.now(), null, BetalingsStatus.FACTUUR_VERZONDEN, klanten.get(0)),
			
			//Ongeldige betalingsstatus
			Arguments.of(1, LocalDate.now(), OrderStatus.GELEVERD, null, klanten.get(0)),
			
			//Ongeldige klant
			Arguments.of(1, LocalDate.now(), OrderStatus.VERZONDEN, BetalingsStatus.FACTUUR_VERZONDEN, null)
		);
	}
	
	@ParameterizedTest
	@MethodSource("geldigeBestellingen")
	void nieuweBestelling_geldigeWaarden_maaktObj(int orderId, LocalDate datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant, String klantExp, String bestellingExp) {
		this.bestelling = new Bestelling(orderId, datum, oStatus, bStatus, klant, null, besteldeProducten,LocalDate.now().plusDays(10));
		
		Assertions.assertEquals(orderId, this.bestelling.getOrderId());
		Assertions.assertEquals(datum, this.bestelling.getDatumGeplaatst());
		Assertions.assertEquals(oStatus, this.bestelling.getOrderStatus());
		Assertions.assertEquals(bStatus, this.bestelling.getBetalingsStatus());
		Assertions.assertEquals(klantExp, klant.getNaam());
		Assertions.assertEquals(bestellingExp, this.bestelling.getBedrijfsnaam());
	}
	
	@ParameterizedTest
	@MethodSource("ongeldigeBestellingen")
	void nieuweBestelling_ongeldigeWaarden_werptException(int orderId, LocalDate datum, OrderStatus oStatus, BetalingsStatus bStatus, Klant klant) {
		Assertions.assertThrows(IllegalArgumentException.class, () -> new Bestelling(orderId, datum, oStatus, bStatus, klant, null, besteldeProducten,LocalDate.now().plusDays(10)));
	}
	
	@Test
	public void veranderOrderStatus() {
		this.bestelling.veranderOrderStatus(OrderStatus.VERWERKT);
		Assertions.assertEquals(OrderStatus.VERWERKT,this.bestelling.getOrderStatus());
	}

	@Test
	void berekenTotalBedrag_berekentJuisteWaarde() {
		Assertions.assertEquals(791479, this.bestelling.berekenTotalBedrag(), 0.01);
	}
	
	@Test
	void setHerinneringsDatum_() {
		//1 dag in het verleden / 1 dag na de betalings-datum
		List<LocalDate> ongeldigeDatums = List.of(LocalDate.now().minusDays(1), LocalDate.now().plusDays(11));
		
		ongeldigeDatums.forEach(datum -> {
			Assertions.assertThrows(IllegalArgumentException.class, () -> bestelling.setHerinneringsDatum(datum));
		});
	}

}
