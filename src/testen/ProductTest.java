package testen;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.BesteldProduct;
import domein.LeverMethode;
import domein.Product;

class ProductTest {
	
	@ParameterizedTest
	@CsvSource({"Pink Lady Appel,500,1000,6.79,STOCK", "Beschermhoesje,200,1000,3.99,STOCK"})
	void nieuwProduct_allesGeldig(String naam, int aantal, int stock, double eenheidsprijs, LeverMethode leverMethode) {
	    BesteldProduct besteldProduct = new BesteldProduct(new Product(naam, stock, eenheidsprijs, leverMethode), aantal);
	    
	    assertEquals(besteldProduct.getProduct().getNaam(), naam);
	    assertEquals(besteldProduct.getAantal(), aantal);
	    assertEquals(besteldProduct.getProduct().getStock(), stock);
	    assertEquals(besteldProduct.getProduct().getEenheidsprijs(), eenheidsprijs);
	}
	@ParameterizedTest
	@NullAndEmptySource
	void nieuwProduct_naamLeeg(String naam) {
	    assertThrows(IllegalArgumentException.class, () -> new Product(naam, 1, 2.5, LeverMethode.STOCK));
	}
	
	@ParameterizedTest
	@CsvSource({"-100", "-2", "-50"})
	void nieuwProduct_aantalOngeldig(int aantal) {
	    assertThrows(IllegalArgumentException.class, () -> new BesteldProduct(new Product("Product", 1, 2.5, LeverMethode.STOCK), aantal));
	}
	@ParameterizedTest
	@CsvSource({"0.0", "-5.52", "-200.3", "-0.01"})
	void nieuwProduct_eenheidsPrijsOngeldig(double eenheidsPrijs) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("GeneriekProduct", 1, eenheidsPrijs, LeverMethode.STOCK));
	}
	
	@ParameterizedTest
	@CsvSource({"-100, 0", "-2, 98", "-50, 50", "50, 150", "350, 450"})
	void bewerkStock_geldigeWaarden_vermindertStock(int stockVerandering, int newStock) {
		int aantalInStock = 100;
		Product product = new Product("GeneriekProduct", aantalInStock, 2.5, LeverMethode.STOCK);
		product.bewerkStock(stockVerandering);
		assertEquals(newStock, product.getStock());
	}
	
	@Test
	void bewerkStock_stockOnde0_werptException() {
		int aantalInStock = 0;
		Product product = new Product("GeneriekProduct", aantalInStock, 2.5, LeverMethode.STOCK);
		assertThrows(IllegalArgumentException.class, () -> product.bewerkStock(-1));
	}

}
