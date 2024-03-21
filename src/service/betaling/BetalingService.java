package service.betaling;

import domein.Betaling;

public interface BetalingService {
	public Betaling getBetalingOpId(int orderId);
	public void voegBetalingToe(Betaling betaling);
}
