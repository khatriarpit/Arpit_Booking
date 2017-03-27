package com.emxcel.dms.core.business.repositories.restrepository.geo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.geo.GeoData;

public interface GeoDataRepository extends JpaRepository<GeoData, Long> {

	@Query("select gd from GeoData gd WHERE gd.id =(SELECT MAX(gd.id) FROM GeoData) AND gd.tripID =?")
	GeoData getGeoData(String tripId);
	
	@Query("select gd from GeoData gd WHERE gd.tripID =?")
	List<GeoData> getTripGeoData(String tripId);
}
