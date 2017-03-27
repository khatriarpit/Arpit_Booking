package com.emxcel.dms.core.business.services.superadmin;

import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.superadmin.DestinationMaster;

/**
 * @author Jimit Patel
 *
 */
public interface DestinationMasterService  extends DMSEntityService<Long, DestinationMaster>{
	
	/**
	 * Created By: Jimit Patel Date: 20-1-2017.
	 * Use:to check the list of source and 
	 * destination list which is already exists
	 * @param source
	 * @param destination
	 * @param price
	 * @param id
	 * @return list
	 */
	List<DestinationMaster> checkdestination(String source, String destination, Integer price, Long id) throws ServiceException;
	/**
	 * Created By: Jimit Patel Date: 23-1-2017.
	 * Use: To get source place list for auto complete field in client booking
	 * @param query query
	 * @return list
	 */
	List<DestinationMaster> getsourcePlaceList(String query) throws ServiceException;
	/**
	 * Created By: Jimit Patel Date: 23-1-2017.
	 * Use: To get Destination place list for
	 *  auto complete field in client booking
	 * @param query query
	 * @return list
	 */
	List<DestinationMaster> getdestinationPlaceList(String query) throws ServiceException;

	List<DestinationMaster>listoDestinationMaster(Long tenantID);
}
