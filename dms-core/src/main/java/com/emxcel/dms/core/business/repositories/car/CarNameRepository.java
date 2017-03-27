package com.emxcel.dms.core.business.repositories.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarName;

public interface CarNameRepository extends JpaRepository<CarName, Long> {
	/**
	 * Created By- Johnson Chunara Date-19/12/2016. Used For Car No Checking
	 * 
	 * @return carType
	 */
	@Query("select cr from CarName cr where cr.carName=? and cr.carTypeId=? and cr.id!=?")
	List<CarName> checkCarName(String carname, Long carTypeId, Long id);

	@Query("select cr from CarName cr where cr.carName=? and cr.carTypeId=?")
	List<CarName> checkCarName(String carname, Long carTypeId);

	@Query("select cn from CarName cn")
	List<CarName> listCarName();

	@Query("select c from Car c where c.carNameId=?")
	List<Car> checkexistcar(Long carnameid);

	@Query("select cn from CarName cn where cn.carTypeId=?")
	List<CarName> getCarNameById(Long carTypeId);

	@Query("select cn from CarName cn where cn.carTypeId=? and cn.tanentID=?")
	List<CarName> checkeExistingCarName(Long carTypeId, Long tanentID);

	@Query("select c from Car c where c.carNo=?")
	Car checkCarByCarNo(String carno);
}
