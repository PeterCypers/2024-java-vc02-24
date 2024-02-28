package domein;

import java.util.Comparator;
import java.util.NoSuchElementException;

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
	
	//sorteren op OrderId gaat nog niet omdat het een int is
	private final Comparator<Bestelling> bijOrderId = (b1, b2)
			-> b1.orderIdProperty().toString().compareToIgnoreCase(b2.orderIdProperty().toString());
	
	private final Comparator<Bestelling> bijDatum = (b1, b2)
			-> b1.getDatumGeplaats().toString().compareToIgnoreCase(b2.getDatumGeplaats().toString());
	
	private final Comparator<Bestelling> bijKlant = (b1, b2)
	            -> b1.getKlantName().compareToIgnoreCase(b2.getKlantName());
	            
	private final Comparator<Bestelling> bijOrderStatus = (b1, b2)
			-> b1.getOrderStatus().toString().compareToIgnoreCase(b2.getOrderStatus().toString());
			
	private final Comparator<Bestelling> bijBetalingStatus = (b1, b2)
			-> b1.getBetalingStatus().toString().compareToIgnoreCase(b2.getBetalingStatus().toString());
			
	private final Comparator<Bestelling> orderSorted = bijOrderId.thenComparing(bijDatum)
			.thenComparing(bijKlant).thenComparing(bijOrderStatus).thenComparing(bijBetalingStatus);
	
	
	public BestellingBeheerder(Gebruiker leverancier) {
		bestellingService = new BestellingServiceMock();
		bestellingen = FXCollections.observableArrayList(bestellingService.getBestellingen(leverancier));
		filteredBestellingen = new FilteredList<>(bestellingen, b -> true);     
        sortedBestellingen = new SortedList<>(filteredBestellingen, orderSorted);
	}
	
	public ObservableList<Bestelling> getBestellingen() {
		return sortedBestellingen;
	}
	
	public void changeFilter(String filterValue) {
        filteredBestellingen.setPredicate(bestelling -> {
            // If filter text is empty, display all orders.
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }
              
            //filter text
            String lowerCaseValue = filterValue.toLowerCase();
            return bestelling.getKlantName().toLowerCase().contains(lowerCaseValue) //filter op klant naam
            		|| bestelling.getOrderStatus().toString().toLowerCase().contains(lowerCaseValue) //filter op orderstatus
            		|| bestelling.getBetalingStatus().toString().toLowerCase().contains(lowerCaseValue); //filter op betalingsstatus
            
        }
        );
    }
}
