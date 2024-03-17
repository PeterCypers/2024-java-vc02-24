package gui;

import java.io.IOException;
import java.time.LocalDate;

import domein.Bestelling;
import domein.BestellingController;
import domein.BetalingsStatus;
import domein.KlantController;
import domein.OrderStatus;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Klant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class KlantenScherm {

    @FXML
    private BorderPane bpKlantenRaadplegen;

    @FXML
    private TextField txfFilerKlanten;

    @FXML
    private Label lbDetailsKlant;

    @FXML
    private Label lbBestellingen;

    @FXML
    private TableView<Klant> tbvOverzichtKlanten;

    @FXML
    private TableColumn<Klant, String> tbcNaam;

    @FXML
    private TableColumn<Klant, Number> tbcAantalOpenstaand;

    @FXML
    private GridPane gpDetailsKlant;

    @FXML
    private ImageView imgvLogo;

    @FXML
    private TextField txfNaam;

    @FXML
    private TextField txfAdresLijn1;

    @FXML
    private TextField txfAdresLijn2;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfTelefoonnummer;

    @FXML
    private TableView<Bestelling> tbvBestellingen;

    @FXML
    private TableColumn<Bestelling, Number> tbcOrderId;

    @FXML
    private TableColumn<Bestelling, LocalDate> tbcDatum;

    @FXML
    private TableColumn<Bestelling, Number> tbcOrderbedrag;

    @FXML
    private TableColumn<Bestelling, String> tbcOrderstatus;

    @FXML
    private TableColumn<Bestelling, String> tbcBetalingsstatus;
    
    @FXML
    private HBox hbFilterbestelling;

    @FXML
    private DatePicker dpFilterBestelling;

    @FXML
    private ChoiceBox<OrderStatus> cbFilterOrderStatus;

    @FXML
    private ChoiceBox<BetalingsStatus> cbFilterBetalingsStatus;

    @FXML
    private TextField txfFilterBestelling;

    
    @FXML
    public void initialize() {
        initializeStatusChoiceBoxes();
    }

    @FXML
    void filterOverzichtBestellingen(ActionEvent event) {    	
    	LocalDate datum = dpFilterBestelling.getValue();
    	OrderStatus orderstatus = cbFilterOrderStatus.getValue();
    	BetalingsStatus betalingsstatus = cbFilterBetalingsStatus.getValue();
    	String keyword = txfFilterBestelling.getText();
    	Gebruiker aangemeldeGebruiker = GebruikerHolder.getInstance();
    	
    	if(keyword.equals("") && datum == null && orderstatus == null && betalingsstatus == null) {
    		tbvBestellingen.setItems(kc.getKlanten().get(index).getObservableListBestellingen(aangemeldeGebruiker));
    	} else {
    		ObservableList<Bestelling> filteredData = FXCollections.observableArrayList();
    		String lowerCaseValue = keyword.toLowerCase();
    		
    		for(Bestelling bestelling: kc.getKlanten().get(index).getObservableListBestellingen(aangemeldeGebruiker)) {
    			if(orderstatus == null && betalingsstatus != null && datum == null && !(lowerCaseValue.equals(""))) {
    				//betalingsstatus  en text filter
    				if((bestelling.getBetalingsStatus().equals(betalingsstatus) && Integer.toString(bestelling.getOrderId()).equals(lowerCaseValue))
    						|| (bestelling.getBetalingsStatus().equals(betalingsstatus) && Double.toString(bestelling.berekenTotalBedrag()).contains(lowerCaseValue)))
    					filteredData.add(bestelling);
    			} else if(orderstatus == null && betalingsstatus == null && datum != null && !(lowerCaseValue.equals(""))) {
    				//datum en text filter 
    				if((bestelling.getDatumGeplaats().equals(datum) && Integer.toString(bestelling.getOrderId()).equals(lowerCaseValue))
    						|| (bestelling.getDatumGeplaats().equals(datum) && Double.toString(bestelling.berekenTotalBedrag()).contains(lowerCaseValue)))
    					filteredData.add(bestelling);
    			} else if(orderstatus != null && betalingsstatus == null && datum == null && !(lowerCaseValue.equals(""))) {
    				//orderstatus en text filter 
    				if((bestelling.getOrderStatus().equals(orderstatus) && Integer.toString(bestelling.getOrderId()).equals(lowerCaseValue))
    						|| (bestelling.getOrderStatus().equals(orderstatus) && Double.toString(bestelling.berekenTotalBedrag()).contains(lowerCaseValue)))
    					filteredData.add(bestelling);
    			} else if(orderstatus == null && betalingsstatus == null && datum == null && !(lowerCaseValue.equals(""))) {
    				//text filter
    				if(Integer.toString(bestelling.getOrderId()).equals(lowerCaseValue)
    						|| Double.toString(bestelling.berekenTotalBedrag()).contains(lowerCaseValue))
    					filteredData.add(bestelling);
    			} else if(orderstatus == null && betalingsstatus != null && datum != null && lowerCaseValue.equals("")) {
    				// betalingsstatus en datum filter
    				if((bestelling.getBetalingsStatus().equals(betalingsstatus) && bestelling.getDatumGeplaats().equals(datum)))
    					filteredData.add(bestelling);
    			} else if(orderstatus != null && betalingsstatus != null && datum == null && lowerCaseValue.equals("")) {
    				//betalinsstatus en orderstatus filter
    				if((bestelling.getBetalingsStatus().equals(betalingsstatus) && bestelling.getOrderStatus().equals(orderstatus)))
    					filteredData.add(bestelling);
    			} else if(orderstatus == null && betalingsstatus != null && datum == null && lowerCaseValue.equals("")) {
    				//betalingsstatus filter
    				if(bestelling.getBetalingsStatus().equals(betalingsstatus) )
    					filteredData.add(bestelling);
    			} else if(orderstatus != null && betalingsstatus == null && datum != null && lowerCaseValue.equals("")) {
    				//orderstatus en datum filter
    				if((bestelling.getOrderStatus().equals(orderstatus) && bestelling.getDatumGeplaats().equals(datum)))
    					filteredData.add(bestelling);
    			} else if(orderstatus != null && betalingsstatus == null && datum == null && lowerCaseValue.equals("")) {
    				//orderstatus filter
    				if(bestelling.getOrderStatus().equals(orderstatus))
    					filteredData.add(bestelling);
    			} else if(orderstatus == null && betalingsstatus == null && datum != null && lowerCaseValue.equals("")) {
    				//datum filter
    				if(bestelling.getDatumGeplaats().equals(datum))
    					filteredData.add(bestelling);
    			}
    		}
    		tbvBestellingen.setItems(filteredData);
    	}
    	
    	dpFilterBestelling.setValue(null);
    	cbFilterOrderStatus.setValue(null);
    	cbFilterBetalingsStatus.setValue(null);
    }
    
    private void initializeStatusChoiceBoxes() {
        cbFilterOrderStatus.getItems().setAll(OrderStatus.values());
        cbFilterBetalingsStatus.getItems().setAll(BetalingsStatus.values());
    }
    
	@FXML
    void filterOverzichtKlanten(ActionEvent event) {
		kc.getFilteredList(txfFilerKlanten.getText());
		toonDetails(false);
    }
	
	private HoofdSchermController hoofdScherm;
	private Node node;
	private KlantController kc;
	private int index;
	
	public KlantenScherm(HoofdSchermController hoofdScherm) {
		this.hoofdScherm = hoofdScherm;
		kc  = new KlantController();
		buildGui();
	}
	
	private void buildGui() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("KlantenScherm.fxml"));
    	loader.setController(this);
        try {
            node = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        tableViewKlanten();
        
        //Geselecteerde klant tonen
        tbvOverzichtKlanten.getSelectionModel().selectedItemProperty()
        	.addListener((observableValue, oudKlant, nieuwKlant) -> {
        		if(nieuwKlant != null) {
        			index = tbvOverzichtKlanten.getSelectionModel().getSelectedIndex();
        			toonDetailsKlant(index);
        		}
        	});
	}
	
	private void tableViewKlanten() {
		tbcNaam.setCellValueFactory(cellData -> cellData.getValue().gebruikersnaamProperty());
		tbcAantalOpenstaand.setCellValueFactory(cellData -> cellData.getValue().openstaandeBestellingenProperty(GebruikerHolder.getInstance()));
		
		tbvOverzichtKlanten.setItems(kc.getKlanten());
	}
	
	private void toonDetailsKlant(int index) {
		new Thread(() -> {
			imgvLogo.setImage(new Image(kc.getKlanten().get(index).getLogo()));
		}).start();
		
		txfNaam.setText(kc.getKlanten().get(index).getNaam());
		txfAdresLijn1.setText(kc.getKlanten().get(index).getAdres().toStringLijn1());
		txfAdresLijn2.setText(kc.getKlanten().get(index).getAdres().toStringLijn2());
		txfEmail.setText(kc.getKlanten().get(index).getEmail());
		txfTelefoonnummer.setText(kc.getKlanten().get(index).getTelefoonnummer());
		
		tableViewBestellingen(index);
		
		toonDetails(true);
	}
	
	private void tableViewBestellingen(int index) {
		tbcOrderId.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		tbcDatum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
		tbcOrderbedrag.setCellValueFactory(cellData -> cellData.getValue().orderbedragProperty());
		tbcOrderstatus.setCellValueFactory(cellData -> cellData.getValue().orderstatusProperty());
		tbcBetalingsstatus.setCellValueFactory(cellData -> cellData.getValue().betalingsstatusProperty());
		
		tbvBestellingen.setItems(kc.getKlanten().get(index).getObservableListBestellingen(GebruikerHolder.getInstance()));
	}
	
	private void toonDetails(Boolean status) {
		lbDetailsKlant.setVisible(status);
		gpDetailsKlant.setVisible(status);
		lbBestellingen.setVisible(status);
		tbvBestellingen.setVisible(status);
		hbFilterbestelling.setVisible(status);
	}

	public Node geefNode() {
		return node;
	}
}

