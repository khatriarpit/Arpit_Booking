package com.emxcel.dms.core.business.services.car.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.business.repositories.car.CarRepository;
import com.emxcel.dms.core.business.repositories.restrepository.geo.GeoDataRepository;
import com.emxcel.dms.core.business.services.car.LocationService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.geo.GeoData;

@Service("LocationService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class LocationServiceImpl extends DMSEntityServiceImpl<Long,GeoData> implements LocationService{

	private GeoDataRepository geoDataRepository;
	@Inject
	public LocationServiceImpl(GeoDataRepository geoDataRepository) {
		super(geoDataRepository);
		this.geoDataRepository = geoDataRepository;
	}
	

	@Override
	public GeoData getgeoData(String tripId) {
		
		return geoDataRepository.getGeoData(tripId);
	}
	@Override
	public List<GeoData> getTripGeoData(String tripId) {
		
		return geoDataRepository.getTripGeoData(tripId);
	}

}
