package domein;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Klant {
	
	//attributen
	private String naam;
	private String contact;
	private Adres leverAdres;
	
	//voor tableView
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	
	//constructor
	public Klant(String naam, String contact, String land, String stad,
			String postcode, String straat, String straatNr) {
		this.naam = naam;
		this.contact = contact;
		setKlantNaam(naam);
		this.leverAdres = new Adres(land, stad, postcode, straat, straatNr);
	}
	
	private void setKlantNaam(String name) {
		naamKlant.set(name);
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
	
	public String getAdres() {
		return leverAdres.toString();
	}
}
