package domein;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

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
	
	//Voor tableView
	@Transient
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty openstaandeBestellingen = new SimpleIntegerProperty();
	
	public Klant() {}
	
	//constructor
	public Klant(String naam, String logoPad, String telefoonnummer, String contact, Adres adres) {
		setKlantNaam(naam);
		setLogoPad(logoPad);
		setTelefoonnummer(telefoonnummer);
		setContact(contact);
		setAdres(adres);
	}
	
	public String getNaam() {
		return naam;
	}
	
	private void setKlantNaam(String name) {
		if(name == null || naam.isBlank())
	        throw new IllegalArgumentException("Klantnaam mag niet leeg zijn");
		
		if(name == null || !name.matches("\\b([\\p{L}\\-'.,]+[ ]*)+"))
	        throw new IllegalArgumentException("Ongeldige klantnaam");
		
		this.naam = name;
	}
	
	public String getContactgegevens() {
		return contact;
	}
	
	public void setContact(String email) {
		if (email == null || email.isBlank()) {
	        throw new IllegalArgumentException("Emailadres van de klant mag niet leeg zijn");
	    }
		
	    this.contact = email;
	}

	public String getLogoPad() {
		return logoPad;
	}

	public void setLogoPad(String logoPad) {
		if (logoPad == null || logoPad.isEmpty())
			throw new IllegalArgumentException("Bedrijfslogo mag niet leeg zijn");
		
		this.logoPad = logoPad;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	
	public void setTelefoonnummer(String telefoonnummer) {
		if(telefoonnummer == null || telefoonnummer.isBlank())
			throw new IllegalArgumentException("Telefoonnummer van de klant mag niet leeg zijn");
		
		String PhoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";

		if(telefoonnummer == null || !telefoonnummer.matches(PhoneRegex))
			throw new IllegalArgumentException("Telefoonnummer van de klant is ongeldig");
			
		this.telefoonnummer = telefoonnummer;
		this.telefoonnummer = telefoonnummer;
	}
	
	public Adres getAdres() {
		return this.adres;
	}
	
	public void setAdres(Adres adres) {
		if(adres == null)
			throw new IllegalArgumentException("Adres van de klant is ongeldig");
		
		this.adres = adres;
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen;
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public StringProperty getName() {
		naamKlant.set(naam);
		return naamKlant;
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
				getBestellingenPerLeverancier(leverancier));
		
		//Sortering van Bestellingen
		Comparator<Bestelling> bijDatum = (b1, b2)
				-> b2.getDatumGeplaats().toString().compareToIgnoreCase(b1.getDatumGeplaats().toString());
				
		Comparator<Bestelling> bijOrderId = (b1, b2)
				-> Integer.toString(b1.getOrderId()).compareToIgnoreCase(Integer.toString(b2.getOrderId()));
		
		Comparator<Bestelling> bijOrderbedrag = (b1, b2)
				-> Double.toString(b1.berekenTotalBedrag()).compareToIgnoreCase(Double.toString(b2.berekenTotalBedrag()));
				
		Comparator<Bestelling> bijOrderstatus = (b1, b2)
				-> b1.getOrderStatus().toString().compareToIgnoreCase(b2.getOrderStatus().toString());
				
		Comparator<Bestelling> bijBetalingsstatus = (b1, b2)
				-> b1.getBetalingsStatus().toString().compareToIgnoreCase(b2.getBetalingsStatus().toString());
				
		Comparator<Bestelling> bestellingSorted = bijDatum.thenComparing(bijOrderId).thenComparing(bijOrderbedrag)
				.thenComparing(bijOrderstatus).thenComparing(bijBetalingsstatus);
		
		FilteredList<Bestelling> filteredBestellingen = new FilteredList<>(bestellingsList, b -> true);     
		SortedList<Bestelling> sortedBestellingen = new SortedList<>(filteredBestellingen, bestellingSorted);
		return sortedBestellingen;
	}
	
	private FilteredList<Bestelling> getObservableListBestelling(Gebruiker leverancier){
		ObservableList<Bestelling> bestellingsList = FXCollections.observableArrayList(
				getBestellingenPerLeverancier(leverancier));
		FilteredList<Bestelling> filteredBestellingen = new FilteredList<>(bestellingsList, b -> true); 
		return filteredBestellingen;
	}
	
	//word niet meer gebruikt normaal
	@Override
	public String toString() {
		//TODO: logo dit stuur ik door als herinnering, maar vermoed andere implementatie verwacht
		return String.format("Naam: %s - Contact: %s - Adres: %s - Logopath: %s - telNr: %s",
				naam, contact, adres, logoPad, telefoonnummer);
	}
}
