package domein;

import jakarta.persistence.Embeddable;

/**
 * Represents an address.
 * <p>This class contains information about an address.</p>
 */
@Embeddable
public class Adres {
	String land;
	String stad;
	String postcode;
	String straat;
	String straatNr;

	/** <code>embeddable class</code> JPA-required default constructor */
	public Adres() {}

	/**
	 * Constructs a new <strong>Adres</strong> with the specified details.
	 *
	 * @param land the country of the address
	 * @param stad the city of the address
	 * @param postcode the postal code of the address
	 * @param straat the street name of the address
	 * @param straatNr the street number of the address
	 */
	public Adres(String land, String stad, String postcode, String straat, String straatNr) {
		setLand(land);
		setStad(stad);
		setPostcode(postcode);
		setStraatnaam(straat);
		setStraatnummer(straatNr);
	}

    public String getLand() {
        return land;
    }

	public void setLand(String land) {
	    if (land == null || land.isEmpty()) {
	        throw new IllegalArgumentException("Land mag niet leeg zijn");
	    }

	    this.land = land;
	}

    public String getStad() {
        return stad;
    }

	/**
	 * <p>setter for attribute <code>stad</code> <br>
	 * the city should match:<br><strong>combination of 1 or more case insensitive letters and whitespaces.</strong>
	 * </p>
	 * @param stad the name of the city
	 */
	public void setStad(String stad) {
	    if (stad == null || stad.isBlank())
	    	throw new IllegalArgumentException("Stad mag niet leeg zijn");

		if(!stad.matches("([a-zA-Z]+[\s]?)+"))
			throw new IllegalArgumentException("Ongeldige stad");

		this.stad = stad;
	}

    public String getPostcode() {
        return postcode;
    }

	public void setPostcode(String postcode) {
	    if (postcode == null || postcode.isBlank())
	    	throw new IllegalArgumentException("Postcode mag niet leeg zijn");

	    this.postcode = postcode;
	}

	public String getStraat() {
        return straat;
    }

	public void setStraatnaam(String straat) {
	    if (straat == null || straat.isEmpty()) {
	        throw new IllegalArgumentException("Straat mag niet leeg zijn");
	    }

	    this.straat = straat;
	}

	public String getStraatNr() {
        return straatNr;
    }

	/**
	 * <p>setter for attribute <code>straatNr</code> <br>
	 * the street number should match:<br><strong> 1 or more numbers and 0-1 uppercase letter(s).</strong>
	 * </p>
	 * @param straatNr the street number
	 */
	public void setStraatnummer(String straatNr) {
		if(straatNr == null || straatNr.isBlank())
			throw new IllegalArgumentException("Straatnummer mag niet leeg zijn");

		if(!straatNr.matches("([0-9]+[A-Z]?)"))
			throw new IllegalArgumentException("Ongeldig straatnummer");

		this.straatNr = straatNr;
	}

	/**
	 * <p>custom address String representation 01 <br>
	 *
	 * use: gui screen <strong>display option 1</strong></p>
	 */
	public String toStringLijn1() {
		return String.format("%s %s", straat, straatNr);
	}

	/**
	 * <p>custom address String representation 02 <br>
	 *
	 * use: gui screen <strong>display option 2</strong></p>
	 */
	public String toStringLijn2() {
		return String.format("%s %s %s", stad, postcode, land);
	}

	@Override
	public String toString() {
		return String.format("%s %s %s %s %s", straat, straatNr, stad, postcode, land);
	}
}