package com.emxcel.dms.core.business.services.client.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.client.TripCancelHistoryRepository;
import com.emxcel.dms.core.business.services.client.TripCancelHistoryService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.client.TripCancelHistory;

@Service("tripCancelHistoryService")
public class TripCancelHistoryServiceImpl extends DMSEntityServiceImpl<Long, TripCancelHistory> implements TripCancelHistoryService{



	private TripCancelHistoryRepository tripCancelHistoryRepository;
	
	@Inject
	public TripCancelHistoryServiceImpl(TripCancelHistoryRepository tripCancelHistoryRepository) {
		super(tripCancelHistoryRepository);
		this.tripCancelHistoryRepository=tripCancelHistoryRepository;
		// TODO Auto-generated constructor stub
	}
	
	
	

}
