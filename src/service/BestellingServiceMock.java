package service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import domein.Bestelling;
import domein.BetalingsStatus;
import domein.Gebruiker;
import domein.Klant;
import domein.OrderStatus;

public class BestellingServiceMock implements BestellingService {
	
	private List<Klant> klanten = Arrays.asList(
			new Klant("Klant1", "klant1@hotmail.com", "Belgie", "Gent", "9000", "Straat", "2"),
			new Klant("Klant2", "klant2@hotmail.com","Belgie", "Gent", "9000", "Regen", "5"),
			new Klant("Klant3", "klant3@hotmail.com","Belgie", "Gent", "9000", "Licht", "10")
			);
	
	private List<Bestelling> bestellingen = Arrays.asList(
			new Bestelling(49111, new Date(), OrderStatus.GEREGISTREERD, BetalingsStatus.NIET_BETAALD, klanten.get(0)),
			new Bestelling(49112, new Date(), OrderStatus.GEREGISTREERD, BetalingsStatus.BETAALD, klanten.get(1)),
			new Bestelling(49113, new Date(), OrderStatus.AAN_HET_VERWERKEN, BetalingsStatus.BETAALD, klanten.get(2)),
			new Bestelling(49114, new Date(), OrderStatus.ONDERWEG, BetalingsStatus.BETAALD, klanten.get(0)),
			new Bestelling(49115, new Date(), OrderStatus.GELEVERD, BetalingsStatus.BETAALD, klanten.get(2))
	);

	@Override
	public List<Bestelling> getBestellingen(Gebruiker leverancier) {
		return bestellingen;
	}
	
	
}
