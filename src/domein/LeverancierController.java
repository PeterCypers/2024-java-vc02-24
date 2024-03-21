package domein;

import java.util.List;

import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Leverancier;

public class LeverancierController {
	
	private Leverancier leverancier;
	
	public LeverancierController() {
		leverancier = (Leverancier) GebruikerHolder.getInstance();
	}
	
	public List<Betaalmethode> getBetaalmethodes(){
		return leverancier.getBetaalmethodes();
	}
	
	public void addBetaalmethodes(BedrijfController bc, Betaalmethode betaalmethode) {
		leverancier.addBetaalmethodes(bc, betaalmethode);
	}
	
	public void removeBetaalmethodes(BedrijfController bc, Betaalmethode betaalmethode) {
		leverancier.removeBetaalmethodes(bc, betaalmethode);
	}
}
