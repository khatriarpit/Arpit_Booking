package com.emxcel.dms.core.business.services.client.impl;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.client.TripDetailsRepository;
import com.emxcel.dms.core.business.services.client.TripDeatilsService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.client.TripDetails;
@Service("tripDetailsService")
public class TripDetailsServiceImpl extends DMSEntityServiceImpl<Long, TripDetails> implements TripDeatilsService {

	
	private TripDetailsRepository tripDetailsRepository;
	
	@Inject
	public TripDetailsServiceImpl(TripDetailsRepository tripDetailsRepository) {
		super(tripDetailsRepository);
		this.tripDetailsRepository=tripDetailsRepository;
	}

	@Override
	public TripDetails getTripDetailsByTripId(String tripID) {
		return tripDetailsRepository.getTripDetailsByTripId(tripID);
	}

}
