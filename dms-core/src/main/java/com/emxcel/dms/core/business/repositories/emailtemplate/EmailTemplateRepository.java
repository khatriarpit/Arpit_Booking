package com.emxcel.dms.core.business.repositories.emailtemplate;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.templates.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long>,EmailTemplateCustomRepository {
}
