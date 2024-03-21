package service.betaling;

import domein.Betaling;
import service.GenericDaoJpa;

public class BetalingDaoJpa extends GenericDaoJpa<Betaling> implements BetalingDao {

	public BetalingDaoJpa() {
		super(Betaling.class);
	}
	
	@Override
	public <U> Betaling get(U id) {
		return super.get(id);
	}

}
