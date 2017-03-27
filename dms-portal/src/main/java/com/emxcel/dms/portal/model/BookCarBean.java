package com.emxcel.dms.portal.model;

import com.emxcel.dms.core.model.driver.Driver;

import java.util.List;

/**
 * Created by root on 1/24/17.
 */

public class BookCarBean {
    private Long carId;
    private String carNumber;
    private String carName;
    private String carType;
    private String previousStartDate;
    private String previousEndDate;
    private String previousStartTime;
    private String previousEndTime;
    private Long previousBufferHours;
    private String nextStartDate;
    private String nextEndDate;
    private String nextStartTime;
    private String nextEndTime;
    private Long nextBufferHours;
    private String pickupDate;
    private String pickupTime;
    private String dropDate;
    private String dropTime;
    private List<Driver> listOfDriver;
    private Long driverId;
    private String driverName;
    private String pickupLocation;
    private String dropLocation;
    private String tripId;


    public BookCarBean() {
    }

    public Long getCarId() {
        return this.carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return this.carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarName() {
        return this.carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarType() {
        return this.carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPreviousStartDate() {
        return this.previousStartDate;
    }

    public void setPreviousStartDate(String previousStartDate) {
        this.previousStartDate = previousStartDate;
    }

    public String getPreviousEndDate() {
        return this.previousEndDate;
    }

    public void setPreviousEndDate(String previousEndDate) {
        this.previousEndDate = previousEndDate;
    }

    public String getPreviousStartTime() {
        return this.previousStartTime;
    }

    public void setPreviousStartTime(String previousStartTime) {
        this.previousStartTime = previousStartTime;
    }

    public String getPreviousEndTime() {
        return this.previousEndTime;
    }

    public void setPreviousEndTime(String previousEndTime) {
        this.previousEndTime = previousEndTime;
    }

    public Long getPreviousBufferHours() {
        return this.previousBufferHours;
    }

    public void setPreviousBufferHours(Long previousBufferHours) {
        this.previousBufferHours = previousBufferHours;
    }

    public String getNextStartDate() {
        return this.nextStartDate;
    }

    public void setNextStartDate(String nextStartDate) {
        this.nextStartDate = nextStartDate;
    }

    public String getNextEndDate() {
        return this.nextEndDate;
    }

    public void setNextEndDate(String nextEndDate) {
        this.nextEndDate = nextEndDate;
    }

    public String getNextStartTime() {
        return this.nextStartTime;
    }

    public void setNextStartTime(String nextStartTime) {
        this.nextStartTime = nextStartTime;
    }

    public String getNextEndTime() {
        return this.nextEndTime;
    }

    public void setNextEndTime(String nextEndTime) {
        this.nextEndTime = nextEndTime;
    }

    public Long getNextBufferHours() {
        return this.nextBufferHours;
    }

    public void setNextBufferHours(Long nextBufferHours) {
        this.nextBufferHours = nextBufferHours;
    }

    public String getPickupDate() {
        return this.pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return this.pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDropDate() {
        return this.dropDate;
    }

    public void setDropDate(String dropDate) {
        this.dropDate = dropDate;
    }

    public String getDropTime() {
        return this.dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public List<Driver> getListOfDriver() {
        return listOfDriver;
    }

    public void setListOfDriver(List<Driver> listOfDriver) {
        this.listOfDriver = listOfDriver;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
