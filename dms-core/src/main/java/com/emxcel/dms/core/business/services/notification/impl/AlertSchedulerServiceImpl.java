package com.emxcel.dms.core.business.services.notification.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.notification.AlertSchedulerRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.notification.AlertSchedulerService;
import com.emxcel.dms.core.model.common.AlertScheduler;

/**
 * Created by root on 1/3/17.
 */
@Service("alertSchedulerService")
public class AlertSchedulerServiceImpl extends DMSEntityServiceImpl<Long, AlertScheduler> implements AlertSchedulerService {

	private AlertSchedulerRepository alertSchedulerRepository;
	
	/**
	 * Created By - Naresh Banda Date 18-02-2017.
	 * 
	 * @param notificationRepository
	 */
	@Inject
	public AlertSchedulerServiceImpl(final AlertSchedulerRepository alertSchedulerRepository) {
		super(alertSchedulerRepository);
		this.alertSchedulerRepository = alertSchedulerRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.notification.AlertSchedulerService#
	 * getAlertSchedulerList(boolean)
	 */
	@Override
	public List<AlertScheduler> getAlertSchedulerList(boolean status, Date pickUpDate) {
		return alertSchedulerRepository.getAlertSchedulerList(status, pickUpDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.notification.AlertSchedulerService#
	 * saveAlertSchedulerReturnEntity(com.emxcel.dms.core.model.common.
	 * AlertScheduler)
	 */
	@Override
	public AlertScheduler saveAlertSchedulerReturnEntity(Date pickUpTime, String tripID, boolean status, int schedulerType) {
		AlertScheduler alertScheduler = new AlertScheduler();
		alertScheduler.setPickUpTime(pickUpTime);
		alertScheduler.setTripID(tripID);
		alertScheduler.setStatus(status);
		alertScheduler.setSchedulerType(schedulerType);
		return alertSchedulerRepository.save(alertScheduler);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.notification.AlertSchedulerService#
	 * updateAlertSchedulerReturnEntity(boolean,
	 * com.emxcel.dms.core.model.common.AlertScheduler)
	 */
	@Override
	public void updateAlertSchedulerReturnEntity(boolean status, AlertScheduler alertScheduler) {
		alertScheduler.setStatus(status);
		alertSchedulerRepository.save(alertScheduler);
	}
}