package com.emxcel.dms.core.business.services.user.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.user.UserGroupRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.user.UserGroupService;
import com.emxcel.dms.core.model.auth.Group;

@Service("UserGroupService")
public class UserGroupServiceImpl extends DMSEntityServiceImpl<Long, Group> implements UserGroupService {

	/**
	 * UserRepository.
	 */
	private UserGroupRepository userGroupRepository;

	@Inject
	public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
		super(userGroupRepository);
		this.userGroupRepository = userGroupRepository;
	}


}
