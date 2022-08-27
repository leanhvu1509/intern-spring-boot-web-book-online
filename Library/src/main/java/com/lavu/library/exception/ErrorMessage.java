package com.lavu.library.exception;

public class ErrorMessage extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ErrorMessage(String message,Exception e) {
		super(message,e);
	}
	public ErrorMessage(String message) {
		super(message);
	}
}
