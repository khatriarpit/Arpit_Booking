package com.emxcel.dms.core.business.modules.email;

import com.emxcel.dms.core.model.common.EmailLog;

public interface HtmlEmailSender {

	public void send(final Email email, EmailLog emailLog) throws Exception;

	public void setEmailConfig(EmailConfig emailConfig);
}