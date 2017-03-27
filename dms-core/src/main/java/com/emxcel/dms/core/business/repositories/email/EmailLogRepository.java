package com.emxcel.dms.core.business.repositories.email;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.common.EmailLog;

/**
 * @author Emxcel Solutions
 *
 */
public interface EmailLogRepository extends JpaRepository<EmailLog, Long> {

}