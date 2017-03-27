package com.emxcel.dms.core.business.repositories.notification;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.common.AlertScheduler;

public interface AlertSchedulerRepository extends JpaRepository<AlertScheduler, Long> {

	@Query("select n from AlertScheduler n where n.status=:status and (n.pickUpTime >= :pickUpDate) order by n.pickUpTime desc")
	List<AlertScheduler> getAlertSchedulerList(@Param("status") boolean status, @Param("pickUpDate") Date pickUpDate);
}