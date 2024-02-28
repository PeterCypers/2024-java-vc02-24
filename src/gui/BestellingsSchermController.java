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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;


public class BestellingsSchermController extends Pane {
	 @FXML
	    private TextField txfZoeken;

	    @FXML
	    private Button btnZoek;

	    @FXML
	    private Label lbNaamIngelogd;

	    @FXML
	    private Button btnOverzichtBestellingen;

	    @FXML
	    private HBox hbElementen;

	    @FXML
	    private Label lbOverzichtBestellingen;

	    @FXML
	    private Label lbDetailsBestelling;

	    @FXML
	    private Label lbOverzichtProduct;

	    @FXML
	    private TableView<Bestelling> tbvBestellingen;

	    @FXML
	    private TableColumn<Bestelling, Number> tbcOrderID;

	    @FXML
	    private TableColumn<Bestelling, Date> tbcDatum;

	    @FXML
	    private TableColumn<Bestelling, String> tbcKlant;

	    @FXML
	    private TableColumn<Bestelling, OrderStatus> tbcOrderstatus;

	    @FXML
	    private TableColumn<Bestelling, BetalingsStatus> tbcBetalingsstatus;

	    @FXML
	    private TextArea txaDetailsBestelling;

	    @FXML
	    private TextArea txaProducten;
	
	private BestellingController bc;

	public BestellingsSchermController(BestellingController bc) {
		this.bc = bc;
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("BestellingsScherm.fxml"));
        loader.setRoot(this);
    	loader.setController(this);
        try {
        	//lbNaamIngelogd.setText(bc.getNaamGebruiker()); //naam van de ingelogde gebruiker
            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        //Vulled de cellen van de tableView
        tbcOrderID.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty());
        tbcDatum.setCellValueFactory(cellData -> cellData.getValue().datumProperty());
        tbcKlant.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
        tbcOrderstatus.setCellValueFactory(cellData -> cellData.getValue().orderstatusProperty());
        tbcBetalingsstatus.setCellValueFactory(cellData -> cellData.getValue().betalingsstatusProperty());
        
		tbvBestellingen.setItems(bc.getBestellingen());
		
		//Geselecteerde bestelling tonen
		tbvBestellingen.getSelectionModel().selectedItemProperty().
        addListener((observableValue, oudBestelling, nieuwBestelling) -> {
            if (nieuwBestelling != null) {
                int index = tbvBestellingen.getSelectionModel().getSelectedIndex();
                toonDetailsBestelling(index);	//toon de details van de gekozen bestelling op deze index
            }
        });
	}
	
	@FXML
    void btnZoek(ActionEvent event) {
		//zoeken op klant om zijn bestellingen te zien bij overzicht bestellingen 
		bc.getFilterdList(txfZoeken.getText());
		toonBestelling(false);	//bij het zoeken dat alleen bestellingen getoont worden
		//txfZoeken.setText("");	//Om de zoekbalk leeg maken
    }
	
	@FXML
	void btnOverzichtBestellingen(ActionEvent event) {
		hbElementen.setVisible(true);
	}
	
	//toon de details van de gekozen bestelling op deze index
	private void toonDetailsBestelling(int index) {
		
		txaDetailsBestelling.setText(bc.getBestellingen().get(index).toString());//gegevens van de gekozen bestelling
		
		txaProducten.setText(bc.getBestellingen().get(index).getProducten().stream().map(Product::toString).collect(Collectors.joining("\n")));
		
		toonBestelling(true);
	}
	
	private void toonBestelling(Boolean status) {
		lbDetailsBestelling.setVisible(status);
		lbOverzichtProduct.setVisible(status);
		txaDetailsBestelling.setVisible(status);
		txaProducten.setVisible(status);
	}
}
