package service;

import java.util.List;

import domein.Bestelling;
import domein.Gebruiker;

public interface BestellingService {
	public List<Bestelling> getBestellingen(Gebruiker leverancier);
}
