package com.emxcel.dms.core.business.services.trip.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.trip.TripRateRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.trip.TripRateService;
import com.emxcel.dms.core.model.trip.TripRate;

@Service("tripRateService")
public class TripRateServiceImpl extends DMSEntityServiceImpl<Long, TripRate>implements TripRateService {

	private TripRateRepository tripRateRepository;

	@Inject
	public TripRateServiceImpl(TripRateRepository tripRateRepository) {
		super(tripRateRepository);
		this.tripRateRepository = tripRateRepository;
	}

	/* (non-Javadoc)
	 * @see com.emxcel.dms.core.business.services.trip.TripRateService#getTripRateList()
	 */
	@Override
	public List<TripRate> getTripRateList() {
		return tripRateRepository.getTripRateList();
	}
}