package service;

import java.util.List;
import domein.Bedrijf;

public class BedrijfServiceDbImpl implements BedrijfService {
	
	private BedrijfDaoJpa bedrijfDao;
	
	public BedrijfServiceDbImpl() {
		this.bedrijfDao = new BedrijfDaoJpa();
	}

	@Override
	public List<Bedrijf> getBedrijven() {
		return this.bedrijfDao.geefAlleBedrijven();
	}

}
