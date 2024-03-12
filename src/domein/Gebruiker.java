package domein;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@NamedQueries({
    @NamedQuery(name = "Gebruiker.meldAan",
                         query = "SELECT g FROM Gebruiker g"
                         		+ " WHERE g.emailadres = :emailadres"
                         		+ " AND g.wachtwoord = :wachtwoord"
                         		+ " AND (g.rol = domein.Rol.ADMINISTRATOR OR g.rol = domein.Rol.LEVERANCIER)")
})
public class Gebruiker implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int gebruikerId;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	@Enumerated(EnumType.STRING)
	private Rol rol;
	
	private String emailadres;
	private String wachtwoord;
	private String naam;
	private boolean isActief;
	
	@Embedded
	private Adres adres;
	
	//Voor tableView
	@Transient
	private final SimpleStringProperty gebruikersnaam = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty wachtWoord = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty actief = new SimpleStringProperty();
	
	public Gebruiker() {}

	public Gebruiker(Rol rol, String email, String wachtwoord, String naam, boolean isActief, Adres adres) {
		setNaam(naam);
		setEmail(email);
		setRol(rol);
		setWachtwoord(wachtwoord);
		setIsActief(isActief);
		
		this.adres = adres;
	}
	
	private void setNaam(String naam) {
		if (naam == null || naam.isBlank()) {
			throw new IllegalArgumentException("Gebruikersnaam mag niet leeg zijn!");
		}
		
		this.naam = naam;
	}
	
	public String getNaam() {
		return naam;
	}
	
	private void setEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Emailadres mag niet leeg zijn!");
		}
		
		String emailRegex = "^[a-zA-Z0-9]+\\.?[a-zA-Z0-9]*@[a-zA-Z]+\\.[a-zA-Z]+$";
	    if (!email.matches(emailRegex)) {
	        throw new IllegalArgumentException("Ongeldig e-mailadres!");
	    }
	    
	    this.emailadres = email;
	}
	
	public String getEmail() {
		return emailadres;
	}
	
	private void setWachtwoord(String wachtwoord) {
		if (wachtwoord == null || wachtwoord.isBlank()) {
			throw new IllegalArgumentException("Wachtwoord mag niet leeg zijn!");
		}
		
		if (wachtwoord.chars().anyMatch(c -> c == ' ')) {
			throw new IllegalArgumentException("Ongeldig wachtwoord!");
		}
		
		this.wachtwoord = wachtwoord;
	}
	
	public String getWachtwoord() {
		return this.wachtwoord;
	}
	
	//public setter
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public Rol getRol() {
		return rol;
	}
	
	public void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}

	public boolean getIsActief() {
		return isActief;
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen.stream().collect(Collectors.toUnmodifiableList());
	}
	
	//Voor tableView
	public StringProperty gebruikersnaamProperty() {
		gebruikersnaam.set(naam);
		return gebruikersnaam;
	}
	
	public StringProperty wachtwoordProperty() {
		wachtWoord.set(wachtwoord);
		return wachtWoord;
	}
	
	public StringProperty actiefProperty() {
		if(isActief == true) {
			actief.set("ja");
		} else {
			actief.set("nee");
		}
		
		return actief;
	}
}