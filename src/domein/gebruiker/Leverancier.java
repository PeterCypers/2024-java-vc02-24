package domein.gebruiker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import domein.Bedrijf;
import domein.BedrijfController;
import domein.Bestelling;
import domein.Betaalmethode;
import domein.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@DiscriminatorValue(value=Rol.Values.LEVERANCIER)
public class Leverancier extends Gebruiker {
	
	private static final long serialVersionUID = 1L;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Bedrijf bedrijf;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	private List<Product> producten;
	
	public Leverancier() {}
	
	public Leverancier(Bedrijf bedrijf, String email, String wachtwoord, 
			String naam, boolean isActief) {
		super(email, wachtwoord, naam, isActief, Rol.LEVERANCIER);
		this.bedrijf = bedrijf; 
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen.stream().collect(Collectors.toUnmodifiableList());
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public List<Product> getProducten() {
		return producten.stream().collect(Collectors.toUnmodifiableList());
	}
	
	public void setProducten(List<Product> producten) {
		this.producten = producten;
	}

	public Bedrijf getBedrijf() {
		return bedrijf;
	}
}
