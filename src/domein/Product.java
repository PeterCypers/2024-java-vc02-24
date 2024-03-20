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
	
	@Enumerated(EnumType.STRING)
	private Stock inStock;
	
	/** <code>entity class</code> JPA-required default constructor */
	public Product() {}
	
	/**
	 * Constructs a new <strong>Product</strong> with the specified details.
	 * 
	 * @param naam the productname
	 * @param inStock <code>enum</code> {@link domein.Stock} inStock status of this product
	 * @param eenheidsprijs the price per unit of this product
	 */
	public Product(String naam, Stock inStock, double eenheidsprijs) {
		setNaam(naam);
		setInStock(inStock);
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
	
	public Stock isInStock() {
	    return inStock;
	}
	
	private void setInStock(Stock inStock) {
		this.inStock = inStock;
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
		return String.format("%s, %s, %.2f", naam, inStock, eenheidsprijs);
	}

}
