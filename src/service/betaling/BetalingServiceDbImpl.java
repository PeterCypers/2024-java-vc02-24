package service.betaling;

import java.util.List;
import java.util.stream.Collectors;

import domein.Bestelling;
import domein.Betaling;
import domein.BetalingsStatus;
import service.GenericDaoJpa;
import service.bestelling.BestellingDaoJpa;

public class BetalingServiceDbImpl implements BetalingService {
	
	private BetalingDaoJpa betalingDao;
	private BestellingDaoJpa bestellingDao;
	
	public BetalingServiceDbImpl() {
		this.betalingDao = new BetalingDaoJpa();
		this.bestellingDao = new BestellingDaoJpa();
	}
	
	@Override
	public List<Betaling> getBetalingen() {
		return betalingDao.findAll();
	}

	@Override
	public Betaling getBetalingOpId(int orderId) {
		return betalingDao.get(orderId);
	}

	@Override
	public void voegBetalingToe(Betaling betaling) {
		GenericDaoJpa.startTransaction();
		try {
			betalingDao.insert(betaling);
			GenericDaoJpa.commitTransaction();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}

	@Override
	public void verwerkBetalingen() {
		GenericDaoJpa.startTransaction();
		try {
			List<Betaling> onverwerkteBetalingen = betalingDao.vindOnverwerkteBetaling();
			onverwerkteBetalingen.forEach(betaling -> {
				betaling.setIsAfgehandeld(true);
				betalingDao.update(betaling);
			});
			
			List<Integer> orderIds = onverwerkteBetalingen
			.stream()
			.map(Betaling::getOrderId)
			.collect(Collectors.toList());
			
			List<Bestelling> nietBetaaldeBestellingen = bestellingDao.vindNietBetaaldeBestellingen();
			nietBetaaldeBestellingen.forEach(bestelling -> {
				if(orderIds.contains(bestelling.getOrderId())) {
					bestelling.setBetalingStatus(BetalingsStatus.BETAALD);
					bestellingDao.update(bestelling);
				}
			});
			GenericDaoJpa.commitTransaction();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}
}
