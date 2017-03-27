package com.emxcel.dms.core.business.services.client;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.client.PreBooking;

public interface PreBookingService extends DMSEntityService<Long,PreBooking>{

	List<PreBooking> getPreBookingAndGuestModel(Long tanentID);
	
	List<PreBooking> getPreBooking(Long tanentID);


}
