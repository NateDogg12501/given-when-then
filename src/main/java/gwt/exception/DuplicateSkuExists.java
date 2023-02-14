package gwt.exception;

public class DuplicateSkuExists extends RuntimeException {

	private static final long serialVersionUID = -3550818389895355611L;

	public DuplicateSkuExists(String message) {
		super(message);
	}
}
