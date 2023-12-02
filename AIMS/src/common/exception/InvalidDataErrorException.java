package common.exception;

public class InvalidDataErrorException extends PaymentException {

	public InvalidDataErrorException() {
		super("Invalid Data!");
		// TODO Auto-generated constructor stub
	}
	public InvalidDataErrorException(String message) {
		super(message);
	}
	private static final long serialVersionUID = 1L;

}
