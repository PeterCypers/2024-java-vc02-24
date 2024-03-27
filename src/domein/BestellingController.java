package domein;

import java.time.LocalDate;

import domein.gebruiker.Klant;
import javafx.collections.ObservableList;

public class BestellingController {

	private BestellingBeheerder bestellingBeheerder;

	public BestellingController() {
		bestellingBeheerder = new BestellingBeheerder();
	}

	public ObservableList<Bestelling> getBestellingen() {
		return bestellingBeheerder.getBestellingen();
	}

	public void getFilterdList(LocalDate filterDate, OrderStatus filterOrderStatus,
			BetalingsStatus filterBetalingsStatus, String filterterm, Klant klant) {
		bestellingBeheerder.changeFilter(filterDate, filterOrderStatus, filterBetalingsStatus, filterterm, klant);
	}

	public void updateBestelling(Bestelling bestelling) {
	    bestellingBeheerder.updateBestelling(bestelling);
	}
}