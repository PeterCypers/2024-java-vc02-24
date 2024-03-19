package domein.gebruiker;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import domein.Adres;
import domein.Bedrijf;
import domein.BesteldProduct;
import domein.Bestelling;
import domein.BetalingsStatus;
import domein.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;


@Entity
@NamedQueries({
    @NamedQuery(name = "Klant.vindPerLeverancier",
                         query = "SELECT DISTINCT k FROM Klant k "
                        		+ "JOIN k.bestellingen b "
                         		+ "WHERE b.leverancier = :leverancier")
})
@DiscriminatorValue(value=Rol.Values.KLANT)
public class Klant extends Gebruiker {
	
	private static final long serialVersionUID = 1L;

	@Embedded
	protected Adres adres;
	
	private String telefoonnummer;
	
	@OneToOne(cascade=CascadeType.PERSIST)
	private Bedrijf bedrijf;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Bestelling> bestellingen;
	
	//voor tableView
	@Transient
	private final SimpleStringProperty naamKlant = new SimpleStringProperty();
	@Transient
	private final SimpleIntegerProperty openstaandeBestellingen = new SimpleIntegerProperty();
	
	public Klant() {}
	
	//constructor
	public Klant(Bedrijf bedrijf, String email, String wachtwoord, String naam, boolean isActief, Adres adres, String telefoonnummer) {
		super(email, wachtwoord, naam, isActief, Rol.KLANT);
		this.bedrijf = bedrijf;
		setAdres(adres);
		setTelefoonnummer(telefoonnummer);
	}
	
	public Adres getAdres() {
		return adres;
	}
	
	public void setAdres(Adres adres) {
		if (adres == null) {
			throw new IllegalArgumentException("Adres van de klant is ongeldig");
		}
		
		this.adres = adres;
	}

	public String getTelefoonnummer() {
		return telefoonnummer;
	}
	
	public void setTelefoonnummer(String telefoonnummer) {
		if(telefoonnummer == null || telefoonnummer.isBlank())
			throw new IllegalArgumentException("Telefoonnummer van de klant mag niet leeg zijn");
		
		String phoneRegex = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";
		if(telefoonnummer == null || !telefoonnummer.matches(phoneRegex))
			throw new IllegalArgumentException("Telefoonnummer van de klant is ongeldig");
		
		this.telefoonnummer = telefoonnummer;
	}
	
	public List<Bestelling> getBestellingen() {
		return bestellingen;
	}
	
	public void setBestellingen(List<Bestelling> bestellingen) {
		this.bestellingen = bestellingen;
	}
	
	public String getLogo() {
		return this.bedrijf.getLogo();
	}
	
	public IntegerProperty openstaandeBestellingenProperty(Gebruiker leverancier) {
		openstaandeBestellingen.set(getAantalOpenstaandeBestellingen(leverancier));
		return openstaandeBestellingen;
	}
	
	public List<Bestelling> getBestellingenPerLeverancier(Gebruiker leverancier) {
		return bestellingen.stream().filter(b -> b.getLeverancier().equals(leverancier)).collect(Collectors.toUnmodifiableList());
	}
	
	public List<Bestelling> getOpenstaandeBestellingen(Gebruiker leverancier) {
		List<Bestelling> openstaand = getBestellingenPerLeverancier(leverancier).stream().filter(b -> 
			!(b.getOrderStatus().equals(OrderStatus.GELEVERD))
		).collect(Collectors.toList());
		return openstaand;
	}
	
	public int getAantalOpenstaandeBestellingen(Gebruiker leverancier) {
		return getOpenstaandeBestellingen(leverancier).size();
	}
	/**
	 * @param leverancier = singleton aangemeldeGebruiker
	 * @return ObservableList<Bestelling> van bestellingen van deze leverancier
	 */
	public ObservableList<Bestelling> getObservableListBestellingen(Gebruiker leverancier) {
		ObservableList<Bestelling> bestellingsList = FXCollections.observableArrayList(
				getBestellingenPerLeverancier(leverancier));
		
		//Sortering van Bestellingen
		Comparator<Bestelling> bijDatum = (b1, b2)
				-> b2.getDatumGeplaats().toString().compareToIgnoreCase(b1.getDatumGeplaats().toString());
				
		Comparator<Bestelling> bijOrderId = (b1, b2)
				-> Integer.toString(b1.getOrderId()).compareToIgnoreCase(Integer.toString(b2.getOrderId()));
		
		Comparator<Bestelling> bijOrderbedrag = (b1, b2)
				-> Double.toString(b1.berekenTotalBedrag()).compareToIgnoreCase(Double.toString(b2.berekenTotalBedrag()));
				
		Comparator<Bestelling> bijOrderstatus = (b1, b2)
				-> b1.getOrderStatus().toString().compareToIgnoreCase(b2.getOrderStatus().toString());
				
		Comparator<Bestelling> bijBetalingsstatus = (b1, b2)
				-> b1.getBetalingsStatus().toString().compareToIgnoreCase(b2.getBetalingsStatus().toString());
				
		Comparator<Bestelling> bestellingSorted = bijDatum.thenComparing(bijOrderId).thenComparing(bijOrderbedrag)
				.thenComparing(bijOrderstatus).thenComparing(bijBetalingsstatus);
		
		FilteredList<Bestelling> filteredBestellingen = new FilteredList<>(bestellingsList, b -> true);     
		SortedList<Bestelling> sortedBestellingen = new SortedList<>(filteredBestellingen, bestellingSorted);
		return sortedBestellingen;
	}
	
