package domein;

import javafx.collections.ObservableList;
import service.bestelling.BestellingService;
import service.bestelling.BestellingServiceDbImpl;

public class BestellingController {
	
	private BestellingBeheerder bestellingBeheerder;
	private BestellingService bestellingService;

	public BestellingController() {
		bestellingBeheerder = new BestellingBeheerder();
		this.bestellingService = new BestellingServiceDbImpl();
	}
	
	public ObservableList<Bestelling> getBestellingen() {
		return bestellingBeheerder.getBestellingen();
	}
	
	// getGebruiker() & getNaamGebruiker() : removed -> singleton

	public void getFilterdList(String zoekterm) {
		bestellingBeheerder.changeFilter(zoekterm);
	}
	
	public void updateBestelling(Bestelling bestelling) {    
	    bestellingService.updateBestelling(bestelling);
	    // Mogelijk wil je de UI of een lijst met bestellingen updaten na de wijziging
	}
}
