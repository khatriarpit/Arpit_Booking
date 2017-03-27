package com.emxcel.dms.core.business.repositories.client;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.client.PreBooking;

public interface PreBookingRepository extends JpaRepository<PreBooking, Long>{
	
	@Query("select p from PreBooking p where p.tanentID = :tanentID")
	List<PreBooking> getPreBookingAndGuestModel(@Param("tanentID") Long tanentID);
	
	@Query("select p from PreBooking p where p.tanentID = :tanentID and p.statusID='0'")
	List<PreBooking> getPreBooking(@Param("tanentID") Long tanentID);

}
