package domein;

import java.util.List;

import service.betaling.BetalingService;

public class BetalingBeheerder {

	private BetalingService betalingService;

	public BetalingBeheerder(BetalingService bs) {
		betalingService = bs;
	}

	public List<Betaling> getBetalingen(){
		return betalingService.getBetalingen();
	}

	public void verwerkBetalingen() {
		betalingService.verwerkBetalingen();
	}
}