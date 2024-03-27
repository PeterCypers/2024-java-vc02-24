package domein;

public enum BetalingsStatus {
	NIET_BETAALD, BETAALD;

	@Override
	public String toString() {
		return switch (this) {
			case NIET_BETAALD -> "Niet betaald";
			default -> String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		};
	}
}