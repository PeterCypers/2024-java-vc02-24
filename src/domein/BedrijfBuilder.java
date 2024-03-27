package domein;

import java.util.ArrayList;
import java.util.List;

import domein.gebruiker.Klant;
import domein.gebruiker.Leverancier;
import exceptions.BuilderEmailException;
import exceptions.BuilderEmptyArgumentException;
import exceptions.BuilderPhoneException;
import exceptions.BuilderUsernameException;

public class BedrijfBuilder {

	// bedrijfinfo
	private String naam;
	private String logo;
	private String sector;
	private String land;
	private String stad;
	private String postcode;
	private String straat;
	private String straatNummer;
	private String rekeningNummer;
	private String emailadres;
	private String telefoonNr;
	private String btwNr;

	// leverancier
	private String lNaam;
	private String lEmailadres;
	private String lWachtwoord;

	// klant
	private String kNaam;
	private String kEmailadres;
	private String kWachtwoord;
	private String kLand;
	private String kStad;
	private String kPostcode;
	private String kStraat;
	private String kStraatNummer;
	private String kTelefoonNr;


	public BedrijfBuilder() {}

	public Bedrijf build() throws IllegalArgumentException {
		Bedrijf bedrijf = new Bedrijf(
			naam,
			logo,
			sector,
			new Adres(land, stad, postcode, straat, straatNummer),
			new ArrayList<Betaalmethode>(),
			rekeningNummer,
			emailadres,
			telefoonNr,
			btwNr,
			true
		);
		bedrijf.setLeverancier(new Leverancier(
			bedrijf,
			lEmailadres,
			lWachtwoord,
			lNaam,
			true
		));
		bedrijf.setKlant(new Klant(
			bedrijf,
			kEmailadres,
			kWachtwoord,
			kNaam,
			true,
			new Adres(kLand, kStad, kPostcode, kStraat, kStraatNummer),
			kTelefoonNr
		));

		return bedrijf;
	}

	public BedrijfBuilder setNaam(String naam) {
		checkNullAndEmpty("Bedrijfsnaam", naam);
	    this.naam = naam;
	    return this;
	}

	public BedrijfBuilder setLogo(String logo) {
		checkNullAndEmpty("Bedrijf logo", logo);
	    this.logo = logo;
	    return this;
	}

	public BedrijfBuilder setSector(String sector) {
		checkNullAndEmpty("Bedrijf sector", sector);
	    this.sector = sector;
	    return this;
	}

	public BedrijfBuilder setLand(String land) {
		checkNullAndEmpty("Bedrijf land", land);
	    this.land = land;
	    return this;
	}

	public BedrijfBuilder setStad(String stad) {
		checkNullAndEmpty("Bedrijf stad", stad);
	    this.stad = stad;
	    return this;
	}

	public BedrijfBuilder setPostcode(String postcode) {
		checkNullAndEmpty("Bedrijf postcode", postcode);
	    this.postcode = postcode;
	    return this;
	}

	public BedrijfBuilder setStraat(String straat) {
		checkNullAndEmpty("Bedrijf straat", straat);
	    this.straat = straat;
	    return this;
	}

	public BedrijfBuilder setStraatNummer(String straatNummer) {
		checkNullAndEmpty("Bedrijf straat nummer", straatNummer);
	    this.straatNummer = straatNummer;
	    return this;
	}

	public BedrijfBuilder setRekeningnummer(String rekeningNummer) {
		checkNullAndEmpty("Bedrijf rekeningnummer", rekeningNummer);
	    this.rekeningNummer = rekeningNummer;
	    return this;
	}

	public BedrijfBuilder setEmailadres(String emailadres) {
		checkNullAndEmpty("Bedrijf email adres", emailadres);
		checkEmailFormat(emailadres);
	    this.emailadres = emailadres;
	    return this;
	}

	public BedrijfBuilder setTelefoonNr(String telefoonNr) {
		checkNullAndEmpty("Bedrijf telefoonNr", telefoonNr);
		checkPhoneFormat(telefoonNr);
	    this.telefoonNr = telefoonNr;
	    return this;
	}

	public BedrijfBuilder setBtwNr(String btwNr) {
		checkNullAndEmpty("Bedrijf btwNr", btwNr);
	    this.btwNr = btwNr;
	    return this;
	}

	public BedrijfBuilder setLNaam(String lNaam) {
		checkNullAndEmpty("Leverancier naam", lNaam);
		checkUsernameFormat(lNaam);
	    this.lNaam = lNaam;
	    return this;
	}

	public BedrijfBuilder setLEmailadres(String lEmailadres) {
		checkNullAndEmpty("Leverancier email adres", lEmailadres);
		checkEmailFormat(lEmailadres);
	    this.lEmailadres = lEmailadres;
	    return this;
	}

	public BedrijfBuilder setLWachtwoord(String lWachtwoord) {
		checkNullAndEmpty("Leverancier wachtwoord", lWachtwoord);
	    this.lWachtwoord = lWachtwoord;
	    return this;
	}

	public BedrijfBuilder setKNaam(String kNaam) {
		checkNullAndEmpty("Klant naam", kNaam);
		checkUsernameFormat(kNaam);
	    this.kNaam = kNaam;
	    return this;
	}

	public BedrijfBuilder setKEmailadres(String kEmailadres) {
		checkNullAndEmpty("Klant email adres", kEmailadres);
		checkEmailFormat(kEmailadres);
	    this.kEmailadres = kEmailadres;
	    return this;
	}

	public BedrijfBuilder setKWachtwoord(String kWachtwoord) {
		checkNullAndEmpty("Klant wachtwoord", kWachtwoord);
	    this.kWachtwoord = kWachtwoord;
	    return this;
	}

	public BedrijfBuilder setKLand(String kLand) {
		checkNullAndEmpty("Klant land", kLand);
	    this.kLand = kLand;
	    return this;
	}

	public BedrijfBuilder setKStad(String kStad) {
		checkNullAndEmpty("Klant stad", kStad);
	    this.kStad = kStad;
	    return this;
	}

	public BedrijfBuilder setKPostcode(String kPostcode) {
		checkNullAndEmpty("Klant postcode", kPostcode);
	    this.kPostcode = kPostcode;
	    return this;
	}

	public BedrijfBuilder setKStraat(String kStraat) {
		checkNullAndEmpty("Klant straat", kStraat);
	    this.kStraat = kStraat;
	    return this;
	}

	public BedrijfBuilder setKStraatNummer(String kStraatNummer) {
		checkNullAndEmpty("Klant straat nummer", kStraatNummer);
	    this.kStraatNummer = kStraatNummer;
	    return this;
	}

	public BedrijfBuilder setKTelefoonNr(String kTelefoonNr) {
		checkNullAndEmpty("Klant telefoonNr", kTelefoonNr);
		checkPhoneFormat(kTelefoonNr);
	    this.kTelefoonNr = kTelefoonNr;
	    return this;
	}

	private void checkNullAndEmpty(String message, String value) {
		if (value == null || value.isBlank())
			throw new BuilderEmptyArgumentException(message);
	}
	private void checkEmailFormat(String email) {
		if (!email.matches("\\w+@\\w+\\.\\w+"))
			throw new BuilderEmailException(email);
	}
	private void checkUsernameFormat(String username) {
		if (!username.matches("\\b([\\p{L}\\-'.,]+[ ]*)+"))
			throw new BuilderUsernameException(username);
	}
	private void checkPhoneFormat(String phone) {
		if(!phone.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$"))
			throw new BuilderPhoneException(phone);
	}
}