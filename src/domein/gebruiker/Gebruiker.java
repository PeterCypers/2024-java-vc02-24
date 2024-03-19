package domein.gebruiker;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(
	    discriminatorType = DiscriminatorType.STRING,
	    name = "ROL"
)
@NamedQueries({
    @NamedQuery(name = "Gebruiker.meldAan",
                         query = "SELECT g FROM Gebruiker g"
                         		+ " WHERE g.emailadres = :emailadres"
                         		+ " AND g.wachtwoord = :wachtwoord"
                         		+ " AND (g.rol = domein.gebruiker.Rol.ADMINISTRATOR OR g.rol = domein.gebruiker.Rol.LEVERANCIER)")
})
public abstract class Gebruiker implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gebruikerId;
	
	@Column(name = "ROL", nullable = false, insertable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	protected Rol rol;
	
	protected String emailadres;
	protected String wachtwoord;
	protected String naam;

	private boolean isActief;
	
	//Voor tableView
	@Transient
	private final SimpleStringProperty rolProp = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty gebruikersnaam = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty wachtWoord = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty actief = new SimpleStringProperty();
	
	public Gebruiker() {}

	public Gebruiker(String email, String wachtwoord, String naam, boolean isActief, Rol rol) {
		setNaam(naam);
		setEmail(email);
		setWachtwoord(wachtwoord);
		setIsActief(isActief);
		this.rol = rol;
	}
	
	public String getNaam() {
		return naam;
	}
	
	private void setNaam(String naam) {
		if (naam == null || naam.isBlank()) {
			throw new IllegalArgumentException("Gebruikersnaam mag niet leeg zijn");
		}
		
		if (!naam.matches("\\b([\\p{L}\\-'.,]+[ ]*)+")) {
	        throw new IllegalArgumentException("Ongeldige gebruikersnaam");
		}
		
		this.naam = naam;
	}
	
	public String getEmail() {
		return emailadres;
	}
	
	private void setEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Emailadres van de gebruiker mag niet leeg zijn");
		}
		
		String emailRegex = "^[a-zA-Z0-9]+\\.?[a-zA-Z0-9]*@[a-zA-Z]+\\.[a-zA-Z]+$";
	    if (!email.matches(emailRegex)) {
	        throw new IllegalArgumentException("Emailadres van de gebruiker is ongeldig");
	    }
	    
	    this.emailadres = email;
	}
	
	public String getWachtwoord() {
		return this.wachtwoord;
	}
	
	private void setWachtwoord(String wachtwoord) {
		if (wachtwoord == null || wachtwoord.isBlank()) {
			throw new IllegalArgumentException("Wachtwoord van de gebruiker mag niet leeg zijn");
		}
		
		if (wachtwoord.contains(" ")) {
			throw new IllegalArgumentException("Wachtwoord van de gebruiker is ongeldig");
		}
		
		this.wachtwoord = wachtwoord;
	}
	
	public Rol getRol() {
		return rol;
	}
	
	public boolean getIsActief() {
		return isActief;
	}
	
	public void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}
	
	//Voor tableView
	public StringProperty rolProperty() {
		rolProp.set(rol.toString());
		return rolProp;
	}
	
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