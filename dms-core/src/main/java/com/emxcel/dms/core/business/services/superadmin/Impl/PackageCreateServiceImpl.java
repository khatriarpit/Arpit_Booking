package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.superadmin.TenantPackageRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.model.superadmin.TenantPackage;

@Service("packagecreateservice")
public class PackageCreateServiceImpl extends DMSEntityServiceImpl<Long, TenantPackage>
		implements PackageCreateService {

	private TenantPackageRepository tenantPackageRepository;
	

	@Inject
	public PackageCreateServiceImpl(TenantPackageRepository tenantPackageRepository) {
		super(tenantPackageRepository);
		this.tenantPackageRepository = tenantPackageRepository;
	}

	@Override
	public List<TenantPackage> getListByTenantID(Long tanentID, String status, Long packageTenantID) {
		if (status != null && !status.equals("")) {
			if (packageTenantID != null) {
				return tenantPackageRepository.getListByTenantID(tanentID, Integer.parseInt(status), packageTenantID);
			} else {
				return tenantPackageRepository.getListByTenantID(tanentID, Integer.parseInt(status));
			}
		}
		return null;
	}

	@Override
	public List<TenantPackage> getByTenantIDLimitOneWithOutStatus(Long tanentID) {
		return tenantPackageRepository.getByTenantIDLimitOneWithOutStatus(tanentID);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * getPendingVerificationByTanentID()
	 */
	@Override
	public List<TenantPackage> getPendingVerificationByTanentID() {
		return tenantPackageRepository.getPendingVerificationByTanentID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * getByTenantIDLimitOne(java.lang.Long)
	 */
	@Override
	public TenantPackage getByTenantIDLimitOne(Long tanentID) {
		return tenantPackageRepository.getByTenantIDLimitOne(tanentID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * checkPackageName(java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
	public int checkPackageName(String name, Long id, Long tanentID) {
		if (id != null) {
			return tenantPackageRepository.checkPackageName(name, id, tanentID);
		} else {
			return tenantPackageRepository.checkPackageNameNoWithOutCompanyId(name, tanentID);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * getListByNotInThisTenantID(java.lang.Long)
	 */
	@Override
	public List<TenantPackage> getListByNotInThisTenantID(Long tanentID) {
		return tenantPackageRepository.getListByNotInThisTenantID(tanentID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * checkPackageName(java.lang.String)
	 */
	@Override
	public int checkPackageName(String name) {
		return tenantPackageRepository.checkPackageName(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.superadmin.PackageCreateService#
	 * checkPackageCount(java.lang.Long)
	 */
	@Override
	public int checkPackageCount(Long tanentID) {
		return tenantPackageRepository.checkPackageCount(tanentID);
	}
	
	@Override
	public List<TenantPackage> getTenantPackageForAdminAndUser(Long tanentID, String status, Long packageID) {
		if(packageID != null) {
			return tenantPackageRepository.getTenantPackageForAdminAndUser(tanentID, Integer.parseInt(status), packageID);
		} else {
			return tenantPackageRepository.getTenantPackageForAdminAndUser(tanentID, Integer.parseInt(status));
		}
	}

	@Override
	public List<TenantPackage> getTenantPackageWithOutCountThisPackageID(Long tanentID, Long tenantPackageID) {
		return tenantPackageRepository.getTenantPackageWithOutCountThisPackageID(tanentID, tenantPackageID);
	}

	@Override
	public int checkPackageNameOnlyFlexibleType(String name, Long id, Long tanentID, Integer packageType) {
		if (id != null) {
			return tenantPackageRepository.checkPackageNameOnlyFlexibleType(name, id, tanentID, packageType);
		} else {
			return tenantPackageRepository.checkPackageNameOnlyFlexibleType(name, tanentID, packageType);
		}
	}
	@Override
	public List<TenantPackage> getTenantPackageValidationFrom(Long id) {
		if(id != null) {
			return tenantPackageRepository.getTenantPackageValidationFrom(id);
		} else {
			return tenantPackageRepository.getTenantPackageValidationFrom();
		}
	}
	@Override
	public void deletePendingVerification(TenantPackage tenantPackage) {
		try {
			tenantPackage.setCarList(null);
			tenantPackage.setDriverList(null);
			tenantPackage.setEmailFlag(false);
			tenantPackage.setEmailStatus(false);
			super.update(tenantPackage);
		}
		catch (Exception e){
			e.getCause();
		}
	}
	@Override
	public Long saveTenantPackage (TenantPackage tenantPackage, String packageName, Date fromDate,Date toDate,boolean defaultFlag, String packageType){
		tenantPackage.setName(packageName);
		if(packageType != null) {
			tenantPackage.setPackageType(Long.valueOf(packageType));
		}
		tenantPackage.setFromDate(fromDate);
		tenantPackage.setToDate(toDate);
		tenantPackage.setStatus(0);
		tenantPackage.setApprovedFlag(false);
		tenantPackage.setEmailStatus(true);
		tenantPackage.setDefaultFlag(defaultFlag);
		TenantPackage tenantPackage1=tenantPackageRepository.saveAndFlush(tenantPackage);
		return tenantPackage1.getId();
	}

	@Override
	public void updateTenantPackage  (TenantPackage tenantPackage,int status)throws Exception{
		tenantPackage.setStatus(status);
		super.update(tenantPackage);
	}

	@Override
	public List<TenantPackage> getTenanatPackageInactiveFrom(Integer status,boolean approved) throws ServiceException {
		return tenantPackageRepository.getTenanatPackageInactiveFrom(status,approved);
	}

	@Override
	public void activeTenantPackage(TenantPackage tenantPackage, Integer status) throws Exception {
       tenantPackage.setStatus(status);
       tenantPackageRepository.save(tenantPackage);
       
	}


}