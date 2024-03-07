package service.klant;

import java.util.List;

import domein.Gebruiker;
import domein.Klant;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import service.GenericDaoJpa;

public class KlantDaoJpa extends GenericDaoJpa<Klant> implements KlantDao {
	public KlantDaoJpa() {
		super(Klant.class);
	}
	
	@Override 
	public List<Klant> vindPerLeverancier(Gebruiker leverancier) throws EntityNotFoundException {
		//TODO query is juist? + query toevoegen in Klantklasse? 
		//Leverancier ->* lijst v Bestelling ->1 Klant
		try {
			return em.createNamedQuery("Klant.vindPerLeverancier", Klant.class)
					.setParameter("leverancier", leverancier)
					.getResultList();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}
}
