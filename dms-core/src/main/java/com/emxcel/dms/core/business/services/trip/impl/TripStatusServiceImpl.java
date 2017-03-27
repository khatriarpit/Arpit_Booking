package com.emxcel.dms.core.business.services.trip.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.trip.TripStatusRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.trip.TripStatusService;
import com.emxcel.dms.core.model.trip.TripStatus;

@Service("tripStatusService")
public class TripStatusServiceImpl extends DMSEntityServiceImpl<Long, TripStatus> implements TripStatusService {

	private TripStatusRepository tripStatusRepository;

	@Inject
	public TripStatusServiceImpl(TripStatusRepository tripStatusRepository) {
		super(tripStatusRepository);
		this.tripStatusRepository = tripStatusRepository;
	}

	@Override
	public void save(TripStatus entity) throws ServiceException {
		super.save(entity);
		throw new ServiceException("Just testing Transaction flow!!!!");
	}

	@Override
	public void updateTripIsClientCancel(String tripId, String string) {
		tripStatusRepository.updateTripIsClientCancel(tripId, string);
	}

	@Override
	public TripStatus getTripDetailsByPending(String tripId, String type) {
		return tripStatusRepository.getTripDetailsByPending(tripId, type);
	}

	@Override
	public TripStatus getTripDetailsByEnd(String tripId, String type) {
		return tripStatusRepository.getTripDetailsByEnd(tripId, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.tripstatus.TripStatusService#
	 * getTripStatusByTripID(java.lang.String, java.lang.String)
	 */
	@Override
	public TripStatus getTripStatusByTripID(String tripID, String status, Long tanentID) {
		if ((tripID != null && status == null) && tanentID != null) {
			return tripStatusRepository.getTripByTripID(tripID, tanentID);
		} else if (status != null && status.equals("start-trip")) {
			return tripStatusRepository.getTripStatusByTripID(tripID, Constants.TRIP_STATUS_LIVE, tanentID);
		} else if (status != null && status.equals("cancel")) {
			return tripStatusRepository.getTripStatusByTripID(tripID, Constants.TRIP_STATUS_CANCEL, tanentID);
		} else if (status != null && status.equals("end")) {
			return tripStatusRepository.getTripStatusByTripID(tripID, Constants.TRIP_STATUS_END, tanentID);
		}
		return null;
	}

	@Override
	public int listOfStatusWithTanentIDCountOfLiveAndPending(Long tanentID) {
		return tripStatusRepository.listOfStatusWithTanentIDCountOfLiveAndPending(tanentID);
	}

	@Override
	public List<TripStatus> getByTripStatusByCar(Long id) {
		// TODO Auto-generated method stub
		return tripStatusRepository.getByTripStatusByCar(id);
	}

	@Override
	public void updateTripStatusByTripid(String tripStatus, String tripId) {
		tripStatusRepository.updateTripStatusByTripid(tripStatus, tripId);

	}
}