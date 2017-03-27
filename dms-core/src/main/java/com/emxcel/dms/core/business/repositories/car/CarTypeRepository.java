package com.emxcel.dms.core.business.repositories.car;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.emxcel.dms.core.model.car.CarType;

public interface CarTypeRepository extends JpaRepository<CarType, Long>, CarTypeRepositoryCustom {

	/**
	 * Created By- Johnson Chunara Date-19/12/2016. Used For Car Type Checking
	 * 
	 * @param carType
	 *            **carType**
	 * @return carType
	 */
	@Query("select c from CarType c where c.carType=? and c.id != ?")
	List<CarType> checkCarType(String carType, Long id);

	@Query("select c from CarType c where c.carType=?")
	List<CarType> checkCarType(String carType);

	@Query("select c from CarType c where c.tanentID=?")
	public List<CarType> getlistByTanentid(Long tanentid);

	@Query("select ct from CarType ct where ct.carType=?")
	CarType getCarTypeByCarTypeName(String carType);
}