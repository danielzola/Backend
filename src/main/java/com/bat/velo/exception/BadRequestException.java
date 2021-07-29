package com.bat.velo.exception;

public class BadRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1660172094856444899L;

	private String msg;

	public static BadRequestException create(String msg) {
		return new BadRequestException(msg);
	}

	public BadRequestException(String msg) {
		super();
		this.msg = msg;
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
