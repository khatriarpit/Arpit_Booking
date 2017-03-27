package com.emxcel.dms.core.business.services.superadmin.Impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.superadmin.DestinationMasterRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.DestinationMasterService;
import com.emxcel.dms.core.model.superadmin.DestinationMaster;

/**
 * @author Jimit Patel
 *
 */
@Service("DestinationMasterService")
public class DestinationMasterServiceImpl extends DMSEntityServiceImpl<Long, DestinationMaster> implements DestinationMasterService {

    /**
     * DestinationMasterRepository.
     */
    DestinationMasterRepository destinationMasterRepository;

    /**
     * @param destinationMasterRepository
     */
    @Inject
    public DestinationMasterServiceImpl(
    			final DestinationMasterRepository destinationMasterRepository) {
        super(destinationMasterRepository);
        this.destinationMasterRepository = destinationMasterRepository;
    }

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
    @Override
    public List<DestinationMaster> checkdestination( final String source,final String destination, final Integer price, final Long id) {
        if (id != null) {
            return destinationMasterRepository.checkdestination(source, destination, price, id);
        } else {
            return destinationMasterRepository.checkdestination(source, destination, price);
        }
    }

    /**
	 * Created By: Jimit Patel Date: 23-1-2017.
	 * Use: To get source place list for auto complete field in client booking
	 * @param query query
	 * @return list
	 */
    @Override
    public List<DestinationMaster> getsourcePlaceList(final String query) {
        return destinationMasterRepository.getsourceplaceList(query);
    }
    /**
	 * Created By: Jimit Patel Date: 23-1-2017.
	 * Use: To get Destination place list for
	 *  auto complete field in client booking
	 * @param query query
	 * @return list
	 */
    @Override
    public List<DestinationMaster> getdestinationPlaceList(String query) {
        return destinationMasterRepository.getdestinationplaceList(query);
    }
    @Override
    public List<DestinationMaster> listoDestinationMaster (Long tenantID){
        return destinationMasterRepository.listoDestinationMaster(tenantID);
    }
}