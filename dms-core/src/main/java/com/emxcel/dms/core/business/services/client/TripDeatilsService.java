package com.emxcel.dms.core.business.services.client;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.client.TripDetails;

public interface TripDeatilsService extends DMSEntityService<Long,TripDetails> {

	TripDetails getTripDetailsByTripId(String tripID);

}
