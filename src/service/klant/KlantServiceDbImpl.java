package service.klant;

import java.util.List;

import domein.Gebruiker;
import domein.Klant;

public class KlantServiceDbImpl implements KlantService {
	private KlantDaoJpa klantDao;
	
	public KlantServiceDbImpl() {
		klantDao = new KlantDaoJpa();
	}
	
	@Override
	public List<Klant> getKlanten(Gebruiker leverancier) {
		return klantDao.vindPerLeverancier(leverancier);
	}
}
 