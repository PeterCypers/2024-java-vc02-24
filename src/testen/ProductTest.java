package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import domein.Product;

class ProductTest {

	Product product;
	
	
	@ParameterizedTest
	@CsvSource({"Pink Lady Appel,500,true,6.79,3395.0", "Beschermhoesje,200,true,3.99,798.0"})
	void nieuwProduct_allesGeldig(String naam, int aantal, boolean inStock, double eenheidsprijs, double totalePrijs) {
	    Product product = new Product(naam, aantal, inStock, eenheidsprijs, totalePrijs);
	    
	    assertEquals(product.getNaam(), naam);
	    assertEquals(product.getAantal(), aantal);
	    assertEquals(product.isInStock(), inStock);
	    assertEquals(product.getEenheidsprijs(), eenheidsprijs);
	    assertEquals(product.getTotaalPrijs(), totalePrijs);
	}
	@ParameterizedTest
	@NullAndEmptySource
	@CsvSource({"A", "$^,;:=&'(!"})
	void nieuwProduct_naamOngeldig(String naam) {
	    assertThrows(IllegalArgumentException.class, () -> new Product(naam, 15, true, 2.5, 15.0));
	}
	
	@ParameterizedTest
	@CsvSource({"-100", "-2", "-50"})
	void nieuwProduct_aantalOngeldig(int aantal) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("Product", aantal, true, 2.5, 15.0));
	}
	@ParameterizedTest
	@CsvSource({"0.0", "-5.52", "-200.3", "-0.01"})
	void nieuwProduct_eenheidsPrijsOngeldig(double eenheidsPrijs) {
	    assertThrows(IllegalArgumentException.class, () -> new Product("GeneriekProduct", 10, true, eenheidsPrijs, 100.0));
	}
	

}
