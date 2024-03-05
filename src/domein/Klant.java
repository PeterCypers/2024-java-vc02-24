package domein;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
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
		String emailRegex = "^[a-zA-Z0-9]+@[hH][oO][tT][mM][aA][iI][lL]\\.[cC][oO][mM]$";
	    if (!contact.matches(emailRegex)) {
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
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public String getAdresString() {
		return adres.toString();
	}
}
