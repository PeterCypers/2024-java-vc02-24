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

}
