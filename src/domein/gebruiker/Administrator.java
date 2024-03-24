package domein.gebruiker;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Represents an Administrator
 * <p>This class contains information about the Administrator.</p>
 */
@Entity
@DiscriminatorValue(value=Rol.Values.ADMINISTRATOR)
public class Administrator extends Gebruiker {

	private static final long serialVersionUID = 1L;
	
	/** <code>entity class</code> JPA-required default constructor */
	public Administrator() {}

	/**
	 * Constructs a new <strong>Administrator</strong> with the specified details.
	 * 
	 * @param email the login email linked to this administrator
	 * @param wachtwoord the login password linked to this administrator
	 * @param naam the name of this administrator
	 */
	public Administrator(String email, String wachtwoord, String naam) {
		super(email, wachtwoord, naam, true, Rol.ADMINISTRATOR);
	}

}
