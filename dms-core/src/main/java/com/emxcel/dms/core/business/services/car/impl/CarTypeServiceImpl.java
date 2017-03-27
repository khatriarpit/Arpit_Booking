package com.emxcel.dms.core.business.services.car.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.business.repositories.car.CarTypeRepository;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.car.CarType;

/**
 * @author Johnson Chunara
 *
 */
@Service("carTypeService")
public class CarTypeServiceImpl extends DMSEntityServiceImpl<Long, CarType> implements CarTypeService {

	/**
	 * **carTypeRepository**
	 */
	private CarTypeRepository carTypeRepository;

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-CarTypeServiceImpl
	 * 
	 * @param carTypeRepository
	 *            **carTypeRepository**
	 */
	@Inject
	public CarTypeServiceImpl(CarTypeRepository carTypeRepository) {
		super(carTypeRepository);
		this.carTypeRepository = carTypeRepository;
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getCarTypeDBStatus
	 * 
	 * @see com.emxcel.dms.core.business.services.car.CarTypeService#
	 * getCarTypeDBStatus(java.lang.String)
	 */
	@Override
	public List<CarType> checkCarType(String carType, Long id) {
		if(id != null) {
			return carTypeRepository.checkCarType(carType, id);
		} else {
			return carTypeRepository.checkCarType(carType);	
		}
	}

	@Override
	public List<CarType> getlistByTanentid(Long tanentid) {
		return carTypeRepository.getlistByTanentid(tanentid);
	}

	@Override
	public CarType getCarTypeByCarTypeName(String carType){
		return carTypeRepository.getCarTypeByCarTypeName(carType);

	}
}