package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.BedrijfService;
import service.BedrijfServiceDbImpl;

public class BedrijfBeheerder {
	
	private ObservableList<Bedrijf> bedrijven;
	private FilteredList<Bedrijf> filteredBedrijven;
	private SortedList<Bedrijf> sortedBedrijven;
	
	private BedrijfService bedrijfService;
	
	private final Comparator<Bedrijf> bijNaam = (b1, b2) 
			-> b1.getNaamProp().toString().compareToIgnoreCase(b2.getNaamProp().toString());
	private final Comparator<Bedrijf> bijSector = (b1, b2)
			-> b1.getSectorProp().toString().compareToIgnoreCase(b2.getSectorProp().toString());
	private final Comparator<Bedrijf> sortOrder = bijNaam.thenComparing(bijSector);
	
	public BedrijfBeheerder() {
		this.bedrijfService = new BedrijfServiceDbImpl();
		this.bedrijven = FXCollections.observableArrayList(bedrijfService.getBedrijven());
		this.filteredBedrijven = new FilteredList<>(this.bedrijven, b -> true);
		this.sortedBedrijven = new SortedList<Bedrijf>(filteredBedrijven, sortOrder);
	}
	
	public ObservableList<Bedrijf> getBedrijven() {
		return this.sortedBedrijven;
	}
}
