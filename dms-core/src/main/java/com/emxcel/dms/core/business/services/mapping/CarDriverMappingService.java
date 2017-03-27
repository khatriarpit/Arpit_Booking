package com.emxcel.dms.core.business.services.mapping;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import java.util.List;

import javax.servlet.http.HttpSession;
public interface CarDriverMappingService extends DMSEntityService<Long, CarDriverMapping> {

	/**
	 * @param tanentID
	 * @return
	 */
	List<CarDriverMapping> findAllCarDriver(Long tanentID);

	List<CarDriverMapping> carByDriverAssign(Long tanentID);


	CarDriverMapping checkRemoveCarDriver(Long tanentID, Long driId);

    CarDriverMapping getByCarIdAndTenant(Long carId);

	CarDriverMapping getCarDriverByCarId(Long carId);
	
	/**
	 * Created By-Nitin Patel Date-14-03-2017. Used For: save carDriverMapping
	 * @param car
	 * @param session
	 */
	void saveCarDriverMapping(Car car,HttpSession session) throws Exception;
}
