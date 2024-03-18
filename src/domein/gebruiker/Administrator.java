package domein.gebruiker;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value=Rol.Values.ADMINISTRATOR)
public class Administrator extends Gebruiker {

	private static final long serialVersionUID = 1L;
	
	public Administrator() {}

	public Administrator(String email, String wachtwoord, String naam) {
		super(email, wachtwoord, naam, true, Rol.ADMINISTRATOR);
	}

}
