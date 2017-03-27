package com.emxcel.dms.core.business.repositories.superadmin;

import com.emxcel.dms.core.model.superadmin.TenantPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantPackageRepository extends JpaRepository<TenantPackage, Long> {

	/**
	 * @param tanentID
	 * @param status
	 * @param packageTenantID
	 * @return List<TenantPackage>
	 */
	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and tp.status = (:status) and tp.id = :packageID")
	List<TenantPackage> getListByTenantID(@Param("tenantID") Long tanentID, @Param("status") Integer status,
			@Param("packageID") Long packageTenantID);

	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and tp.status=:status and (STR_TO_DATE(now(),'%Y-%m-%d') BETWEEN tp.fromDate and tp.toDate)")
	List<TenantPackage> getTenantPackageForAdminAndUser(@Param("tenantID") Long tanentID,
			@Param("status") Integer status);

	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and tp.status = (:status) and tp.id = :packageID and (STR_TO_DATE(now(),'%Y-%m-%d') BETWEEN tp.fromDate and tp.toDate)")
	List<TenantPackage> getTenantPackageForAdminAndUser(@Param("tenantID") Long tanentID,
			@Param("status") Integer status, @Param("packageID") Long packageTenantID);

	/**
	 * @param tanentID
	 * @param status1
	 * @param status2
	 * @return List<TenantPackage>
	 */
	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and (tp.status in (:status1,:status2))")
	List<TenantPackage> getListByTenantID(@Param("tenantID") Long tanentID, @Param("status1") Integer status1,
			@Param("status2") Integer status2);

	/**
	 * @return
	 */
	@Query("select tp from TenantPackage tp where tp.approvedFlag=0 and tp.status=0 and tp.emailFlag=1")
	List<TenantPackage> getPendingVerificationByTanentID();

	/**
	 * @param tanentID
	 * @return
	 */
	@Query("select tp from TenantPackage tp where tp.tanentID=? and tp.status not in (1) order by tp.id desc")
	TenantPackage getByTenantIDLimitOne(Long tanentID);

	/**
	 * @param name
	 * @param id
	 * @param tanentID
	 * @return
	 */
	@Query("select count(t.id) from TenantPackage t where t.name=:name and t.id != :id and t.tanentID = :tanentID")
	int checkPackageName(@Param("name") String name, @Param("id") Long id, @Param("tanentID") Long tanentID);

	/**
	 * @param name
	 * @param tanentID
	 * @return
	 */
	@Query("select count(t.id) from TenantPackage t where t.name=:name and t.tanentID = :tanentID")
	int checkPackageNameNoWithOutCompanyId(@Param("name") String name, @Param("tanentID") Long tanentID);

	/**
	 * @param tanentID
	 * @return
	 */
	@Query("select tp from TenantPackage tp where tp.tanentID != :tanentID and tp.status not in (1)")
	List<TenantPackage> getListByNotInThisTenantID(@Param("tanentID") Long tanentID);

	/**
	 * @param tanentID
	 * @return
	 */
	@Query("select tp from TenantPackage tp where tp.tanentID=? and tp.status not in (1)")
	List<TenantPackage> getByTenantIDLimitOneWithOutStatus(Long tanentID);

	@Query("select count(tp.id) from TenantPackage tp where tp.name=:name")
	int checkPackageName(@Param("name") String name);

	@Query("select count(tp.id) from TenantPackage tp where tp.tanentID=:tanentID and tp.status not in (1) order by tp.id asc")
	int checkPackageCount(@Param("tanentID") Long tanentID);

	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and tp.status=:status")
	List<TenantPackage> getListByTenantID(@Param("tenantID") Long tenantID, @Param("status") int status);

	@Query("select tp from TenantPackage tp where tp.tanentID=:tenantID and tp.id != :packageID")
	List<TenantPackage> getTenantPackageWithOutCountThisPackageID(@Param("tenantID") Long tanentID, @Param("packageID") Long tenantPackageID);

	@Query("select count(t.id) from TenantPackage t where t.name=:name and t.id != :id and t.tanentID = :tanentID and t.packageStatus=:packageType")
	int checkPackageNameOnlyFlexibleType(@Param("name") String name, @Param("id") Long id, @Param("tanentID") Long tanentID, @Param("packageType") Integer packageType);

	@Query("select count(t.id) from TenantPackage t where t.name=:name and t.tanentID = :tanentID and t.packageStatus=:packageType")
	int checkPackageNameOnlyFlexibleType(@Param("name") String name, @Param("tanentID") Long tanentID, @Param("packageType") Integer packageType);

	@Query("select t from TenantPackage t where t.fromDate < (STR_TO_DATE(now(),'%Y-%m-%d')) and (STR_TO_DATE(now(),'%Y-%m-%d') NOT BETWEEN t.fromDate and t.toDate)")
	List<TenantPackage> getTenantPackageValidationFrom();
	
	@Query("select t from TenantPackage t where t.fromDate = (STR_TO_DATE(now(),'%Y-%m-%d')) and t.status= :status and t.approvedFlag= :approved ")
	List<TenantPackage> getTenanatPackageInactiveFrom(@Param("status") Integer status, @Param("approved") boolean approved);

	@Query("select t from TenantPackage t where t.id = :id and (t.fromDate < (STR_TO_DATE(now(),'%Y-%m-%d')) and (STR_TO_DATE(now(),'%Y-%m-%d') NOT BETWEEN t.fromDate and t.toDate))")
	List<TenantPackage> getTenantPackageValidationFrom(@Param("id") Long id);
}