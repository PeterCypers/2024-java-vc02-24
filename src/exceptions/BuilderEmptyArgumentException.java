package exceptions;

public class BuilderEmptyArgumentException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;
	
	public BuilderEmptyArgumentException(String message) {
		super(String.format("%s is niet ingevuld.", message));
	}

	public BuilderEmptyArgumentException() {
		super("Er ging iets mis in de builder");
	}
}
