package service.bedrijf;

import java.util.List;

import domein.Bedrijf;
import service.GenericDaoJpa;

public class BedrijfServiceDbImpl implements BedrijfService {
	
	private BedrijfDaoJpa bedrijfDao;
	
	public BedrijfServiceDbImpl() {
		this.bedrijfDao = new BedrijfDaoJpa();
	}

	@Override
	public List<Bedrijf> getBedrijven() {
		return this.bedrijfDao.geefAlleBedrijven();
	}

	@Override
	public void voegBedrijfToe(Bedrijf bedrijf) {
		GenericDaoJpa.startTransaction();
		try {
			bedrijfDao.insert(bedrijf);
			GenericDaoJpa.commitTransaction();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}

	@Override
	public void updateBedrijf(Bedrijf bedrijf) {
		GenericDaoJpa.startTransaction();
		try {
			bedrijfDao.update(bedrijf);
			GenericDaoJpa.commitTransaction();
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			GenericDaoJpa.rollbackTransaction();
		}
	}
}