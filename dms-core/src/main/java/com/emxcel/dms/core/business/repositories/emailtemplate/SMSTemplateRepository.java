package com.emxcel.dms.core.business.repositories.emailtemplate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.templates.SMSTemplate;


public interface SMSTemplateRepository extends JpaRepository<SMSTemplate, Long>,SMSTemplateCustomRepository {
}
