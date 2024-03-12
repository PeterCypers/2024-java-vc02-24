package service;

import java.util.List;

import domein.Bestelling;
import domein.Gebruiker;

public interface BestellingService {
	public abstract List<Bestelling> getBestellingen(Gebruiker leverancier);
	void updateBestelling(Bestelling bestelling); 
}
