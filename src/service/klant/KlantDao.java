package service.klant;

import java.util.List;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;
import jakarta.persistence.EntityNotFoundException;
import service.GenericDao;

public interface KlantDao extends GenericDao<Klant> {
	public List<Klant> vindPerLeverancier(Gebruiker leverancier) throws EntityNotFoundException; 
}
