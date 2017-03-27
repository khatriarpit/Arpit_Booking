package com.emxcel.dms.core.business.services.auth.impl;

import javax.inject.Inject;

import com.emxcel.dms.core.business.repositories.auth.UserPackageRepository;
import com.emxcel.dms.core.business.services.auth.UserPackageService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.webservice.UserPackage;
import org.springframework.stereotype.Service;

@Service("userPackageService")
public class UserPackageServiceImpl extends DMSEntityServiceImpl<Long, UserPackage> implements UserPackageService {


	private UserPackageRepository userPackageRepository;
	
	@Inject
	public UserPackageServiceImpl(UserPackageRepository userPackageRepository) {
		super(userPackageRepository);
		this.userPackageRepository=userPackageRepository;
	
	}

}
