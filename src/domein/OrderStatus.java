package domein;

public enum OrderStatus {
	GELEVERD,
	ONDERWEG,
	AAN_HET_VERWERKEN,
	GEREGISTREERD;
	
	public String toString() {
		switch (this) {
		case AAN_HET_VERWERKEN: return "Aan het verwerken";
		default: return String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		}
	}
}
