package exceptions;

public class BuilderEmailException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	public BuilderEmailException(String message) {
		super(String.format("%s is geen geldige email.", message));
	}
	
	public BuilderEmailException() {
		super("Er ging iets mis in de builder");
	}

}
