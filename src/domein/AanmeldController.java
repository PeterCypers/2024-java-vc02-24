package domein;

import java.util.List;
import java.util.NoSuchElementException;

import service.AanmeldService;
import service.AanmeldServiceMock;

public class AanmeldController {
	
	private final AanmeldService ams;
	
	public AanmeldController() {
		ams = new AanmeldServiceMock();
	}
	
	//implementatie correct? gebruiker class hashcode+equals om op equality te controlleren -> wacht op vervolg
	public Gebruiker meldGebruikerAan(String emailadres, String wachtwoord) throws NoSuchElementException {
		/*
		List<Gebruiker> geldigeGebruikers = ams.getGebruikers();
		Gebruiker gevondenGebruiker = null;
		for (Gebruiker g : geldigeGebruikers) {
			if(g.getEmail().equals(emailadres) && g.getWachtwoord().equals(wachtwoord)) gevondenGebruiker = g;
		}
		return gevondenGebruiker;
		*/
		
		return ams.meldGebruikerAan(emailadres, wachtwoord);
	}
}
