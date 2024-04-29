package domein;

public enum OrderStatus {
	GEPLAATST,
	VERWERKT,
	VERZONDEN,
	UIT_VOOR_LEVERING,
	GELEVERD,
	VOLTOOID,
	filter;
	
	@Override
	public String toString() {
		return switch (this) {
			case UIT_VOOR_LEVERING -> "Uit voor levering";
			default -> String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
		};
	}
}