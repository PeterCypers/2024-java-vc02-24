package domein;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Klant {
	
	//attributen
	private String naam;
	private String contact;
	//private Adres leverAdres;
	private Adres adres;
	
	//voor tableView
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	
	//constructor
	public Klant(String naam, String contact, Adres adres) {
		this.naam = naam;
		setContact(contact);
		setKlantNaam(naam);
		setAdres(adres);
		
	}
	
	private void setKlantNaam(String name) {
		if(naam == null || !naam.matches("\\b([\\p{L}\\-'.,]+[ ]*)+"))
	        throw new IllegalArgumentException("Ongeldige naam.");
		
		naamKlant.set(name);
	}
	public void setContact(String contact) {
		String emailRegex = "^[a-zA-Z0-9]+@[hH][oO][tT][mM][aA][iI][lL]\\.[cC][oO][mM]$";
	    if (!contact.matches(emailRegex)) {
	        throw new IllegalArgumentException("Ongeldig e-mailadres!");
	    }
	    this.contact = contact;
		
	}

	public StringProperty getNaam() {
		return naamKlant;
	}
	
	public String getName() {
		return naam;
	}
	
	public String getContactgegevens() {
		return contact;
	}
	public Adres getAdres() {
		return this.adres;
	}
	public String getAdresString() {
		return adres.toString();
	}
	public void setAdres(Adres adres) {
		if(adres == null)
			throw new IllegalArgumentException("Adres ongeldig.");
		
		this.adres = adres;
	}
	 
	 
}
