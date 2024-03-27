package domein;

import java.util.NoSuchElementException;

import domein.gebruiker.GebruikerHolder;
import service.gebruiker.GebruikerService;
import service.gebruiker.GebruikerServiceDbImpl;
/**
 * Controller for logging in a user
 */
public class AanmeldController {

	private final GebruikerService gs;

	/**
	 * <p>Constructs a new <strong>AanmeldController</strong> <br>
	 * & instantiates <code>gs</code> for interaction with the service-layer.
	 * </p>
	 */
	public AanmeldController() {
		gs = new GebruikerServiceDbImpl();
	}

	/**
	 * This takes the input from the login screen input fields <br>
	 * and attempts to find a <strong><code>Gebruiker</code></strong> in the database <br>
	 * who's values match the given parameter values
	 *
	 * @param emailadres txtField email address input
	 * @param wachtwoord pwdField password input
	 * @throws NoSuchElementException if no matching user is found in the database
	 */
	public void meldGebruikerAan(String emailadres, String wachtwoord) throws NoSuchElementException {
		GebruikerHolder.setInstance(gs.meldGebruikerAan(emailadres, wachtwoord));
	}
}