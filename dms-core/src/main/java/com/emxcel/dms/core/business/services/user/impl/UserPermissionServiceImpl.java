package com.emxcel.dms.core.business.services.user.impl;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.user.UserPermissionRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.user.UserPermissionService;
import com.emxcel.dms.core.model.auth.Permission;

@Service("userPermissionService")
public class UserPermissionServiceImpl extends DMSEntityServiceImpl<Long, Permission> implements UserPermissionService{

	
	
	private UserPermissionRepository userPermissionRepository;
	
	@Inject	public UserPermissionServiceImpl(UserPermissionRepository userPermissionRepository) {
		super(userPermissionRepository);
		this.userPermissionRepository=userPermissionRepository;
		// TODO Auto-generated constructor stub
	}

	
	
	
	

}
