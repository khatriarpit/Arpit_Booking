package com.emxcel.dms.core.business.services.superadmin;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.superadmin.RateOfContract;

/**
 * @author Emxcel Solutions
 *
 */
public interface RateOfContractService extends DMSEntityService<Long,RateOfContract> {

	/**Created By- Johnson Chunara Date-26-01-2017.
	 * Used For-getRateOfContractDBStatus
	 * @param rateOfContract
	 * @param id
	 * @return
	 */
	List<RateOfContract> getRateOfContractDBStatus(String rateOfContract, Long id) throws ServiceException;
	
	

}
