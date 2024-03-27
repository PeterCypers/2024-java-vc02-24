package exceptions;

public class BuilderPhoneException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public BuilderPhoneException(String message) {
		super(String.format("%s is geen geldig telefoonnummer.", message));
	}
	
	public BuilderPhoneException() {
		super("Er ging iets mis in de builder");
	}
}