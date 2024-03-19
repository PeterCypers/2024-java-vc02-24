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
			
			//Eén veld is ingevuld -> moet het eerste veld zijn
			if((filterValue != null || !(filterValue.isEmpty())) && (filterValue2 == null || filterValue2.isEmpty())) {
				if(lowerCaseValue.equals("ja") || lowerCaseValue.equals("nee")) {
					return filterActief(bedrijf, filterValue);
				}
				
				return bedrijf.getNaam().toLowerCase().contains(lowerCaseValue) 
						|| bedrijf.getSector().toLowerCase().contains(lowerCaseValue) 
						|| bedrijf.getAdres().toString().toLowerCase().contains(lowerCaseValue)
						|| Integer.toString(bedrijf.aantalKlanten()).equals(lowerCaseValue);
			}
			
			//De twee velden zijn ingevuld -> logica twee keer omdat je ook in het tweede veld naam zet en adres in eerst veld;
			return filterNaamSector(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterNaamAdres(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterNaamAantalKlanten(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterNaamActief(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterSectorAdres(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterSectorAantalKlanten(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterSectorActief(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterAdresAantalKlanten(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterAdresActief(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterAantalKlantenActief(bedrijf, lowerCaseValue, lowerCaseValue2)
					|| filterNaamSector(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterNaamAdres(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterNaamAantalKlanten(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterNaamActief(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterSectorAdres(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterSectorAantalKlanten(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterSectorActief(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterAdresAantalKlanten(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterAdresActief(bedrijf, lowerCaseValue2, lowerCaseValue)
					|| filterAantalKlantenActief(bedrijf, lowerCaseValue2, lowerCaseValue);
		});
	}
	
	private boolean filterNaamSector(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getNaam().toLowerCase().contains(filterValue) && bedrijf.getSector().toLowerCase().contains(filterValue2);
	}
	
	private boolean filterNaamAdres(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getNaam().toLowerCase().contains(filterValue) && bedrijf.getAdres().toString().toLowerCase().contains(filterValue2);
	}
	
	private boolean filterNaamAantalKlanten(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getNaam().toLowerCase().contains(filterValue) && Integer.toString(bedrijf.aantalKlanten()).equals(filterValue2);
	}
	
	private  boolean filterNaamActief(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getNaam().toLowerCase().contains(filterValue) && filterActief(bedrijf, filterValue2);
	}
	
	private boolean filterSectorAdres(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getSector().toLowerCase().contains(filterValue) && bedrijf.getAdres().toString().toLowerCase().contains(filterValue2);
	}
	
	private boolean filterSectorAantalKlanten(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getSector().toLowerCase().contains(filterValue) && Integer.toString(bedrijf.aantalKlanten()).equals(filterValue2);
	}
	
	private boolean filterSectorActief(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getSector().toLowerCase().contains(filterValue) && filterActief(bedrijf, filterValue2);
	}
	
	private boolean filterAdresAantalKlanten(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getAdres().toString().toLowerCase().contains(filterValue) && Integer.toString(bedrijf.aantalKlanten()).equals(filterValue2);
	}
	
	private boolean filterAdresActief(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return bedrijf.getAdres().toString().toLowerCase().contains(filterValue) && filterActief(bedrijf, filterValue2);
	}
	
	private boolean filterAantalKlantenActief(Bedrijf bedrijf, String filterValue, String filterValue2) {
		return Integer.toString(bedrijf.aantalKlanten()).equals(filterValue) && filterActief(bedrijf, filterValue2);
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
