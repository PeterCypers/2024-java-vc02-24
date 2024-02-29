package domein;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;

public class Bestelling {
	
	//attributen
	private int orderId;
	private Date datumGeplaats;
	private OrderStatus orderStatus;
	private BetalingsStatus betalingStatus;
	private Klant klant;
	private List<Product> producten = Arrays.asList(new Product("Inkt", 2, true, 2.30, 4.60),
				new Product("Bekers", 10, false, 3.00, 30),
				new Product("Cola", 20, true, 1.45, 29)
			);
	
	//Voor tabelView
	private final SimpleIntegerProperty orderID = new SimpleIntegerProperty();
	private final ObjectProperty<Date> datum = new SimpleObjectProperty<>(this, "datum");
	private final SimpleObjectProperty<OrderStatus> orderstatus = new SimpleObjectProperty<OrderStatus>();
	private final SimpleObjectProperty<BetalingsStatus> betalingsstatus = new SimpleObjectProperty<BetalingsStatus>();
	
	//constructor
	public Bestelling(int orderId, Date datumGeplaats, OrderStatus orderStatus, BetalingsStatus betalingStatus, Klant klant) {
		setKlant(klant);
		setOrderId(orderId);
		setDatumGeplaats(datumGeplaats);
		setOrderStatus(orderStatus);
		setBetalingStatus(betalingStatus);
	}

	public String getKlantName() {
		return klant.getName();
	}
	
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
		orderID.set(oId);
		orderId = oId;
	}
	
	public int getOrderId() {
		return this.orderId;
	}

	private void setDatumGeplaats(Date date) {
		datum.set(date);
		datumGeplaats = date;
	}
	
	public Date getDatumGeplaatst() {
		return this.datumGeplaats;
	}

	private void setBetalingStatus(BetalingsStatus bStatus) {
		if (bStatus == null)
			throw new IllegalArgumentException("Betalingsstatus is niet meegegeven");
		betalingsstatus.set(bStatus);
		betalingStatus = bStatus;
	}
	
	public BetalingsStatus getBetalingsStatus() {
		return this.betalingStatus;
	}

	private void setOrderStatus(OrderStatus oStatus) {
		if (oStatus == null)
			throw new IllegalArgumentException("Orderstatus is niet meegegeven");
		orderstatus.set(oStatus);
		orderStatus = oStatus;
	}
	
	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}
	
	public IntegerProperty orderIdProperty() {
		return orderID;
	}
	
	public ObjectProperty<Date> datumProperty() {
		return datum;
	}
	
	public StringProperty naamProperty() {
		return klant.getNaam();
	}
	
	public SimpleObjectProperty<OrderStatus> orderstatusProperty() {
		return orderstatus;
	}
	
	public SimpleObjectProperty<BetalingsStatus> betalingsstatusProperty() {
		return betalingsstatus;
	}
	
	public String toString() {
		Float bedrag = 100.35f;
		return String.format("Naam: %s%nContactgevevens: %s%n%nOrder ID: %d%nDatum geplaatst: %s%nLeveradres: %s%n%n" + 
								"Orderstatus: %s%nBetalingsstatus: %s%nBetalingsherinnering: %s%n%nTotale bedrag: € %.2f", 
								klant.getName(), klant.getContactgegevens(), orderId, datumGeplaats, klant.getAdres(),
								orderStatus, betalingStatus, "todo", bedrag);
	}
	
	/*
	 * Naam klant + contactgevevens 

Order ID 

Datum geplaatst 

Leveradres 

Orderstatus 

Betalingsstatus + datum laatste betalingsherinnering 

Overzicht van producten (naam, aantal, in stock, eenheidsprijs, totale prijs product) 

Totale orderbedrag 
	 */
}
