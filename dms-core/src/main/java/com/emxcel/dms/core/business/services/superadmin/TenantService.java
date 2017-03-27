package com.emxcel.dms.core.business.services.superadmin;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.model.superadmin.Tenant;

public interface TenantService extends DMSEntityService<Long, Tenant> {

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-saveCompany
	 * @param company ***Company **
	 * @return  Long
	 * @throws ServiceException
	 */
	Long saveTenant(Tenant tenant) throws ServiceException;

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getCompanyServiceNo
	 * @param serviceNo **serviceNo**
	 * @param id **id**
	 * @return int
	 * @throws ServiceException
	 */
	int getCompanyServiceNo(String serviceNo, Long id) throws ServiceException;

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getCompanyPanNo
	 * @param panNo **panNo**
	 * @param id **id**
	 * @return int
	 * @throws ServiceException
	 */
	int getCompanyPanNo(String panNo, Long id) throws ServiceException;

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getCompanyEmailId
	 * @param emailId **email**
	 * @param id **id**
	 * @return int
	 * @throws ServiceException
	 */
	int getCompanyEmailId(String emailId, Long id) throws ServiceException;

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-getCompanyContactNo
	 * @param contactNo **contact no**
	 * @param id **id**
	 * @return int
	 * @throws ServiceException
	 */
	int getCompanyContactNo(Long contactNo, Long id) throws ServiceException;

	/**
	 * Created By-Johnson Chunara Date-26-01-2017.
	 * Used For-checkCompanyName
	 * @param companyname **companyName**
	 * @param id **id**
	 * @return id
	 * @throws ServiceException
	 */
	int checkCompanyName(String companyname, Long id) throws ServiceException;

	List<Tenant> getTenantByCityId(Long id);

	Tenant getTenantByCityTenant(String tenant,Long cityid);

	Long  savetenant(Tenant tenant,int status)throws Exception;

	void updatTenant(Tenant tenant,int status) throws  Exception;

	void deleteTenant(Tenant tenant,int statsus) throws  Exception;

	void updateTenantAsPerPackage(Tenant tenant,boolean emailFlag,int emailStatus,int status)throws Exception;

	int getCompanyTypeStatus(CompanyType companyType);


}