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

/**
 * Represents the logged-in user
 * <p>This class contains information about the User.</p>
 */
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
	
	/** <code>entity class</code> JPA-required default constructor */
	public Gebruiker() {}

	/**
	 * Constructs a new <strong>Gebruiker</strong> with the specified details.
	 *
	 * @param rol <code>enum</code> {@link domein.gebruiker.Rol} role of this user
	 * @param email the login email linked to this user
	 * @param wachtwoord the login password linked to this user
	 * @param naam the name of this user
	 * @param isActief <code>boolean</code> status of this user: active/inactive
	 * @param adres the Adres object representing this user's address
	 */
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
	
	/**
	 * <p>setter for attribute <code>naam</code><br>
	 * the user name should match:</p>
	 * <ul>
	 * <li> <strong>\\b</strong> == ???
	 * <li> <strong>(...)+</strong> == capturing group containing 1 or more
	 * <li> <strong>\\p{L}</strong> == unicode property matching any letter in any language
	 * <li> <strong>\\-'.,</strong> == literal: any - ' . , character
	 * <li> <strong>[\\p{L}\\-'.,]+</strong> == see above disambiguation (1 or more)
	 * <li> <strong>[ ]*</strong> spaces are allowed (0 or more)
	 * <li> <strong>[a-zA-Z]+$</strong> == Any case letter (1 or more), at the end of the string
	 * </ul>
	 * @param naam
	 */
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
	
	/**
	 * <p>setter for attribute <code>emailadres</code> <br>
	 * <ul><li>originates from txtField in login screen</ul>
	 * the user email should match:</p>
	 * <ul>
	 * <li> <strong>^[a-zA-Z0-9]+</strong> == Beginning of the string must be any case letter or number (1 or more)
	 * <li> <strong>\\.?</strong> == 0 or 1 (optional) dot
	 * <li> <strong>[a-zA-Z0-9]*</strong> == any case letter or number (0 or more)
	 * <li> <strong>@</strong> == Important, required '@' character
	 * <li> <strong>[a-zA-Z]+</strong> == Any case letter (1 or more)
	 * <li> <strong>\\.</strong> Important, required '.' character
	 * <li> <strong>[a-zA-Z]+$</strong> == Any case letter (1 or more), at the end of the string
	 * </ul>
	 * @param email the user email
	 * @throws IllegalArgumentException when email is not filled in, null or doesn't match regex
	 */
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
	
	/**
	 * <p>setter for attribute <code>wachtwoord</code><br></p>
	 * <ul>
	 * <li>not allowed to contain spaces<br>
	 * <li>originates from pwdField in login-screen
	 * </ul>
	 * @param wachtwoord user password <br>
	 * @throws IllegalArgumentException when password is empty/blank/null or contains spaces<br>
	 * 
	 */
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
	
	/**
	 * User can be set active or inactive -> linked to <br>
	 * whether or not their company is set active/inactive
	 * 
	 * @param isActief <code>boolean</code> the active status of the user
	 */
	public void setIsActief(boolean isActief) {
		this.isActief = isActief;
	}
	
	//Voor tableView
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} rolProp -> the user role as string
	 */
	public StringProperty rolProperty() {
		rolProp.set(rol.toString());
		return rolProp;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} gebruikersnaam -> the user name
	 */
	public StringProperty gebruikersnaamProperty() {
		gebruikersnaam.set(naam);
		return gebruikersnaam;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} wachtWoord -> the user's password
	 */
	public StringProperty wachtwoordProperty() {
		wachtWoord.set(wachtwoord);
		return wachtWoord;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} actief -> String representation
	 * of <code>boolean</code><br> "yes" or "no" <-> "ja" of "nee"
	 */
	public StringProperty actiefProperty() {
		if(isActief == true) {
			actief.set("ja");
		} else {
			actief.set("nee");
		}
		
		return actief;
	}
}