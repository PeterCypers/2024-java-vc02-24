package gui;

import java.io.IOException;
import java.util.Optional;

import domein.Adres;
import domein.Bedrijf;
import domein.BedrijfController;
import domein.gebruiker.Gebruiker;
import domein.gebruiker.GebruikerHolder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.WindowEvent;
import javafx.scene.Node;

public class BedrijvenScherm {
	
	@FXML
	private Button btnBedrijfToevoegen;
	
    @FXML
    private Button btnBedrijfDeactiveren;
	
    @FXML
    private TextField txfFilterBedrijven;

    @FXML
    private TextField txfFilterbedrijven2;

	@FXML
	private Label lbDetailsBedrijf;

	@FXML
	private Label lbOverzichtAccounts;

	@FXML
	private TableView<Bedrijf> tbvOverzichtBedrijven;

	@FXML
	private TableColumn<Bedrijf, String> tbcNaam;

	@FXML
	private TableColumn<Bedrijf, String> tbcSector;

	@FXML
	private TableColumn<Bedrijf, String> tbcAdres;

	@FXML
	private TableColumn<Bedrijf, Number> tbcAantalKlanten;

	@FXML
	private TableColumn<Bedrijf, String> tbcIsActief;

	@FXML
	private GridPane gpDetailsBedrijf;

	@FXML
	private ImageView imvLogo;

	@FXML
	private TextField txfNaam;

	@FXML
	private TextField txfSector;

	@FXML
	private TextField txfAdresLijn1;

	@FXML
	private TextField txfAdresLijn2;

	@FXML
	private TextField txfBetalingsmogelijkheid;

	@FXML
	private TextField txfBetalingsinfo;

	@FXML
	private TextField txfEmailadres;

	@FXML
	private TextField txfTelefoonnummer;

	@FXML
	private TextField txfBTWNummer;

	@FXML
	private TableView<Gebruiker> tbvOverzichtAccounts;
	
	@FXML
	private TableColumn<Gebruiker, String> tbcRol;

	@FXML
	private TableColumn<Gebruiker, String> tbcGebruikersnaam;

	@FXML
	private TableColumn<Gebruiker, String> tbcWachtwoord;

	@FXML
	private TableColumn<Gebruiker, String> tbcAccountActief;

	@FXML
	void bedrijfDeactiveren(ActionEvent event) {
		Bedrijf geselecteerdBedrijf = tbvOverzichtBedrijven.getSelectionModel().getSelectedItem();
		
		if (geselecteerdBedrijf == null)
			return;
		
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Bedrijf deactiveren");
		alert.setHeaderText(String.format("Ben je zeker dat je bedrijf \"%s\" wilt deactiveren?", geselecteerdBedrijf.getNaam()));

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.isPresent() && result.get() == ButtonType.OK) {
			bc.deactiveerBedrijf(geselecteerdBedrijf);
			tbvOverzichtBedrijven.refresh();
			tbvOverzichtAccounts.refresh();
			btnBedrijfDeactiveren.setDisable(true);
		}
	}

	@FXML
	void bedrijfToevoegen(ActionEvent event) {
		BedrijfToevoegenController toevoegenScherm = new BedrijfToevoegenController(bc);
		adminSchermController.setDisable(true);
		toevoegenScherm.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::onBedrijfToevoegenSluiten);
	}
	
    private void onBedrijfToevoegenSluiten(WindowEvent event) {
    	adminSchermController.setDisable(false);
    	tbvOverzichtBedrijven.setItems(bc.getBedrijven());
    	tbvOverzichtBedrijven.refresh();
    }

	@FXML
	void filterBedrijven(ActionEvent event) {
		bc.getFilterdList(txfFilterBedrijven.getText(), txfFilterbedrijven2.getText());
		toonBedrijf(false);
	}

	private Node node;
	private AdminSchermController adminSchermController;
	private BedrijfController bc;

	public BedrijvenScherm(AdminSchermController adminSchermController) {
		this.adminSchermController = adminSchermController;
		bc = new BedrijfController();
		buildGui();
	}
	
	private void buildGui() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BedrijvenScherm.fxml"));
    	loader.setController(this);
        try {
            node = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        tableViewBedrijf();
        
        //Geselecteerde bedrijf tonen
        tbvOverzichtBedrijven.getSelectionModel().selectedItemProperty()
        .addListener((observableValue, oudBedrijf, nieuwBedrijf) ->{
        	if(nieuwBedrijf != null) {
        		int index = tbvOverzichtBedrijven.getSelectionModel().getSelectedIndex();
        		toonDetailsBedrijf(index);
        	}
        });
	}

	private void tableViewBedrijf() {
		tbcNaam.setCellValueFactory(cellData -> cellData.getValue().getNaamProp());
		tbcSector.setCellValueFactory(cellData -> cellData.getValue().getSectorProp());
		tbcAdres.setCellValueFactory(cellData -> cellData.getValue().getAdresProp());
		tbcAantalKlanten.setCellValueFactory(cellData -> cellData.getValue().getAantalKlantenProp());
		tbcIsActief.setCellValueFactory(cellData -> cellData.getValue().getIsActiefProp());
		
		tbvOverzichtBedrijven.setItems(bc.getBedrijven());
		tbvOverzichtBedrijven.getSelectionModel().selectedItemProperty().addListener(
			    (observable, oldValue, newValue) -> {
			    	btnBedrijfDeactiveren.setDisable(newValue == null ? true : !newValue.getIsActief());
			    }
			);
	}
	
	private void toonDetailsBedrijf(int index) {
		// image laden async
		new Thread(() -> { 
			imvLogo.setImage(new Image(bc.getBedrijven().get(index).getLogo())); 
		}).start();
		
		txfNaam.setText(bc.getBedrijven().get(index).getNaam());
		txfSector.setText(bc.getBedrijven().get(index).getSector());
		txfAdresLijn1.setText(bc.getBedrijven().get(index).getAdres().toStringLijn1());
		txfAdresLijn2.setText(bc.getBedrijven().get(index).getAdres().toStringLijn2());
		txfBetalingsmogelijkheid.setText(bc.getBedrijven().get(index).getBetalingsmogelijkhedenEnInfo());
		txfBetalingsinfo.setText("");
		txfEmailadres.setText(bc.getBedrijven().get(index).getEmail());
		txfTelefoonnummer.setText(bc.getBedrijven().get(index).getTelefoon());
		txfBTWNummer.setText(bc.getBedrijven().get(index).getBtwNr());
		
		tableViewAccounts(index);
		
		toonBedrijf(true);
	}
	
	private void tableViewAccounts(int index) {
		tbcRol.setCellValueFactory(cellData -> cellData.getValue().rolProperty());
		tbcGebruikersnaam.setCellValueFactory(cellData -> cellData.getValue().gebruikersnaamProperty());
		tbcWachtwoord.setCellValueFactory(cellData -> {
			String wachtwoord = cellData.getValue().getWachtwoord().replaceAll(".", "*");
			return new SimpleStringProperty(wachtwoord);
		});
		tbcAccountActief.setCellValueFactory(cellData -> cellData.getValue().actiefProperty());
		
		tbvOverzichtAccounts.setItems(bc.getBedrijven().get(index).getGebruikers());
	}
	
	private void toonBedrijf(Boolean status) {
		lbDetailsBedrijf.setVisible(status);
		gpDetailsBedrijf.setVisible(status);
		lbOverzichtAccounts.setVisible(status);
		tbvOverzichtAccounts.setVisible(status);
	}

	public Node geefNode() {
		return node;
	}

}
