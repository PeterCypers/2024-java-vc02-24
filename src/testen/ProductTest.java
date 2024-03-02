package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.Product;
import domein.Stock;

class ProductTest {

	Product product;
	
	
	@ParameterizedTest
	@CsvSource({"Pink Lady Appel,500,STOCK,6.79", "Beschermhoesje,200,STOCK,3.99"})
	void nieuwProduct_allesGeldig(String naam, int aantal, Stock inStock, double eenheidsprijs) {
	    Product product = new Product(naam, aantal, inStock, eenheidsprijs);
	    
	    assertEquals(product.getNaam(), naam);
	    assertEquals(product.getAantal(), aantal);
	    assertEquals(product.isInStock(), inStock);
	    assertEquals(product.getEenheidsprijs(), eenheidsprijs);
	}
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"$^,;:=&'(!"})
	void nieuwProduct_naamOngeldig(String naam) {
	    assertThrows(IllegalArgumentException.class, () -> new Product(naam, 15, Stock.STOCK, 2.5));
	}
	
	@ParameterizedTest
	@CsvSource({"-100", "-2", "-50"})
	void nieuwProduct_aantalOngeldig(int aantal) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("Product", aantal, Stock.STOCK, 2.5));
	}
	@ParameterizedTest
	@CsvSource({"0.0", "-5.52", "-200.3", "-0.01"})
	void nieuwProduct_eenheidsPrijsOngeldig(double eenheidsPrijs) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("GeneriekProduct", 10, Stock.STOCK, eenheidsPrijs));
	}
	

}
