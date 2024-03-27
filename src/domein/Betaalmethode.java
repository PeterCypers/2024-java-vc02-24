package domein;

public enum Betaalmethode {
	APPLE_PAY, BANCONTACT, MAESTRO, MASTERCARD, PAYCONIQ, PAYPAL, VENMO, VISA, ACHTERAF_BETALEN;

	@Override
	public String toString() {
		return switch (this) {
			case APPLE_PAY -> "Apple Pay";
			case PAYPAL -> "PayPal";
			case ACHTERAF_BETALEN -> "Achteraf betalen";
			default -> String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		};
	}
}