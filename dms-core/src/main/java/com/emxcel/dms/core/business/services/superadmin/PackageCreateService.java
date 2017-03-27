package com.emxcel.dms.core.business.services.superadmin;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.superadmin.TenantPackage;

import java.sql.Date;
import java.util.List;

/**
 * Created by swami on 1/4/17.
 */
public interface PackageCreateService extends DMSEntityService<Long, TenantPackage> {

	/**
	 * @param tanentID
	 * @param packageTenantID
	 * @return List<TenantPackage>
	 */
	List<TenantPackage> getListByTenantID(Long tanentID, String status, Long packageTenantID) throws ServiceException;

	/**
	 * @return List<TenantPackage>
	 */
	List<TenantPackage> getPendingVerificationByTanentID() throws ServiceException;

	/**
	 * @param tanentID
	 * @return
	 */
	TenantPackage getByTenantIDLimitOne(Long tanentID) throws ServiceException;

	/**
	 * @param tanentID
	 * @return
	 */
	List<TenantPackage> getByTenantIDLimitOneWithOutStatus(Long tanentID) throws ServiceException;

	/**
	 * @param name
	 * @param id
	 * @param tanentID
	 * @return
	 */
	int checkPackageName(String name, Long id, Long tanentID) throws ServiceException;

	/**
	 * @param tanentID
	 * @return
	 */
	List<TenantPackage> getListByNotInThisTenantID(Long tanentID) throws ServiceException;

	/**
	 * @param name
	 * @return
	 */
	int checkPackageName(String name) throws ServiceException;

	/**
	 * @param tanentID
	 * @return
	 */
	int checkPackageCount(Long tanentID) throws ServiceException;

	/**
	 * @param tanentID
	 * @param status
	 * @param packageID
	 * @return
	 */
	List<TenantPackage> getTenantPackageForAdminAndUser(Long tanentID, String status, Long packageID) throws ServiceException;

	/**
	 * @param valueOf
	 * @param string
	 * @param valueOf2
	 * @return
	 */
	List<TenantPackage> getTenantPackageWithOutCountThisPackageID(Long tenantID, Long tenantPackageID) throws ServiceException;

	int checkPackageNameOnlyFlexibleType(String name, Long id, Long tanentID, Integer packageType) throws ServiceException;

	List<TenantPackage> getTenantPackageValidationFrom(Long id) throws ServiceException;

	List<TenantPackage> getTenanatPackageInactiveFrom(Integer status,boolean approved)throws ServiceException;

	void deletePendingVerification(TenantPackage tenantPackage);

	Long saveTenantPackage (TenantPackage tenantPackage,String packageName,Date fromDate,Date toDate,boolean defaultFlag, String packageType);

	void updateTenantPackage (TenantPackage tenantPackage,int status) throws Exception;
	
	void activeTenantPackage(TenantPackage tenantPackage, Integer status) throws Exception;
}
