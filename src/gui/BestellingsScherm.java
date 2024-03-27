package gui;
import java.io.IOException;
import java.time.LocalDate;

import javax.swing.text.DateFormatter;

import domein.BesteldProduct;
import domein.Bestelling;
import domein.BestellingController;
import domein.BetalingsStatus;
import domein.OrderStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class BestellingsScherm {

    @FXML
    private Label lbBestellingDetails;

    @FXML
    private Label lbOverzichtProducten;

    @FXML
    private TableView<Bestelling> tbvOverzichtBestellingen;

    @FXML
    private TableColumn<Bestelling, Number> tbcOrderId;

    @FXML
    private TableColumn<Bestelling, LocalDate> tbcDatum;

    @FXML
    private TableColumn<Bestelling, String> tbcKlant;

    @FXML
    private TableColumn<Bestelling, String> tcbOrderstatus;

    @FXML
    private TableColumn<Bestelling, String> tbcBetalingsstatus;

    @FXML
    private TableView<BesteldProduct> tbvOverzichtProducten;

    @FXML
    private TableColumn<BesteldProduct, String> tbcNaam;

    @FXML
    private TableColumn<BesteldProduct, Number> tbcAantal;

    @FXML
    private TableColumn<BesteldProduct, String> tbcStock;

    @FXML
    private TableColumn<BesteldProduct, String> tbcEenheidsprijs;

    @FXML
    private TableColumn<BesteldProduct, String> tbcPrijs;

    @FXML
    private GridPane gpDetailsBestelling;

    @FXML
    private TextField txfNaam;

    @FXML
    private TextField txfEmail;

    @FXML
    private TextField txfTelefoon;

    @FXML
    private TextField txfOrderID;

    @FXML
    private TextField txfDatum;

    @FXML
    private TextField txfAdresLijn1;

    @FXML
    private TextField txfAdresLijn2;
    
    @FXML
    private ChoiceBox<OrderStatus> choiceboxOrderStatus;
    
    @FXML
    private ChoiceBox<BetalingsStatus> choiceboxBestellingsStatus;

    @FXML
    private DatePicker dpBetalingsherinnering;

    @FXML
    private TextField txfBedrag;

    @FXML
    private HBox hbFilterProducten;

    @FXML
    private TextField txfFilterProducten;

    @FXML
    private TextField txfFilterProduct2;

    @FXML
    private DatePicker dpFilterBestelling;

    @FXML
    private ChoiceBox<OrderStatus> cbFilterBestellingen;

    @FXML
    private ChoiceBox<BetalingsStatus> cbFilterBestelling2;

    @FXML
    private TextField txfFilterBestelling;
    @FXML
    private TextField txfBetaalDatum;

    @FXML
    void filterBestelling(ActionEvent event) {
    	bc.getFilterdList(dpFilterBestelling.getValue(), cbFilterBestellingen.getValue(),
    			cbFilterBestelling2.getValue(), txfFilterBestelling.getText(), null);
    	toonBestelling(false);	//bij het zoeken dat alleen bestellingen getoont worden
    	//nodig om alle bestellingen te zien
    	dpFilterBestelling.setValue(null);
    	cbFilterBestellingen.setValue(null);
    	cbFilterBestelling2.setValue(null);
    }

    @FXML
    void filterProducten(ActionEvent event) {
    	String keyword = txfFilterProducten.getText();
    	String secondKeyword = txfFilterProduct2.getText();
    	  if(keyword.equals("") && secondKeyword.equals("")) {
    		  tbvOverzichtProducten.setItems(bc.getBestellingen().get(index).getObservableListProducten());
    	 } else {
    	     tbvOverzichtProducten.setItems(bc.getBestellingen().get(index).filter(keyword, secondKeyword));
    	 }
    }
    
    @FXML
    public void initialize() {
        initializeStatusChoiceBoxes();
        dpBetalingsherinnering.valueProperty().addListener((obs, oudeDatum, nieuweDatum) -> {
            handleHerinneringsDatumWijziging(nieuweDatum);
        });
    }
    
    private void initializeStatusChoiceBoxes() {
        choiceboxOrderStatus.getItems().setAll(OrderStatus.values());
        choiceboxBestellingsStatus.getItems().setAll(BetalingsStatus.values());
        //filters
        cbFilterBestellingen.getItems().setAll(OrderStatus.values());
        cbFilterBestelling2.getItems().setAll(BetalingsStatus.values());

        choiceboxOrderStatus.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleOrderStatusChange();
        });
        choiceboxBestellingsStatus.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleBetalingStatusChange();
        });
    }

    private BestellingController bc;
    private HoofdSchermController hoofdScherm;
    private int index;
    private boolean isOrderVeranderen; // flag om bij te houden of de gebruiker hier wel een bestelling aanpast, 
    							       // en niet gewoon het systeem die een andere bestelling toont in de combobox
    private Node node;

	public BestellingsScherm(HoofdSchermController hoofdScherm) {
		this.hoofdScherm = hoofdScherm;
		bc = new BestellingController();
		buildGui();
	}
	
	private void buildGui() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BestellingsScherm.fxml"));
    	loader.setController(this);
        try {
            node = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        tableView();
        
        //Geselecteerde bestelling tonen
        tbvOverzichtBestellingen.getSelectionModel().selectedItemProperty().
        addListener((observableValue, oudBestelling, nieuwBestelling) -> {
            if (nieuwBestelling != null) {
                index = tbvOverzichtBestellingen.getSelectionModel().getSelectedIndex();
                toonDetailsBestelling(index);	//toon de details van de gekozen bestelling op deze index
            }
        });
	}
	
	private void tableView() {
		tbcOrderId.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        tbcDatum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
        tbcKlant.setCellValueFactory(cellData -> cellData.getValue().klantNaamProperty());
        tcbOrderstatus.setCellValueFactory(cellData -> cellData.getValue().orderstatusProperty());
        tbcBetalingsstatus.setCellValueFactory(cellData -> cellData.getValue().betalingsstatusProperty());
        
        tbvOverzichtBestellingen.setItems(bc.getBestellingen());
	}
	
	private void toonDetailsBestelling(int index) {
		LocalDate datum = bc.getBestellingen().get(index).getDatumGeplaats();
		LocalDate Betaaldatum = bc.getBestellingen().get(index).getBetalingsDatum();
		Bestelling selectedBestelling = tbvOverzichtBestellingen.getItems().get(index);
		Bestelling geselecteerdeBestelling = tbvOverzichtBestellingen.getItems().get(index);

		
		isOrderVeranderen = true;
		
		txfNaam.setText(bc.getBestellingen().get(index).getKlantName());
		txfEmail.setText(bc.getBestellingen().get(index).getKlant().getEmail());
		txfTelefoon.setText(bc.getBestellingen().get(index).getKlant().getTelefoonnummer());
		txfOrderID.setText(String.format("%d", bc.getBestellingen().get(index).getOrderId()));
		txfDatum.setText(String.format("%s-%s-%s",datum.getYear() , datum.getMonthValue(), datum.getDayOfMonth()));
		txfAdresLijn1.setText(bc.getBestellingen().get(index).getKlant().getAdres().toStringLijn1());
		txfAdresLijn2.setText(bc.getBestellingen().get(index).getKlant().getAdres().toStringLijn2());
		dpBetalingsherinnering.setPromptText(""); //TODO 
	    choiceboxOrderStatus.setValue(selectedBestelling.getOrderStatus());
	    choiceboxBestellingsStatus.setValue(selectedBestelling.getBetalingStatus());
		txfBedrag.setText(String.format("\u20AC%.2f", bc.getBestellingen().get(index).berekenTotalBedrag()));
	    txfBetaalDatum.setText(String.format("%s", Betaaldatum));
		
		dpBetalingsherinnering.setValue(geselecteerdeBestelling.getHerinneringsDatum());
		
		isOrderVeranderen = false;
		
		tableViewProducten(index);
		
		toonBestelling(true);
	}
	
	private void tableViewProducten(int index) {
		tbcNaam.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
		tbcAantal.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());
		tbcStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty());
		tbcEenheidsprijs.setCellValueFactory(cellData -> cellData.getValue().eenheidsprijsProperty());
		tbcPrijs.setCellValueFactory(cellData -> cellData.getValue().totaalPrijsProperty());
		
		tbvOverzichtProducten.setItems(bc.getBestellingen().get(index).getObservableListProducten());
	}
	
	private void toonBestelling(Boolean status) {
		lbBestellingDetails.setVisible(status);
		gpDetailsBestelling.setVisible(status);
		lbOverzichtProducten.setVisible(status);
		tbvOverzichtProducten.setVisible(status);
		hbFilterProducten.setVisible(status);
	}
	
	private void handleOrderStatusChange() {
        Bestelling selectedBestelling = tbvOverzichtBestellingen.getSelectionModel().getSelectedItem();
        if (isOrderVeranderen || selectedBestelling == null || choiceboxOrderStatus.getValue() == null) {
        	return;
        }
        
        try {
        	selectedBestelling.veranderOrderStatus(choiceboxOrderStatus.getValue());
        	
            bc.updateBestelling(selectedBestelling); 
            tbvOverzichtBestellingen.refresh();
            tbvOverzichtProducten.refresh();
        } catch (IllegalArgumentException iae) {
        	Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fout bij het veranderen van de bestellingsstatus");
			alert.setHeaderText(iae.getMessage());
			alert.showAndWait();
        }
	}
	
	
	private void handleBetalingStatusChange() {
        Bestelling selectedBestelling = tbvOverzichtBestellingen.getSelectionModel().getSelectedItem();
        if (isOrderVeranderen || selectedBestelling == null || choiceboxBestellingsStatus.getValue() == null ) {
        	return;
        }
        
        selectedBestelling.setBetalingStatus(choiceboxBestellingsStatus.getValue());
        
        dpBetalingsherinnering.setDisable(choiceboxBestellingsStatus.getValue() == BetalingsStatus.BETAALD);
    
        bc.updateBestelling(selectedBestelling); 
        tbvOverzichtBestellingen.refresh();
        
    }
	private void handleHerinneringsDatumWijziging(LocalDate nieuweDatum) {
	    Bestelling geselecteerdeBestelling = tbvOverzichtBestellingen.getSelectionModel().getSelectedItem();
	    
	    LocalDate vandaag = LocalDate.now();
	    LocalDate betalingsDatum = geselecteerdeBestelling.getBetalingsDatum();    	
	    
	    try {
	    if (geselecteerdeBestelling != null && geselecteerdeBestelling.getBetalingStatus() != BetalingsStatus.BETAALD || !nieuweDatum.isBefore(vandaag) || !nieuweDatum.isAfter(betalingsDatum)) {
	    	dpBetalingsherinnering.setDisable(false);
	        geselecteerdeBestelling.setHerinneringsDatum(nieuweDatum);
	        bc.updateBestelling(geselecteerdeBestelling); 
	        tbvOverzichtBestellingen.refresh();
	    }else {
	    	dpBetalingsherinnering.setDisable(true);
	    }
	    }catch(IllegalArgumentException iae){
	    	Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Ongeldige Datum");
	        alert.setHeaderText("Fout bij het instellen van de herinneringsdatum");
	        alert.setContentText(iae.getMessage());
	        alert.showAndWait();
	    }
	}
	    
	
	
	
    
	public Node geefNode() {
	    return node;
	}

}
