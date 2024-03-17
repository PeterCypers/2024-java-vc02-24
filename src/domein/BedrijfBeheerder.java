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

	public void changeFilter(String filterValue, String filterValue2) {
		filteredBedrijven.setPredicate(bedrijf -> {
			if((filterValue == null || filterValue.isEmpty()) && (filterValue2 == null || filterValue2.isEmpty())) {
				return true;
			}
			
			String lowerCaseValue = filterValue.toLowerCase();
			String lowerCaseValue2 = filterValue2.toLowerCase();
			
			if((filterValue != null || !(filterValue.isEmpty())) && (filterValue2 == null || filterValue2.isEmpty())) {
				if(lowerCaseValue.equals("ja") || lowerCaseValue.equals("nee")) {
					return filterActief(bedrijf, filterValue);
				}
				
				return bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) 
						|| bedrijf.getSector().toLowerCase().contains(lowerCaseValue) 
						|| bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue)
						|| Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue);
			}
			
			if((filterValue != null || !(filterValue.isEmpty())) && (filterValue2 != null || !(filterValue2.isEmpty()))) {
				return (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) && bedrijf.getSector().toLowerCase().contains(lowerCaseValue2))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) && bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue2))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue2))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) && filterActief(bedrijf, lowerCaseValue2))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue) && bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue2))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue2))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue) && filterActief(bedrijf, lowerCaseValue2))
						|| (bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue2))
						|| (bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue) && filterActief(bedrijf, lowerCaseValue2))
						|| (Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue) &&  filterActief(bedrijf, lowerCaseValue2))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue2) && bedrijf.getSector().toLowerCase().contains(lowerCaseValue))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue2) && bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue2) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue))
						|| (bedrijf.getNaam().toLowerCase().contains(lowerCaseValue2) && filterActief(bedrijf, lowerCaseValue))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue2) && bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue2) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue))
						|| (bedrijf.getSector().toLowerCase().contains(lowerCaseValue2) && filterActief(bedrijf, lowerCaseValue))
						|| (bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue2) && Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue))
						|| (bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue2) && filterActief(bedrijf, lowerCaseValue))
						|| (Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue2) &&  filterActief(bedrijf, lowerCaseValue));
						
			}
			return false;
		});
	}
	
	private boolean filterActief(Bedrijf bedrijf, String filterValue) {
		if(filterValue.equals("ja"))
			return bedrijf.getIsActief() == true;
		return bedrijf.getIsActief() == false;
	}
	
	public void deactiveerBedrijf(Bedrijf bedrijf) {
		bedrijf.setIsActief(false);
		bedrijf.getKlant().setIsActief(false);
		bedrijf.getLeverancier().setIsActief(false);
		
		this.bedrijfService.updateBedrijf(bedrijf);
	}

	public void voegBedrijfToe(Bedrijf bedrijf) {
		this.bedrijfService.voegBedrijfToe(bedrijf);
		haalBedrijvenOp();
	}
}
