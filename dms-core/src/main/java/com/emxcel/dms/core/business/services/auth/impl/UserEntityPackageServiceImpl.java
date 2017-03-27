package com.emxcel.dms.core.business.services.auth.impl;

import javax.inject.Inject;

import com.emxcel.dms.core.business.repositories.auth.UserEntityPackageRepository;
import com.emxcel.dms.core.business.services.auth.UserEntityPackageService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.webservice.UserEntityPackage;
import org.springframework.stereotype.Service;

@Service("userEntityPackageService")
public class UserEntityPackageServiceImpl extends DMSEntityServiceImpl<Long, UserEntityPackage> implements UserEntityPackageService{


	private UserEntityPackageRepository userEntityPackageRepository;
	
	@Inject
	public UserEntityPackageServiceImpl(UserEntityPackageRepository userEntityPackageRepository) {
		super(userEntityPackageRepository);
		this.userEntityPackageRepository=userEntityPackageRepository;
	}

}
