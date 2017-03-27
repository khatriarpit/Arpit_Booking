package com.emxcel.dms.core.business.repositories.superadmin;

import java.util.List;

import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.superadmin.DestinationMaster;
import org.springframework.data.repository.query.Param;

public interface DestinationMasterRepository extends JpaRepository<DestinationMaster, Long>{
	
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
	 @Query("select dm from DestinationMaster dm where dm.sourcePlace= ? and dm.destinationPlace=? and dm.price=? and dm.id!=?")
	 List<DestinationMaster> checkdestination(String source,String destination,Integer price,Long id);
	
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
	 @Query("select dm from DestinationMaster dm where dm.sourcePlace= ? and dm.destinationPlace=? and dm.price=?")
	 List<DestinationMaster> checkdestination(String source,String destination,Integer price);

	 /**
		 * Created By: Jimit Patel Date: 23-1-2017.
		 * Use: To get source place list for 
		 * auto complete field in client booking
		 * @param query query
		 * @return list
		 */
	 @Query("select dm from DestinationMaster dm where dm.sourcePlace like %:query%")
	List<DestinationMaster> getsourceplaceList(@Param("query") String query);
    
	 /**
		 * Created By: Jimit Patel Date: 23-1-2017.
		 * Use: To get Destination place list for
		 *  auto complete field in client booking
		 * @param query query
		 * @return list
		 */
	@Query("select dm from DestinationMaster dm where dm.destinationPlace like %:query%")
	List<DestinationMaster> getdestinationplaceList(@Param("query") String query);

	@Query("select dm from DestinationMaster dm where dm.tanentID=?")
	List<DestinationMaster> listoDestinationMaster(Long tenantID);

}
