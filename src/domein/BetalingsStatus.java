package domein;

public enum BetalingsStatus {
	ONVERWERKT,
	FACTUUR_VERZONDEN,
	BETAALD, 
	filter;
	
	@Override
	public String toString() {
		return switch (this) {
			case FACTUUR_VERZONDEN -> "Factuur verzonden";
			default -> String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		};
	}
}