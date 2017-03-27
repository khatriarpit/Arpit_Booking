package com.emxcel.dms.core.business.services.companymaster;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;

/**
 * @author emxcelsolution
 *
 */
public interface CompanyMasterService extends DMSEntityService<Long, CompanyMaster> {

    /**
	* Created By : Nittin Patel. Date: 26-01-2017 
	* Use: method for get list of company master
	* @return List<CompanyMaster>
	*/
	List<CompanyMaster> listOfCompanyMaster();
	
	/**
     * Created By : Nittin Patel. Date: 26-01-2017
	 * Use: Method for get company master by status and id
     * @param id **id**.
     * @param status **status**.
     * @return CompanyMaster
     */
	CompanyMaster getCompanyMasterByStatus(Long id, Integer status);

	/**
     * Created By : Nittin Patel. Date: 26-01-2017
	 * Use: method for get company name list
     * @param query **query**.
     * @return List<CompanyMaster>
     */
	List<CompanyMaster> getCompanyNameList(String query);


	/**
     * Created By : Darshan. Date: 15-03-2017
	 * Use: method for update CompanyMaster
     * @param companyMaster **companyMaster** status **status**.
     * @return List<CompanyMaster>
     */
	void updateCompanyMaster(CompanyMaster companyMaster, Integer status)throws ServiceException;


	/**
     * Created By : Darshan. Date: 15-03-2017
	 * Use: method for update CompanyMaster
     * @param companyMaster **companyMaster**,status **status**,tanentID **tanentID**.
     * @return List<CompanyMaster>
     */
	void saveCompanyMaster(CompanyMaster companyMaster, Long tanentID, Integer status)throws ServiceException;

	CompanyMaster getCompanyMasterByName(String companyName,Long tanentID);
	Long saveCompanyMaster(CompanyMaster companyMaster)throws ServiceException;
}
