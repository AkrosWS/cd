package ch.akros.workshop.cd.exception;

public class GameAlreadyInPlayException extends Exception {

	public GameAlreadyInPlayException() {
	}

	public GameAlreadyInPlayException(String arg0) {
		super(arg0);
	}

	public GameAlreadyInPlayException(Throwable arg0) {
		super(arg0);
	}

	public GameAlreadyInPlayException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public GameAlreadyInPlayException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
