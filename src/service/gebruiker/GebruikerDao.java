package service.gebruiker;
import domein.gebruiker.Gebruiker;
import jakarta.persistence.EntityNotFoundException;
import service.GenericDao;

public interface GebruikerDao extends GenericDao<Gebruiker> {
	public Gebruiker meldAan(String emailadres, String wachtwoord) throws EntityNotFoundException;   
}