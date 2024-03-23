package domein;

public enum Betaalmethode {
	APPLE_PAY, BANCONTACT, MAESTRO, MASTERCARD, PAYCONIQ, PAYPAL, VENMO, VISA, ACHTERAF_BETALEN;
	
	public String toString() {
		switch (this) {
		case APPLE_PAY: return "Apple Pay";
		case PAYPAL: return "PayPal";
		case ACHTERAF_BETALEN : return "Achteraf betalen";
		default: return String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		}
	}
}
