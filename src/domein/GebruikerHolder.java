package domein;

/** SingleTon with setter + getter for the logged-in Leverancier/Admin
 * 
 * feature: [afmelden] -> justify setter
 * new logged-in user overwrites the previous logged-in user
 * @author peter
 */
public class GebruikerHolder {
	
	private static Gebruiker instance = null;
	public static Gebruiker getInstance() {
		if (instance == null) 
			throw new IllegalArgumentException("Er is nog geen ingelogde gebruiker (singleton)");
		return instance;
	}
	
	public static void setInstance(Gebruiker aangemeldeGebruiker) {
		if(aangemeldeGebruiker.getRol().equals(Rol.KLANT))
			throw new IllegalArgumentException("klant mag niet inloggen");
		instance = aangemeldeGebruiker;
	}
	
	//optioneel gebruiker-singleton met constructor
	public static void setWithConstructor(Rol rol, String email, String wachtwoord, String naam, boolean isActief, Adres adres) {
		if (rol != Rol.KLANT) {
			instance = new Gebruiker(rol, email, wachtwoord, naam, isActief, adres);
		}
	}
}
