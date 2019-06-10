package edu.fatec.alunos.control;

public class ControlException extends Exception {
	public ControlException() {
		super();
	}

	public ControlException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}

	public ControlException(String msg) {
		super(msg);
	}

	public ControlException(Throwable cause) {
		super(cause);
	}
}
