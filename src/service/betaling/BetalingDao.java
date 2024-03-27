package service.betaling;

import java.util.List;

import domein.Betaling;
import service.GenericDao;

public interface BetalingDao extends GenericDao<Betaling> {
	public List<Betaling> vindOnverwerkteBetaling();
}