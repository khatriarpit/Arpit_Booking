package com.emxcel.dms.core.business.repositories.trip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.trip.TripStatus;
import org.springframework.data.repository.query.Param;

/**
 * @author emxcelsolution
 *
 */
public interface TripStatusRepository extends JpaRepository<TripStatus, Long>, TripStatusRepositoryCustom {

	@Query("select ts from TripStatus ts where ts.status in ('cancel','Cancel')")
	void updateTripIsClientCancel(String tripId, String string);

	@Query("select ts from TripStatus ts where ts.is_client=0 and tripID=? and ts.status in ('pending','Pending')")
	TripStatus getTripDetailsByPending(String tripId,String type);

	@Query("select ts from TripStatus ts where tripID=? and ts.status in ('end','End')")
	TripStatus getTripDetailsByEnd(String tripId,String type);
	
	@Query("select ts from TripStatus ts where ts.tripID=? and ts.tanentID=?")
	TripStatus getTripByTripID(String tripID , Long tanentID);

	@Query("select ts from TripStatus ts where ts.tripID=? and ts.status=? and ts.tanentID=?")
	TripStatus getTripStatusByTripID(String tripID, String status, Long tanentID);

	@Query("select count(ts.id) from TripStatus ts where ts.status in ('Live', 'Pending') and ts.tanentID=:tanentID")
	int listOfStatusWithTanentIDCountOfLiveAndPending(@Param("tanentID") Long tanentID);

	@Query("select ts from TripStatus ts where ts.carID=?")
	List<TripStatus> getByTripStatusByCar(Long id);
	
	@Modifying
	@Query("update TripStatus ts set ts.status=? where ts.tripID=?")
	void updateTripStatusByTripid(String tripStatus, String tripid);

}