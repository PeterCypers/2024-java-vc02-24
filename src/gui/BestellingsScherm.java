package gui;
import java.io.IOException;
import java.time.LocalDate;

import domein.BesteldProduct;
import domein.Bestelling;
import domein.BestellingController;
import domein.BetalingsStatus;
import domein.OrderStatus;
import domein.Stock;
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
    private TableColumn<BesteldProduct, Stock> tbcInStock;

    @FXML
    private TableColumn<BesteldProduct, Number> tbcEenheidsprijs;

    @FXML
    private TableColumn<BesteldProduct, Number> tbcPrijs;

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
    void filterBestelling(ActionEvent event) {
    	bc.getFilterdList(dpFilterBestelling.getValue(), cbFilterBestellingen.getValue(),
    			cbFilterBestelling2.getValue(), txfFilterBestelling.getText());
    	toonBestelling(false);	//bij het zoeken dat alleen bestellingen getoont worden
    	dpFilterBestelling.setValue(null); //te veranderen
    	cbFilterBestellingen.setValue(null);
    	cbFilterBestelling2.setValue(null);
    }
    
    @FXML
    public void initialize() {
        initializeStatusChoiceBoxes();
    }

    @FXML
    void filterProducten(ActionEvent event) {
    	String keyword = txfFilterProducten.getText();
    	String secondKeyword = txfFilterProduct2.getText();
    	  if(keyword.equals("") && secondKeyword.equals("")) {
    		  tbvOverzichtProducten.setItems(bc.getBestellingen().get(index).getObservableListProducten());
    	 } else {
    	     ObservableList<Product> filteredData = FXCollections.observableArrayList();
    	     String lowerCaseValue1 = keyword.toLowerCase();
    	     String lowerCaseValue2 = secondKeyword.toLowerCase();
    	     for (Product product : bc.getBestellingen().get(index).getObservableListProducten()) {
    	    	 if(!(keyword.equals("")) && secondKeyword.equals("")) {
    	    		 //één veld
    	    		 if(product.getNaam().toLowerCase().contains(lowerCaseValue1)	//filter op naam van het product
        	        		 || Integer.toString(product.getAantal()).equals(lowerCaseValue1)	//filter op aantal
        	        		 || product.isInStock().toString().toLowerCase().equals(lowerCaseValue1)	//filter op in stock
        	        		 || Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue1)	//filter op eenheidsprijs
        	        		 || Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))	//filter op totale prijs
        	             filteredData.add(product);
    	    	 } else if(!(keyword.equals("")) && !(secondKeyword.equals(""))) {
    	    		 if((product.getNaam().toLowerCase().contains(lowerCaseValue1) && Integer.toString(product.getAantal()).equals(lowerCaseValue2)) 
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue1) && product.isInStock().toString().toLowerCase().equals(lowerCaseValue2))
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue1) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue2))
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue1) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue2))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue1) && product.isInStock().toString().toLowerCase().equals(lowerCaseValue2))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue1) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue2))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue1) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue2))
    	    				 || (product.isInStock().toString().toLowerCase().equals(lowerCaseValue1) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue2))
    	    				 || (product.isInStock().toString().toLowerCase().equals(lowerCaseValue1) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue2))
    	    				 || (Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue1) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue2))
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue2) && Integer.toString(product.getAantal()).equals(lowerCaseValue1)) 
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue2) && product.isInStock().toString().toLowerCase().equals(lowerCaseValue1))
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue2) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue1))
    	    				 || (product.getNaam().toLowerCase().contains(lowerCaseValue2) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue2) && product.isInStock().toString().toLowerCase().equals(lowerCaseValue1))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue2) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue1))
    	    				 || (Integer.toString(product.getAantal()).equals(lowerCaseValue2) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))
    	    				 || (product.isInStock().toString().toLowerCase().equals(lowerCaseValue2) && Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue1))
    	    				 || (product.isInStock().toString().toLowerCase().equals(lowerCaseValue2) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))
    	    				 || (Double.toString(product.getEenheidsprijs()).contains(lowerCaseValue2) && Double.toString(product.getTotalePrijs()).contains(lowerCaseValue1))
							)
    	    			 filteredData.add(product);
    	    	 }
    	     }
    	     tbvOverzichtProducten.setItems(filteredData);
    	   }
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
		Bestelling selectedBestelling = tbvOverzichtBestellingen.getItems().get(index);
		
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
		txfBedrag.setText(String.format("€%.2f", bc.getBestellingen().get(index).berekenTotalBedrag()));
		
		tableViewProducten(index);
		
		toonBestelling(true);
	}
	
	private void tableViewProducten(int index) {
		tbcNaam.setCellValueFactory(cellData -> cellData.getValue().naamProperty());
		tbcAantal.setCellValueFactory(cellData -> cellData.getValue().aantalProperty());
		tbcInStock.setCellValueFactory(cellData -> cellData.getValue().stockProperty());
		tbcEenheidsprijs.setCellValueFactory(cellData -> cellData.getValue().eenheidsprijsProperty());
		tbcPrijs.setCellValueFactory(cellData -> cellData.getValue().totalePrijsProperty());
		
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
        if (selectedBestelling == null || choiceboxOrderStatus.getValue() == null ) {
        	return;
        }
        
        selectedBestelling.setOrderStatus(choiceboxOrderStatus.getValue());
    
        bc.updateBestelling(selectedBestelling); 
        tbvOverzichtBestellingen.refresh();
	}
	
	private void handleBetalingStatusChange() {
        Bestelling selectedBestelling = tbvOverzichtBestellingen.getSelectionModel().getSelectedItem();
        if (selectedBestelling == null || choiceboxBestellingsStatus.getValue() == null ) {
        	return;
        }
        
        selectedBestelling.setBetalingStatus(choiceboxBestellingsStatus.getValue());
    
        bc.updateBestelling(selectedBestelling); 
        tbvOverzichtBestellingen.refresh();
    }
    
	public Node geefNode() {
	    return node;
	}

}
