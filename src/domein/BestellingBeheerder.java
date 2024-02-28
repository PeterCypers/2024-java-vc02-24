package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.BestellingService;
import service.BestellingServiceMock;

public class BestellingBeheerder {
	private ObservableList<Bestelling> bestellingen;
	private FilteredList<Bestelling> filteredBestellingen;
	private SortedList<Bestelling> sortedBestellingen;
	
	private BestellingService bestellingService;
	
	private final Comparator<Bestelling> bijKlant = (b1, b2)
	            -> b1.getKlantName().compareToIgnoreCase(b2.getKlantName());
	
	
	public BestellingBeheerder(Gebruiker leverancier) {
		bestellingService = new BestellingServiceMock();
		bestellingen = FXCollections.observableArrayList(bestellingService.getBestellingen(leverancier));
		filteredBestellingen = new FilteredList<>(bestellingen, b -> true);     
        sortedBestellingen = new SortedList<>(filteredBestellingen, bijKlant);
	}
	
	public ObservableList<Bestelling> getBestellingen() {
		//return FXCollections.unmodifiableObservableList(bestellingen);
		return sortedBestellingen;
	}
	
	public void changeFilter(String filterValue) {
        filteredBestellingen.setPredicate(bestelling -> {
            // If filter text is empty, display all persons.
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with   
            //filter text.
            String lowerCaseValue = filterValue.toLowerCase();
            return bestelling.getKlantName().toLowerCase().contains(lowerCaseValue);  
        }
        );
    }
}
