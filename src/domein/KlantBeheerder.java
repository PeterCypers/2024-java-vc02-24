package domein;

import java.util.Comparator;

import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Klant;
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
	
	//sort op String naam (er is ook een SimpleStringProperty naamKlant)
	private final Comparator<Klant> opNaam = (k1, k2)
			-> k1.getNaam().compareToIgnoreCase(k2.getNaam());
			
	public KlantBeheerder() {
		klantService = new KlantServiceDbImpl();
		klanten = FXCollections.observableArrayList(klantService.getKlanten(GebruikerHolder.getInstance()));
		filteredKlanten = new FilteredList<>(klanten, k -> true);
		sortedKlanten = new SortedList<>(filteredKlanten, opNaam);
		
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
					|| Integer.toString(klant.getAantalOpenstaandeBestellingen(GebruikerHolder.getInstance())).equals(filterValue);
			
		});
	}
	
}
