package edu.fatec.alunos.control.dao;

public class DAOException extends Exception {
	public DAOException() {
		super();
	}

	public DAOException(String msg, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(msg, cause, enableSuppression, writableStackTrace);
	}

	public DAOException(String msg) {
		super(msg);
	}

	public DAOException(Throwable cause) {
		super(cause);
	}
}
