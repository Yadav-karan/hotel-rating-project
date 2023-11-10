package com.lcwd.hotel.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {

	}
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
