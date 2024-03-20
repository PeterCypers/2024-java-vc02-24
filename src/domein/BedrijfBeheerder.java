package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.bedrijf.BedrijfService;
import service.bedrijf.BedrijfServiceDbImpl;

/**
 * Manager class for Company containing:<br>
 * <ul>
 * <li> An {@link ObservableList} of companies
 * <li> A {@link FilteredList} of the same companies filtered on a predicate
 * <li> A {@link SortedList} of the same companies sorted by the<br> specified {@link Comparator}(s)
 * </ul>
 */
public class BedrijfBeheerder {
	
	private ObservableList<Bedrijf> bedrijven;
	private FilteredList<Bedrijf> filteredBedrijven;
	private SortedList<Bedrijf> sortedBedrijven;
	
	/** connection to the service layer */
	private BedrijfService bedrijfService;
	
	private final Comparator<Bedrijf> bijNaam = (b1, b2) 
			-> b1.getNaamProp().toString().compareToIgnoreCase(b2.getNaamProp().toString());
	private final Comparator<Bedrijf> bijSector = (b1, b2)
			-> b1.getSectorProp().toString().compareToIgnoreCase(b2.getSectorProp().toString());
	private final Comparator<Bedrijf> sortOrder = bijNaam.thenComparing(bijSector);
	
	/**
	 * Constructs a new <strong>BedrijfBeheerder</strong><br>
	 * to manage companies.
	 * <ul>
	 * <li>Also initializes the company service class which calls the DB<br>
	 * <li>and creates/fills the lists with companies.
	 * </ul><br>
	 */
	public BedrijfBeheerder() {
		this.bedrijfService = new BedrijfServiceDbImpl();
		haalBedrijvenOp();
	}
	
	private void haalBedrijvenOp() {
		this.bedrijven = FXCollections.observableArrayList(bedrijfService.getBedrijven());
		this.filteredBedrijven = new FilteredList<>(this.bedrijven, b -> true);
		this.sortedBedrijven = new SortedList<Bedrijf>(filteredBedrijven, sortOrder);
	}
	
	/**
	 * This returns the sorted list of companies sorted by the Comparators.
	 * 
	 * @return {@link ObservableList} of sorted companies
	 */
	public ObservableList<Bedrijf> getBedrijven() {
		return sortedBedrijven;
	}

	/**
	 * This sets the predicate on which the {@link FilteredList}<br>
	 * <code>filteredBedrijven</code> will be filtered.
	 * 
	 * @param filterValue String determining the new predicate on which to filter
	 */
	public void changeFilter(String filterValue) {
		filteredBedrijven.setPredicate(bedrijf -> {
			if(filterValue == null || filterValue.isEmpty()) {
				return true;
			}
			
			if(filterValue.equals("ja")) {
				return bedrijf.getIsActief() == true;
			}
			
			if(filterValue.equals("nee")) {
				return bedrijf.getIsActief() == false;
			}
			
			String lowerCaseValue = filterValue.toLowerCase();
			return bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) 
					|| bedrijf.getSector().toLowerCase().contains(lowerCaseValue) 
					|| bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue);
		});
		
	}
	
	/**
	 * A user with Rol.ADMINISTRATOR can set a company as inactive
	 * 
	 * @param bedrijf The company to set as inactive
	 */
	public void deactiveerBedrijf(Bedrijf bedrijf) {
		bedrijf.setIsActief(false);
		bedrijf.getKlant().setIsActief(false);
		bedrijf.getLeverancier().setIsActief(false);
		
		this.bedrijfService.updateBedrijf(bedrijf);
	}

	/**
	 * A user with Rol.ADMIN can add a company
	 * 
	 * @param bedrijf The company to add <br>
	 * calls <code>haalBedrijvenOp</code> -> updates GUI with added company
	 */
	public void voegBedrijfToe(Bedrijf bedrijf) {
		this.bedrijfService.voegBedrijfToe(bedrijf);
		haalBedrijvenOp();
	}
}
