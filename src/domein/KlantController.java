package domein;

import javafx.collections.ObservableList;

public class KlantController {
	
	private KlantBeheerder klantBeheerder;
	private Gebruiker gebruiker;
	
	public KlantController(Gebruiker leverancier) {
		this.gebruiker = leverancier;
		klantBeheerder = new KlantBeheerder(gebruiker);
	}
	
	public ObservableList<Klant> getKlanten(){
		return klantBeheerder.getKlanten();
	}
	
	public Gebruiker getGebruiker() {
		return gebruiker;
	}
	
	public String getNaamGebruiker() {
		return gebruiker.getNaam();
	}
	
	public void getFilteredList(String zoekterm) {
		klantBeheerder.changeFilter(zoekterm);
	}
}
