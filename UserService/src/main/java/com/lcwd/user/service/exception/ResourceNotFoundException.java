package com.lcwd.user.service.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {

	}
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
