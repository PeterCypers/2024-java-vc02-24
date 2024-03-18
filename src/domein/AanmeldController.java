package domein;

import java.util.NoSuchElementException;

import domein.gebruiker.GebruikerHolder;
import service.gebruiker.GebruikerService;
import service.gebruiker.GebruikerServiceDbImpl;

public class AanmeldController {
	
	private final GebruikerService gs;
	
	public AanmeldController() {
		gs = new GebruikerServiceDbImpl();
	}
	
	//implementatie correct? gebruiker class hashcode+equals om op equality te controlleren -> wacht op vervolg
	public /*Gebruiker*/void meldGebruikerAan(String emailadres, String wachtwoord) throws NoSuchElementException {
		/*
		List<Gebruiker> geldigeGebruikers = ams.getGebruikers();
		Gebruiker gevondenGebruiker = null;
		for (Gebruiker g : geldigeGebruikers) {
			if(g.getEmail().equals(emailadres) && g.getWachtwoord().equals(wachtwoord)) gevondenGebruiker = g;
		}
		return gevondenGebruiker;
		*/
		
		GebruikerHolder.setInstance(gs.meldGebruikerAan(emailadres, wachtwoord));
		//return gs.meldGebruikerAan(emailadres, wachtwoord);
		//return GebruikerHolder.getInstance();
	}
}
