package domein;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import service.bedrijf.BedrijfService;
import service.bedrijf.BedrijfServiceDbImpl;

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
		haalBedrijvenOp();
	}
	
	private void haalBedrijvenOp() {
		this.bedrijven = FXCollections.observableArrayList(bedrijfService.getBedrijven());
		this.filteredBedrijven = new FilteredList<>(this.bedrijven, b -> true);
		this.sortedBedrijven = new SortedList<Bedrijf>(filteredBedrijven, sortOrder);
	}
	
	public ObservableList<Bedrijf> getBedrijven() {
		return sortedBedrijven;
	}

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
	
	public void deactiveerBedrijf(Bedrijf bedrijf) {
		bedrijf.setIsActief(false);
		bedrijf.getKlantGebruiker().setIsActief(false);
		bedrijf.getLeverancierGebruiker().setIsActief(false);
		
		this.bedrijfService.updateBedrijf(bedrijf);
	}

	public void voegBedrijfToe(Bedrijf bedrijf) {
		this.bedrijfService.voegBedrijfToe(bedrijf);
		haalBedrijvenOp();
	}
}
