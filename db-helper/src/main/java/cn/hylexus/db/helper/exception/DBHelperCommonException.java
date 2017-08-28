package cn.hylexus.db.helper.exception;

public class DBHelperCommonException extends Exception {

	private static final long serialVersionUID = 1L;

	public DBHelperCommonException() {
		super();
	}

	public DBHelperCommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DBHelperCommonException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBHelperCommonException(String message) {
		super(message);
	}

	public DBHelperCommonException(Throwable cause) {
		super(cause);
	}

}
