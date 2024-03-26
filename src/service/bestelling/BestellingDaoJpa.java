package service.bestelling;

import java.util.List;

import domein.Bestelling;
import domein.gebruiker.Gebruiker;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import service.GenericDaoJpa;

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
	public void updateBestelling(Bestelling bestelling) {
	    GenericDaoJpa.startTransaction();
	    try {
	        update(bestelling); 
	        GenericDaoJpa.commitTransaction();
	    } catch(Exception ex) {
	        System.out.println(ex.getMessage());
	        GenericDaoJpa.rollbackTransaction();
	    }
	}

	@Override
	public List<Bestelling> vindNietBetaaldeBestellingen() {
		try {
			return em.createNamedQuery("Bestelling.vindNietBetaaldeBestellingen", Bestelling.class)
					.getResultList();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}
}