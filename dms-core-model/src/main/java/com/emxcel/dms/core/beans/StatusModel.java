package com.emxcel.dms.core.beans;

import java.util.List;

public class StatusModel {

	/**
	 * login.
	 */
	private String status;

	/**
	 * message
	 */
	private List<String> message;

	/**
	 * login.
	 */
	private Object data;

	private List<?> dataList;

	/**
	 * @return status
	 */
	public final String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public final void setStatus(final String status) {
		this.status = status;
	}

	/**
	 * @param message
	 */
	public void setMessage(List<String> message) {
		this.message = message;
	}

	/**
	 * @return List
	 */
	public List<String> getMessage() {
		return message;
	}

	/**
	 * @return String
	 */
	public final Object getData() {
		return data;
	}

	/**
	 * @param otp
	 */
	public final void setData(final Object data) {
		this.data = data;
	}

	public List<?> getDataList() {
		return dataList;
	}

	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
}