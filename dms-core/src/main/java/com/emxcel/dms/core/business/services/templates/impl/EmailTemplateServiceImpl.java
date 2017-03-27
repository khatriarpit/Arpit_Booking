package com.emxcel.dms.core.business.services.templates.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.emailtemplate.EmailTemplateRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.templates.EmailTemplateService;
import com.emxcel.dms.core.model.templates.EmailTemplate;

@Service("EmailTemplateService")
public class EmailTemplateServiceImpl extends DMSEntityServiceImpl<Long, EmailTemplate> implements EmailTemplateService {

	/**
	 * EmailTemplateRepository.
	 */
	private EmailTemplateRepository emailTemplateRepository; 

	@Inject
	public EmailTemplateServiceImpl(EmailTemplateRepository emailTemplateRepository) {
		super(emailTemplateRepository);
		this.emailTemplateRepository = emailTemplateRepository;
	}
}