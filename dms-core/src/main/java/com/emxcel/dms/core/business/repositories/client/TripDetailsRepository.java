package com.emxcel.dms.core.business.repositories.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.client.TripDetails;

public interface TripDetailsRepository extends JpaRepository<TripDetails, Long> {

	@Query("select td from TripDetails td where td.tripId=?")
	TripDetails getTripDetailsByTripId(String tripID);

}
