package gui;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import domein.Bestelling;
import domein.BestellingController;
import domein.BetalingsStatus;
import domein.OrderStatus;
import domein.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class BestellingsScherm {

    @FXML
    private TextField txfFilterBestelling;

    @FXML
    private Label lbBestellingDetails;

    @FXML
    private Label lbOverzichtProducten;

    @FXML
    private TableView<Bestelling> tbvOverzichtBestellingen;

    @FXML
    private TableColumn<Bestelling, Number> tbcOrderId;

    @FXML
    private TableColumn<Bestelling, Date> tbcDatum; //moet nog veranderd worden

    @FXML
    private TableColumn<Bestelling, String> tbcKlant;

    @FXML
    private TableColumn<Bestelling, OrderStatus> tcbOrderstatus;

    @FXML
    private TableColumn<Bestelling, BetalingsStatus> tbcBetalingsstatus;

    @FXML
    private VBox vbBestellingDetails;

    @FXML
    private TextField txfNaam;

    @FXML
    private TextField txfContactgegevens;

    @FXML
    private TextField txfOrderId;

    @FXML
    private TextField txfDatum;

    @FXML
    private TextField txfLeveradres;

    @FXML
    private ChoiceBox<OrderStatus> choiceboxOrderStatus;

    @FXML
    private ChoiceBox<BetalingsStatus> choiceboxBestellingsStatus;


    @FXML
    private TextField txfBetalingsherinnering;

    @FXML
    private TextField txfBedrag;

    @FXML
    private TextArea txaOverzichtProducten;
    
    private BestellingController bc;
    private HoofdSchermController hoofdScherm;
    private Node node;
    
    @FXML
    void Zoeken(ActionEvent event) { 
    	bc.getFilterdList(txfFilterBestelling.getText());
    	toonBestelling(false);	
    }
    @FXML
    public void initialize() {
        initializeStatusChoiceBoxes();
    }

    private void initializeStatusChoiceBoxes() {
        choiceboxOrderStatus.getItems().setAll(OrderStatus.values());
        choiceboxBestellingsStatus.getItems().setAll(BetalingsStatus.values());

        choiceboxOrderStatus.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleStatusChange();
        });
        choiceboxBestellingsStatus.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            handleStatusChange();
        });
    }
    
    
    
    
    


	public BestellingsScherm(HoofdSchermController hoofdScherm) {
		this.hoofdScherm = hoofdScherm;
		bc = new BestellingController(hoofdScherm.getGebruiker());
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
                int index = tbvOverzichtBestellingen.getSelectionModel().getSelectedIndex();
                toonDetailsBestelling(index);	//toon de details van de gekozen bestelling op deze index
            }
        });
	}
	
	private void tableView() {
		tbcOrderId.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        tbcDatum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
        tbcKlant.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        tcbOrderstatus.setCellValueFactory(cellData -> cellData.getValue().orderstatusProperty());
        tbcBetalingsstatus.setCellValueFactory(cellData -> cellData.getValue().betalingsstatusProperty());
        
        tbvOverzichtBestellingen.setItems(bc.getBestellingen());
	}
	
	private void toonDetailsBestelling(int index) {
		Date datum = bc.getBestellingen().get(index).getDatumGeplaats();
		Bestelling selectedBestelling = tbvOverzichtBestellingen.getItems().get(index);

		
		txfNaam.setText(bc.getBestellingen().get(index).getKlantName());
	    txfContactgegevens.setText(bc.getBestellingen().get(index).getKlant().getContactgegevens());
	    txfOrderId.setText(String.format("%d", bc.getBestellingen().get(index).getOrderId()));
	    txfDatum.setText(String.format("%td/%tm/%tY", datum, datum, datum));
	    txfLeveradres.setText(bc.getBestellingen().get(index).getKlant().getAdres().toString());
	    choiceboxOrderStatus.setValue(selectedBestelling.getOrderStatus());
	    choiceboxBestellingsStatus.setValue(selectedBestelling.getBetalingStatus());
		
		txfBetalingsherinnering.setText("todo"); //TODO
		txfBedrag.setText(String.format("€%.2f", bc.getBestellingen().get(index).berekenTotalBedrag()));
		
		txaOverzichtProducten.setText(bc.getBestellingen().get(index).getProducten().stream().map(Product::toString).collect(Collectors.joining("\n")));
		
		toonBestelling(true);
	}
	
	private void toonBestelling(Boolean status) {
		vbBestellingDetails.setVisible(status);
		txaOverzichtProducten.setVisible(status);
		lbOverzichtProducten.setVisible(status);
		lbBestellingDetails.setVisible(status);
	}
	private void handleStatusChange() {
        Bestelling selectedBestelling = tbvOverzichtBestellingen.getSelectionModel().getSelectedItem();
        if (selectedBestelling == null || choiceboxOrderStatus.getValue() == null|| choiceboxBestellingsStatus.getValue() == null ) {
        	return;
        }
        if (selectedBestelling != null)	{
            selectedBestelling.setOrderStatus(choiceboxOrderStatus.getValue());
            selectedBestelling.setBetalingStatus(choiceboxBestellingsStatus.getValue());
        
            bc.updateBestelling(selectedBestelling); 
            tbvOverzichtBestellingen.refresh();
        }
        
        
    }
    
	public Node geefNode() {
	    return node;
	}
	
	

	
	
	

}
