package com.emxcel.dms.core.business.services.city;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.city.City;

public interface CityService extends DMSEntityService<Long, City> {

	public List<City> listCity(Long stateID);

	City getCityByCityName(String city);
	

}