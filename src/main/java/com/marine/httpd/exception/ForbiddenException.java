package com.marine.httpd.exception;

public class ForbiddenException extends Exception {

	private static final long serialVersionUID = -1735975721856747694L;

	public ForbiddenException() {
		super();
	}

	public ForbiddenException(Exception e) {
		super(e);
	}

	public ForbiddenException(String message, Exception e) {
		super(message, e);
	}

}
