package domein;

import java.time.LocalDate;
import java.util.Comparator;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.bestelling.BestellingService;
import service.bestelling.BestellingServiceDbImpl;

/**
 * Manager class for Orders containing:<br>
 * <ul>
 * <li> An {@link ObservableList} of orders
 * <li> A {@link FilteredList} of the same orders filtered on a predicate
 * <li> A {@link SortedList} of the same orders sorted by the<br> specified {@link Comparator}(s)
 * </ul>
 */
public class BestellingBeheerder {

	private FilteredList<Bestelling> filteredBestellingen;
	private SortedList<Bestelling> sortedBestellingen;

	/** connection to the service layer */
	private BestellingService bestellingService;

	private final Comparator<Bestelling> bijOrderId = (b1, b2)
			-> Integer.toString(b1.getOrderId()).compareToIgnoreCase(Integer.toString(b2.getOrderId()));

	private final Comparator<Bestelling> bijDatum = (b1, b2)
			-> b2.getDatumGeplaats().toString().compareToIgnoreCase(b1.getDatumGeplaats().toString());

	private final Comparator<Bestelling> bijKlant = (b1, b2)
	        -> b1.getBedrijfsnaam().compareToIgnoreCase(b2.getBedrijfsnaam());

	private final Comparator<Bestelling> bijOrderStatus = (b1, b2)
			-> b1.getOrderStatus().toString().compareToIgnoreCase(b2.getOrderStatus().toString());

	private final Comparator<Bestelling> bijBetalingStatus = (b1, b2)
			-> b1.getBetalingStatus().toString().compareToIgnoreCase(b2.getBetalingStatus().toString());

	private final Comparator<Bestelling> orderSorted = bijDatum.thenComparing(bijOrderId)
			.thenComparing(bijKlant).thenComparing(bijOrderStatus).thenComparing(bijBetalingStatus);

	/**
	 * Extra constructor mainly for Mockito-injection
	 *
	 * @param leverancier Singleton logged-in user
	 * @param bs a new BestellingServiceDbImpl from default constructor<br>
	 * facilitates Mockito-injection for testing
	 */
	public BestellingBeheerder(Gebruiker leverancier, BestellingService bs) {
		this.bestellingService = bs;
		ObservableList<Bestelling> bestellingen = FXCollections.observableArrayList(bestellingService.getBestellingen(leverancier));
		filteredBestellingen = new FilteredList<>(bestellingen, b -> true);
        sortedBestellingen = new SortedList<>(filteredBestellingen, orderSorted);
	}

	/**
	 * Constructs a new <strong>BestellingBeheerder</strong><br>
	 * to manage orders.
	 * <ul>
	 * <li>Also initializes the order service class which calls the DB
	 * <li>and creates/fills the lists with orders.
	 * </ul>
	 * This default constructor is the main constructor in use.<br>
	 */
	public BestellingBeheerder() {
		this(GebruikerHolder.getInstance(), new BestellingServiceDbImpl());
	}

	/**
	 * This returns the sorted list of orders sorted by the Comparators.
	 *
	 * @return {@link ObservableList} of sorted orders
	 */
	public ObservableList<Bestelling> getBestellingen() {
		return sortedBestellingen;
	}

	/**
	 * This sets the predicate on which the {@link FilteredList}<br>
	 * <code>filteredBestellingen</code> will be filtered.
	 *
	 * @param filterValue String determining the new predicate on which to filter
	 */
	public void changeFilter(LocalDate filterDate, OrderStatus filterOrderStatus,
			BetalingsStatus filterBetalingsStatus, String filterValue, Klant klant) {

		filteredBestellingen.setPredicate(bestelling ->
	    	(filterDate == null || bestelling.toSearchString().contains(filterDate.toString())) &&
	    	(filterValue == null || filterValue.isBlank() || bestelling.toSearchString().toLowerCase().contains(filterValue.toLowerCase())) &&
	    	(filterOrderStatus == OrderStatus.filter || bestelling.getOrderStatus() == filterOrderStatus) &&
	    	(filterBetalingsStatus == BetalingsStatus.filter ||  bestelling.getBetalingsStatus() == filterBetalingsStatus) &&
	    	(klant == null || bestelling.getKlant() == klant)
		);
	}

	public void updateBestelling(Bestelling bestelling) {
		bestellingService.updateBestelling(bestelling);
	}
}