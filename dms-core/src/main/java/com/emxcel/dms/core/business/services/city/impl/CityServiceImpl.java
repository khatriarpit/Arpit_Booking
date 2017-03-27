package com.emxcel.dms.core.business.services.city.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.city.CityRepository;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.city.City;

@Service("cityService")
public class CityServiceImpl extends DMSEntityServiceImpl<Long, City> implements CityService {

	private CityRepository cityRepository;
	
	
	@Inject
	public CityServiceImpl(CityRepository cityRepository) {
		super(cityRepository);
		this.cityRepository = cityRepository;
	}

	@Override
	public List<City> listCity(Long stateID) {
		return cityRepository.listCity(stateID);
	}

	@Override
	public City getCityByCityName(String city) {
		return cityRepository.getCityByCityName(city);
	}

}
