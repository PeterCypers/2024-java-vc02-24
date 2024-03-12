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
	
	private double totalePrijs;
	
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
		berekenTotalePrijs(aantal, eenheidsprijs);
	}
	
	private void berekenTotalePrijs(int aantal, double eenheidsprijs) {
		totalePrijs = aantal * eenheidsprijs;
	}
	
	//getters
	public double getTotalePrijs() {
		return totalePrijs;
	}

	//setters
	private void setNaam(String naam) {
		if(naam == null || naam.isBlank())
			throw new IllegalArgumentException("Naam product incorrect");
		
		this.naam = naam;
	}
	

	private void setAantal(int aantal) {
		if (aantal < 0)
			throw new IllegalArgumentException("Aantal moet positief zijn");
		this.aantal = aantal;
	}
	
	private void setInStock(Stock inStock) {
		this.inStock = inStock;
	}
	
	private void setEenheidsprijs(Double eenheidsprijs) {
		if (eenheidsprijs <= 0)
			throw new IllegalArgumentException("Eenheidsprijs moet strikt positief zijn");
		this.eenheidsprijs = eenheidsprijs;
	}
	public Double getTotaalPrijs(){
		return aantal * eenheidsprijs;
	}
	public String getNaam() {
	    return naam;
	}

	public int getAantal() {
	    return aantal;
	}

	public double getEenheidsprijs() {
	    return eenheidsprijs;
	}
	
	public Stock isInStock() {
	    return inStock;
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
		totaalPrijs.set(totalePrijs);
		return totaalPrijs;
	}
	
	public String toString() {
		return String.format("%s, %d, %s, %.2f, %.2f", naam, aantal, inStock, eenheidsprijs, totalePrijs);
	}

}
