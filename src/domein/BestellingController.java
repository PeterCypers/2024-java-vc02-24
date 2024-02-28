package domein;

import javafx.collections.ObservableList;

public class BestellingController {
	
	private BestellingBeheerder bestellingBeheerder;
	private Gebruiker gebruiker;
	
	public BestellingController(Gebruiker leverancier) {
		this.gebruiker = leverancier;
		bestellingBeheerder = new BestellingBeheerder(gebruiker);
	}
	
	public ObservableList<Bestelling> getBestellingen() {
		return bestellingBeheerder.getBestellingen();
	}
	
	public Gebruiker getGebruiker() {
		return gebruiker;
	}
	
	public String getNaamGebruiker() {
		return gebruiker.getNaam();
	}

	public void getFilterdList(String zoekterm) {
		bestellingBeheerder.changeFilter(zoekterm);
	}
}
