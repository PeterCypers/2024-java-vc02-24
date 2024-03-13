package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.klant.KlantService;
import service.klant.KlantServiceDbImpl;

public class KlantBeheerder {
	private ObservableList<Klant> klanten;
	private FilteredList<Klant> filteredKlanten;
	private SortedList<Klant> sortedKlanten;
	
	private KlantService klantService;
	
	private Gebruiker leverancier;
	
	//sort op String naam (er is ook een SimpleStringProperty naamKlant)
	private final Comparator<Klant> opNaam = (k1, k2)
			-> k1.getNaam().compareToIgnoreCase(k2.getNaam());

	//TODO extra sorteringen -> zie BestellingBeheerder
			
	public KlantBeheerder(Gebruiker leverancier) {
		klantService = new KlantServiceDbImpl();
		klanten = FXCollections.observableArrayList(klantService.getKlanten(leverancier));
		filteredKlanten = new FilteredList<>(klanten, k -> true);
		sortedKlanten = new SortedList<>(filteredKlanten, opNaam);
		
		this.leverancier = leverancier;
	}
	
	public ObservableList<Klant> getKlanten() {
		return sortedKlanten;
	}
	
	public void changeFilter(String filterValue) {
		filteredKlanten.setPredicate(klant -> {
			// If filter text is empty, display all customers.
			if (filterValue == null || filterValue.isBlank()) {
				return true;
			}
			
			//filter text
			String lowerCaseValue = filterValue.toLowerCase();
			return klant.getNaam().toLowerCase().equals(lowerCaseValue) 
					|| Integer.toString(klant.getAantalOpenstaandeBestellingen(leverancier)).equals(filterValue);
			
		});
	}
	
}
