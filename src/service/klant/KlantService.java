package service.klant;

import java.util.List;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;

public interface KlantService {
	public abstract List<Klant> getKlanten(Gebruiker leverancier);
}