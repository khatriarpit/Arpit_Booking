package com.emxcel.dms.core.business.services.car;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;

/**
 * @author Johnson Chunara
 *
 */
public interface CarService extends DMSEntityService<Long, Car>  {

	/**
	 * Created By - Naresh Banda Date 19-12-2016.
	 * Used For - save car return car id
	 * @param car **
	 * @return Car
	 */
	Long saveCarReturnCarId(Car car);

	/**
	 * Created By - Johnson Chunara Date 19-12-2016.
	 * Used For-findById
	 * @param carId **carId
	 * @param long1 **
	 * @return Car
	 */
	Car findById(Long carId);
	
	/**Created By - Johnson Chunara Date 19-12-2016.
	 * Used For-checkCarNoDBStatus
	 * @param carNo **carNo**
	 * @return List
	 */
	List<Car> checkCarNoDBStatus(String carNo);
	

	/**Created By - Johnson Chunara Date 19-12-2016.
	 * Used For-getCarColor
	 * @param driColor **driColor
	 * @param tanentID 
	 * @param true1 **
	 * @return Car
	 */
	Car getCarColor(String driColor, Long tanentID, String status);


	/**Created By - Johnson Chunara Date 19-12-2016.
	 * Used For-findAllCars
	 * @param long1 
	 * @return List
	 */
	
	/**
	 * @param long1
	 * @return
	 */
	List<Car> findAllCars(Long long1);

	/**
	 * @param tanentID
	 * @return
	 */
	List<Car> findAllCarsNoDrivers(Long tanentID);

	/**
	 * @param carId
	 * @return
	 */
	Car findWithOutDriverById(Long carId);

	/**
	 * @param carno
	 * @return
	 */
	Car checkCarByCarNo(String carno);

    /**
     * @param picUpDateTime
     * @param dropDate
     * @param carType
     * @param tenantId
     * @return
     */
    List<Car> getCarAvailableList(String picUpDateTime, String dropDate, String carType ,Long tenantId);

	/**
	 * Created By-Nitin Patel Date-26-01-2017.
	 * Used For-listOfCar
	 * @param tanentId **tanentId**
	 * @return List
	 */
	List<Car> listOfCarByTanent(Long tanentId);

	/**
	 * @param carNo
	 * @return
	 */
	Car getCarByCarNo(String carNo);

	int findAllCars(Long tanentID, Long packageID) throws ServiceException;
	
	List<Car> findAllCarsList(Long tanentID, Long packageID) throws ServiceException;

	/**
	 * Created By-Nitin Patel Date-14-03-2017. Used For: set Status true if car not null.
	 * @param car
	 */
	void setStatusforCar(Car car,HttpSession session) throws ServiceException;

	void setStatusForCarDelete(Car car) throws ServiceException;
}