package com.emxcel.dms.core.business.services.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.user.UserLogRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.user.UserLogService;
import com.emxcel.dms.core.model.user.LoginLogoutLogModel;

/**
 * @author emxcelsolution
 *
 */
@Service("userLogService")
public class UserLogServiceImpl extends DMSEntityServiceImpl<Long, LoginLogoutLogModel> implements UserLogService {

	/**
	 * UserRepository.
	 */
	private UserLogRepository userLogRepository;

	@Inject
	public UserLogServiceImpl(UserLogRepository userLogRepository) {
		super(userLogRepository);
		this.userLogRepository = userLogRepository;
	}

	@Override
	public List<LoginLogoutLogModel> getUsernameLastData(String username) {
		return userLogRepository.getUsernameLastData(username);
	}
}