package com.emxcel.dms.core.business.services.trip;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.trip.TripRate;

public interface TripRateService extends DMSEntityService<Long, TripRate> {

	List<TripRate> getTripRateList();	
}