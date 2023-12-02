package common.exception;

public class MediaOrProvinceNotSupportRushOrderException extends AimsException {
	public MediaOrProvinceNotSupportRushOrderException() {
		super("Media or province not support!");
	}
	public MediaOrProvinceNotSupportRushOrderException(String message) {
		super(message);
	}
}
