package com.emxcel.dms.core.business.repositories.superadmin;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.superadmin.Tenant;
import org.springframework.data.repository.query.Param;

/**
 * @author Emxcel Solutions
 *
 */
public interface TenantRepository extends JpaRepository<Tenant, Long> {

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyServiceNoWithOutCompanyIdss
	 * @param serviceNo
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.servicetaxno = :serviceNo")
	int getCompanyServiceNoWithOutCompanyId(@Param("serviceNo") String serviceNo);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyPanNoWithOutCompanyId
	 * @param panNo **panNo**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.panno=:panNo")
	int getCompanyPanNoWithOutCompanyId(@Param("panNo") String panNo);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyEmailIdWithOutCompanyId
	 * @param emailId **email**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.emailid=:emailId")
	int getCompanyEmailIdWithOutCompanyId(@Param("emailId") String emailId);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyContactNoWithOutCompanyId
	 * @param contactNo **contactNo**
	 * @return  int
	 */
	@Query("select count(c.id) from Tenant c where c.contactno=:contactNo")
	int getCompanyContactNoWithOutCompanyId(@Param("contactNo") Long contactNo);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyServiceNo
	 * @param serviceNo **serviceNo**
	 * @param id **id**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.servicetaxno = :serviceNo and c.id != :id")
	int getCompanyServiceNo(@Param("serviceNo") String serviceNo, @Param("id") Long id);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyPanNo
	 * @param panNo **panNo**
	 * @param id *id***
	 * @return
	 */
	@Query("select count(c.id) from Tenant c where c.panno=:panNo and c.id != :id")
	int getCompanyPanNo(@Param("panNo") String panNo, @Param("id") Long id);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyEmailId
	 * @param emailId
	 * @param id
	 * @return
	 */
	@Query("select count(c.id) from Tenant c where c.emailid=:emailId and c.id != :id")
	int getCompanyEmailId(@Param("emailId") String emailId, @Param("id") Long id);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-getCompanyContactNo
	 * @param contactNo **contactNo**
	 * @param id **id**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.contactno=:contactNo and c.id != :id")
	int getCompanyContactNo(@Param("contactNo") Long contactNo, @Param("id") Long id);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-checkCompanyName
	 * @param companyname **companyname**
	 * @param id **id**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.companyname=:companyName and c.id != :id")
	int checkCompanyName(@Param("companyName") String companyname,@Param("id") Long id);

	/**
	 * Created By -Naresh Banda Date-26-01-2017.
	 * Used For-checkCompanyContactNoWithOutCompanyId
	 * @param companyname **checkCompanyContactNoWithOutCompanyId**
	 * @return int
	 */
	@Query("select count(c.id) from Tenant c where c.companyname=:companyName")
	int checkCompanyContactNoWithOutCompanyId(@Param("companyName") String companyname);

	@Query("select count(t.id) from Tenant t where t.companytypename=:companyType")
	int getCompanyTypeStatus(@Param("companyType") CompanyType companyType);


}