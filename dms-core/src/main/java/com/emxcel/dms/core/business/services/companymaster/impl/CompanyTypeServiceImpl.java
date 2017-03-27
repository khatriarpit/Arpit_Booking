package com.emxcel.dms.core.business.services.companymaster.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.companymaster.CompanyTypeRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.companymaster.CompanyTypeService;
import com.emxcel.dms.core.model.companymaster.CompanyType;
@Service("companyTypeService")
public class CompanyTypeServiceImpl extends DMSEntityServiceImpl<Long,CompanyType> implements CompanyTypeService{

	private CompanyTypeRepository companyTypeRepository;
	
	@Inject
	public CompanyTypeServiceImpl(final CompanyTypeRepository companyTypeRepository) {
		super(companyTypeRepository);
		this.companyTypeRepository = companyTypeRepository;
	}

	@Override
	public List<CompanyType> checkcompanytype(String companyType,Long id) {
		// TODO Auto-generated method stub
		if(id != null){
			return companyTypeRepository.checkcompanytype(companyType,id);
		}

		return companyTypeRepository.checkcompanytypeby(companyType);

	}

	@Override
	public List<CompanyType> getcompanyListByStatus(boolean b) {
		return companyTypeRepository.getcompanyListByStatus(b);
	}

	@Override
	public void updatecompanyType(CompanyType companyType, boolean status) {
		companyType.setCompanyStatus(status);
	}

	@Override
	public void saveCompanytypeWithStatus(CompanyType companyType) throws ServiceException {
		companyType.setCompanyStatus(true);
		super.save(companyType);
	}


}
