package domein;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a Product.
 * <p>This class contains information about a Product.</p>
 */
@Entity
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
  
	private String naam;
	
	private double eenheidsprijs;
	
	private int stock;
	
	/** <code>entity class</code> JPA-required default constructor */
	public Product() {}
	
	/**
	 * Constructs a new <strong>Product</strong> with the specified details.
	 * 
	 * @param naam the productname
	 * @param inStock <code>enum</code> {@link domein.Stock} inStock status of this product
	 * @param eenheidsprijs the price per unit of this product
	 */
	public Product(String naam, int stock, double eenheidsprijs) {
		setNaam(naam);
		setStock(stock);
		setEenheidsprijs(eenheidsprijs);
	}
	
	public String getNaam() {
	    return naam;
	}
	
	private void setNaam(String naam) {
		if(naam == null || naam.isBlank())
			throw new IllegalArgumentException("Productsnaam mag niet leeg zijn");
		
		this.naam = naam;
	}
	
	public int getStock() {
	    return stock;
	}
	
	private void setStock(int stock) {
		if (stock < 0)
			throw new IllegalArgumentException("De stock van een product mag niet onder nul liggen");
		
		this.stock = stock;
	}
	
	/**
	 * Geef een positief getal door om de stock te verhogen, of een negatief getal om de stock the verlagen
	 * @param aantal
	 */
	public void bewerkStock(int aantal) {
		if (stock + aantal < 0)
			throw new IllegalArgumentException("De stock van een product mag niet verlaagd worden onder nul");
		
		this.stock += aantal;
	}
	
	public double getEenheidsprijs() {
	    return eenheidsprijs;
	}
	
	private void setEenheidsprijs(Double eenheidsprijs) {
		if (eenheidsprijs <= 0)
			throw new IllegalArgumentException("Eenheidsprijs van een product moet strikt positief zijn");
		this.eenheidsprijs = eenheidsprijs;
	}
	
	public String toString() {
		return String.format("%s, %d, %.2f", naam, stock, eenheidsprijs);
	}

}
