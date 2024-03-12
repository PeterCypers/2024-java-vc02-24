package domein;

import javafx.collections.ObservableList;

public class BedrijfController {
	
	private BedrijfBeheerder bedrijfBeheerder;
	
	public BedrijfController(Gebruiker gebruiker) {
		this.bedrijfBeheerder = new BedrijfBeheerder();
	}
	
	public ObservableList<Bedrijf> getBedrijven() {
		return this.bedrijfBeheerder.getBedrijven();
	}

	public void getFilterdList(String filterValue) {
		bedrijfBeheerder.changeFilter(filterValue);
	}
	
	public void deactiveerBedrijf(Bedrijf bedrijf) {
		bedrijfBeheerder.deactiveerBedrijf(bedrijf);
	}
	
	public void voegBedrijfToe(Bedrijf bedrijf) {
		bedrijfBeheerder.voegBedrijfToe(bedrijf);
	}
}
