package common.exception;

public class NotEnoughBalanceException extends PaymentException{

	public NotEnoughBalanceException() {
		super("ERROR: Not enough balance in card!");
	}
	public NotEnoughBalanceException(String message) {
		super(message);
	}

}
