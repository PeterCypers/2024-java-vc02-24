package domein.gebruiker;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import domein.Adres;
import domein.Bedrijf;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.BetalingsStatus;
import domein.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

/**
 * Represents an Customer.
 * <p>This class contains information about a Customer.</p>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Klant.vindPerLeverancier",
                         query = "SELECT DISTINCT k FROM Klant k "
                        		+ "JOIN k.bestellingen b "
                         		+ "WHERE b.leverancier = :leverancier")
})
@DiscriminatorValue(value=Rol.Values.KLANT)
public class Klant extends Gebruiker {
	
	private static final long serialVersionUID = 1L;

	@Embedded
	protected Adres adres;
	
	private String telefoonnummer;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Bedrijf bedrijf;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	//voor tableView
	@Transient
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty openstaandeBestellingen = new SimpleIntegerProperty();
	
	/** <code>entity class</code> JPA-required default constructor */
	public Klant() {}
	
	/**
	 * Constructs a new <strong>Klant</strong> with the specified details.
	 * 
	 * @param bedrijf the company to which this customer belongs
	 * @param email the email of this customer
	 * @param wachtwoord the password of this customer
	 * @param naam the name of this customer
	 * @param isActief <code>boolean</code> isActive status of this customer
	 * @param adres the Adres object representing this customer's address
	 * @param telefoonnummer the phone number of this customer
	 */
	public Klant(Bedrijf bedrijf, String email, String wachtwoord, String naam, boolean isActief, Adres adres, String telefoonnummer) {
		super(email, wachtwoord, naam, isActief, Rol.KLANT);
		this.bedrijf = bedrijf;
		setAdres(adres);
		setTelefoonnummer(telefoonnummer);
	}
	
	public Adres getAdres() {
		return adres;
	}
	
	public void setAdres(Adres adres) {
		if (adres == null) {
			throw new IllegalArgumentException("Adres van de klant is ongeldig");
		}
		
		this.adres = adres;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	
	/**
	 * TODO
	 * 
	 * @param telefoonnummer
	 */
	public void setTelefoonnummer(String telefoonnummer) {
		if(telefoonnummer == null || telefoonnummer.isBlank())
			throw new IllegalArgumentException("Telefoonnummer van de klant mag niet leeg zijn");
		
		String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
		if(telefoonnummer == null || !telefoonnummer.matches(phoneRegex))
			throw new IllegalArgumentException("Telefoonnummer van de klant is ongeldig");
		
		this.telefoonnummer = telefoonnummer;
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen;
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public String getLogo() {
		return this.bedrijf.getLogo();
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleIntegerProperty} openstaandeBestellingen -> the amount of open orders
	 * this customer has
	 */
	public IntegerProperty openstaandeBestellingenProperty(Gebruiker leverancier) {
		openstaandeBestellingen.set(getAantalOpenstaandeBestellingen(leverancier));
		return openstaandeBestellingen;
	}
	
	/**
	 * This returns a filtered list of orders of this customer <br>
	 * filtered by supplier == given supplier
	 * 
	 * @param leverancier the supplier of orders
	 * @return all orders this customer has with the given supplier
	 */
	public List<Bestelling> getBestellingenPerLeverancier(Gebruiker leverancier) {
		return bestellingen.stream().filter(b -> b.getLeverancier().equals(leverancier)).collect(Collectors.toUnmodifiableList());
	}
	
	/**
	 * This returns a filtered list of orders of this customer <br>
	 * filtering out OrderStatus.<strong>GELEVERD</strong> <code>enum</code>
	 * 
	 * @param leverancier the supplier of orders for this customer
	 * @return the filtered list of orders
	 */
	public List<Bestelling> getOpenstaandeBestellingen(Gebruiker leverancier) {
		List<Bestelling> openstaand = getBestellingenPerLeverancier(leverancier).stream().filter(b -> 
			!(b.getOrderStatus().equals(OrderStatus.GELEVERD))
		).collect(Collectors.toList());
		return openstaand;
	}
	
	/**
	 * This returns the amount of orders this customer has with a supplier<br>
	 * whose OrderStatus is:
	 * <ul>
	 * <li>OrderStatus.<strong>ONDERWEG</strong>
	 * <li>OrderStatus.<strong>AAN_HET_VERWERKEN</strong>
	 * <li>OrderStatus.<strong>GEREGISTREERD</strong>
	 * </ul>
	 *
	 * @param leverancier the supplier of orders for this customer
	 * @return the amount of orders for this customer that are still open
	 */
	public int getAantalOpenstaandeBestellingen(Gebruiker leverancier) {
		return getOpenstaandeBestellingen(leverancier).size();
	}
	
	@Override
	public String toString() {
		//TODO: logo dit stuur ik door als herinnering, maar vermoed andere implementatie verwacht
		return String.format("Naam: %s - Contact: %s - Adres: %s - Logopath: %s - telNr: %s",
				naam, emailadres, adres, bedrijf.getLogo(), telefoonnummer);
	}

}
