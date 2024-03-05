package domein;

import jakarta.persistence.Embeddable;

@Embeddable
public class Adres {
	String land;
	String stad;
	String postcode;
	String straat;
	String straatNr;
	
	public Adres() {}
	
	public Adres(String land, String stad, String postcode, String straat, String straatNr) {
		setLand(land);
		setStraatnaam(straat);
		setStraatnummer(straatNr);
		setPostcode(postcode);
		setStad(stad);
	}
	public void setLand(String land) {
	    if (land == null || land.isEmpty()) {
	        throw new IllegalArgumentException("Ongeldige landnaam!");
	    }
	    
	    this.land = land;
	}
	
	public void setStraatnaam(String straat) {
	    
	    if (straat == null || straat.isEmpty() || straat.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
	        throw new IllegalArgumentException("Straatnaam incorrect!");
	    }
	    
	    this.straat = straat;
	}
	
	public void setStraatnummer(String straatNr) {
		if(straatNr == null || !straatNr.matches("([0-9]+[A-Z]?)"))
			throw new IllegalArgumentException("Straatnummer incorrect!");
		
		this.straatNr = straatNr;
	}
	public void setPostcode(String postcode) {
	    int numericPostcode;
	    try {
	        numericPostcode = Integer.parseInt(postcode);
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Postcode moet een numerieke waarde zijn!");
	    }
	   
	    if (numericPostcode < 1000 || numericPostcode > 99999) {
	        throw new IllegalArgumentException("Postcode incorrect!");
	    }
	   
	    this.postcode = postcode;
	}

	public void setStad(String stad) {
		if(stad == null || !stad.matches("([a-zA-Z]+[\s]?)+"))
			throw new IllegalArgumentException("Stad incorrect!");
		
		this.stad = stad;
	}
    public String getLand() {
        return land;
    }
    
    public String getStad() {
        return stad;
    }
    
    public String getPostcode() {
        return postcode;
    }
    
    public String getStraat() {
        return straat;
    }
    
    public String getStraatNr() {
        return straatNr;
    }
	
	public String toString() {
		return String.format("%s %s %s %s %s", straat, straatNr, stad, postcode, land);
	}
}
