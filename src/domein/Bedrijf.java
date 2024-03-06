package domein;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Adres adres;
	
	private String betalingsmogelijkhedenEnInfo;
	
	private String contact;
	
	private String btwNr;
	
	private boolean isActief;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	private List<Klant> klanten;

	// klantaccount + leverancier
	// logo?
	
	// Voor tabelView?
	@Transient
	private final SimpleStringProperty naamProp = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty sectorProp = new SimpleStringProperty();
	@Transient
	private final ObjectProperty<Adres> adresProp = new SimpleObjectProperty<>(this, "adresProp");
	@Transient
	private final SimpleIntegerProperty aantalKlantenProp = new SimpleIntegerProperty();
	@Transient
	private final SimpleBooleanProperty isActiefProp = new SimpleBooleanProperty();
	
	public Bedrijf() {}
	
	// klanten?
	public Bedrijf(String naam, String sector, Adres adres, 
			String betalingsmogelijkhedenEnInfo, String contact, 
			String btwNr, boolean isActief) {
		setNaam(naam);
		setSector(sector);
		setAdres(adres);
		setBetalingsMogelijkhedenEnInfo(betalingsmogelijkhedenEnInfo);
		setContact(contact);
		setBtwNr(btwNr);
		setIsActief(isActief);
	}
	
	private void setNaam(String naam) {
		if (naam.isBlank() || !naam.matches("\\b([\\p{L}\\-'.,]+[ ]*)+")) 
			throw new IllegalArgumentException("Naam ongeldig.");
		this.naam = naam;
	}
	
	private void setSector(String sector) {
		if (sector.isBlank())
			throw new IllegalArgumentException("Sector ongeldig.");
		this.sector = sector;
	}
	
	private void setAdres(Adres adres) {
		if (adres == null)
			throw new IllegalArgumentException("Adres ongeldig.");
		this.adres = adres;
	}
	
	private void setBetalingsMogelijkhedenEnInfo(String betalingsmogelijkhedenEnInfo) {
		if (betalingsmogelijkhedenEnInfo.isBlank())
			throw new IllegalArgumentException("Betalingsmogelijkheden en -info ongeldig.");
		this.betalingsmogelijkhedenEnInfo = betalingsmogelijkhedenEnInfo;
	}
	
	private void setContact(String contact) {
		String emailRegex = "^[a-zA-Z0-9]+@[hH][oO][tT][mM][aA][iI][lL]\\.[cC][oO][mM]$";
		if (!contact.matches(emailRegex))
			throw new IllegalArgumentException("Contact ongeldig.");
		this.contact = contact;
	}
	
	private void setBtwNr(String btwNr) {
		String btwNrRegex = "^BE \\d{4}\\.\\d{3}\\.\\d{3}$";
		if (btwNr.isBlank() || !btwNr.matches(btwNrRegex))
			throw new IllegalArgumentException("Btw nummer ongelidg.");
		this.btwNr = btwNr;
	}
	
	private void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}

	public String getNaam() {
		return this.naam;
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

	public String getContact() {
		return this.contact;
	}

	public String getBtwNr() {
		return this.btwNr;
	}

	public List<Klant> getKlanten() {
		return this.klanten;
	}

	public boolean getIsActief() {
		return this.isActief;
	}

	public SimpleStringProperty getNaamProp() {
		this.naamProp.set(this.naam);
		return naamProp;
	}

	public SimpleStringProperty getSectorProp() {
		this.sectorProp.set(this.sector);
		return sectorProp;
	}

	public ObjectProperty<Adres> getAdresProp() {
		this.adresProp.set(this.adres);
		return adresProp;
	}

	public SimpleIntegerProperty getAantalKlantenProp() {
		this.aantalKlantenProp.set(this.klanten.size());
		return aantalKlantenProp;
	}

	public SimpleBooleanProperty getIsActiefProp() {
		this.isActiefProp.set(this.isActief);
		return isActiefProp;
	}

	@Override
	public String toString() {
		return "Bedrijf [naam=" + naam + ", sector=" + sector + ", adres=" + adres + ", betalingsmogelijkhedenEnInfo="
				+ betalingsmogelijkhedenEnInfo + ", contact=" + contact + ", btwNr=" + btwNr + ", isActief=" + isActief
				+ ", klanten=" + klanten + "]";
	}
	
}
