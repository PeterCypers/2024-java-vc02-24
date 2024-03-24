package testen;

import domein.BesteldProduct;
import domein.LeverMethode;
import domein.Product;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BesteldProductTest {
	
	private BesteldProduct bp;
	private static final Product geldigProduct = new Product("soep", 200, 1.5, LeverMethode.STOCK);
	//String naam, int stock, double eenheidsprijs, LeverMethode leverMethode
	
	static Stream<Arguments> geldigeData() {
		return Stream.of(
			Arguments.of(new Product("sla", 100, 1.5, LeverMethode.ORDER), 50, 75, "sla"),
			Arguments.of(new Product("tomaat", 200, 2.0, LeverMethode.ORDER), 100, 200, "tomaat"),
			Arguments.of(new Product("appel", 30, 1.0, LeverMethode.STOCK), 125, 125, "appel"),
			Arguments.of(new Product("wortel", 50, 0.5, LeverMethode.STOCK), 75, 37.5, "wortel"));
	}
	
	@Test
	void maakBesteldProduct_ongeldigAantal_throwsException() {
		assertThrows(IllegalArgumentException.class, () -> bp = new BesteldProduct(geldigProduct, -1));
	}
	
	//mag het product null zijn?
//	@Test
//	void maakBesteldProduct_nullProduct_throwsException() {
//		assertThrows(IllegalArgumentException.class, () -> bp = new BesteldProduct(null, 100));
//	}
	
	@ParameterizedTest
	@MethodSource("geldigeData")
	void maakBesteldProduct_geldigeWaarden(Product geldigProduct, int aantalInOrder, double nvt, String naam) {
		bp = new BesteldProduct(geldigProduct, aantalInOrder);
		assertEquals(naam, bp.naamProperty().get());
		assertEquals(aantalInOrder, bp.getAantal());
		assertEquals(aantalInOrder, bp.aantalProperty().get());
	}
	
	@ParameterizedTest
	@MethodSource("geldigeData")
	void maakBesteldProduct_getTotalePrijs_returnsCorrectWaarde(Product geldigProduct, int aantalInOrder, double correcteWaarde, String naam) {
		bp = new BesteldProduct(geldigProduct, aantalInOrder);
		assertEquals(correcteWaarde, bp.getTotalePrijs(), 0.01);
	}
	
	/**
	 * info: the correct functioning of tests using these arguments 
	 * is dependant on the User's locale setting for DecimalSeperator
	 */
	static Stream<Arguments> geldigeDataStockEnPrijs() {
		return Stream.of(
			Arguments.of(new Product("sla", 100, 1.5, LeverMethode.ORDER), 1, "Op order", String.format("%s1,50", "\u20AC")),
			Arguments.of(new Product("tomaat", 200, 2.0, LeverMethode.ORDER), 1, "Op order", String.format("%s2,00", "\u20AC")),
			Arguments.of(new Product("appel", 30, 1.0, LeverMethode.STOCK), 1, "30", String.format("%s1,00", "\u20AC")),
			Arguments.of(new Product("wortel", 50, 0.5, LeverMethode.STOCK), 1, "50", String.format("%s0,50", "\u20AC")));
	}
	
	@ParameterizedTest
	@MethodSource("geldigeDataStockEnPrijs")
	void stockProperty_returnsCorrectString(Product geldigProduct, int aantalInOrder, String stockStatus) {
		bp = new BesteldProduct(geldigProduct, aantalInOrder);
		assertEquals(stockStatus, bp.stockProperty().get());
	}
	
	@ParameterizedTest
	@MethodSource("geldigeDataStockEnPrijs")
	void eenheidsprijsProperty_returnsCorrectString(Product geldigProduct, int aantalInOrder, String stockStatus, String eenheidsPrijsRepr) {
		bp = new BesteldProduct(geldigProduct, aantalInOrder);
		assertEquals(eenheidsPrijsRepr, bp.eenheidsprijsProperty().get());
	}
	
}
