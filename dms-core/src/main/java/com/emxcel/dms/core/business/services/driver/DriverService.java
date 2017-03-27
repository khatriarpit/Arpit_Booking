package com.emxcel.dms.core.business.services.driver;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.driver.Driver;

public interface DriverService extends DMSEntityService<Long, Driver> {

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-getDriverDetailBytokenID
	 * @param otp **otp**
	 * @return boolean
	 */
	boolean getDriverDetailBytokenID(String otp);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-updateDriverDetailBytokenID
	 * @param tokenID **tokenID**
	 * @param otp **otp**
	 */
	void updateDriverDetailBytokenID(String tokenID, String otp);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-getDriverColor
	 * @param driColor **driColor**
	 * @return boolean
	 */
	Driver getDriverColor(String driColor);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-getDriverOtp
	 * @param carNo **carNo**
	 * @return Driver 
	 */
	Driver getDriverOtp(String carNo);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-setDriverOtp
	 * @param otp **otp**
	 * @param driID **driID**
	 */
	void setDriverOtp(String otp, Long driID);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-listOfDriver
	 * @param tanentId **tanentId**
	 * @return List
	 */
	List<Driver> listOfDriver(Long tanentId);

    /**
     * @param pickUpDateTime
     * @param dropDate
     * @param tanentID
     * @return
     */
    List<Driver> getAvailableDrivers(String pickUpDateTime, String dropDate, Long tanentID);

    /**
     * @param driverName
     * @return
     */
    Driver getDriverByDriverName(String driverName);

    /**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-listOfDriver
	 * @param tanentId **tanentId**
	 * @return List
	 */
	List<Driver> listOfDriverByTanent(Long tanentId);

	/**
	 * @param tenantId
	 * @param imeino
	 * @return
	 */
	Driver getDriverByImeinoTanent(Long tenantId, Long imeino);

	/**
	 * @param tanentID
	 * @param id
	 * @return
	 */
	int listOfDriver(Long tanentID, Long packageID);

	List<Driver> findAllDriversList(Long tanentID, Long packageID);

	Driver getDriverByLicenseNo(String license,Long id);


	Driver getDriverByLicenseNumber(String driver);

	/**
	 * Created By-Nitin Patel Date-14-03-2017. Used For: set Status false if driver deleted Successfully.
	 * @param driver
	 */
	void setStatusforDelete(Driver driver) throws Exception;

	/**
	 * Created By-Nitin Patel Date-14-03-2017. Used For: save driver lisence and adharcar images.
	 * @param driverDetail
	 * @param session
	 */
	void saveDriverImages(Driver driverDetail, HttpSession session) throws Exception;
}