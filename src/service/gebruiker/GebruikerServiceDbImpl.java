package service.gebruiker;

import java.util.List;
import java.util.NoSuchElementException;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Rol;
import jakarta.persistence.EntityNotFoundException;
import service.GenericDaoJpa;

public class GebruikerServiceDbImpl implements GebruikerService {
	
	private GebruikerDaoJpa gebruikerDao;
	
	public GebruikerServiceDbImpl() {
		//gebruikerDao = new GebruikerDaoJpa();
		setGebruikerDaoJpa(new GebruikerDaoJpa());
	}
	
	public void setGebruikerDaoJpa(GebruikerDaoJpa gebruikerDaoJpa) {
		this.gebruikerDao = gebruikerDaoJpa;
	}

	@Override
	public List<Gebruiker> getGebruikers() {
		return gebruikerDao.findAll();
	}

	@Override
	public Gebruiker meldGebruikerAan(String emailadres, String wachtwoord) throws EntityNotFoundException, IllegalArgumentException {
		Gebruiker gebruiker = gebruikerDao.meldAan(emailadres, wachtwoord);
		
		if (!gebruiker.getIsActief()) {
			throw new IllegalArgumentException("Deze gebruiker werd gedeactiveerd");
		}
		if (gebruiker.getRol() == Rol.KLANT)
			throw new IllegalArgumentException("Klant mag hier niet inloggen");
		
		return gebruiker;
	}

	@Override
	public void voegGebruikerToe(Gebruiker gebruiker) {
		GenericDaoJpa.startTransaction();
		try {
			gebruikerDao.insert(gebruiker);
			GenericDaoJpa.commitTransaction();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}

}
