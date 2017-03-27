package com.emxcel.dms.core.business.services.email.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.email.EmailLogRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.model.common.EmailLog;

/**
 * @author emxcelsolution
 *
 */
@Service("emailLogService")
public class EmailLogServiceImpl extends DMSEntityServiceImpl<Long, EmailLog> implements EmailLogService {

	/**
	 * driverRepository.
	 */
	private EmailLogRepository emailLogRepository;
	
	/**
	 * Created By - Naresh Banda Date 18-02-2017.
	 * 
	 * @param notificationRepository
	 */
	@Inject
	public EmailLogServiceImpl(final EmailLogRepository emailLogRepository) {
		super(emailLogRepository);
		this.emailLogRepository = emailLogRepository;
	}

	@Override
	public List<EmailLog> emailLogList(String status) {
		if (status != null) {
			boolean isSent = false;
			if(status.equals("0")) {
				isSent = true;
			}
			return emailLogRepository.findAll();
		} else {
			return emailLogRepository.findAll();
		}
	}

	@Override
	public EmailLog saveEmailLog(EmailLog emailLog) {
		return emailLogRepository.save(emailLog);
	}

	@Override
	public EmailLog getEmailLog(Long emailLogID) {
		return emailLogRepository.findOne(emailLogID);
	}
}
