package com.emxcel.dms.core.business.services.client;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.TripDetails;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.guest.Guest;

public interface ClientModelService extends DMSEntityService<Long, ClientModel> {

	/**
	 * @param id
	 * @param picUpDateTime
	 * @return
	 */
	ClientModel getPreviousTripForCar(Long id, String picUpDateTime);

	/**
	 * @param id
	 * @param dropDateTime
	 * @return
	 */
	ClientModel getNextTripForCar(Long id, String dropDateTime);

	/**
	 * @param car
	 * @return
	 */
	List<ClientModel> getByTripStatusByCar(Car car);

	List<ClientModel> getByTripStatusByCarDriver(Car car, Driver driver);

	List<ClientModel> getByTripStatusByDriver(Driver driver);

	/**
	 * @param tripid
	 * @return
	 */
	ClientModel getTripByTripId(String tripid);

	/**
	 * @param tanentID
	 * @return
	 */
	List<ClientModel> getClientAndGuestModel(Long tanentID);

	/**
	 * @param client
	 * @param tripDetails
	 * @return
	 */
	Double countMinimumKm(final ClientModel client, final TripDetails tripDetails);

	/**
	 * @param client
	 * @param tripDetails
	 * @return
	 */
	Double countHoursandKm(final ClientModel client, final TripDetails tripDetails);

	/**
	 * @param driver
	 * @return
	 */
	List<ClientModel> getDriverByDriverId(Driver driver);

	/**
	 * @param tripID
	 * @param tenantID
	 * @return
	 */
	ClientModel getTripByTripIdTenant(String tripID, Long tenantID);

	/**
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param carNo
	 * @param tenantID
	 * @return List<ClientModel>
	 */
	List<ClientModel> getSortByType(String type, String startDate, String endDate, String carNo, Long tenantID);

	/**
	 * @param tripID
	 * @param status
	 * @param tanentID
	 */
	ClientModel getClientModelTripStatusByTripID(String tripID, int status, Long tanentID);

	/**
	 * @return list
	 */
	List<ClientModel> getPastTrips();

	/**
	 * @param tanentID
	 * @return
	 */
	List<ClientModel> getPendingTrip(Long tanentID);

	/**
	 * Created: Naresh Banda Date: 18-02-2017. Use : Get Trips by Status IDs
	 * 
	 * @param statusIDByStatus
	 * @return
	 */
	List<ClientModel> getDriverCancelList(Long tanentID);

	List<ClientModel> getCustomerCancelList(Long tanentID);

	List<ClientModel> getTripsByStatusID(int status);

	List<ClientModel> getTripsBypendingStatus(Guest guest);

	List<ClientModel> getTripsByStatus(Guest guest);

	List<ClientModel> getEndTrips(Guest guest);

	List<Car> getCarAvailableListByParamTenant(List<String> listoftenant, String fromdatetime, String todatetime,
			String cartype);

	ClientModel checkTripId(String tripID);

	List<ClientModel> getDriverByClient(Driver driver);

	List<ClientModel> getCancelTripByDriver(Long tenantId);

	List<ClientModel> getCancelTripByCustomer(Long tenantId);

	List<ClientModel> getWeekTrips(Long tenantId);

	List<ClientModel> getPendingtripsofCar(Car car);

	ClientModel getTripsByStatusByGuest(Guest guest, int status);

	List<ClientModel> getCanceledTrips(Long tenantId);

	List<ClientModel> getEndTrips(Long tenantId);

	List<ClientModel> getCurrentTrips(Long tenantId);

	List<ClientModel> getwholeTrips(Driver driver);

	List<ClientModel> getTotalTrips(Driver driver);

	ClientModel getByTanentID(Long tanentID,String tripId);
	
	void saveClientDetails(ClientModel clientModel, HttpSession session,String pickUpDateTime,String dropDate,String preBookingId);
	
	void updateClientDetsils(ClientModel clientModel,String pickUpDateTime,String dropDate);
	
	void setSatusCancel(String tripId);
	
}