package com.emxcel.dms.core.business.services.trip;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.trip.TripStatus;

public interface TripStatusService extends DMSEntityService<Long, TripStatus> {

	void updateTripIsClientCancel(String tripId, String string);

	TripStatus getTripDetailsByPending(String tripId, String type);

	TripStatus getTripDetailsByEnd(String string, String type);

	TripStatus getTripStatusByTripID(String tripID, String status,Long tanentID);

	int listOfStatusWithTanentIDCountOfLiveAndPending(Long tanentID);

	List<TripStatus> getByTripStatusByCar(Long id);

	void updateTripStatusByTripid(String tripStatus, String tripId);
}
