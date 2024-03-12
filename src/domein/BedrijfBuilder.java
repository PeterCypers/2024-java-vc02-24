package domein;

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
	private String betalingsMogelijkheden;
	private String betalingsInfo;
	private String emailadres;
	private String telefoonNr;
	private String btwNr;
	
	// leverancierGebruiker
	private String lgNaam;
	private String lgEmailadres;
	private String lgWachtwoord;
	private String lgLand;
	private String lgStad;
	private String lgPostcode;
	private String lgStraat;
	private String lgStraatNummer;
	
	// klantGebruiker
	private String kgNaam;
	private String kgEmailadres;
	private String kgWachtwoord;
	private String kgLand;
	private String kgStad;
	private String kgPostcode;
	private String kgStraat;
	private String kgStraatNummer;
	
	// klant
	private String kNaam;
	private String kLogoPad;
	private String kEmailadres;
	private String kTelefoonNr;
	private String kLand;
	private String kStad;
	private String kPostcode;
	private String kStraat;
	private String kStraatNummer;
	
	
	public BedrijfBuilder() {}
	
	public Bedrijf build() throws IllegalArgumentException {
		Bedrijf bedrijf = new Bedrijf(
			naam,
			logo,
			sector,
			new Adres(land, stad, postcode, straat, straatNummer),
			"TODO",
			emailadres,
			telefoonNr,
			btwNr,
			true
		);
		bedrijf.setLeverancierGebruiker(new Gebruiker(
			Rol.LEVERANCIER,
			lgEmailadres,
			lgWachtwoord,
			lgNaam,
			true,
			new Adres(lgLand, lgStad, lgPostcode, lgStraat, lgStraatNummer)
		));
		bedrijf.setKlantGebruiker(new Gebruiker(
			Rol.KLANT,
			kgEmailadres,
			kgWachtwoord,
			kgNaam,
			true,
			new Adres(kgLand, kgStad, kgPostcode, kgStraat, kgStraatNummer)
		));
		bedrijf.setKlant(new Klant(
			kNaam,
			kLogoPad,
			kTelefoonNr,
			kEmailadres,
			new Adres(kLand, kStad, kPostcode, kStraat, kStraatNummer)
		));
		
		return bedrijf;
	}
	
	public BedrijfBuilder setNaam(String naam) {
	    this.naam = naam;
	    return this;
	}

	public BedrijfBuilder setLogo(String logo) {
	    this.logo = logo;
	    return this;
	}

	public BedrijfBuilder setSector(String sector) {
	    this.sector = sector;
	    return this;
	}

	public BedrijfBuilder setLand(String land) {
	    this.land = land;
	    return this;
	}

	public BedrijfBuilder setStad(String stad) {
	    this.stad = stad;
	    return this;
	}

	public BedrijfBuilder setPostcode(String postcode) {
	    this.postcode = postcode;
	    return this;
	}

	public BedrijfBuilder setStraat(String straat) {
	    this.straat = straat;
	    return this;
	}

	public BedrijfBuilder setStraatNummer(String straatNummer) {
	    this.straatNummer = straatNummer;
	    return this;
	}

	public BedrijfBuilder setBetalingsMogelijkheden(String betalingsMogelijkheden) {
	    this.betalingsMogelijkheden = betalingsMogelijkheden;
	    return this;
	}
	
	public BedrijfBuilder setBetalingsInfo(String betalingsInfo) {
	    this.betalingsInfo = betalingsInfo;
	    return this;
	}

	public BedrijfBuilder setEmailadres(String emailadres) {
	    this.emailadres = emailadres;
	    return this;
	}

	public BedrijfBuilder setTelefoonNr(String telefoonNr) {
	    this.telefoonNr = telefoonNr;
	    return this;
	}

	public BedrijfBuilder setBtwNr(String btwNr) {
	    this.btwNr = btwNr;
	    return this;
	}

	public BedrijfBuilder setLgNaam(String lgNaam) {
	    this.lgNaam = lgNaam;
	    return this;
	}

	public BedrijfBuilder setLgEmailadres(String lgEmailadres) {
	    this.lgEmailadres = lgEmailadres;
	    return this;
	}

	public BedrijfBuilder setLgWachtwoord(String lgWachtwoord) {
	    this.lgWachtwoord = lgWachtwoord;
	    return this;
	}

	public BedrijfBuilder setLgLand(String lgLand) {
	    this.lgLand = lgLand;
	    return this;
	}

	public BedrijfBuilder setLgStad(String lgStad) {
	    this.lgStad = lgStad;
	    return this;
	}

	public BedrijfBuilder setLgPostcode(String lgPostcode) {
	    this.lgPostcode = lgPostcode;
	    return this;
	}

	public BedrijfBuilder setLgStraat(String lgStraat) {
	    this.lgStraat = lgStraat;
	    return this;
	}

	public BedrijfBuilder setLgStraatNummer(String lgStraatNummer) {
	    this.lgStraatNummer = lgStraatNummer;
	    return this;
	}

	public BedrijfBuilder setKgNaam(String kgNaam) {
	    this.kgNaam = kgNaam;
	    return this;
	}

	public BedrijfBuilder setKgEmailadres(String kgEmailadres) {
	    this.kgEmailadres = kgEmailadres;
	    return this;
	}

	public BedrijfBuilder setKgWachtwoord(String kgWachtwoord) {
	    this.kgWachtwoord = kgWachtwoord;
	    return this;
	}

	public BedrijfBuilder setKgLand(String kgLand) {
	    this.kgLand = kgLand;
	    return this;
	}

	public BedrijfBuilder setKgStad(String kgStad) {
	    this.kgStad = kgStad;
	    return this;
	}

	public BedrijfBuilder setKgPostcode(String kgPostcode) {
	    this.kgPostcode = kgPostcode;
	    return this;
	}

	public BedrijfBuilder setKgStraat(String kgStraat) {
	    this.kgStraat = kgStraat;
	    return this;
	}

	public BedrijfBuilder setKgStraatNummer(String kgStraatNummer) {
	    this.kgStraatNummer = kgStraatNummer;
	    return this;
	}

	public BedrijfBuilder setKNaam(String kNaam) {
	    this.kNaam = kNaam;
	    return this;
	}

	public BedrijfBuilder setKLogoPad(String kLogoPad) {
	    this.kLogoPad = kLogoPad;
	    return this;
	}

	public BedrijfBuilder setKEmailadres(String kEmailadres) {
	    this.kEmailadres = kEmailadres;
	    return this;
	}

	public BedrijfBuilder setKTelefoonNr(String kTelefoonNr) {
	    this.kTelefoonNr = kTelefoonNr;
	    return this;
	}

	public BedrijfBuilder setKLand(String kLand) {
	    this.kLand = kLand;
	    return this;
	}

	public BedrijfBuilder setKStad(String kStad) {
	    this.kStad = kStad;
	    return this;
	}

	public BedrijfBuilder setKPostcode(String kPostcode) {
	    this.kPostcode = kPostcode;
	    return this;
	}

	public BedrijfBuilder setKStraat(String kStraat) {
	    this.kStraat = kStraat;
	    return this;
	}

	public BedrijfBuilder setKStraatNummer(String kStraatNummer) {
	    this.kStraatNummer = kStraatNummer;
	    return this;
	}
}
