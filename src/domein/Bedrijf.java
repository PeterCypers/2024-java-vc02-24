package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a Company.
 * <p>This class contains information about a Company.</p>
 */
@Entity
@NamedQueries ({
	@NamedQuery(name="Bedrijf.geefBedrijven",
			query="SELECT b FROM Bedrijf b")
})
public class Bedrijf implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private String naam;
	
	private String sector;
	
	@Embedded
	protected Adres adres;
	
	private String betalingsInfo;
	
	@ElementCollection
	@JoinTable(name = "BETAALMETHODES")
	@Column(name = "BETAALMETHODES", nullable = false)
	@Enumerated(EnumType.STRING)
	private Set<Betaalmethode> betaalmethodes;

	private String logo;
	
	private String emailadres;
	
	private String telefoonnummer;
	
	private String btwNr;
	
	/** administration can activate/deactivate a company */
	private boolean isActief;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Klant klant;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Leverancier leverancier;

	
	// Voor tabelView?
	@Transient
	private final SimpleStringProperty naamProp = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty sectorProp = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty adresProp = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty aantalKlantenProp = new SimpleIntegerProperty();
	@Transient
	private final SimpleStringProperty isActiefProp = new SimpleStringProperty();
	
	/** <code>entity class</code> JPA-required default constructor */
	public Bedrijf() {}
	
	/**
	 * Constructs a new <strong>Bedrijf</strong> with the specified details.
	 * 
	 * @param naam the name of the company
	 * @param logo an http link to the company logo
	 * @param sector the company sector
	 * @param adres the company address
	 * @param betalingsmogelijkhedenEnInfo ??? -> TODO
	 * @param email the company email-address
	 * @param telefoon the company phone number
	 * @param btwNr company tax number
	 * @param isActief whether or not the company has been set active/inactive by the administrator <br>
	 * 
	 * klanten?
	 */
	public Bedrijf(String naam, String logo, String sector, Adres adres, List<Betaalmethode> betaalmethodes,
			String betalingsInfo, String email, String telefoon,
			String btwNr, boolean isActief) {
		setNaam(naam);
		setLogo(logo);
		setSector(sector);
		setAdres(adres);
		setBetaalmethodes(betaalmethodes);
		setBetalingsInfo(betalingsInfo);
		setEmail(email);
		setTelefoon(telefoon);
		setBtwNr(btwNr);
		setIsActief(isActief);
	}

	/**
	 * <p>setter for attribute <code>naam</code> <br>
	 * the company name should match:</p>
	 * <ul>
	 * <li> <strong>\\b</strong> == ???
	 * <li> <strong>(...)+</strong> == capturing group containing 1 or more
	 * <li> <strong>\\p{L}</strong> == unicode property matching any letter in any language
	 * <li> <strong>\\-'.,</strong> == literal: any - ' . , character
	 * <li> <strong>[\\p{L}\\-'.,]+</strong> == see above disambiguation (1 or more)
	 * <li> <strong>[ ]*</strong> spaces are allowed (0 or more)
	 * <li> <strong>[a-zA-Z]+$</strong> == Any case letter (1 or more), at the end of the string
	 * </ul>
	 * @param naam the company name
	 * @throws IllegalArgumentException when name is not filled in, null or doesn't match regex
	 */
	private void setNaam(String naam) {
		if (naam == null || naam.isBlank()) 
			throw new IllegalArgumentException("Bedrijfsnaam mag niet leeg zijn");
		
		if (!naam.matches("\\b([\\p{L}\\-'.,]+[ ]*)+")) 
			throw new IllegalArgumentException("Bedrijfsnaam is ongeldig");
		this.naam = naam;
	}
	
	private void setLogo(String logo) {
		if (logo == null || logo.isBlank()) 
			throw new IllegalArgumentException("Bedrijfslogo mag niet leeg zijn");
		this.logo = logo;
	}
	
	private void setSector(String sector) {
		if (sector == null || sector.isBlank())
			throw new IllegalArgumentException("Sector van het bedrijf mag niet leeg zijn");
		this.sector = sector;
	}
	
	private void setAdres(Adres adres) {
		if (adres == null)
			throw new IllegalArgumentException("Adres van het bedrijf is ongeldig");
		this.adres = adres;
	}
	
	public void setBetaalmethodes(List<Betaalmethode> betaalmethodes) {
		if (betaalmethodes == null)
			throw new IllegalArgumentException("betaalmethodes is null");
		this.betaalmethodes = new HashSet<>(betaalmethodes);
	}
	
	private void setBetalingsInfo(String betalingsmogelijkhedenEnInfo) {
		//if (betalingsmogelijkhedenEnInfo.isBlank())
		//	throw new IllegalArgumentException("Betalingsmogelijkheden en -info ongeldig");
		this.betalingsInfo = betalingsmogelijkhedenEnInfo;
	}
	
	/**
	 * <p>setter for attribute <code>emailadres</code> <br>
	 * the company email should match:</p>
	 * <ul>
	 * <li> <strong>^[a-zA-Z0-9]+</strong> == Beginning of the string must be any case letter or number (1 or more)
	 * <li> <strong>\\.?</strong> == 0 or 1 (optional) dot
	 * <li> <strong>[a-zA-Z0-9]*</strong> == any case letter or number (0 or more)
	 * <li> <strong>@</strong> == Important, required '@' character
	 * <li> <strong>[a-zA-Z]+</strong> == Any case letter (1 or more)
	 * <li> <strong>\\.</strong> Important, required '.' character
	 * <li> <strong>[a-zA-Z]+$</strong> == Any case letter (1 or more), at the end of the string
	 * </ul>
	 * @param email the company email
	 * @throws IllegalArgumentException when email is not filled in, null or doesn't match regex
	 */
	private void setEmail(String email) {
		if (email == null || email.isBlank())
			throw new IllegalArgumentException("Emailadres mag niet leeg zijn");
		
		String emailRegex = "^[a-zA-Z0-9]+\\.?[a-zA-Z0-9]*@[a-zA-Z]+\\.[a-zA-Z]+$";
	    if (!email.matches(emailRegex)) {
	        throw new IllegalArgumentException("Emailadres van het bedrijf is ongeldig");
	    }
		
		this.emailadres = email;
	}
	
	/**
	 * <p>setter for attribute <code>telefoonnummer</code> <br>
	 * the company telNr should match:</p>
	 * <ul>
	 * <li> <strong>^[\\+]?</strong> == Beginning of the string has optional "+" character
	 * <li> <strong>[(]?</strong> == optional "("
	 * <li> <strong>[0-9]{3}</strong> == exactly 3 digits
	 * <li> <strong>[)]?</strong> == optional ")"
	 * <li> <strong>[-\\s\\.]?</strong> == optional "-" whitespace or "."
	 * <li> <strong>[0-9]{4,6}$</strong> == 4 to 6 digits at the end of the string
	 * </ul>
	 * example matches:
	 * <ul>
	 * <li>+123-456-7890
	 * <li>(123) 456.7890
	 * <li>123 4567890
	 * <li>+1(123)456-789012
	 * </ul>
	 * @param telefoonnummer the company phone number
	 * @throws IllegalArgumentException when phone number is not filled in, null or doesn't match regex
	 */
	private void setTelefoon(String telefoon) {
		if (telefoon == null || telefoon.isBlank())
			throw new IllegalArgumentException("Telefoonnummer van het bedrijf mag niet leeg zijn");
		
		String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
		if(!telefoon.matches(phoneRegex))
			throw new IllegalArgumentException("Telefoonnummer van het bedrijf is ongeldig");
		
		this.telefoonnummer = telefoon;
		
	}
	
	private void setBtwNr(String btwNr) {
		if (btwNr == null || btwNr.isBlank())
			throw new IllegalArgumentException("BTW nummer mag niet leeg zijn");
		
		this.btwNr = btwNr;
	}
	
	public void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}
	
	public void setLeverancier(Leverancier leverancier) {
		this.leverancier = leverancier;
	}
	
	public void setKlant(Klant klant) {
		this.klant = klant;
	}

	public String getNaam() {
		return this.naam;
	}
	
	public String getLogo() {
		return this.logo;
	}

	public String getSector() {
		return this.sector;
	}

	public Adres getAdres() {
		return this.adres;
	}
	
	public List<Betaalmethode> getBetaalmethodes(){
		return new ArrayList<>(this.betaalmethodes);
	}
	

	public void addBetaalmethodes(Betaalmethode betaalmethode) {
		if (betaalmethode == null)
			throw new IllegalArgumentException("betaalmethode is null");
		betaalmethodes.add(betaalmethode);
	}
	
	public void removeBetaalmethodes(Betaalmethode betaalmethode) {
		betaalmethodes.remove(betaalmethode);
	}

	public String getBetalingsInfo() {
		return this.betalingsInfo;
	}

	public String getEmail() {
		return this.emailadres;
	}
	
	public String getTelefoon() {
		return this.telefoonnummer;
	}

	public String getBtwNr() {
		return this.btwNr;
	}
	
	public Leverancier getLeverancier() {
		return this.leverancier;
	}
	
	public Klant getKlant() {
		return this.klant;
	}

	public boolean getIsActief() {
		return this.isActief;
	}
	
	public int aantalKlanten() {
		return 0; //TODO
	}
	
	//Voor tableView
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleIntegerProperty} aantalKlantenProp -> the amount of customers of this company
	 */
	public IntegerProperty getAantalKlantenProp() {
		int aantalKlanten = 0; //TODO
		
		this.aantalKlantenProp.set(aantalKlanten);
		return aantalKlantenProp;
	}

	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} naamProp -> the name of this company
	 */
	public StringProperty getNaamProp() {
		this.naamProp.set(this.naam);
		return naamProp;
	}

	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} sectorProp -> the sector of this company
	 */
	public StringProperty getSectorProp() {
		this.sectorProp.set(this.sector);
		return sectorProp;
	}

	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} adresProp -> the address of this company
	 */
	public StringProperty getAdresProp() {
		this.adresProp.set(this.adres.toString());
		return adresProp;
	}

	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} isActiefProp<br> -> a String representation of
	 * the <code>isActief</code> <code>boolean</code> status of this company
	 */
	public StringProperty getIsActiefProp() {
		if(isActief == true) {
			this.isActiefProp.set("ja");
		} else {
			this.isActiefProp.set("nee");
		}
		return isActiefProp;
	}
	
	/**
	 * JavaFX Collections implementation
	 * 
	 * @return {@link ObservableList} of Gebruiker objects<br>
	 * used in gui.BedrijvenScherm
	 */
	public ObservableList<Gebruiker> getGebruikers(){
		List<Gebruiker> gebruikers = new ArrayList<>();
		gebruikers.add(klant);
		gebruikers.add(leverancier);
		return FXCollections.observableArrayList(gebruikers);
	}

	//word dit gebruikt?
	@Override
	public String toString() {
		return "Bedrijf [naam=" + naam + ", sector=" + sector + ", adres=" + adres + ", betalingsmogelijkhedenEnInfo="
				+ betalingsInfo + ", contact=" + emailadres + ", btwNr=" + btwNr + ", isActief=" + isActief
				+ ", klant=" + klant + "]";
	}
	
	public String getAsSearchString() {
		return String.format("%s %s %s %s %s", naam, sector, adres, getIsActiefProp().getValue(), aantalKlanten()).toLowerCase();
	}
}
