package service.klant;

import java.util.List;

import domein.Gebruiker;
import domein.Klant;

public interface KlantService {
	public abstract List<Klant> getKlanten(Gebruiker leverancier);
}
