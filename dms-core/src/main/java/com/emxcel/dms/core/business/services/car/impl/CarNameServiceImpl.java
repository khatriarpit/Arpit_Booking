package com.emxcel.dms.core.business.services.car.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.business.repositories.car.CarNameRepository;
import com.emxcel.dms.core.business.repositories.car.CarTypeRepository;
import com.emxcel.dms.core.business.services.car.CarNameService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;

/**
 * @author Johnson Chunara.
 *
 */
@Service("carNameService")
public class CarNameServiceImpl extends DMSEntityServiceImpl<Long, CarName> implements CarNameService {

	/**
	 * **carNameRepository**
	 */
	@Inject
	private CarNameRepository carNameRepository;

	/**
	 * **carTypeRepository**
	 */
	@Inject
	private CarTypeRepository carTypeRepository;

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-CarTypeServiceImpl
	 * 
	 * @param carNameRepository
	 */
	@Inject
	public CarNameServiceImpl(CarNameRepository carNameRepository) {
		super(carNameRepository);
		this.carNameRepository = carNameRepository;
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-listCarName
	 * 
	 * @see dms.master.service.CarNameService#deletecarNameDetail(int)
	 */
	@Override
	public List<CarName> listCarName() {
		List<CarName> list = carNameRepository.listCarName();
		for (CarName carName : list) {
			CarType carType = carTypeRepository.getOne(carName.getCarTypeId());
			if (carType != null) {
				carName.setCarType(carType);
			}
		}
		return list;
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getDBStatus
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.car.CarNameService#getDBStatus(java
	 * .lang.String)
	 */
	@Override
	public List<CarName> checkCarName(String carname, Long carTypeId, Long id) {
		if(id!=null){
			return carNameRepository.checkCarName(carname,carTypeId,id);
		}
		else{
			return carNameRepository.checkCarName(carname,carTypeId);
		}
			
	}

	@Override
	public List<Car> checkexistcar(Long carnameid) {
		return carNameRepository.checkexistcar(carnameid);
	}

	@Override
	public List<CarName> getCarNameById(Long carTypeId) {
		return carNameRepository.getCarNameById(carTypeId);
	}

	@Override
	public List<CarName> checkeExistingCarName(Long carTypeId, Long tanentID) {
		return carNameRepository.checkeExistingCarName(carTypeId, tanentID);
	}



}