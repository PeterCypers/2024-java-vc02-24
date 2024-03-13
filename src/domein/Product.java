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

@Entity
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
  
	private String naam;
	
	private int aantal;
	
	private double eenheidsprijs;
	
	@Enumerated(EnumType.STRING)
	private Stock inStock;
	
	//Voor tableView
	@Transient
	private final SimpleStringProperty naamProduct = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty aantalProduct = new SimpleIntegerProperty();
	@Transient
	private final SimpleObjectProperty<Stock> stock = new SimpleObjectProperty<Stock>();
	@Transient
	private final SimpleDoubleProperty eenheidsPrijs = new SimpleDoubleProperty();
	@Transient 
	private final SimpleDoubleProperty totaalPrijs = new SimpleDoubleProperty();
	
	public Product() {}
	
	//constructor
	public Product(String naam, int aantal, Stock inStock, double eenheidsprijs) {
		setNaam(naam);
		setAantal(aantal);
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
	
	public int getAantal() {
	    return aantal;
	}
	
	private void setAantal(int aantal) {
		if (aantal < 0)
			throw new IllegalArgumentException("Aantal van een product moet positief zijn");
		this.aantal = aantal;
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

	public double getTotalePrijs(){
		return aantal * eenheidsprijs;
	}
	
	//Voor tableView
	public StringProperty naamProperty() {
		naamProduct.set(naam);
		return naamProduct;
	}
	
	public IntegerProperty aantalProperty() {
		aantalProduct.set(aantal);
		return aantalProduct;
	}
	
	public ObjectProperty<Stock> stockProperty(){
		stock.set(inStock);
		return stock;
	}
	
	public DoubleProperty eenheidsprijsProperty() {
		eenheidsPrijs.set(eenheidsprijs);
		return eenheidsPrijs;
	}
	
	public DoubleProperty totalePrijsProperty() {
		totaalPrijs.set(getTotalePrijs());
		return totaalPrijs;
	}
	
	public String toString() {
		return String.format("%s, %d, %s, %.2f, %.2f", naam, aantal, inStock, eenheidsprijs, getTotalePrijs());
	}

}
