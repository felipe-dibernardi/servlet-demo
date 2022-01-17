package br.org.senai.devinhouse.servletdemo.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		super();
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
}
