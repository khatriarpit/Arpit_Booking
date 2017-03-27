package com.emxcel.dms.core.business.services.restservice.geo.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.restrepository.geo.GeoDataRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.restservice.geo.GeoDataService;
import com.emxcel.dms.core.model.geo.GeoData;

@Service("geoDataService")
public class GeoDataServiceImpl extends DMSEntityServiceImpl<Long, GeoData> implements GeoDataService {

	private GeoDataRepository geoDataRepository;

	@Inject
	public GeoDataServiceImpl(GeoDataRepository geoDataRepository) {
		super(geoDataRepository);
		this.geoDataRepository = geoDataRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.service.geo.GeoDataService#getgeoData(java.
	 * lang.String)
	 */
	@Override
	public GeoData getgeoData(String tripId) {
		return geoDataRepository.getGeoData(tripId);
	}
}