package service;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import domein.Adres;
import domein.Gebruiker;
import domein.Rol;
// Gebruiker(int gebruikerId, Rol rol, String email, String wachtwoord, String naam, boolean isActief, Adres adres)
//Adres(String land, String stad, String postcode, String straat, String straatNr)
public class AanmeldServiceMock implements AanmeldService {
	
	//land, stad, postcode, straat, straatNr
	private Adres[] adressen = {
			new Adres("Belgium","Brussels","1000","Kerkstraat","1"),
			new Adres("America","New York","10001","Broadway","20"),
			new Adres("Netherlands","Rotterdam","3011","Wolfshoek","7")};
	
	//gebruikerId, rol, email, wachtwoord, naam, isActief, adres
	private List<Gebruiker> mockGebruikers = Arrays.asList(
			new Gebruiker(1, Rol.LEVERANCIER, "eerste@hotmail.com", "1234", "Mark", true, adressen[0]),
			new Gebruiker(2, Rol.LEVERANCIER, "tweede@hotmail.com", "password", "Mike", true, adressen[1]),
			new Gebruiker(3, Rol.ADMINISTRATOR, "derde@hotmail.com", "123abc", "Joris", true, adressen[2]),
			new Gebruiker(4, Rol.KLANT, "vierde@hotmail.com", "321", "Jeff", true, adressen[1]),
			new Gebruiker(5, Rol.LEVERANCIER, "vijfde@hotmail.com", "654", "Jos", false, adressen[2])
			);
	
	@Override
	public List<Gebruiker> getGebruikers(){
		return mockGebruikers;
	}

	@Override
	public Gebruiker meldGebruikerAan(String emailadres, String wachtwoord) throws NoSuchElementException {
		Gebruiker gebruiker = mockGebruikers
			.stream()
			.filter((Gebruiker g) -> g.getEmail().equals(emailadres) && g.getWachtwoord().equals(wachtwoord))
			.findFirst()
			.get();
		
		if (!gebruiker.getIsActief()) {
			throw new IllegalArgumentException("De gebruikersaccount is niet actief.");
		}
		
		if (gebruiker.getRol() == Rol.KLANT) {
			throw new IllegalArgumentException("De gebruiker mag geen klant zijn.");
		}
		
		return gebruiker;
	}
}
