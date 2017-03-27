package com.emxcel.dms.core.business.services.superadmin;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.superadmin.PackageModel;

public interface PackageService extends DMSEntityService<Long, PackageModel> {

	/**
	 * @return List
	 */
	List<PackageModel> getlistByStatus() throws ServiceException;

	/**
	 * @param name
	 * @param id
	 * @return int
	 */
	int checkPackageName(String name, Long id) throws ServiceException;
	
	/**
	 * @param name
	 * @return PackageModel
	 */
	PackageModel checkPackageName(String name) throws ServiceException;

	/**
	 * @param id
	 * @return List
	 */
	List<PackageModel> getlistByStatus(Long id) throws ServiceException;

	/**
	 * @param name
	 * @param packageID 
	 * @return PackageModel
	 */
	PackageModel checkPackageNameWithOutStatusCheck(String name, Long packageID) throws ServiceException;

	void saveStatus(PackageModel  packageModel) throws Exception;

	void setStatusActive(PackageModel packageModel)throws Exception;

	void setStatusInactive(PackageModel packageModel)throws Exception;
}
