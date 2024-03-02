package service;

import java.util.List;

import domein.Bestelling;
import domein.Gebruiker;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;

public class BestellingDaoJpa extends GenericDaoJpa<Bestelling> implements BestellingDao {
    public BestellingDaoJpa() {
        super(Bestelling.class);
    }

	@Override
	public List<Bestelling> vindPerLeverancier(Gebruiker leverancier) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("Bestelling.vindPerLeverancier", Bestelling.class)
					.setParameter("leverancier", leverancier)
					.getResultList();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}
}