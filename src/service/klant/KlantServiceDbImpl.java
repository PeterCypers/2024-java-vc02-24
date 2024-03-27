package service.klant;

import java.util.List;

import domein.gebruiker.Gebruiker;
import domein.gebruiker.Klant;

public class KlantServiceDbImpl implements KlantService {
	
	private KlantDaoJpa klantDao;
	
	public KlantServiceDbImpl() {
		setKlantDaoJpa(new KlantDaoJpa());
	}
	
	public void setKlantDaoJpa(KlantDaoJpa klantDao) {
		this.klantDao = klantDao;
	}
	
	@Override
	public List<Klant> getKlanten(Gebruiker leverancier) {
		return klantDao.vindPerLeverancier(leverancier);
	}
}