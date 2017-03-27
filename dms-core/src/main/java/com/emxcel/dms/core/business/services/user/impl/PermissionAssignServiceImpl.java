package com.emxcel.dms.core.business.services.user.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.user.PermissionAssignRepository;
import com.emxcel.dms.core.business.services.user.PermissionAssignService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.auth.PermissionAssign;

@Service("PermissionAssignService")
public class PermissionAssignServiceImpl extends DMSEntityServiceImpl<Long, PermissionAssign> implements PermissionAssignService{

	private PermissionAssignRepository permissionAssignRepository;
	
	@Inject
	public PermissionAssignServiceImpl(PermissionAssignRepository permissionAssignRepository) {
		super(permissionAssignRepository);
		this.permissionAssignRepository = permissionAssignRepository;
	}
}
