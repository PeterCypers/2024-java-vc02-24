package domein.gebruiker;

import java.util.List;
import java.util.stream.Collectors;

import domein.Bedrijf;
import domein.Bestelling;
import domein.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Represents a Supplier
 * <p>This class contains information about the Supplier.</p>
 */
@Entity
@DiscriminatorValue(value=Rol.Values.LEVERANCIER)
public class Leverancier extends Gebruiker {
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Bedrijf bedrijf;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Product> producten;
	
	/** <code>entity class</code> JPA-required default constructor */
	public Leverancier() {}
	
	/**
	 * Constructs a new <strong>Leverancier</strong> with the specified details.
	 * 
	 * @param bedrijf the company this supplier belongs to
	 * @param email the login email linked to this supplier
	 * @param wachtwoord the login password linked to this supplier
	 * @param naam the name of this supplier
	 * @param isActief <code>boolean</code> status of this supplier: active/inactive
	 */
	public Leverancier(Bedrijf bedrijf, String email, String wachtwoord, 
			String naam, boolean isActief) {
		super(email, wachtwoord, naam, isActief, Rol.LEVERANCIER);
		this.bedrijf = bedrijf; 
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen.stream().collect(Collectors.toUnmodifiableList());
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public List<Product> getProducten() {
		return producten.stream().collect(Collectors.toUnmodifiableList());
	}
	
	public void setProducten(List<Product> producten) {
		this.producten = producten;
	}

	public Bedrijf getBedrijf() {
		return bedrijf;
	}
}