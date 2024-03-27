package domein;

import java.util.List;

import service.betaling.BetalingService;
import service.betaling.BetalingServiceDbImpl;

public class BetalingController {

	private BetalingBeheerder betalingBeheerder;
	private BetalingService betalingService;

	public BetalingController() {
		betalingService = new BetalingServiceDbImpl();
		betalingBeheerder = new BetalingBeheerder(betalingService);
	}

	public List<Betaling> getBetalingen(){
		return betalingBeheerder.getBetalingen();
	}

	public void verwerkBetalingen() {
		betalingBeheerder.verwerkBetalingen();
	}
}