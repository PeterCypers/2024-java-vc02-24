package domein;

import java.util.Comparator;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.klant.KlantService;
import service.klant.KlantServiceDbImpl;

/**
 * Manager class for Customers containing:<br>
 * <ul>
 * <li> An {@link ObservableList} of Customers
 * <li> A {@link FilteredList} of the same Customers filtered on a predicate
 * <li> A {@link SortedList} of the same Customers sorted by the<br> specified {@link Comparator}(s)
 * </ul>
 */
public class KlantBeheerder {
	private ObservableList<Klant> klanten;
	private FilteredList<Klant> filteredKlanten;
	private SortedList<Klant> sortedKlanten;
	
	/** connection to the service layer */
	private KlantService klantService;
	
	//sort op String naam (er is ook een SimpleStringProperty naamKlant)
	private final Comparator<Klant> opNaam = (k1, k2)
			-> k1.getNaam().compareToIgnoreCase(k2.getNaam());

	//TODO extra sorteringen -> zie BestellingBeheerder
	
	/**
	 * Extra constructor for Mockito-injection
	 * 
	 * @param leverancier Singleton logged-in user
	 * @param ks a new KlantServiceDbImpl from default constructor<br>
	 * facilitates Mockito-injection for testing
	 */
	public KlantBeheerder(Gebruiker leverancier, KlantService ks) {
		klantService = ks;
		klanten = FXCollections.observableArrayList(klantService.getKlanten(leverancier));
		filteredKlanten = new FilteredList<>(klanten, k -> true);
		sortedKlanten = new SortedList<>(filteredKlanten, opNaam);
	}
	
	/**
	 * Constructs a new <strong>KlantBeheerder</strong><br>
	 * to manage customers.
	 * <ul>
	 * <li>Also initializes the customer service class which calls the DB
	 * <li>and creates/fills the lists with customers.
	 * </ul><br>
	 */
	public KlantBeheerder() {
		this(GebruikerHolder.getInstance(), new KlantServiceDbImpl());		
	}
	
	/**
	 * This returns the sorted list of customers sorted by the Comparators.
	 * 
	 * @return {@link ObservableList} of sorted customers
	 */
	public ObservableList<Klant> getKlanten() {
		return sortedKlanten;
	}
	
	/**
	 * This sets the predicate on which the {@link FilteredList}<br>
	 * <code>filteredKlanten</code> will be filtered.
	 * 
	 * @param filterValue String determining the new predicate on which to filter
	 */
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
