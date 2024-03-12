package domein;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


@Entity
@NamedQueries({
    @NamedQuery(name = "Klant.vindPerLeverancier",
                         query = "SELECT DISTINCT k FROM Klant k "
                        		+ "JOIN k.bestellingen b "
                         		+ "WHERE b.leverancier = :leverancier")
})
public class Klant implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String naam;
	private String contact;
	
	@Embedded
	private Adres adres;
	private String logoPad;
	private String telefoonnummer;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	//voor tableView
	@Transient
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty openstaandeBestellingen = new SimpleIntegerProperty();
	
	public Klant() {}
	
	//constructor
	public Klant(String naam, String logoPad, String telefoonnummer, String contact, Adres adres) {
		this.naam = naam;
		setContact(contact);
		setKlantNaam(naam);
		setAdres(adres);
		setLogoPad(logoPad);
		setTelefoonnummer(telefoonnummer);
		
	}
	
	private void setKlantNaam(String name) {
		if(name == null || !name.matches("\\b([\\p{L}\\-'.,]+[ ]*)+"))
	        throw new IllegalArgumentException("Ongeldige naam.");
		
		this.naam = name;
	}
	public void setContact(String contact) {
		if (contact == null || contact.isBlank()) {
	        throw new IllegalArgumentException("Ongeldig e-mailadres!");
	    }
		
	    this.contact = contact;
	}
	

	public String getLogoPad() {
		return logoPad;
	}

	public void setLogoPad(String logoPad) {
		if (logoPad == null || logoPad.isEmpty())
			throw new IllegalArgumentException("Ongeldig logo");
		
		this.logoPad = logoPad;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	

	public void setTelefoonnummer(String telefoonnummer) {
		
		String PhoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";


		if(telefoonnummer == null || !telefoonnummer.matches(PhoneRegex))
			throw new IllegalArgumentException("Telefoonnummer ongeldig.");
			
		this.telefoonnummer = telefoonnummer;
		this.telefoonnummer = telefoonnummer;
	}

	public StringProperty getNaam() {
		naamKlant.set(naam);
		return naamKlant;
	}
	
	public String getName() {
		return naam;
	}
	
	public String getContactgegevens() {
		return contact;
	}
	
	public Adres getAdres() {
		return this.adres;
	}
	
	public void setAdres(Adres adres) {
		if(adres == null)
			throw new IllegalArgumentException("Adres ongeldig.");
		
		this.adres = adres;
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen;
	}
	
	public List<Bestelling> getBestellingenPerLeverancier(Gebruiker leverancier) {
		return bestellingen.stream().filter(b -> b.getLeverancier().equals(leverancier)).collect(Collectors.toUnmodifiableList());
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public String getAdresString() {
		return adres.toString();
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
	
	public IntegerProperty openstaandeBestellingenProperty(Gebruiker leverancier) {
		openstaandeBestellingen.set(getAantalOpenstaandeBestellingen(leverancier));
		return openstaandeBestellingen;
	}
	

	public ObservableList<Bestelling> getObservableListBestellingen(Gebruiker leverancier) {
		ObservableList<Bestelling> bestellingsList = FXCollections.observableArrayList(
				getBestellingenPerLeverancier(leverancier)
		);
		return bestellingsList;
	}
	
	@Override
	public String toString() {
		//TODO: logo dit stuur ik door als herinnering, maar vermoed andere implementatie verwacht
		return String.format("Naam: %s - Contact: %s - Adres: %s - Logopath: %s - telNr: %s",
				naam, contact, adres, logoPad, telefoonnummer);
	}
}
