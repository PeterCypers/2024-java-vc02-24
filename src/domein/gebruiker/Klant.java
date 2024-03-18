package domein.gebruiker;

import java.util.List;
import java.util.stream.Collectors;

import domein.Adres;
import domein.Bedrijf;
import domein.Bestelling;
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
	
	public Klant() {}
	
	//constructor
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
	
	public IntegerProperty openstaandeBestellingenProperty(Gebruiker leverancier) {
		openstaandeBestellingen.set(getAantalOpenstaandeBestellingen(leverancier));
		return openstaandeBestellingen;
	}
	
	public List<Bestelling> getBestellingenPerLeverancier(Gebruiker leverancier) {
		return bestellingen.stream().filter(b -> b.getLeverancier().equals(leverancier)).collect(Collectors.toUnmodifiableList());
	}
	
	public List<Bestelling> getOpenstaandeBestellingen(Gebruiker leverancier) {
		List<Bestelling> openstaand = getBestellingenPerLeverancier(leverancier).stream().filter(b -> 
			!(b.getOrderStatus().equals(OrderStatus.GELEVERD))
		).collect(Collectors.toList());
		return openstaand;
	}
	
	public int getAantalOpenstaandeBestellingen(Gebruiker leverancier) {
		return getOpenstaandeBestellingen(leverancier).size();
	}
	/**
	 * @param leverancier = singleton aangemeldeGebruiker
	 * @return ObservableList<Bestelling> van bestellingen van deze leverancier
	 */
	public ObservableList<Bestelling> getObservableListBestellingen(Gebruiker leverancier) {
		ObservableList<Bestelling> bestellingsList = FXCollections.observableArrayList(
				getBestellingenPerLeverancier(leverancier)
		);
		return bestellingsList;
	}
	
	public String getAdresString() {
		return getAdres().toString();
	}
	
	@Override
	public String toString() {
		//TODO: logo dit stuur ik door als herinnering, maar vermoed andere implementatie verwacht
		return String.format("Naam: %s - Contact: %s - Adres: %s - Logopath: %s - telNr: %s",
				naam, emailadres, adres, bedrijf.getLogo(), telefoonnummer);
	}
}
