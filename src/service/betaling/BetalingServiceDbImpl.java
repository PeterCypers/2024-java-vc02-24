package service.betaling;

import java.util.List;

import domein.Betaling;
import service.GenericDaoJpa;

public class BetalingServiceDbImpl implements BetalingService {
	
	private BetalingDaoJpa betalingDao;
	
	public BetalingServiceDbImpl() {
		this.betalingDao = new BetalingDaoJpa();
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
			onverwerkteBetalingen.forEach(betaling -> betalingDao.update(betaling));
			GenericDaoJpa.commitTransaction();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}
}
