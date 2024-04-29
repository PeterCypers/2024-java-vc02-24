package domein;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
	private LeverMethode leverMethode;

	private int stock;
	
	private String imageUrl;

	/** <code>entity class</code> JPA-required default constructor */
	public Product() {}

	/**
	 * Constructs a new <strong>Product</strong> with the specified details.
	 *
	 * @param naam the productname
	 * @param stock amount in stock
	 * @param eenheidsprijs the price per unit of this product
	 * @param leverMethode <code>enum</code> {@link domein.LeverMethode} inStock status of this product the method of delivery
	 */
	public Product(String naam, int stock, double eenheidsprijs, LeverMethode leverMethode, String imageUrl) {
		setNaam(naam);
		setStock(stock);
		setEenheidsprijs(eenheidsprijs);
		this.leverMethode = leverMethode;
		this.imageUrl = imageUrl;
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

	public double getEenheidsprijs() {
	    return eenheidsprijs;
	}

	private void setEenheidsprijs(Double eenheidsprijs) {
		if (eenheidsprijs <= 0)
			throw new IllegalArgumentException("Eenheidsprijs van een product moet strikt positief zijn");
		this.eenheidsprijs = eenheidsprijs;
	}

	public LeverMethode getLeverMethode() {
		return leverMethode;
	}

	/**
	 * Geef een positief getal door om de stock te verhogen, of een negatief getal om de stock the verlagen
	 * @param aantal
	 */
	public void bewerkStock(int aantal) {
		if (leverMethode == LeverMethode.ORDER)
			return;

		if (stock + aantal < 0)
			throw new IllegalArgumentException("De stock van een product mag niet onder nul komen te staan!");

		this.stock += aantal;
	}
}