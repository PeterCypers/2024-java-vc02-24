package domein;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.Arrays;
import java.util.Date;
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
		this.orderId = orderId;
		this.datumGeplaats = datumGeplaats;
		this.orderStatus = orderStatus;
		this.betalingStatus = betalingStatus;
		setKlant(klant);
		//Tableview
		setOrderId(orderId);
		setDatumGeplaats(datumGeplaats);
		setOrderStatus(orderStatus);
		setBetalingStatus(betalingStatus);
	}

	//getters
	public String getKlantName() {
		return klant.getName();
	}
	
	private void setKlant(Klant klant) {
		this.klant = klant;
	}

	public void setProducten(List<Product> producten) {
		this.producten = producten;
	}

	public List<Product> getProducten(){
		return this.producten;
	}
	
	//voor tabelView
	private void setOrderId(int oId) {
		orderID.set(oId);
	}

	private void setDatumGeplaats(Date date) {
		datum.set(date);
	}

	private void setBetalingStatus(BetalingsStatus bStatus) {
		betalingsstatus.set(bStatus);
	}

	private void setOrderStatus(OrderStatus oStatus) {
		orderstatus.set(oStatus);
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
