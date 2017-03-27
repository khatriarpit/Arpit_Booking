package com.emxcel.dms.core.business.repositories.driver;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.driver.Driver;

/**
 * @author Emxcel Solutions
 *
 */
public interface DriverRepository extends JpaRepository<Driver, Long>, DriverRepositoryCustom {

	/**
	 * Created by-Nitin Patel Used For-getDriverDetailBytokenID. Date
	 * -26-01-2017
	 * 
	 * @param otp
	 *            **otp**
	 * @return Driver
	 */
	@Query("select d from Driver d where d.otp= ?")
	Driver getDriverDetailBytokenID(String otp);

	/**
	 * Created by-Nitin Patel Used For-getDriverDetailBytokenID. Date
	 * -26-01-2017
	 * 
	 * @param tokenID
	 *            **tokenID**
	 * @param otp
	 *            **otp**
	 */
	@Modifying
	@Query("update Driver set tokenID =? where otp=?")
	void updateDriverDetailBytokenID(String tokenID, String otp);

	/**
	 * Created by-Nitin Patel Used For-getDriverColor. Date -26-01-2017
	 * 
	 * @param color
	 *            **color**
	 * @return Driver
	 */
	@Query("select d from Driver d where d.color= ?")
	Driver getDriverColor(String color);

	// select d from Driver d left join Car c on d.id =c.driId where c.carNo=?
	/**
	 * Created by-Nitin Patel Used For-getDriverColor. Date -26-01-2017.
	 * 
	 * @param carNo
	 *            **carNo**
	 * @return Driver
	 */

	@Query("select d from Driver d where d.id IN (select driId from Car where carNo= ?)")
	Driver getDriverOtp(String carNo);

	/**
	 * Created by-Nitin Patel Used For-getDriverOtp. Date -26-01-2017.
	 * 
	 * @param otp
	 *            **otp**
	 * @param driID
	 *            **driID**
	 */

	@Modifying
	@Query("update Driver set otp= ? where id= ?")
	void setDriverOtp(String otp, Long driID);

	/**
	 * Created by-Nitin Patel Used For-getDriverOtp. Date -26-01-2017.
	 * 
	 * @param tanentId
	 * @return List
	 */
	@Query("select d from Driver d where d.status='true'and d.tanentID=?")
	List<Driver> listOfDriver(Long tanentId);

	/**
	 * @param driverName
	 * @return
	 */
	@Query("select d from Driver d where d.fullName = :driverName")
	Driver getDriverByDriverName(@Param("driverName") String driverName);

	/**
	 * @param tanentId
	 * @return
	 */
	@Query("select d from Driver d where d.tanentID= :tanentId")
	List<Driver> listOfDriverByTanent(@Param("tanentId") Long tanentId);

	/**
	 * @param tanentId
	 * @param imeino
	 * @return
	 */
	@Query("select d from Driver d where d.tanentID= :tanentId and d.imeino= :imeino")
	Driver getDriverByImeinoTanent(@Param("tanentId") Long tanentId, @Param("imeino") Long imeino);

	/**
	 * @param tanentID
	 * @param packageID
	 * @return
	 */
	@Query("select count(d.id) from Driver d where d.tanentID= :tanentID and d.tenantPackageID=:packageID")
	int listOfDriver(@Param("tanentID") Long tanentID, @Param("packageID") Long packageID);

	@Query("select d from Driver d where d.tanentID= :tanentID and d.tenantPackageID=:packageID")
	List<Driver> findAllDriversList(@Param("tanentID") Long tanentID, @Param("packageID") Long packageID);


	@Query("select d from Driver d where d.licenseNo=:driverLicense and d.status='true'")
	Driver getDriverByLicenseNumber(@Param("driverLicense") String driverLicense);
	
	@Query("select d from Driver d where d.licenseNo= :license")
	Driver getDriverByLicenseNo(@Param("license")String license);

	@Query("select d from Driver d where d.licenseNo= :license and d.id!=:id")
	Driver getDriverByLicenseNo(@Param("license")String license,@Param("id") Long id);


}