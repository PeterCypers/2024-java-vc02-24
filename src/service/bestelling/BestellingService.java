package service.bestelling;

import java.util.List;

import domein.Bestelling;
import domein.gebruiker.Gebruiker;

public interface BestellingService {
	public abstract List<Bestelling> getBestellingen(Gebruiker leverancier);
	public void updateBestelling(Bestelling bestelling); 
}