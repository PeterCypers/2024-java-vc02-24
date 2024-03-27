package domein.gebruiker;

/** SingleTon with setter + getter for the logged-in Leverancier/Admin
 * 
 * feature: [afmelden] -> justify setter
 * new logged-in user overwrites the previous logged-in user
 * @author peter
 */
public class GebruikerHolder {
	
	/** Singleton single instance representing the logged-in user */
	private static Gebruiker instance = null;
	
	/**
	 * @return the logged-in user
	 * @throws IllegalArgumentException when instance == null <br>
	 * this would mean there is no logged in user
	 */
	public static Gebruiker getInstance() {
		if (instance == null) 
			throw new IllegalArgumentException("Er is nog geen ingelogde gebruiker (singleton)");
		return instance;
	}
	
	/**
	 * <p>This sets <code>instance</code> to a new logged-in user. <br>
	 * Condition: Role cannot be client.</p>
	 * 
	 * @param aangemeldeGebruiker the user to be logged-in <br>
	 * object originates from a find-operation at service - DB interaction <br>
	 * called from <strong><code>domein.AanmeldController</code></strong>
	 */
	public static void setInstance(Gebruiker aangemeldeGebruiker) {
		if(aangemeldeGebruiker.getRol().equals(Rol.KLANT))
			throw new IllegalArgumentException("klant mag niet inloggen");
		instance = aangemeldeGebruiker;
	}
}