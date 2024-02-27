package service;

import java.util.List;
import java.util.NoSuchElementException;

import domein.Gebruiker;

public interface AanmeldService {
	public abstract List<Gebruiker> getGebruikers();
	public abstract Gebruiker meldGebruikerAan(String emailadres, String wachtwoord) throws NoSuchElementException;
}
