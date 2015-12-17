package com.beacon.framework.config;

public class SkipException extends RuntimeException {

	
	public SkipException(String message){
	  super(message);
	}
	
	public SkipException(String message, Throwable cause){
		super(message, cause);
	}

	public String getMessage() {
		return this.getMessage();
	}

}