	private FilteredList<Bestelling> getObservableListBestelling(Gebruiker leverancier){
		ObservableList<Bestelling> bestellingsList = FXCollections.observableArrayList(
				getBestellingenPerLeverancier(leverancier));
		FilteredList<Bestelling> filteredBestellingen = new FilteredList<>(bestellingsList, b -> true); 
		return filteredBestellingen;
	}
	

	public ObservableList<Bestelling> filter(LocalDate datum, OrderStatus orderstatus, BetalingsStatus betalingsstatus,
			String filterValue) {
		ObservableList<Bestelling> filteredData = FXCollections.observableArrayList();
		
		for (Bestelling bestelling : getObservableListBestellingen(GebruikerHolder.getInstance())) {
			if(datum == null && orderstatus == null && betalingsstatus == null){
				if(filterText(bestelling, filterValue))
					filteredData.add(bestelling);
			} 
			
			if(orderstatus == null && betalingsstatus == null) {
				if(filterDate(bestelling, datum, filterValue))
					filteredData.add(bestelling);
			}
			
			if(betalingsstatus == null) {
				if(filterOrderStatus(bestelling, orderstatus, datum, filterValue))
					filteredData.add(bestelling);
			}
			
			if(filterBetalingsStatus(bestelling, betalingsstatus, orderstatus, datum, filterValue))
				filteredData.add(bestelling);
		}
		return filteredData;
	}
	
	private boolean filterBetalingsStatus(Bestelling bestelling, BetalingsStatus filterBetalingsStatus,
			OrderStatus filterOrderStatus, LocalDate filterDate, String filterValue) {
		if((filterValue == null || filterValue.isEmpty()) && filterDate == null && filterOrderStatus == null) {
			return bestelling.getBetalingsStatus().equals(filterBetalingsStatus);
		}
		return (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterDate(bestelling, filterDate, filterValue)) //filter op betalingsstatus en Datum
				|| (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterOrderStatus(bestelling, filterOrderStatus, filterDate, filterValue)) //filter op Betalingsstatus en Orderstatus
				|| (bestelling.getBetalingsStatus().equals(filterBetalingsStatus) && filterText(bestelling, filterValue)); //filter op Betalingsstatus en text
	}

	private boolean filterOrderStatus(Bestelling bestelling, OrderStatus filterOrderStatus, LocalDate filterDate,
			String filterValue) {
		if((filterValue == null || filterValue.isEmpty()) && filterDate == null) {
			return bestelling.getOrderStatus().equals(filterOrderStatus);
		}
		return (bestelling.getOrderStatus().equals(filterOrderStatus) && filterDate(bestelling, filterDate, filterValue)) //filter op Orderstatus en datum
				|| (bestelling.getOrderStatus().equals(filterOrderStatus) && filterText(bestelling, filterValue));//filter op OrderStatus en text
	}

	private boolean filterDate(Bestelling bestelling, LocalDate filterDate, String filterValue) {
        if(filterValue == null || filterValue.isEmpty()) {
        	return bestelling.getDatumGeplaats().equals(filterDate);
        }
        
        return bestelling.getDatumGeplaats().equals(filterDate) && filterText(bestelling, filterValue); //filter op Datum en text
	}
	
	private boolean filterText(Bestelling bestelling, String filterValue) {
		return Integer.toString(bestelling.getOrderId()).equals(filterValue) //filter op OrderID
            		|| bestelling.getKlantName().toLowerCase().equals(filterValue); //filter op Klantnaam 
    }
	
	@Override
	public String toString() {
		//TODO: logo dit stuur ik door als herinnering, maar vermoed andere implementatie verwacht
		return String.format("Naam: %s - Contact: %s - Adres: %s - Logopath: %s - telNr: %s",
				naam, emailadres, adres, bedrijf.getLogo(), telefoonnummer);
	}

}
