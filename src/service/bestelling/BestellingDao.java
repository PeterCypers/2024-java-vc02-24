package service.bestelling;

import java.util.List;

import domein.Bestelling;
import domein.gebruiker.Gebruiker;
import jakarta.persistence.EntityNotFoundException;
import service.GenericDao;

public interface BestellingDao extends GenericDao<Bestelling>  {
    public List<Bestelling> vindPerLeverancier(Gebruiker leverancier) throws EntityNotFoundException; 
    public List<Bestelling> vindNietBetaaldeBestellingen();
}
