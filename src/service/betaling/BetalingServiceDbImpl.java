package service.betaling;

import domein.Betaling;
import service.GenericDaoJpa;

public class BetalingServiceDbImpl implements BetalingService {
	
	private BetalingDao betalingDao;
	
	public BetalingServiceDbImpl() {
		this.betalingDao = new BetalingDaoJpa();
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

}
