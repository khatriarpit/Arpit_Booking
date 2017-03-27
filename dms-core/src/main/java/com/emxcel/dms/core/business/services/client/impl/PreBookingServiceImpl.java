package com.emxcel.dms.core.business.services.client.impl;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.client.PreBookingRepository;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.client.PreBooking;

@Service("preBookingService")
public class PreBookingServiceImpl extends DMSEntityServiceImpl<Long,PreBooking> implements PreBookingService{

	@Inject
	private PreBookingRepository preBookingRepository;
	
	@Inject
	public PreBookingServiceImpl(PreBookingRepository preBookingRepository) {
		super(preBookingRepository);
		this.preBookingRepository =  preBookingRepository;
	}

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<PreBooking> getPreBookingAndGuestModel(Long tanentID) {
		// TODO Auto-generated method stub
		return preBookingRepository.getPreBookingAndGuestModel(tanentID);
	}

	@Override
	public List<PreBooking> getPreBooking(Long tanentID) {
		// TODO Auto-generated method stub
		return preBookingRepository.getPreBooking(tanentID);
	}

}
