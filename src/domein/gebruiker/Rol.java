package domein.gebruiker;

public enum Rol {
	LEVERANCIER, ADMINISTRATOR, KLANT;
	
	public static class Values {
	     public static final String LEVERANCIER = "LEVERANCIER";
	     public static final String ADMINISTRATOR = "ADMINISTRATOR";
	     public static final String KLANT = "KLANT";
	}
	
	public String toString() {
		return String.format("%s%s", this.name().charAt(0), this.name().substring(1).toLowerCase());
	}
}