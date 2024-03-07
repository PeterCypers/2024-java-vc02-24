package domein;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

@Entity
@NamedQueries({
    @NamedQuery(name = "Bestelling.vindPerLeverancier",
                         query = "SELECT b FROM Bestelling b"
                         		+ " WHERE b.leverancier = :leverancier")
})      
public class Bestelling implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	private List<Product> producten;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Klant klant;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Gebruiker leverancier;
	
	private LocalDate datumGeplaatst;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	private BetalingsStatus betalingStatus;
	
	//Voor tabelView
	@Transient
	private final SimpleIntegerProperty orderID = new SimpleIntegerProperty();
	@Transient
	private final ObjectProperty<LocalDate> datum = new SimpleObjectProperty<>(this, "datum");
	@Transient
	private final SimpleObjectProperty<OrderStatus> orderstatus = new SimpleObjectProperty<OrderStatus>();
	@Transient
	private final SimpleObjectProperty<BetalingsStatus> betalingsstatus = new SimpleObjectProperty<BetalingsStatus>();
	
	public Bestelling() {}
	
	//constructor
	public Bestelling(int orderId, LocalDate datumGeplaats, OrderStatus orderStatus, BetalingsStatus betalingStatus, Klant klant, List<Product> producten) {
		setKlant(klant);
		setOrderId(orderId);
		setDatumGeplaats(datumGeplaats);
		setOrderStatus(orderStatus);
		setBetalingStatus(betalingStatus);
		setProducten(producten);
	}
	
	public double berekenTotalBedrag() {
		return producten.stream().mapToDouble(p -> p.getTotalePrijs()).sum();
	}

	public String getKlantName() {
		return klant.getName();
	}

	public LocalDate getDatumGeplaats() {
		return datumGeplaatst;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public BetalingsStatus getBetalingStatus() {
		return betalingStatus;
	}
	
	public Klant getKlant() {
		return klant;
	}

	//setters
	private void setKlant(Klant klant) {
		if (klant == null)
			throw new IllegalArgumentException("Er is geen klant meegegeven");
		this.klant = klant;
	}

	public void setProducten(List<Product> producten) {
		if (producten.isEmpty())
			throw new IllegalArgumentException("De bestelling bevat geen producten");
		this.producten = producten;
	}

	public List<Product> getProducten(){
		return this.producten;
	}
	
	private void setOrderId(int oId) {
		if (oId <= 0)
			throw new IllegalArgumentException("OrderId moet strikt positief zijn");
		orderId = oId;
	}
	
	public int getOrderId() {
		return this.orderId;
	}

	private void setDatumGeplaats(LocalDate date) {
		datumGeplaatst = date;
	}
	
	public LocalDate getDatumGeplaatst() {
		return this.datumGeplaatst;
	}

	private void setBetalingStatus(BetalingsStatus bStatus) {
		if (bStatus == null)
			throw new IllegalArgumentException("Betalingsstatus is niet meegegeven");
		betalingStatus = bStatus;
	}
	
	public BetalingsStatus getBetalingsStatus() {
		return this.betalingStatus;
	}

	private void setOrderStatus(OrderStatus oStatus) {
		if (oStatus == null)
			throw new IllegalArgumentException("Orderstatus is niet meegegeven");
		orderStatus = oStatus;
	}
	
	public Gebruiker getLeverancier() {
		return leverancier;
	}
	
	public void setLeverancier(Gebruiker leverancier) {
		this.leverancier = leverancier;
	}
	
	public IntegerProperty orderIdProperty() {
		orderID.set(orderId);
		return orderID;
	}
	
	public ObjectProperty<LocalDate> datumProperty() {
		datum.set(datumGeplaatst);
		return datum;
	}
	
	public StringProperty naamProperty() {
		return klant.getNaam();
	}
	
	public SimpleObjectProperty<OrderStatus> orderstatusProperty() {
		orderstatus.set(orderStatus);
		return orderstatus;
	}
	
	public SimpleObjectProperty<BetalingsStatus> betalingsstatusProperty() {
		betalingsstatus.set(betalingStatus);
		return betalingsstatus;
	}
	
	public String toString() {
		return String.format("Naam: %s%nContactgevevens: %s%n%nOrder ID: %d%nDatum geplaatst: %s%nLeveradres: %s%n%n" + 
								"Orderstatus: %s%nBetalingsstatus: %s%nBetalingsherinnering: %s%n%nTotale bedrag: € %.2f", 
								klant.getName(), klant.getContactgegevens(), orderId, datumGeplaatst, klant.getAdres(),
								orderStatus, betalingStatus, "todo",  berekenTotalBedrag());
	}
}
