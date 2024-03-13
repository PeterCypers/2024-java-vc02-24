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
		setStad(stad);
		setPostcode(postcode);
		setStraatnaam(straat);
		setStraatnummer(straatNr);
	}
	
	public void setLand(String land) {
	    if (land == null || land.isEmpty()) {
	        throw new IllegalArgumentException("Land mag niet leeg zijn");
	    }
	    
	    this.land = land;
	}
	
	public void setStraatnaam(String straat) {
	    if (straat == null || straat.isEmpty()) {
	        throw new IllegalArgumentException("Straat mag niet leeg zijn");
	    }
	    
	    this.straat = straat;
	}
	
	public void setStraatnummer(String straatNr) {
		if(straatNr == null || straatNr.isBlank())
			throw new IllegalArgumentException("Straatnummer mag niet leeg zijn");
		
		if(!straatNr.matches("([0-9]+[A-Z]?)"))
			throw new IllegalArgumentException("Ongeldig straatnummer");
		
		this.straatNr = straatNr;
	}
	
	public void setPostcode(String postcode) {
	    if (postcode == null || postcode.isBlank())
	    	throw new IllegalArgumentException("Postcode mag niet leeg zijn");
	   
	    this.postcode = postcode;
	}

	public void setStad(String stad) {
	    if (stad == null || stad.isBlank())
	    	throw new IllegalArgumentException("Stad mag niet leeg zijn");
		
		if(!stad.matches("([a-zA-Z]+[\s]?)+"))
			throw new IllegalArgumentException("Ongeldige stad");
		
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
	
	public String toStringLijn1() {
		return String.format("%s %s", straat, straatNr);
	}
	
	public String toStringLijn2() {
		return String.format("%s %s %s", stad, postcode, land);
	}
	
	public String toString() {
		return String.format("%s %s %s %s %s", straat, straatNr, stad, postcode, land);
	}

}
