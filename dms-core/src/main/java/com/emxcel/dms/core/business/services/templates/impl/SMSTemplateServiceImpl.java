package com.emxcel.dms.core.business.services.templates.impl;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.emailtemplate.EmailTemplateRepository;
import com.emxcel.dms.core.business.repositories.emailtemplate.SMSTemplateRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.templates.SMSTemplateService;
import com.emxcel.dms.core.model.templates.SMSTemplate;

@Service("SMSTemplateService")
public class SMSTemplateServiceImpl extends DMSEntityServiceImpl<Long, SMSTemplate> implements SMSTemplateService {

	private SMSTemplateRepository smsTemplateRepository; 
	
	/**
	 * SMSTemplateRepository.
	 */
	@Inject
	public SMSTemplateServiceImpl(SMSTemplateRepository smsTemplateRepository) {
		super(smsTemplateRepository);
		this.smsTemplateRepository = smsTemplateRepository;
	}
}
