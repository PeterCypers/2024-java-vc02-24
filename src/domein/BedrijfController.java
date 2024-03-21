package domein;

import javafx.collections.ObservableList;

public class BedrijfController {
	
	private BedrijfBeheerder bedrijfBeheerder;
	
	public BedrijfController() {
		this.bedrijfBeheerder = new BedrijfBeheerder();
	}
	
	public ObservableList<Bedrijf> getBedrijven() {
		return this.bedrijfBeheerder.getBedrijven();
	}

	public void getFilterdList(String filterValue, String filterValue2) {
		bedrijfBeheerder.changeFilter(filterValue, filterValue2);
	}
	
	public void deactiveerBedrijf(Bedrijf bedrijf) {
		bedrijfBeheerder.deactiveerBedrijf(bedrijf);
	}
	
	public void voegBedrijfToe(Bedrijf bedrijf) {
		bedrijfBeheerder.voegBedrijfToe(bedrijf);
	}
	
	public void updateBedrijf(Bedrijf bedrijf) {
		bedrijfBeheerder.updateBedrijf(bedrijf);
	}
}
