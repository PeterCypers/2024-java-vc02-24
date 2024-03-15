package domein;

import javafx.collections.ObservableList;

public class KlantController {
	
	private KlantBeheerder klantBeheerder;
	
	public KlantController() {
		klantBeheerder = new KlantBeheerder();
	}
	
	public ObservableList<Klant> getKlanten(){
		return klantBeheerder.getKlanten();
	}
	
	// getGebruiker() & getNaamGebruiker() : removed -> singleton
	
	public void getFilteredList(String zoekterm) {
		klantBeheerder.changeFilter(zoekterm);
	}
}
