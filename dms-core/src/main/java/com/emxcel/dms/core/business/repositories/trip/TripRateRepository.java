package com.emxcel.dms.core.business.repositories.trip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.trip.TripRate;

public interface TripRateRepository extends JpaRepository<TripRate, Long>, TripRateRepositoryCustom {

	@Query("select tr from TripRate tr")
	List<TripRate> getTripRateList();
}