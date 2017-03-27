package com.emxcel.dms.core.model.client;

import java.util.Date;

public class SearchCar {
	private String carId;
	private Date fromDate;
	private Date toDate;
	private String checkPoint;
	private String fromDateError;
	private String toDateError;
	private String searchByError;

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}

	public String getFromDateError() {
		return fromDateError;
	}

	public void setFromDateError(String fromDateError) {
		this.fromDateError = fromDateError;
	}

	public String getToDateError() {
		return toDateError;
	}

	public void setToDateError(String toDateError) {
		this.toDateError = toDateError;
	}

	public String getSearchByError() {
		return searchByError;
	}

	public void setSearchByError(String searchByError) {
		this.searchByError = searchByError;
	}
}