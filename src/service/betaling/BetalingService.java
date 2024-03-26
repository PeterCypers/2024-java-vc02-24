package service.betaling;

import java.util.List;

import domein.Betaling;

public interface BetalingService {
	public List<Betaling> getBetalingen();
	public Betaling getBetalingOpId(int orderId);
	public void voegBetalingToe(Betaling betaling);
	public void verwerkBetalingen();
}
