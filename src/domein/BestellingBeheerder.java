package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.bestelling.BestellingService;
import service.bestelling.BestellingServiceDbImpl;

public class BestellingBeheerder {
	private ObservableList<Bestelling> bestellingen;
	private FilteredList<Bestelling> filteredBestellingen;
	private SortedList<Bestelling> sortedBestellingen;
	
	private BestellingService bestellingService;
	
	//sorteren op OrderId gaat nog niet omdat het een int is
	private final Comparator<Bestelling> bijOrderId = (b1, b2)
			-> b1.orderIdProperty().toString().compareToIgnoreCase(b2.orderIdProperty().toString());
	
	private final Comparator<Bestelling> bijDatum = (b1, b2)
			-> b2.getDatumGeplaats().toString().compareToIgnoreCase(b1.getDatumGeplaats().toString());
	
	private final Comparator<Bestelling> bijKlant = (b1, b2)
	            -> b1.getKlantName().compareToIgnoreCase(b2.getKlantName());
	            
	private final Comparator<Bestelling> bijOrderStatus = (b1, b2)
			-> b1.getOrderStatus().toString().compareToIgnoreCase(b2.getOrderStatus().toString());
			
	private final Comparator<Bestelling> bijBetalingStatus = (b1, b2)
			-> b1.getBetalingStatus().toString().compareToIgnoreCase(b2.getBetalingStatus().toString());
			
	private final Comparator<Bestelling> orderSorted = bijDatum.thenComparing(bijOrderId)
			.thenComparing(bijKlant).thenComparing(bijOrderStatus).thenComparing(bijBetalingStatus);
	//Mockito-injection
	public BestellingBeheerder(Gebruiker leverancier, BestellingService bs) {
		bestellingService = bs;
		bestellingen = FXCollections.observableArrayList(bestellingService.getBestellingen(leverancier));
		filteredBestellingen = new FilteredList<>(bestellingen, b -> true);     
        sortedBestellingen = new SortedList<>(filteredBestellingen, orderSorted);
	}
	
	public BestellingBeheerder() {
		this(GebruikerHolder.getInstance(), new BestellingServiceDbImpl());
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
            return Integer.toString(bestelling.getOrderId()).equals(lowerCaseValue)	//filter op OrderID
            		|| bestelling.getDatumGeplaats().toString().toLowerCase().equals(lowerCaseValue)	//filter op Datum
            		||bestelling.getKlantName().toLowerCase().equals(lowerCaseValue) //filter op Klantnaam
            		|| bestelling.getOrderStatus().toString().toLowerCase().equals(lowerCaseValue) //filter op Orderstatus
            		|| bestelling.getBetalingStatus().toString().toLowerCase().equals(lowerCaseValue); //filter op Betalingsstatus
        });
    }
}
