package domein;

import javafx.collections.ObservableList;
import service.bestelling.BestellingService;
import service.bestelling.BestellingServiceDbImpl;

public class BestellingController {
	
	private BestellingBeheerder bestellingBeheerder;
	private Gebruiker gebruiker;
	private BestellingService bestellingService;

	
	public BestellingController(Gebruiker leverancier) {
		this.gebruiker = leverancier;
		bestellingBeheerder = new BestellingBeheerder(gebruiker);
		this.bestellingService = new BestellingServiceDbImpl();
		
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
	
	public void updateBestelling(Bestelling bestelling) {    
	    bestellingService.updateBestelling(bestelling);
	    // Mogelijk wil je de UI of een lijst met bestellingen updaten na de wijziging
	}
}
