package domein;

public class Adres {
	String land;
	String stad;
	String postcode;
	String straat;
	String straatNr;
	
	public Adres(String land, String stad, String postcode, String straat, String straatNr) {
		this.land = land;
		this.stad = stad;
		this.postcode = postcode;
		this.straat = straat;
		this.straatNr = straatNr;
	}
}