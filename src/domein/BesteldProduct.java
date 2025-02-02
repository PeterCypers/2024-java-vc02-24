package domein;

import java.text.DecimalFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents an Ordered Product.
 * <p>This is to create a unique entity for each Ordered Product based on a given Product.</p>
 */
@Entity
public class BesteldProduct {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int besteldProductId;

	@ManyToOne
	private Product product;

	private int aantal;
	
	private double eenheidsprijs;

	@Transient
	private final SimpleStringProperty naamProduct = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty aantalProduct = new SimpleIntegerProperty();
	@Transient
	private final SimpleStringProperty stockProduct = new SimpleStringProperty();
	@Transient
    private final SimpleStringProperty eenheidsprijsProduct = new SimpleStringProperty();
    @Transient
    private final SimpleStringProperty totaalPrijs = new SimpleStringProperty();

    /** <code>entity class</code> JPA-required default constructor */
	public BesteldProduct() {}

	/**
	 * Constructs a new <strong>BesteldProduct</strong> with the specified details.
	 *
	 * @param product the product this ordered Product is based on
	 * @param aantal amount ordered
	 */
	public BesteldProduct(Product product, int aantal) {
		this.product = product;
		this.eenheidsprijs = product.getEenheidsprijs();
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
		return aantal * eenheidsprijs;
	}

	//Voor tableView
	/**
	 * JavaFX property implementation
	 *
	 * @return {@link SimpleStringProperty} naamProduct -> the name of this product
	 */
	public StringProperty naamProperty() {
		naamProduct.set(product.getNaam());
		return naamProduct;
	}

	/**
	 * JavaFX property implementation
	 *
	 * @return {@link SimpleIntegerProperty} aantalProduct -> the amount this product that is ordered
	 */
	public IntegerProperty aantalProperty() {
		aantalProduct.set(aantal);
		return aantalProduct;
	}

	/**
	 * JavaFX property implementation
	 *
	 * @return {@link SimpleStringProperty} stockProduct -> returns either the amount of this product still in stock<br>
	 * or a descriptor that it is needs to be ordered
	 */
	public StringProperty stockProperty(){
		String stockString = product.getLeverMethode() == LeverMethode.STOCK ? Integer.toString(product.getStock()) : "Op order";
		stockProduct.set(stockString);
		return stockProduct;
	}

	/**
	 * JavaFX property implementation
	 *
	 * @return {@link SimpleStringProperty} eenheidsprijsProduct -> the unit price of this Product<br>
	 * displaying the specified {@link DecimalFormat} (\u20AC)
	 */
	public StringProperty eenheidsprijsProperty() {
		 DecimalFormat df = new DecimalFormat("\u20AC0.00");
		 eenheidsprijsProduct.set(df.format(eenheidsprijs));
        return eenheidsprijsProduct;
    }

	/**
	 * JavaFX property implementation
	 *
	 * @return {@link SimpleStringProperty} totaalPrijs -> the calculated total price of this Ordered Product<br>
	 * based on the amount of Products in this O.P. and the unit price of the Product<br>
	 * displaying the specified {@link DecimalFormat} (\u20AC)
	 */
    public StringProperty totaalPrijsProperty() {
    	 DecimalFormat df = new DecimalFormat("\u20AC0.00");
    	 totaalPrijs.set(df.format(getTotalePrijs()));
        return totaalPrijs;
    }

	public String toSearchString() {
		return String.format("%s %s %s %s %s", product.getNaam(), aantal, stockProperty().getValue(), eenheidsprijs, getTotalePrijs()).toLowerCase();
	}
}