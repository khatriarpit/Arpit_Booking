package com.emxcel.dms.core.business.repositories.notification;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.common.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	@Query("select n from Notification n where n.status=:status and n.tanentID=:tenantID")
	List<Notification> getNotificationByUsers(@Param("status") boolean status, @Param("tenantID") Long tenantID);

	@Query("select n from Notification n where n.status=:status and n.tanentID IS NULL")
	List<Notification> getNotificationByUsersForSuperAdmin(@Param("status") boolean status);
}