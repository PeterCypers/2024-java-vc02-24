package domein;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class BesteldProduct {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int besteldProductId;
	
	@ManyToOne
	Product product;
	
	int aantal;
	
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
	
	public BesteldProduct() {}
	
	public BesteldProduct(Product product, int aantal) {
		this.product = product;
		setAantal(aantal);
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getAantal() {
		return aantal;
	}
	
	private void setAantal(int aantal) {
		if (aantal < 0)
			throw new IllegalArgumentException("Aantal van een product moet positief zijn");
		this.aantal = aantal;
	}
	
	public double getTotalePrijs(){
		return aantal * product.getEenheidsprijs();
	}
	
	//Voor tableView
	public StringProperty naamProperty() {
		naamProduct.set(product.getNaam());
		return naamProduct;
	}
	
	public IntegerProperty aantalProperty() {
		aantalProduct.set(aantal);
		return aantalProduct;
	}
	
	public ObjectProperty<Stock> stockProperty(){
		stock.set(product.isInStock());
		return stock;
	}
	
	public DoubleProperty eenheidsprijsProperty() {
		eenheidsPrijs.set(product.getEenheidsprijs());
		return eenheidsPrijs;
	}
	
	public DoubleProperty totalePrijsProperty() {
		totaalPrijs.set(getTotalePrijs());
		return totaalPrijs;
	}
	
	public String toSearchString() {
		return String.format("%s %s %s %s %s", product.getNaam(), aantal, product.isInStock(), product.getEenheidsprijs(), getTotalePrijs()).toLowerCase();
	}
}
