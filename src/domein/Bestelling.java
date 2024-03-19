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
	private final SimpleStringProperty klantnaam = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty orderstatus = new SimpleStringProperty();
	@Transient
	private final SimpleStringProperty betalingsstatus = new SimpleStringProperty();
	@Transient
	private final SimpleDoubleProperty orderbedrag = new SimpleDoubleProperty();
	
	public Bestelling() {}
	
	//constructor
	public Bestelling(int orderId, LocalDate datumGeplaatst, OrderStatus orderStatus, BetalingsStatus betalingStatus, Klant klant, Leverancier leverancier, List<BesteldProduct> producten) {
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
	
	public StringProperty orderstatusProperty() {
		orderstatus.set(orderStatus.toString());
		return orderstatus;
	}
	
	public StringProperty betalingsstatusProperty() {
		betalingsstatus.set(betalingStatus.toString());
		return betalingsstatus;
	}
	
	public DoubleProperty orderbedragProperty() {
		orderbedrag.set(berekenTotalBedrag());
		return orderbedrag;
	}
	
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
	    	//Eén veld is ingevuld
	    	if(lowerCaseValue2.isEmpty() || lowerCaseValue2 == null) {
	    		 if(product.getProduct().getNaam().toLowerCase().contains(lowerCaseValue1)	//filter op naam van het product
   	        		 || Integer.toString(product.getAantal()).equals(lowerCaseValue1)	//filter op aantal
   	        		 || product.getProduct().isInStock().toString().toLowerCase().equals(lowerCaseValue1)	//TODO: filter op in stock
   	        		 || Double.toString(product.getProduct().getEenheidsprijs()).contains(lowerCaseValue1)	//filter op eenheidsprijs
   	        		 || Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))	//filter op totale prijs
   	             filteredData.add(product);
	    	} else {
	    		//De twee velden zijn ingevuld -> logica twee keer omdat je ook in het tweede veld naam zet en adres in eerst veld;
	    		if(filterNaamAantal(product, lowerCaseValue1, lowerCaseValue2) 
	    				 || filterNaamStock(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterNaamEenheidsprijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterNaamPrijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterAantalStock(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterAantalEenheidsprijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterAantalPrijst(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterStockEenheidsprijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterStockPrijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterEenheidsprijsPrijs(product, lowerCaseValue1, lowerCaseValue2)
	    				 || filterNaamAantal(product, lowerCaseValue2, lowerCaseValue1) 
	    				 || filterNaamStock(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterNaamEenheidsprijs(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterNaamPrijs(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterAantalStock(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterAantalEenheidsprijs(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterAantalPrijst(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterStockEenheidsprijs(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterStockPrijs(product, lowerCaseValue2, lowerCaseValue1)
	    				 || filterEenheidsprijsPrijs(product, lowerCaseValue2, lowerCaseValue1))
	    			 filteredData.add(product);
	    	}
	    }
	    return filteredData;
	}
	
	private boolean filterNaamAantal(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().getNaam().toLowerCase().contains(filterValue) && Integer.toString(product.getAantal()).equals(filterValue2);
	}
	
	private boolean filterNaamStock(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().getNaam().toLowerCase().contains(filterValue) && product.getProduct().isInStock().toString().toLowerCase().equals(filterValue2);
	}
	
	private boolean filterNaamEenheidsprijs(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().getNaam().toLowerCase().contains(filterValue) && Double.toString(product.getProduct().getEenheidsprijs()).contains(filterValue2);
	}
	
	private boolean filterNaamPrijs(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().getNaam().toLowerCase().contains(filterValue) && Double.toString(product.getTotalePrijs()).contains(filterValue2);
	}
	
	private boolean filterAantalStock(BesteldProduct product, String filterValue, String filterValue2) {
		return Integer.toString(product.getAantal()).equals(filterValue) && product.getProduct().isInStock().toString().toLowerCase().equals(filterValue2);
	}
	
	private boolean filterAantalEenheidsprijs(BesteldProduct product, String filterValue, String filterValue2) {
		return Integer.toString(product.getAantal()).equals(filterValue) && Double.toString(product.getProduct().getEenheidsprijs()).contains(filterValue2);
	}
	
	private boolean filterAantalPrijst(BesteldProduct product, String filterValue, String filterValue2) {
		return Integer.toString(product.getAantal()).equals(filterValue) && Double.toString(product.getTotalePrijs()).contains(filterValue2);
	}
	
	private boolean filterStockEenheidsprijs(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().isInStock().toString().toLowerCase().equals(filterValue) && Double.toString(product.getProduct().getEenheidsprijs()).contains(filterValue2);
	}
	
	private boolean filterStockPrijs(BesteldProduct product, String filterValue, String filterValue2) {
		return product.getProduct().isInStock().toString().toLowerCase().equals(filterValue) && Double.toString(product.getTotalePrijs()).contains(filterValue2);
	}
	
	private boolean filterEenheidsprijsPrijs(BesteldProduct product, String filterValue, String filterValue2) {
		return Double.toString(product.getProduct().getEenheidsprijs()).contains(filterValue) && Double.toString(product.getTotalePrijs()).contains(filterValue2);
	}
	
	public String toString() {
		return String.format("%d %s %.2f %s %s", orderId, datumGeplaatst.toString(), berekenTotalBedrag(), orderStatus, betalingStatus);
	}
}
