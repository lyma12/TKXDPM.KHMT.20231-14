package common.exception;

public class GatewayTimeOutException extends AimsException {
	public GatewayTimeOutException() {
		super("504 gate way time out! ");
	}
}
