package domein;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	
	private String betalingsmogelijkhedenEnInfo;

	private String logo;
	
	private String emailadres;
	
	private String telefoonnummer;
	
	private String btwNr;
	
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
	
	public Bedrijf() {}
	
	// klanten?
	public Bedrijf(String naam, String logo, String sector, Adres adres, 
			String betalingsmogelijkhedenEnInfo, String email, String telefoon,
			String btwNr, boolean isActief) {
		setNaam(naam);
		setLogo(logo);
		setSector(sector);
		setAdres(adres);
		setBetalingsMogelijkhedenEnInfo(betalingsmogelijkhedenEnInfo);
		setEmail(email);
		setTelefoon(telefoon);
		setBtwNr(btwNr);
		setIsActief(isActief);
	}

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
	
	private void setBetalingsMogelijkhedenEnInfo(String betalingsmogelijkhedenEnInfo) {
		//if (betalingsmogelijkhedenEnInfo.isBlank())
		//	throw new IllegalArgumentException("Betalingsmogelijkheden en -info ongeldig");
		this.betalingsmogelijkhedenEnInfo = betalingsmogelijkhedenEnInfo;
	}
	
	private void setEmail(String email) {
		if (email == null || email.isBlank())
			throw new IllegalArgumentException("Emailadres mag niet leeg zijn");
		
		String emailRegex = "^[a-zA-Z0-9]+\\.?[a-zA-Z0-9]*@[a-zA-Z]+\\.[a-zA-Z]+$";
	    if (!email.matches(emailRegex)) {
	        throw new IllegalArgumentException("Emailadres van het bedrijf is ongeldig");
	    }
		
		this.emailadres = email;
	}

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

	public String getBetalingsmogelijkhedenEnInfo() {
		return this.betalingsmogelijkhedenEnInfo;
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
	
	//Voor tableView
	public IntegerProperty getAantalKlantenProp() {
		int aantalKlanten = getGebruikers().size();
		
		this.aantalKlantenProp.set(aantalKlanten);
		return aantalKlantenProp;
	}

	public StringProperty getNaamProp() {
		this.naamProp.set(this.naam);
		return naamProp;
	}

	public StringProperty getSectorProp() {
		this.sectorProp.set(this.sector);
		return sectorProp;
	}

	public StringProperty getAdresProp() {
		this.adresProp.set(this.adres.toString());
		return adresProp;
	}

	public StringProperty getIsActiefProp() {
		if(isActief == true) {
			this.isActiefProp.set("ja");
		} else {
			this.isActiefProp.set("nee");
		}
		return isActiefProp;
	}
	
	public ObservableList<Gebruiker> getGebruikers(){
		List<Gebruiker> gebruikers = new ArrayList<>();
		gebruikers.add(klant);
		gebruikers.add(leverancier);
		return FXCollections.observableArrayList(gebruikers);
	}

	@Override
	public String toString() {
		return "Bedrijf [naam=" + naam + ", sector=" + sector + ", adres=" + adres + ", betalingsmogelijkhedenEnInfo="
				+ betalingsmogelijkhedenEnInfo + ", contact=" + emailadres + ", btwNr=" + btwNr + ", isActief=" + isActief
				+ ", klant=" + klant + "]";
	}
}
