package com.emxcel.dms.core.business.services.mapping.Impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.repositories.car.CarNameRepository;
import com.emxcel.dms.core.business.repositories.car.CarRepository;
import com.emxcel.dms.core.business.repositories.car.CarTypeRepository;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.repositories.mapping.CarDriverMappingRepository;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.mapping.CarDriverMappingService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.user.User;


/**
 * Created by root on 1/3/17.
 */
@Service("CarDriverMappingService")
public class CarDriverMappingServiceImpl extends DMSEntityServiceImpl<Long, CarDriverMapping> implements CarDriverMappingService {

    private CarDriverMappingRepository carDriverMappingRepository;

    @Inject
    private DriverRepository driverRepository;

    @Inject
    private CarRepository carRepository;

    @Inject
    private CarTypeRepository carTypeRepository;

    @Inject
    private CarNameRepository carNameRepository;
    
    @Inject
    private CarService carService;
    /**
     * Created By - Johnson Chunara Date 19-12-2016. Used For-getCarColor
     *
     * @param carDriverMappingRepository **carRepository**
     */
    @Inject
    public CarDriverMappingServiceImpl(final CarDriverMappingRepository carDriverMappingRepository) {
        super(carDriverMappingRepository);
        this.carDriverMappingRepository = carDriverMappingRepository;

    }

    /*
     * Created By - Ashka Thaker Date 3-1-2017. Used For-Car Driver Mapping List
	 *
	 * @see com.emxcel.dms.core.business.services.mapping.CarDriverMappingService#findAllCarDriver()
	 */
    @Override
    public List<CarDriverMapping> findAllCarDriver(Long tanentid) {
        List<CarDriverMapping> list = carDriverMappingRepository.findAllCarDriver(tanentid);
        List listn = new ArrayList<Car>();
        if (list.size() > 0) {
            for (CarDriverMapping carDriverMapping : list) {
                if (carDriverMapping.getCarId() != null) {
                    Car car = carRepository.findById(carDriverMapping.getCarId());
                    carDriverMapping.setCar(car);
                    if (car != null) {
                        if (car.getCarTypeId() != null) {
                            CarType carType = carTypeRepository.findOne(car.getCarTypeId());
                            if (carType != null) {
                                car.setCarType(carType);
                            }
                        }
                        if (car.getCarNameId() != null) {
                            CarName carName = carNameRepository.findOne(car.getCarNameId());
                            if (carName != null) {
                                car.setCarName(carName);
                            }

                        }

                    }
                }
                if (carDriverMapping.getDriId() != null) {
                    Driver driver = driverRepository.findOne(carDriverMapping.getDriId());
                    if (driver != null) {
                        carDriverMapping.setDriver(driver);
                    }
                }
                listn.add(carDriverMapping);
            }
        }
        return listn;
    }

    @Override
    public List<CarDriverMapping> carByDriverAssign(Long tanentID){
            List<CarDriverMapping> list= carDriverMappingRepository.carByDriverAssign(tanentID);
        return list;
    }

    @Override
    public CarDriverMapping checkRemoveCarDriver(Long tanentID, Long driId) {
        return carDriverMappingRepository.checkRemoveCarDriver(tanentID, driId);
    }

    @Override
    public CarDriverMapping getByCarIdAndTenant(Long carId) {
        return carDriverMappingRepository.getByCarIdAndTenant(carId);
    }

	@Override
	public CarDriverMapping getCarDriverByCarId(Long carId) {
		return carDriverMappingRepository.getCarDriverByCarId(carId);
	}

	/* created By: Nitin Patel Date:14-03-2017 Use: save CarDriverMapping
	 * @see com.emxcel.dms.core.business.services.mapping.CarDriverMappingService#saveCarDriverMapping(com.emxcel.dms.core.model.car.Car, javax.servlet.http.HttpSession)
	 */
	@Override
	public void saveCarDriverMapping(Car car,HttpSession session) throws Exception {
		User userSession = (User) session.getAttribute("user");
		Long carId = carService.saveCarReturnCarId(car);
		CarDriverMapping carDriverMapping = new CarDriverMapping();
		carDriverMapping.setTanentID(userSession.getTanentID());
		carDriverMapping.setCarId(carId);
		carDriverMapping.setDriId(null);
		super.save(carDriverMapping);
	}
}