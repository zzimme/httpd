package com.marine.httpd.exception;

public class NotFoundException extends Exception {

	private static final long serialVersionUID = -4556532132544499289L;

	public NotFoundException() {
		super();
	}

	public NotFoundException(Exception e) {
		super(e);
	}

	public NotFoundException(String message, Exception e) {
		super(message, e);
	}

}
