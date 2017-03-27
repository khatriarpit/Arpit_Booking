package com.emxcel.dms.core.business.services.notification;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.common.Notification;

public interface NotificationService extends DMSEntityService<Long, Notification> {

	List<Notification> getNotificationByUsers(boolean status, Long id) throws ServiceException;
	
	Notification saveAlertSchedulerNotification(String msg, Long tenantID) throws ServiceException;
}