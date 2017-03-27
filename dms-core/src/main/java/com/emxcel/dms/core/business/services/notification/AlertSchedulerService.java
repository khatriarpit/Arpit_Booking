package com.emxcel.dms.core.business.services.notification;

import java.util.Date;
import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.common.AlertScheduler;

public interface AlertSchedulerService extends DMSEntityService<Long, AlertScheduler> {

	/**
	 * @param status
	 * @param pickUpDate 
	 * @return
	 */
	List<AlertScheduler> getAlertSchedulerList(boolean status, Date pickUpDate);

	/**
	 * @param alertScheduler
	 * @return
	 */
	AlertScheduler saveAlertSchedulerReturnEntity(Date pickUpTime, String tripID, boolean status, int schedulerType);

	/**
	 * @param status
	 * @param alertScheduler
	 */
	void updateAlertSchedulerReturnEntity(boolean status, AlertScheduler alertScheduler);
}