package service.bestelling;

import java.util.List;
import java.util.stream.Collectors;

import domein.Bestelling;
import domein.Gebruiker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BestellingServiceDbImpl implements BestellingService {
	
	private BestellingDaoJpa bestellingDao;
	
	public BestellingServiceDbImpl() {
		bestellingDao = new BestellingDaoJpa();
	}

	@Override
	public List<Bestelling> getBestellingen(Gebruiker leverancier) {
		return bestellingDao.vindPerLeverancier(leverancier);
	}
	@Override
    public void updateBestelling(Bestelling bestelling) {
        
        GenericDaoJpa.startTransaction();
        bestellingDao.update(bestelling); 
        GenericDaoJpa.commitTransaction();
	}
}