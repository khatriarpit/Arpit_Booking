package com.emxcel.dms.core.business.services.companymaster.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.companymaster.CompanyMasterRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.companymaster.CompanyMasterService;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;

/**
 * @author emxcelsolution
 *
 */
@Service("companyMasterService")
public class CompanyMasterServiceImpl extends 
DMSEntityServiceImpl<Long, CompanyMaster>
		implements CompanyMasterService {

	/**
	 * Inject for companyMasterRepository.
	 */
	@Inject
	private CompanyMasterRepository companyMasterRepository;

	/**
	 * @param companyMasterRepository **companyMasterRepository**.
	 */
	@Inject
	public CompanyMasterServiceImpl(final CompanyMasterRepository 
					companyMasterRepository) {
		super(companyMasterRepository);
		this.companyMasterRepository = companyMasterRepository;
	}

	/* Created By : Nittin Patel. Date: 26-01-2017 
	 * Use: method for get list of company master
	 * @see com.emxcel.dms.core.business.services.companymaster.
	 * 			CompanyMasterService#listOfCompanyMaster()
	 */
	@Override
	public List<CompanyMaster> listOfCompanyMaster() {
		return companyMasterRepository.listOfCompanyMaster();
	}

	/* Created By : Nittin Patel. Date: 26-01-2017
	 * Use: method for get company name list
	 * @see com.emxcel.dms.core.business.services.companymaster.
	 * 			CompanyMasterService#getCompanyNameList(java.lang.String)
	 */
	@Override
	public List<CompanyMaster> getCompanyNameList(final String query) {
		return companyMasterRepository.getCompanyNameList(query);
	}

	/* Created By : Nittin Patel. Date: 26-01-2017
	 * Use: Method for get company master by status and id
	 * @see com.emxcel.dms.core.business.services.companymaster.
	 * 		CompanyMasterService#getCompanyMasterByStatus(
	 * 			java.lang.Long, java.lang.Integer)
	 */
	@Override
	public CompanyMaster getCompanyMasterByStatus(final Long id, final Integer status) {
		return companyMasterRepository.getCompanyMasterByStatus(id, status);
	}
	/* Created By : Darshan Limbachiya. Date: 15-03-2017
	 * Use: Method for set company master by status
	 * @see com.emxcel.dms.core.business.services.companymaster.
	 * 		CompanyMasterService#getCompanyMasterByStatus(
	 * 			java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void updateCompanyMaster(CompanyMaster companyMaster, Integer status) throws ServiceException {
		companyMaster.setStatus(status);
		super.update(companyMaster);
	}

	/* Created By : Darshan Limbachiya. Date: 15-03-2017
	 * Use: Method for save company master by status
	 * @see com.emxcel.dms.core.business.services.companymaster.
	 * 		CompanyMasterService#getCompanyMasterByStatus(
	 * 			java.lang.Long, java.lang.Integer)
	 */
	@Override
	public void saveCompanyMaster(CompanyMaster companyMaster, Long tanentID, Integer status)
			throws ServiceException {
		companyMaster.setStatus(status);

		if(companyMaster.getId() == null){
			companyMaster.setTanentID(tanentID);
		}
		super.save(companyMaster);
	}

	@Override
	public CompanyMaster getCompanyMasterByName(String companyName, Long tanentID) {
		return companyMasterRepository.getCompanyMasterByName(companyName,tanentID);
	}

	@Override
	public Long saveCompanyMaster(CompanyMaster companyMaster)
			throws ServiceException {
		super.save(companyMaster);
		companyMaster = companyMasterRepository.save(companyMaster);
		return companyMaster.getId();
	}

}
