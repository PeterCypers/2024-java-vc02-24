package domein;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Transient;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

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
	private Leverancier leverancier;
	
	private LocalDate datumGeplaatst;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	private BetalingsStatus betalingStatus;
	
	//Voor tabelView
	@Transient
	private final SimpleIntegerProperty orderID = new SimpleIntegerProperty();
	@Transient
	private final SimpleObjectProperty<LocalDate> datum = new SimpleObjectProperty<LocalDate>(); //(this, "datum");
	@Transient
	private final SimpleStringProperty klantnaam = new SimpleStringProperty();
	@Transient
	private final SimpleObjectProperty<OrderStatus> orderstatus = new SimpleObjectProperty<OrderStatus>();
	@Transient
	private final SimpleObjectProperty<BetalingsStatus> betalingsstatus = new SimpleObjectProperty<BetalingsStatus>();
	@Transient
	private final SimpleDoubleProperty orderbedrag = new SimpleDoubleProperty();
	
	public Bestelling() {}
	
	//constructor
	public Bestelling(int orderId, LocalDate datumGeplaatst, OrderStatus orderStatus, BetalingsStatus betalingStatus, Klant klant, Leverancier leverancier, List<Product> producten) {
		setOrderId(orderId);
		setDatumGeplaatst(datumGeplaatst);
		setOrderStatus(orderStatus);
		setBetalingStatus(betalingStatus);
		setKlant(klant);
		setLeverancier(leverancier);
		setProducten(producten);
	}
	
	public double berekenTotalBedrag() {
		return producten.stream().mapToDouble(p -> p.getTotalePrijs()).sum();
	}

	public String getKlantName() {
		return klant.getNaam();
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

	private void setDatumGeplaatst(LocalDate date) {
		datumGeplaatst = date;
	}
	
	public LocalDate getDatumGeplaatst() {
		return this.datumGeplaatst;
	}

	public void setBetalingStatus(BetalingsStatus bStatus) {
		if (bStatus == null)
			throw new IllegalArgumentException("Betalingsstatus is niet meegegeven");
		betalingStatus = bStatus;
	}
	
	public BetalingsStatus getBetalingsStatus() {
		return this.betalingStatus;
	}

	public void setOrderStatus(OrderStatus oStatus) {
		if (oStatus == null)
			throw new IllegalArgumentException("Orderstatus is niet meegegeven");
		orderStatus = oStatus;
	}
	
	public Leverancier getLeverancier() {
		return leverancier;
	}
	
	public void setLeverancier(Leverancier leverancier) {
		this.leverancier = leverancier;
	}
	
	//tableView
	public IntegerProperty orderIdProperty() {
		orderID.set(orderId);
		return orderID;
	}
	
	public ObjectProperty<LocalDate> datumProperty() {
		datum.set(datumGeplaatst);
		return datum;
	}
	
	public StringProperty klantNaamProperty() {
		klantnaam.set(klant.getNaam());
		return klantnaam;
	}
	
	public ObjectProperty<OrderStatus> orderstatusProperty() {
		orderstatus.set(orderStatus);
		return orderstatus;
	}
	
	public ObjectProperty<BetalingsStatus> betalingsstatusProperty() {
		betalingsstatus.set(betalingStatus);
		return betalingsstatus;
	}
	
	public DoubleProperty orderbedragProperty() {
		orderbedrag.set(berekenTotalBedrag());
		return orderbedrag;
	}
	
	public ObservableList<Product> getObservableListProducten(){
		ObservableList<Product> productenList = FXCollections.observableArrayList(producten);
		FilteredList<Product> filteredProducten = new FilteredList<>(productenList, p -> true);
		SortedList<Product> sortedProducten = new SortedList<>(filteredProducten);
		return sortedProducten;
	}
}
