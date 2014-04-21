package ch.akros.workshop.cd.exception;

public class NotEnoughPlayerException extends Exception {

	public NotEnoughPlayerException() {
	}

	public NotEnoughPlayerException(String arg0) {
		super(arg0);
	}

	public NotEnoughPlayerException(Throwable arg0) {
		super(arg0);
	}

	public NotEnoughPlayerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotEnoughPlayerException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
