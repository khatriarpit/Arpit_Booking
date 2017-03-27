package com.emxcel.dms.core.business.services.user;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.user.LoginLogoutLogModel;

/**
 * @author emxcelsolution
 *
 */
public interface UserLogService extends
			DMSEntityService<Long, LoginLogoutLogModel> {

	List<LoginLogoutLogModel> getUsernameLastData(String username);
}
