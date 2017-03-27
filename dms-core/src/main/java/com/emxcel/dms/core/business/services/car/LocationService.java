package com.emxcel.dms.core.business.services.car;

import java.util.List;

import com.emxcel.dms.core.model.geo.GeoData;



public interface LocationService {
	GeoData getgeoData(String tripId);
	List<GeoData> getTripGeoData(String tripId);
}
