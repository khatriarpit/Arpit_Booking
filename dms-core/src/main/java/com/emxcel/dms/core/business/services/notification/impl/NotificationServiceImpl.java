package com.emxcel.dms.core.business.services.notification.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.notification.NotificationRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.model.common.Notification;

/**
 * Created by root on 1/3/17.
 */
@Service("notificationService")
public class NotificationServiceImpl extends DMSEntityServiceImpl<Long, Notification> implements NotificationService {

	private NotificationRepository notificationRepository;

	/*
	 * public void sendMessage(String message) {
	 * messagingTemplate.convertAndSend("/topic/greetings", message); }
	 */

	/**
	 * Created By - Naresh Banda Date 18-02-2017.
	 * 
	 * @param notificationRepository
	 */
	@Inject
	public NotificationServiceImpl(final NotificationRepository notificationRepository) {
		super(notificationRepository);
		this.notificationRepository = notificationRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.notification.NotificationService#
	 * getNotificationByUsers(boolean, java.lang.Long)
	 */
	@Override
	public List<Notification> getNotificationByUsers(boolean status, Long tenantID) {
		if(tenantID != null) {
			return notificationRepository.getNotificationByUsers(status, tenantID);
		} else {
			return notificationRepository.getNotificationByUsersForSuperAdmin(status);
		}
	}

	@Override
	public Notification saveAlertSchedulerNotification(String msg, Long tenantID) {
		Notification notification = new Notification();
		notification.setTanentID(tenantID);
		notification.setDescription(msg);
		notification.setStatus(false);
		try {
			return super.saveReturnEntity(notification);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return notification;
	}
}