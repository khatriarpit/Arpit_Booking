package com.emxcel.dms.portal.controller;

public class ErrorInfo {
	public final String url;
	public final String ex;

	/**
	 * @param url
	 * @param ex
	 */
	public ErrorInfo(String url, Exception ex) {
		this.url = url;
		this.ex = ex.getLocalizedMessage();
	}
}