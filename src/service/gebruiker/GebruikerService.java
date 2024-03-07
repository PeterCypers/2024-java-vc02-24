package service.gebruiker;

import java.util.List;
import java.util.NoSuchElementException;

import domein.Gebruiker;
import jakarta.persistence.EntityNotFoundException;

public interface GebruikerService {
	public abstract List<Gebruiker> getGebruikers();
	public abstract Gebruiker meldGebruikerAan(String emailadres, String wachtwoord) throws EntityNotFoundException, IllegalArgumentException;
	public abstract void voegGebruikerToe(Gebruiker gebruiker);
}
