package com.douzone.mysite.exception;

import java.sql.SQLException;

public class UserRepositoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserRepositoryException(SQLException e) {
		super(e);
	}

	public UserRepositoryException() {
		super("UserRepository 예외 발생");
	}
}
