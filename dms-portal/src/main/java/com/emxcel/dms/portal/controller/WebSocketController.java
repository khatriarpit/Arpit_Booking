package com.emxcel.dms.portal.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;

/**
 * @author emxcelsolutions
 */
@Controller
public class WebSocketController {

	private static final Logger logger = Logger.getLogger(WebSocketController.class);

	/**
	 * **We Inject it to use services of NotificationService **.
	 */
	@Inject
	private NotificationService notificationService;

	/**
	 * @param session
	 * @param status
	 * @return List<Notification>
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_NOTIFICATIONS_BY_USER, method = RequestMethod.POST)
	public final List<Notification> getNotificationByUser(final HttpSession session,
			@RequestParam(value = "status", required = false) String status) {
		User user = (User) session.getAttribute("user");
		boolean statusFlag = false;
		if (status.equals("true")) {
			statusFlag = true;
		}
		List<Notification> list = null;
		try {
			list = notificationService.getNotificationByUsers(statusFlag, user.getTanentID());
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * @param session
	 * @param status
	 * @return List<Notification>
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.UPDATE_NOTIFICATION_BY_ID, method = RequestMethod.POST)
	public boolean updateNotificationStatusByTenantID(final HttpSession session,
			@RequestParam(value = "id", required = false) String id) {
		try {
			if (id != null && !id.equals("")) {
				Notification notification = notificationService.getById(Long.valueOf(id));
				if (notification != null) {
					notification.setStatus(true);
					notificationService.update(notification);
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}