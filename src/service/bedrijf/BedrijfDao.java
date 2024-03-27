package service.bedrijf;

import java.util.List;

import domein.Bedrijf;
import jakarta.persistence.EntityNotFoundException;

public interface BedrijfDao {
	public List<Bedrijf> geefAlleBedrijven() throws EntityNotFoundException;
}