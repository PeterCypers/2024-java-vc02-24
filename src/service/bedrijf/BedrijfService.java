package service.bedrijf;

import java.util.List;
import domein.Bedrijf;

public interface BedrijfService {
	public List<Bedrijf> getBedrijven();
	public void voegBedrijfToe(Bedrijf bedrijf);
	public void updateBedrijf(Bedrijf bedrijf);
}
