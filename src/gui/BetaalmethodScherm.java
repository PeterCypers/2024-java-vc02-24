package gui;

import java.io.IOException;
import java.util.List;

import domein.BedrijfController;
import domein.Betaalmethode;
import domein.gebruiker.GebruikerHolder;
import domein.gebruiker.Leverancier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class BetaalmethodScherm extends BorderPane{
	@FXML
    private CheckBox cbBancontact;

    @FXML
    private ImageView imvBancontact;

    @FXML
    private CheckBox cbApplePay;

    @FXML
    private ImageView imgApplePay;

    @FXML
    private CheckBox cbMastercard;

    @FXML
    private ImageView imgvMastercard;

    @FXML
    private CheckBox cbVenmo;

    @FXML
    private ImageView imgvVenmo;

    @FXML
    private CheckBox cbPayconiq;

    @FXML
    private ImageView imgvPayconiq;

    @FXML
    private CheckBox cbVisa;

    @FXML
    private ImageView imgvVisa;

    @FXML
    private CheckBox cbMaestro;

    @FXML
    private ImageView imgvMaestro;

    @FXML
    private CheckBox cbPayPal;

    @FXML
    private ImageView imgvPayPal;

    @FXML
    private CheckBox cbAchterAf;

    @FXML
    void selectAchterAf(ActionEvent event) {
    	changeBetaalmethodes(cbAchterAf, Betaalmethode.ACHTERAF_BETALEN);
    }

    @FXML
    void selectApplePay(ActionEvent event) {
    	changeBetaalmethodes(cbApplePay, Betaalmethode.APPLE_PAY);
    }

    @FXML
    void selectBancontact(ActionEvent event) {
    	changeBetaalmethodes(cbBancontact, Betaalmethode.BANCONTACT);
    }

    @FXML
    void selectMaestro(ActionEvent event) {
    	changeBetaalmethodes(cbMaestro, Betaalmethode.MAESTRO);
    }

    @FXML
    void selectMastercard(ActionEvent event) {
    	changeBetaalmethodes(cbMastercard, Betaalmethode.MASTERCARD);
    }

    @FXML
    void selectPayPal(ActionEvent event) {
    	changeBetaalmethodes(cbPayPal, Betaalmethode.PAYPAL);
    }

    @FXML
    void selectPayconiq(ActionEvent event) {
    	changeBetaalmethodes(cbPayconiq, Betaalmethode.PAYCONIQ);
    }

    @FXML
    void selectVenmo(ActionEvent event) {
    	changeBetaalmethodes(cbVenmo, Betaalmethode.VENMO);
    }

    @FXML
    void selectVisa(ActionEvent event) {
    	changeBetaalmethodes(cbVisa, Betaalmethode.VISA);
    }
    
    private void changeBetaalmethodes(CheckBox box, Betaalmethode betaalmethode) {
    	if(box.isSelected())
    		leverancier.getBedrijf().addBetaalmethodes(betaalmethode);
    	else
    		leverancier.getBedrijf().removeBetaalmethodes(betaalmethode);
    	
    	bc.updateBedrijf(leverancier.getBedrijf());
    }
    
    private HoofdSchermController hoofdScherm;
    private BedrijfController bc;
    private Node node;
    private Leverancier leverancier = (Leverancier) GebruikerHolder.getInstance();

	public BetaalmethodScherm(HoofdSchermController hoofdScherm) {
		this.hoofdScherm = hoofdScherm;
		bc = new BedrijfController();
		buildGui();
	}
	
	private void buildGui() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("BetaalmethodScherm.fxml"));
    	loader.setController(this);
        try {
            node = loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        checktCheckBox();
	}
	
	private void checktCheckBox() {
		List<Betaalmethode> betaalmethodes = leverancier.getBedrijf().getBetaalmethodes();
		System.out.print(betaalmethodes);
		for(Betaalmethode betaalmethode : betaalmethodes) {
			switch (betaalmethode) {
			case ACHTERAF_BETALEN -> changeCheckBox(cbAchterAf, betaalmethode == Betaalmethode.ACHTERAF_BETALEN);
			case APPLE_PAY -> changeCheckBox(cbApplePay, betaalmethode == Betaalmethode.APPLE_PAY);
			case BANCONTACT -> changeCheckBox(cbBancontact, betaalmethode == Betaalmethode.BANCONTACT);
			case MAESTRO -> changeCheckBox(cbMaestro, betaalmethode == Betaalmethode.MAESTRO);
			case MASTERCARD -> changeCheckBox(cbMastercard, betaalmethode == Betaalmethode.MASTERCARD);
			case PAYCONIQ -> changeCheckBox(cbPayconiq, betaalmethode == Betaalmethode.PAYCONIQ);
			case PAYPAL -> changeCheckBox(cbPayPal, betaalmethode == Betaalmethode.PAYPAL);
			case VENMO -> changeCheckBox(cbVenmo, betaalmethode == Betaalmethode.VENMO);
			case VISA -> changeCheckBox(cbVisa, betaalmethode == Betaalmethode.VISA);
			}
		}
	}
	
	private void changeCheckBox(CheckBox box, Boolean status) {
		box.setSelected(status);
	}

	public Node geefNode() {
		return node;
	}
}

