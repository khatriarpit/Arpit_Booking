package com.emxcel.dms.core.business.services.restservice.geo;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.geo.GeoData;

public interface GeoDataService extends DMSEntityService<Long,GeoData> {
	
	 GeoData getgeoData(String tripId);
}
