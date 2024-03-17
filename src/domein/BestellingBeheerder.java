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
		
		filteredBestellingen.setPredicate(bestelling -> {
			if((filterValue.isEmpty() || filterValue == null) && filterDate == null && filterOrderStatus == null 
					&& filterBetalingsStatus == null) {
				return true;
			}
			
			String lowerCaseValue = filterValue.toLowerCase();
			
			if(filterDate == null && filterBetalingsStatus == null && filterOrderStatus == null) {
				return filterText(bestelling, lowerCaseValue);
			}
			if(filterOrderStatus == null && filterBetalingsStatus == null) {
				return filterDate(bestelling, filterDate, lowerCaseValue);
			}
			if(filterBetalingsStatus == null) {
				return filterOrderStatus(bestelling, filterOrderStatus, filterDate, lowerCaseValue);
			}
			return filterBetalingsStatus(bestelling, filterBetalingsStatus, filterOrderStatus, filterDate, lowerCaseValue);
		});
	}
	
	private boolean filterBetalingsStatus(Bestelling bestelling, BetalingsStatus filterBetalingsStatus,
			OrderStatus filterOrderStatus, LocalDate filterDate, String filterValue) {
		if((filterValue == null || filterValue.isEmpty()) && filterDate == null && filterOrderStatus == null) {
			return bestelling.getBetalingsStatus().equals(filterBetalingsStatus);
		}
		return (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterDate(bestelling, filterDate, filterValue)) //filter op betalingsstatus en Datum
				|| (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterOrderStatus(bestelling, filterOrderStatus, filterDate, filterValue)) //filter op Betalingsstatus en Orderstatus
				|| (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterText(bestelling, filterValue)); //filter op Betalingsstatus en text
	}

	private boolean filterOrderStatus(Bestelling bestelling, OrderStatus filterOrderStatus, LocalDate filterDate,
			String filterValue) {
		if((filterValue == null || filterValue.isEmpty()) && filterDate == null) {
			return bestelling.getOrderStatus().equals(filterOrderStatus);
		}
		return (bestelling.getOrderStatus().equals(filterOrderStatus) && filterDate(bestelling, filterDate, filterValue)) //filter op Orderstatus en datum
				|| (bestelling.getOrderStatus().equals(filterOrderStatus) && filterText(bestelling, filterValue));//filter op OrderStatus en text
	}

	private boolean filterDate(Bestelling bestelling, LocalDate filterDate, String filterValue) {
        if(filterValue == null || filterValue.isEmpty()) {
        	return bestelling.getDatumGeplaats().equals(filterDate);
        }
        
        return bestelling.getDatumGeplaats().equals(filterDate) && filterText(bestelling, filterValue); //filter op Datum en text
	}
	
	private boolean filterText(Bestelling bestelling, String filterValue) {
		return Integer.toString(bestelling.getOrderId()).equals(filterValue) //filter op OrderID
            		|| bestelling.getKlantName().toLowerCase().equals(filterValue); //filter op Klantnaam 
    }
}
