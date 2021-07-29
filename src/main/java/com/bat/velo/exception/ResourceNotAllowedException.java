package com.bat.velo.exception;

public class ResourceNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8720716765345933158L;

	private String msg;

	public static ResourceNotAllowedException create(String msg) {
		return new ResourceNotAllowedException(msg);
	}

	public ResourceNotAllowedException(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
