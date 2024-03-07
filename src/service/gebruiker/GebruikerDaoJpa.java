package service.gebruiker;

import domein.Gebruiker;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import service.GenericDaoJpa;

public class GebruikerDaoJpa extends GenericDaoJpa<Gebruiker> implements GebruikerDao  {
    public GebruikerDaoJpa() {
        super(Gebruiker.class);
    }
    
	@Override
	public Gebruiker meldAan(String emailadres, String wachtwoord) throws EntityNotFoundException {
		try {
			return em.createNamedQuery("Gebruiker.meldAan", Gebruiker.class)
					.setParameter("emailadres", emailadres)
					.setParameter("wachtwoord", wachtwoord)
					.getSingleResult();
		} catch (NoResultException nre) {
			throw new EntityNotFoundException();
		}
	}
}
