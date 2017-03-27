package com.emxcel.dms.core.business.services.companymaster;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.business.exception.ServiceException;

public interface CompanyTypeService extends DMSEntityService<Long, CompanyType>{

	List<CompanyType> checkcompanytype(String companyType,Long id);

	List<CompanyType> getcompanyListByStatus(boolean b);

	void updatecompanyType(CompanyType companyType, boolean status);

	void saveCompanytypeWithStatus(CompanyType companyType)throws ServiceException ;

}
