package domein;

public class Gebruiker {
	private Bestelling bestellingen;
	private int gebruikerId;
	private Rol rol;
	private String emailadres;
	private String wachtwoord;
	private String naam;
	private boolean isActief;
	private Adres adres;
	

	public Gebruiker(int gebruikerId, Rol rol, String email, String wachtwoord, String naam, boolean isActief, Adres adres) {
		setNaam(naam);
		setEmail(email);
		setRol(rol);
		setWachtwoord(wachtwoord);
		setIsActief(isActief);
		
		this.gebruikerId = gebruikerId;
		this.adres = adres;
	}
	
	private void setNaam(String naam) {
		if (naam == null || naam.isBlank()) {
			throw new IllegalArgumentException("Gebruikersnaam mag niet leeg zijn!");
		}
		
		if (naam.chars().anyMatch(c -> c == ' ')) {
			throw new IllegalArgumentException("Ongeldige gebruikersnaam!");
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
		
		String emailRegex = "^[a-zA-Z0-9]+@[a-zA-Z]+\\.[a-zA-Z]+$";
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
}