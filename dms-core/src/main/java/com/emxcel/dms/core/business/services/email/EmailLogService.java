package com.emxcel.dms.core.business.services.email;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.common.EmailLog;

public interface EmailLogService extends DMSEntityService<Long, EmailLog>  {

	List<EmailLog> emailLogList(String status);

	EmailLog saveEmailLog(EmailLog emailLog);

	EmailLog getEmailLog(Long emailLogID);
}
