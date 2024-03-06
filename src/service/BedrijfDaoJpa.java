package service;

import java.util.List;

import domein.Bedrijf;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class BedrijfDaoJpa extends GenericDaoJpa<Bedrijf> implements BedrijfDao {

	public BedrijfDaoJpa() {
		super(Bedrijf.class);
	}

	@Override
	public List<Bedrijf> geefAlleBedrijven() throws EntityNotFoundException {
		try {
			return em.createNamedQuery("Bedrijf.geefBedrijven", Bedrijf.class)
					.getResultList();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}

}
