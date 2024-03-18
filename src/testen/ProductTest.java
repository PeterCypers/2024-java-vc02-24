package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.BesteldProduct;
import domein.Product;
import domein.Stock;

class ProductTest {
	
	@ParameterizedTest
	@CsvSource({"Pink Lady Appel,500,STOCK,6.79", "Beschermhoesje,200,STOCK,3.99"})
	void nieuwProduct_allesGeldig(String naam, int aantal, Stock inStock, double eenheidsprijs) {
	    BesteldProduct besteldProduct = new BesteldProduct(new Product(naam, inStock, eenheidsprijs), aantal);
	    
	    assertEquals(besteldProduct.getProduct().getNaam(), naam);
	    assertEquals(besteldProduct.getAantal(), aantal);
	    assertEquals(besteldProduct.getProduct().isInStock(), inStock);
	    assertEquals(besteldProduct.getProduct().getEenheidsprijs(), eenheidsprijs);
	}
	@ParameterizedTest
	@NullAndEmptySource
	void nieuwProduct_naamLeeg(String naam) {
	    assertThrows(IllegalArgumentException.class, () -> new Product(naam, Stock.STOCK, 2.5));
	}
	
	@ParameterizedTest
	@CsvSource({"-100", "-2", "-50"})
	void nieuwProduct_aantalOngeldig(int aantal) {
	    assertThrows(IllegalArgumentException.class, () -> new BesteldProduct(new Product("Product", Stock.STOCK, 2.5), aantal));
	}
	@ParameterizedTest
	@CsvSource({"0.0", "-5.52", "-200.3", "-0.01"})
	void nieuwProduct_eenheidsPrijsOngeldig(double eenheidsPrijs) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("GeneriekProduct", Stock.STOCK, eenheidsPrijs));
	}
	

}
