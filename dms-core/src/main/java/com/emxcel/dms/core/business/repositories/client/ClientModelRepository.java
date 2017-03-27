package com.emxcel.dms.core.business.repositories.client;

import java.sql.Timestamp;
import java.util.List;

import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.guest.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;

public interface ClientModelRepository extends JpaRepository<ClientModel, Long> {

	@Query("select cm from ClientModel cm where cm.car=? and cm.statusID IN (1,2,5,6) ORDER BY cm.pickUpDateTime asc ")
	List<ClientModel> getByTripStatusByCar(Car car);

	@Query("select cm from ClientModel cm where cm.car=:car and cm.driver=:driver and cm.statusID IN (1,2,5,6) ORDER BY cm.pickUpDateTime asc ")
	List<ClientModel> getByTripStatusByCarDriver(@Param("car")Car car,@Param("driver")Driver driver);


	@Query("select cm from ClientModel cm where cm.driver=:driver and  cm.statusID IN (1,2,5,6) ORDER BY cm.pickUpDateTime asc ")
	List<ClientModel> getByTripStatusByDriver(@Param("driver")Driver driver);

	@Query("select cm from ClientModel cm where cm.tripId=?")
	ClientModel getTripByTripId(String tripid);

   /* @Query("select c from ClientModel c where c.user=:user and c.tanentID=:tenantID and c.")
	List<ClientModel> getClientModelListByTenantIDAndCreated(@Param("user") User user, @Param("tenantID") Long tanentID, @Param("statusId") Long statusId);*/

	@Query("select c from ClientModel c where c.tanentID=:tenantID ORDER BY c.pickUpDateTime asc ")
	 List<ClientModel> getClientModelListByTenantIDAndCreated(@Param("tenantID") Long tanentID);


    @Query("select cml from ClientModel cml where  cml.pickUpDateTime=(select max(cm.pickUpDateTime)from ClientModel cm where cm.pickUpDateTime<=STR_TO_DATE(:picUpDateTime,\'%d/%m/%Y %H:%i:%s\') and cm.car.id=:id)")
    ClientModel getPreviousTripForCar(@Param("id") Long var1, @Param("picUpDateTime") String var2);

    @Query("select cml from ClientModel cml where cml.dropDateTime=(select max(cm.dropDateTime) from ClientModel cm where cm.dropDateTime >=STR_TO_DATE(:dropDateTime, \'%d/%m/%Y %H:%i:%s\') and cm.car.id=:id)")
    ClientModel getNextTripForCar(@Param("id") Long var1, @Param("dropDateTime") String var2);


    @Query("select cm from ClientModel cm where cm.tripId=:tripID and cm.tanentID=:tenantID")
	ClientModel getTripByTripIdTenant(@Param("tripID") String tripID, @Param("tenantID") Long tenantID);

    @Query("select cm from ClientModel cm where cm.tripId=:tripID and cm.tanentID=:tanentID and cm.statusID=:status")
	ClientModel getClientModelTripStatusByTripID(@Param("tripID") String tripID, @Param("status") int status, @Param("tanentID") Long tanentID);

    @Query("select cm from ClientModel cm where cm.statusID IN(3,4)")
    List<ClientModel> getPastTrips();

    @Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID =1 ORDER BY cm.pickUpDateTime asc ")
	List<ClientModel> getPendingTrip(@Param("tanentID") Long tanentID);

	@Query("select cm from ClientModel cm where cm.statusID in (:status) ORDER BY cm.pickUpDateTime asc")
	List<ClientModel> getTripsByStatusID(@Param("status") int status);

	@Query("select cm from ClientModel cm where cm.statusID='5' and cm.tanentID= :tanentID")
	List<ClientModel> getDriverCancelList(@Param("tanentID")Long tenantId);
	
	@Query("select cm from ClientModel cm where cm.statusID='6' and cm.tanentID= :tanentID")
	List<ClientModel> getCustomerCancelList(@Param("tanentID")Long tanentID);

	@Query("select cm from ClientModel cm where cm.guest=:guest and cm.statusID IN (3,4)")
	List<ClientModel> getTripsByStatus(@Param("guest")Guest guest);
	
	@Query("select cm from ClientModel cm where cm.guest=:guest and cm.statusID IN (3)")
	List<ClientModel> getEndTrips(@Param("guest")Guest guest);

	@Query("select cm from ClientModel cm where cm.guest=:guest and cm.statusID IN (1)")
	List<ClientModel> getTripsBypendingStatus(@Param("guest")Guest guest);

	@Query("select cm from ClientModel cm where cm.tripId=:tripID")
	ClientModel checkTripId(@Param("tripID")String tripID);
	
	@Query("select cm from ClientModel cm where cm.driver=:driver and cm.statusID NOT IN (3,4)")
	List<ClientModel>  getDriverByClient (@Param("driver")Driver driver);

	@Query("select cm from ClientModel cm where cm.car=:car and cm.statusID not in (3,4)")
	List<ClientModel> getPendingtripsofCar(@Param("car")Car car);

	
	@Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID IN (5)")
	List <ClientModel> getCancelTripByDriver(@Param("tanentID")Long tenantId);
	
	@Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID IN (6)")
	List <ClientModel> getCancelTripByCustomer(@Param("tanentID")Long tenantId);

	@Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID IN (4)")
	List<ClientModel> getCanceledTrips(@Param("tanentID")Long tenantId);

	@Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID IN (3)")
	List<ClientModel> getEndTrips(@Param("tanentID")Long tenantId);

	@Query("select cm from ClientModel cm where cm.tanentID= :tanentID and cm.statusID IN (2)")
	List<ClientModel> getCurrentTrips(@Param("tanentID")Long tenantId);

	@Query(value = "SELECT * FROM clientmodel cml where (cml.pick_up_date_time BETWEEN STR_TO_DATE(:currentDate,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:nextWeekDate,'%d/%m/%Y %H:%i:%s')) and cml.status_id = 1 and cml.tanent_id=:tenantId", nativeQuery = true)
	List<ClientModel> getWeekTrips(@Param("currentDate") String currentDate,@Param("nextWeekDate") String nextWeekDate,@Param("tenantId") Long tenantId);

	@Query("select cm from ClientModel cm where cm.guest=:guest and cm.statusID=:status")
	ClientModel getTripsByStatusByGuest(@Param("guest") Guest guest,@Param("status")int status);

	@Query("select cm from ClientModel cm where cm.driver=:driver and cm.statusID NOT IN (4,5,6)")
	List<ClientModel>getwholeTrips(@Param("driver")Driver driver);

	@Query("select cm from ClientModel cm where cm.driver=:driver and STR_TO_DATE(cm.pickUpDateTime,'%Y-%m-%d')=STR_TO_DATE(now(),'%Y-%m-%d')")
	List<ClientModel> getTotalTrips(@Param("driver")Driver driver);

	@Query("select cm from ClientModel cm where cm.tanentID = :tanentID and cm.tripId = :tripId")
	ClientModel getByTanentID(@Param("tanentID") Long tanentID,@Param("tripId") String tripId);
}