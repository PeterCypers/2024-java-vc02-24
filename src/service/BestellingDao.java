package service;

import java.util.List;

import domein.Bestelling;
import domein.Gebruiker;
import jakarta.persistence.EntityNotFoundException;

public interface BestellingDao extends GenericDao<Bestelling>  {
    public List<Bestelling> vindPerLeverancier(Gebruiker leverancier) throws EntityNotFoundException;   
}
