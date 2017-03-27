package com.emxcel.dms.core.business.services.car;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarName;

public interface CarNameService extends DMSEntityService<Long, CarName> {

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-List Of CarName
	 * 
	 * @return list
	 */
	List<CarName> listCarName();

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getDBStatus
	 * 
	 * @param carname
	 *            **carName
	 * @param long1 
	 * @param carType **
	 * @return List.
	 */
	 List<CarName> checkCarName(String carname, Long carTypeId, Long id);

	List<Car> checkexistcar(Long carnameid);

	List<CarName> getCarNameById(Long long1);

	List<CarName> checkeExistingCarName(Long carTypeId, Long tanentID);


}