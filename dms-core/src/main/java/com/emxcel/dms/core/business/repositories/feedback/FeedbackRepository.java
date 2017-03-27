package com.emxcel.dms.core.business.repositories.feedback;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long>, FeedbackRepositoryCustom {

	/*
	 * @Query("select f from Feedback f where f.tripID in(select tripID from TripStatus where status in('End'))"
	 * ) List<Feedback> getFeedbackList();
	 */
	@Query("select f from Feedback f where f.tanentID=:tenantID")
	List<Feedback> getFeedbackList(@Param("tenantID") Long tanentID);

	@Query("select f from Feedback f where f.tripID=? and f.tanentID=?")
	Feedback getFeedbackByTripID(String tripID, Long tanentID);

	@Query("select f from Feedback f where f.tripID=?")
	Feedback getFeedbackByTripID(String tripID);

	@Query("select f from Feedback f where f.tanentID=:tenantID")
	List<Feedback> driverFeedBackByTanent(@Param("tenantID") Long tanentID);

	@Query("select f from Feedback f where f.tanentID=:tenantID")
	List<Feedback> carFeedBackByTanent(@Param("tenantID") Long tanentID);

	/*@Query("select f from Feedback f where (f.dropDateTime BETWEEN STR_TO_DATE(:startDate,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:endDate,'%d/%m/%Y %H:%i:%s')")
	List<Feedback> getFeedbackList(@Param("startDate") String startDate, @Param("endDate") String endDate);*/

	@Query("select f from Feedback f where f.car=:car and f.pickUpDateTime>= STR_TO_DATE(:startDate,'%d/%m/%Y %H:%i:%s') and f.dropDateTime<=STR_TO_DATE(:endDate,'%d/%m/%Y %H:%i:%s')")
	List<Feedback> getCarNoList(@Param("startDate") String startDate, @Param("endDate") String endDate,
			@Param("car") Car car);

	@Query("select f from Feedback f where f.driver=:driver and f.pickUpDateTime >= STR_TO_DATE(:startDate,'%d/%m/%Y %H:%i:%s') and f.dropDateTime <= STR_TO_DATE(:endDate,'%d/%m/%Y %H:%i:%s')")
	List<Feedback> getDriverList(String startDate, String endDate, Driver driver);
}