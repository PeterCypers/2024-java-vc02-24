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
		
		this.gebruikerId = gebruikerId;
		this.isActief = isActief;
		this.adres = adres;
	}
	
	private void setNaam(String naam) {
		//TODO controle op naam
		this.naam = naam;
	}
	
	public String getNaam() {
		return naam;
	}
	
	private void setEmail(String email) {
		//TODO controle op email
		this.emailadres = email;
	}
	
	public String getEmail() {
		return emailadres;
	}
	
	private void setWachtwoord(String wachtwoord) {
		//TODO controle op wachtwoord
		this.wachtwoord = wachtwoord;
	}
	
	public String getWachtwoord() {
		return this.wachtwoord;
	}
	
	//public setter
	public void setRol(Rol rol) {
		//TODO rolcontrole en logic
		this.rol = rol;
	}
}