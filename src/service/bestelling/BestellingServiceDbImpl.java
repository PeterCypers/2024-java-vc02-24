package service.bestelling;

import java.util.List;

import domein.Bestelling;
import domein.Gebruiker;

public class BestellingServiceDbImpl implements BestellingService {
	
	private BestellingDaoJpa bestellingDao;
	
	public BestellingServiceDbImpl() {
		// bestellingDao = new BestellingDaoJpa();
		setBestellingDaoJpa(new BestellingDaoJpa());
	}
	
	public void setBestellingDaoJpa(BestellingDaoJpa bestellingDao) {
		this.bestellingDao = bestellingDao;
	}

	@Override
	public List<Bestelling> getBestellingen(Gebruiker leverancier) {
		return bestellingDao.vindPerLeverancier(leverancier);
	}
}