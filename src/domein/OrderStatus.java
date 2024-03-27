package domein;

public enum OrderStatus {
	GELEVERD,
	ONDERWEG,
	AAN_HET_VERWERKEN,
	GEREGISTREERD;

	@Override
	public String toString() {
		return switch (this) {
			case AAN_HET_VERWERKEN -> "Aan het verwerken";
			default -> String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		};
	}
}