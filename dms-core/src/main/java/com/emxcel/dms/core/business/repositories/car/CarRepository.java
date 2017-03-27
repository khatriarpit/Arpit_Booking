package com.emxcel.dms.core.business.repositories.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;

/**
 * Spring Data repository for interacting with user details data store.
 * 
 * @author Mark Meany
 */
public interface CarRepository extends JpaRepository<Car, Long> {

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-checkCarNoDBStatus
	 *
	 * @param tanentId
	 * @return Long
	 */
	@Query("select cr from Car cr  where carNo=? and status='true' ")
	Long getLastCarIdInserted(Long tanentId);

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-checkCarNoDBStatus
	 * 
	 * @param carNo
	 * @return
	 */
	@Query("select cr from Car cr  where carNo=? and status='true' ")
	List<Car> checkCarNoDBStatus(String carNo);

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getCarColor
	 * 
	 * @param driColor
	 * @param tanentID
	 * @return
	 */
	@Query("select d from Car d where d.color=:carColor and d.status=:status and d.tanentID=:tanentID")
	Car getCarColor(@Param("carColor") String carColor, @Param("tanentID") Long tanentID,
			@Param("status") String status);

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-removeCarDetail
	 * 
	 * @param remove
	 */
	@Modifying
	@Query("Update Car cd set cd.status = 'false' where cd.id = ? ")
	void removeCarDetail(Long remove);

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-findAllCars
	 * 
	 * @param tanentid
	 * @return
	 */
	@Query("select c from Car c where c.status = 'true' and c.tanentID=?")
	List<Car> findAllCars(Long tanentid);

	@Query("select c from Car c where c.id=? and c.status = 'true'")
	Car findById(Long carId);

	@Query("select c from Car c where c.status = 'true' and c.tanentID=?")
	List<Car> findAllCarsNoDrivers(Long tanentID);

	@Query("select c from Car c, CarType ct where c.id not in (:ids) and ct.carType=:carType and c.carTypeId=ct.id")
	List<Car> getCarAvailableList(@Param("ids") List<Long> var1, @Param("carType") String var2);

	@Query("select c from Car c, CarType ct where c.id not in (:ids) and c.carTypeId=ct.id")
	List<Car> getCarAvailableListWithAllTypes(@Param("ids") List<Long> var1);

	@Query(value = "SELECT * FROM DMS.clientmodel where pick_up_date_time NOT BETWEEN STR_TO_DATE(:picUpDateTime,\'%d/%m/%Y %H:%i:%s\') AND STR_TO_DATE(:dropDate,\'%d/%m/%Y %H:%i:%s\') AND drop_up_date_time NOT BETWEEN STR_TO_DATE(:picUpDateTime,\'%d/%m/%Y %H:%i:%s\') AND STR_TO_DATE(:dropDate,\'%d/%m/%Y %H:%i:%s\')", nativeQuery = true)
	List<ClientModel> getCarAvailableListBydates(@Param("picUpDateTime") String var1, @Param("dropDate") String var2);

	@Query("select c from Car c where c.carNo = :carNo")
	Car getCarByCarNo(@Param("carNo") String carNo);

	@Query("select c from Car c where c.tanentID=?")
	List<Car> listOfCarByTanent(Long tanentId);

	@Query("select count(c.id) from Car c where c.tanentID=:tanentID and c.tenantPackageID=:packageID")
	int findAllCars(@Param("tanentID") Long tanentID, @Param("packageID") Long packageID);

	@Query("select c from Car c where c.tanentID=:tanentID and c.tenantPackageID=:packageID")
	List<Car> findAllCarsList(@Param("tanentID") Long tanentID, @Param("packageID") Long packageID);
}