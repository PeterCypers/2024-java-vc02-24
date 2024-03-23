package domein;

import java.time.LocalDate;

import domein.gebruiker.Klant;
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
	public void getFilterdList(LocalDate filterDate, OrderStatus filterOrderStatus, 
			BetalingsStatus filterBetalingsStatus, String filterterm, Klant klant) {
		bestellingBeheerder.changeFilter(filterDate, filterOrderStatus, filterBetalingsStatus, filterterm, klant);
	}
	
	public void updateBestelling(Bestelling bestelling) {    
	    bestellingService.updateBestelling(bestelling);
	    // Mogelijk wil je de UI of een lijst met bestellingen updaten na de wijziging
	}
}
