package domein;

public enum BetalingsStatus {
	NIET_BETAALD, BETAALD, filter;
	
	public String toString() {
		switch (this) {
		case NIET_BETAALD: return "Niet betaald";
		default: return String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		}
	}
}
