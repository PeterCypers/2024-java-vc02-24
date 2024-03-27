package gui;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    private TableColumn<Bestelling, String> tbcOrderbedrag;

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
    void filterOverzichtKlanten(ActionEvent event) {
		bc.getFilterdList(null, null, null, null, kc.getKlanten().get(klantIndex));
		toonDetails(false);
    }

    @FXML
    void filterOverzichtBestellingen(ActionEvent event) {    	
    	LocalDate datum = dpFilterBestelling.getValue();
    	OrderStatus orderstatus = cbFilterOrderStatus.getValue();
    	BetalingsStatus betalingsstatus = cbFilterBetalingsStatus.getValue();
    	String keyword = txfFilterBestelling.getText();
    	
    	bc.getFilterdList(datum, orderstatus, betalingsstatus, keyword, kc.getKlanten().get(klantIndex));
		tbvBestellingen.setItems(bc.getBestellingen());
    	
    	//nodig om alle bestellingen te zien
    	dpFilterBestelling.setValue(null);
    	cbFilterOrderStatus.setValue(OrderStatus.filter);
    	cbFilterBetalingsStatus.setValue(BetalingsStatus.filter);
    }
    
    
    @FXML
    public void initialize() {
        initializeStatusChoiceBoxes();
    }
    
    private void initializeStatusChoiceBoxes() {
        cbFilterOrderStatus.getItems().setAll(OrderStatus.values());
        cbFilterBetalingsStatus.getItems().setAll(BetalingsStatus.values());
        
        cbFilterBetalingsStatus.setValue(BetalingsStatus.filter);
        cbFilterOrderStatus.setValue(OrderStatus.filter);
    }
	
	private Node node;
	private KlantController kc;
	private BestellingController bc;
	private int klantIndex;
	
	public KlantenScherm() {
		kc = new KlantController();
		bc = new BestellingController();
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
        			klantIndex = tbvOverzichtKlanten.getSelectionModel().getSelectedIndex();
        			toonDetailsKlant(klantIndex);
        		}
        	});
	}
	
	private void tableViewKlanten() {
		tbcNaam.setCellValueFactory(cellData -> cellData.getValue().bedrijfsnaamProperty());
		tbcAantalOpenstaand.setCellValueFactory(cellData -> cellData.getValue().openstaandeBestellingenProperty(GebruikerHolder.getInstance()));
		
		tbvOverzichtKlanten.setItems(kc.getKlanten());
	}
	
	private void toonDetailsKlant(int index) {
		toonLogo(index);
		txfNaam.setText(kc.getKlanten().get(index).getBedrijfsnaam());
		txfAdresLijn1.setText(kc.getKlanten().get(index).getAdres().toStringLijn1());
		txfAdresLijn2.setText(kc.getKlanten().get(index).getAdres().toStringLijn2());
		txfEmail.setText(kc.getKlanten().get(index).getEmail());
		txfTelefoonnummer.setText(kc.getKlanten().get(index).getTelefoonnummer());
		
		tableViewBestellingen(index);
		
		toonDetails(true);
	}
	
	private void toonLogo(int index) {
		// laad spinner
		URL imageUrl = this.getClass().getResource("/images/loading_spinner.gif");
		Image image = new Image(imageUrl.toExternalForm());
		imgvLogo.setImage(image);
        
		// laad image van bedrijf async
		new Thread(() -> {
			imgvLogo.setImage(new Image(kc.getKlanten().get(index).getLogo())); 
		}).start();
	}
	
	private void tableViewBestellingen(int index) {
		tbcOrderId.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
		tbcDatum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
		tbcOrderbedrag.setCellValueFactory(cellData -> cellData.getValue().orderbedragProperty());
		tbcOrderstatus.setCellValueFactory(cellData -> cellData.getValue().orderstatusProperty());
		tbcBetalingsstatus.setCellValueFactory(cellData -> cellData.getValue().betalingsstatusProperty());
		
		bc.getFilterdList(null, OrderStatus.filter, BetalingsStatus.filter, null, kc.getKlanten().get(klantIndex));
		tbvBestellingen.setItems(bc.getBestellingen());
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

