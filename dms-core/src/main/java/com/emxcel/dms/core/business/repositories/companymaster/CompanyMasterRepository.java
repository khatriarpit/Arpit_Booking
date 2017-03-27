package com.emxcel.dms.core.business.repositories.companymaster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import org.springframework.data.repository.query.Param;

/**
 * @author emxcelsolution
 *
 */
public interface CompanyMasterRepository extends 
		JpaRepository<CompanyMaster, Long>, CompanyMasterRepositoryCustom {

    /**
     * Created By : Nittin Patel. Date: 26-01-2017
	 * Use: method for get list of company master
     * @return List<CompanyMaster>
     */
    @Query("select cm from CompanyMaster cm where cm.status=1")
    List<CompanyMaster> listOfCompanyMaster();

    /**
     * Created By : Nittin Patel. Date: 26-01-2017
	 * Use: method for get company name list
     * @param query **query**.
     * @return List<CompanyMaster>
     */
    @Query("select cm from CompanyMaster cm where"
    		+ " cm.status=1 and cm.comapanyName like %:query%")
    List<CompanyMaster> getCompanyNameList(@Param("query") String query);

    /**
     * Created By : Nittin Patel. Date: 26-01-2017
	 * Use: Method for get company master by status and id
     * @param id **id**.
     * @param status **status**.
     * @return CompanyMaster
     */
    @Query("select cm from CompanyMaster cm where cm.id=? and cm.status=?")
	CompanyMaster getCompanyMasterByStatus(Long id, Integer status);

    @Query("select cm from CompanyMaster cm where cm.comapanyName=? and cm.tanentID=? and cm.status=1")
    CompanyMaster getCompanyMasterByName(String companyName ,Long tenantId);
}
