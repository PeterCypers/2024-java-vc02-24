package domein;

import java.time.LocalDate;
import java.util.Comparator;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
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
	
	private final Comparator<Bestelling> bijOrderId = (b1, b2)
			-> Integer.toString(b1.getOrderId()).compareToIgnoreCase(Integer.toString(b2.getOrderId()));
	
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
	
	public void changeFilter(LocalDate filterDate, OrderStatus filterOrderStatus, 
			BetalingsStatus filterBetalingsStatus, String filterValue) {
		
		filteredBestellingen.setPredicate(bestelling ->
	    	(filterDate == null || bestelling.toSearchString().contains(filterDate.toString())) &&
	    	(filterValue.isBlank() ||  bestelling.toSearchString().toLowerCase().contains(filterValue.toLowerCase())) &&
	    	(filterOrderStatus == null || bestelling.getOrderStatus() == filterOrderStatus) &&
	    	(filterBetalingsStatus == null ||  bestelling.getBetalingsStatus() == filterBetalingsStatus)
		);
	}
}
