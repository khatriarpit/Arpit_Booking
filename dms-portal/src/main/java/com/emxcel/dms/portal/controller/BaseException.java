package com.emxcel.dms.portal.controller;

public class BaseException extends RuntimeException {

    /**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	public BaseException(String message){
        super("Hello");
    }
}