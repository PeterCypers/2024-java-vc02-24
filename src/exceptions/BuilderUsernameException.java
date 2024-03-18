package exceptions;

public class BuilderUsernameException extends IllegalArgumentException {
	
	private static final long serialVersionUID = 1L;

	public BuilderUsernameException(String message) {
		super(String.format("%s is geen geldige gebruikersnaam.", message));
	}
	
	public BuilderUsernameException() {
		super("Er ging iets mis in de builder");
	}
}
