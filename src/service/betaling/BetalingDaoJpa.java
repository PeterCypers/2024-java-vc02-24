package service.betaling;

import java.util.List;

import domein.Betaling;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import service.GenericDaoJpa;

public class BetalingDaoJpa extends GenericDaoJpa<Betaling> implements BetalingDao {

	public BetalingDaoJpa() {
		super(Betaling.class);
	}
	
	@Override
	public List<Betaling> findAll() {
		return super.findAll();
	}
	
	@Override
	public <U> Betaling get(U id) {
		return super.get(id);
	}

	@Override
	public List<Betaling> vindOnverwerkteBetaling() {
		try {
			return em.createNamedQuery("Betaling.vindOnverwerkteBetalingen", Betaling.class)
					.getResultList();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}

}
