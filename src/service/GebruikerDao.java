package service;
import domein.Gebruiker;
import jakarta.persistence.EntityNotFoundException;

public interface GebruikerDao extends GenericDao<Gebruiker>  {
        public Gebruiker meldAan(String emailadres, String wachtwoord) throws EntityNotFoundException;   
}