package domein;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

/**
 * Represents an Order.
 * <p>This class contains information about an Order.</p>
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Bestelling.vindPerLeverancier",
                         query = "SELECT b FROM Bestelling b"
                         		+ " WHERE b.leverancier = :leverancier"),
    @NamedQuery(name = "Bestelling.vindNietBetaaldeBestellingen",
    				query = "SELECT b FROM Bestelling b WHERE b.betalingStatus = domein.BetalingsStatus.NIET_BETAALD")
})      
public class Bestelling implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private int orderId;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	private List<BesteldProduct> producten;
	
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
	private final SimpleObjectProperty<LocalDate> datum = new SimpleObjectProperty<LocalDate>();
	@Transient
	private final SimpleStringProperty bedrijfsnaam = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty orderstatus = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty betalingsstatus = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty orderbedrag = new SimpleStringProperty();
	
    private LocalDate betalingsDatum;
    private LocalDate herinneringsDatum;
	
	
	/** <code>entity class</code> JPA-required default constructor */
	public Bestelling() {}
	
	/**
	 * Constructs a new <strong>Bestelling</strong> with the specified details.
	 * 
	 * @param orderId the id belonging to this order
	 * @param datumGeplaats the <code>LocalDate</code> when this order was placed
	 * @param orderStatus <code>enum</code> {@link domein.OrdersStatus} OrderStatus of this order
	 * @param betalingStatus <code>enum</code> {@link domein.BetalingsStatus} BetalingsStatus of this order
	 * @param leverancier the supplier of this order
	 * @param klant customer to which this order belongs
	 * @param producten a <code>list</code> of products in this order
	 */
	public Bestelling(int orderId, LocalDate datumGeplaatst, OrderStatus orderStatus, BetalingsStatus betalingStatus, Klant klant, Leverancier leverancier, List<BesteldProduct> producten, LocalDate betalingsDatum) {
		setOrderId(orderId);
		setDatumGeplaatst(datumGeplaatst);
		setOrderStatus(orderStatus);
		setBetalingStatus(betalingStatus);
		setKlant(klant);
		setLeverancier(leverancier);
		setProducten(producten);
		setBetalingsDatum(betalingsDatum);
		this.herinneringsDatum = betalingsDatum.minusDays(3);
	}
	
	/**
	 * This calculates the sum total cost of products for this Order
	 * 
	 * @return sum total cost of products
	 */
	public double berekenTotalBedrag() {
		return producten.stream().mapToDouble(p -> p.getTotalePrijs()).sum();
	}
	public LocalDate getBetalingsDatum() {
        return betalingsDatum;
    }
	public LocalDate getHerinneringsDatum() {
        return herinneringsDatum;
    }
	public void setBetalingsDatum(LocalDate betalingsDatum) {
	    this.betalingsDatum = betalingsDatum;
	}
	public void setHerinneringsDatum(LocalDate herinneringsDatum) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = betalingsDatum.format(formatter);
	    if (herinneringsDatum.isAfter(this.betalingsDatum)) {
	    	String error = String.format("Herinneringsdatum moet voor de betalingsdatum zijn (%s)", formattedDate);
	        throw new IllegalArgumentException(error);
	    }else if(herinneringsDatum.isBefore(LocalDate.now()))
	    	throw new IllegalArgumentException("Herinneringsdatum kan niet voor vandaag zijn");
	    
	    this.herinneringsDatum = herinneringsDatum;
	}
	

	public String getBedrijfsnaam() {
		return klant.getBedrijfsnaam();
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

	public void setProducten(List<BesteldProduct> producten) {
		if (producten.isEmpty())
			throw new IllegalArgumentException("De bestelling bevat geen producten");
		this.producten = producten;
	}

	public List<BesteldProduct> getProducten(){
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

	private void setOrderStatus(OrderStatus nieuweOrderStatus) {
		if (nieuweOrderStatus == null)
			throw new IllegalArgumentException("Orderstatus is niet meegegeven");
		
		orderStatus = nieuweOrderStatus;
	}
	
	public Leverancier getLeverancier() {
		return leverancier;
	}
	
	public void setLeverancier(Leverancier leverancier) {
		this.leverancier = leverancier;
	}
	
	//tableView
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleIntegerProperty} orderID -> the orderID of this order
	 */
	public IntegerProperty orderIdProperty() {
		orderID.set(orderId);
		return orderID;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleObjectProperty} datum -> a simple object of the date <br>
	 * this order was placed
	 */
	public ObjectProperty<LocalDate> datumProperty() {
		datum.set(datumGeplaatst);
		return datum;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleStringProperty} klantnaam -> the customer name
	 * to whom this order belongs
	 */
	public StringProperty bedrijfsnaamProperty() {
		bedrijfsnaam.set(getBedrijfsnaam());
		return bedrijfsnaam;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleObjectProperty} orderstatus -> the status(enum) of this order<br>
	 * 
	 * options:
	 * <ul>
	 * <li>OrderStatus.<strong>GELEVERD</strong>
	 * <li>OrderStatus.<strong>ONDERWEG</strong>
	 * <li>OrderStatus.<strong>AAN_HET_VERWERKEN</strong>
	 * <li>OrderStatus.<strong>GEREGISTREERD</strong>
	 * </ul>
	 */
	public StringProperty orderstatusProperty() {
		orderstatus.set(orderStatus.toString());
		return orderstatus;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleObjectProperty} betalingsstatus -> payment status(enum)<br>
	 * of this order<br>
	 * options:<br>
	 * <ul>
	 * <li>BetalingsStatus.<strong>NIET_BETAALD</strong>
	 * <li>BetalingsStatus.<strong>BETAALD</strong>
	 * </ul>
	 */
	public StringProperty betalingsstatusProperty() {
		betalingsstatus.set(betalingStatus.toString());
		return betalingsstatus;
	}
	
	/**
	 * JavaFX property implementation
	 * 
	 * @return {@link SimpleDoubleProperty} orderbedrag -> the sum total cost 
	 * of the products in this order
	 */
	public StringProperty orderbedragProperty() {
		DecimalFormat df = new DecimalFormat("\u20AC0.00");
		orderbedrag.set(df.format(berekenTotalBedrag()));
		return orderbedrag;
	}
	
	/**
	 * JavaFX Collections implementation
	 * 
	 * @return {@link ObservableList} of Product objects belonging to this order<br>
	 *
	 * used in gui.BestellingScherm
	 */
	public ObservableList<BesteldProduct> getObservableListProducten(){
		ObservableList<BesteldProduct> productenList = FXCollections.observableArrayList(producten);
		FilteredList<BesteldProduct> filteredProducten = new FilteredList<>(productenList, p -> true);
		SortedList<BesteldProduct> sortedProducten = new SortedList<>(filteredProducten);
		return sortedProducten;
	}
	
	public ObservableList<BesteldProduct> filter(String filterValue, String filterValue2){
		ObservableList<BesteldProduct> filteredData = FXCollections.observableArrayList();
		String lowerCaseValue1 = filterValue.toLowerCase();
		String lowerCaseValue2 = filterValue2.toLowerCase();

	    for (BesteldProduct product : getObservableListProducten()) {

	    	if((filterValue.isBlank() || product.toSearchString().contains(lowerCaseValue1)) && 
	    			(filterValue2.isBlank() || product.toSearchString().contains(lowerCaseValue2)))
	    		filteredData.add(product);
	    }
	    return filteredData;
	}
	
	public void veranderOrderStatus(OrderStatus nieuweOrderStatus) {
		// verwerk stock van producten als de producten het bedrijf verlaten, of als ze teruggebracht worden
		if ((orderStatus == OrderStatus.GEREGISTREERD || orderStatus == OrderStatus.AAN_HET_VERWERKEN)
				&& (nieuweOrderStatus == OrderStatus.ONDERWEG || nieuweOrderStatus == OrderStatus.GELEVERD)) {
			veranderProductenStock(true);
		} else if ((orderStatus == OrderStatus.ONDERWEG || orderStatus == OrderStatus.GELEVERD)
				&& (nieuweOrderStatus == OrderStatus.GEREGISTREERD || nieuweOrderStatus == OrderStatus.AAN_HET_VERWERKEN)) {
			veranderProductenStock(false);
		}
		
		setOrderStatus(nieuweOrderStatus);
	}
	
	private void veranderProductenStock(boolean verlagen) {
		producten.forEach(p -> {
			int aantal = p.getAantal();
			
			if (verlagen)
				aantal = -aantal;
			
			p.getProduct().bewerkStock(aantal);
		});
	}
	
	public String toString() {
		return String.format("%d %s %.2f %s %s", orderId, datumGeplaatst.toString(), berekenTotalBedrag(), orderStatus, betalingStatus);
	}
	
	public String toSearchString() {
		return String.format("%s %s %s %s %s %s", orderId, datumGeplaatst.toString(), klant.getNaam(), orderStatus, betalingStatus, orderbedragProperty().getValue()).toLowerCase();
	}
}
