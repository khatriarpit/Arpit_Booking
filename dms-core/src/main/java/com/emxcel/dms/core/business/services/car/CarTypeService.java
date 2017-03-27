package com.emxcel.dms.core.business.services.car;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.CarType;

public interface CarTypeService extends DMSEntityService<Long, CarType> {

	/**Created By - Johnson Chunara Date 19-12-2016.
	 * Used For-getCarTypeDBStatus
	 * @param carType **CarType
	 * @param long1 
	 * @return CarType
	 */
	List<CarType> checkCarType(String carType, Long id);

	List<CarType> getlistByTanentid(Long setTanentid);

	CarType getCarTypeByCarTypeName(String carType);

}