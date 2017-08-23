package cn.hylexus.db.helper.exception;

public class UnSupportedDataTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnSupportedDataTypeException() {
		super();
	}

	public UnSupportedDataTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UnSupportedDataTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnSupportedDataTypeException(String message) {
		super(message);
	}

	public UnSupportedDataTypeException(Throwable cause) {
		super(cause);
	}

}
