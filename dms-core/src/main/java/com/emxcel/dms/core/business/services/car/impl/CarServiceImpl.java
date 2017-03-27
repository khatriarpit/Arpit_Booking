package com.emxcel.dms.core.business.services.car.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.car.CarNameRepository;
import com.emxcel.dms.core.business.repositories.car.CarRepository;
import com.emxcel.dms.core.business.repositories.car.CarTypeRepository;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.repositories.superadmin.TenantRepository;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.user.User;

/**
 * @author emxcelsolution
 *
 */
@Service("carService")
public class CarServiceImpl extends DMSEntityServiceImpl<Long, Car> implements CarService {

	/**
	 * **carRepository**.
	 */
	private CarRepository carRepository;

	/**
	 * **carTypeRepository**.
	 */
	@Inject
	private CarTypeRepository carTypeRepository;

	/**
	 * **carNameRepository**.
	 */
	@Inject
	private CarNameRepository carNameRepository;

	/**
	 * **driverRepository**.
	 */
	@Inject
	private DriverRepository driverRepository;

	@Inject
	private TenantRepository companyRepository;

	@PersistenceContext
	private EntityManager manager;

	/**
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getCarColor
	 * 
	 * @param carRepository
	 *            **carRepository**
	 */
	@Inject
	public CarServiceImpl(final CarRepository carRepository) {
		super(carRepository);
		this.carRepository = carRepository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long saveCarReturnCarId(Car car) {
		Car carBean = carRepository.save(car);
		return carBean.getId();
		// return carRepository.getLastCarIdInserted(car.getTanentID());
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-findById
	 *
	 * @see com.emxcel.dms.core.business.services.car.
	 * CarService#findById(java.lang.Long)
	 */
	@Override
	public Car findById(final Long carId) {
		Car carDetail = carRepository.findById(carId);
		if (carDetail != null && carDetail.getCarTypeId() > 0) {
			CarType carType = carTypeRepository.findOne(carDetail.getCarTypeId());
			carDetail.setCarType(carType);
		}

		if (carDetail != null && carDetail.getCarNameId() > 0) {
			CarName carName = carNameRepository.findOne(carDetail.getCarNameId());
			carDetail.setCarName(carName);
		}

		return carDetail;
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-checkCarNoDBStatus
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.car.CarService#checkCarNoDBStatus(
	 * java.lang.String)
	 */
	@Override
	public List<Car> checkCarNoDBStatus(final String carNo) {
		return carRepository.checkCarNoDBStatus(carNo);
	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-getCarColor
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.car.CarService#getCarColor(java.
	 * lang.String)
	 */
	@Override
	public Car getCarColor(String carColor, Long tanentID, String status) {
		Car car = carRepository.getCarColor(carColor, tanentID, status);
		return car;

	}

	/*
	 * Created By - Johnson Chunara Date 19-12-2016. Used For-removeCarDetail
	 * 
	 * @see com.emxcel.dms.core.business.services.car.CarService#findAllCars()
	 */
	@Override
	public List<Car> findAllCars(Long tanentid) {
		List<Car> list = carRepository.findAllCars(tanentid);
		List listn = new ArrayList<Car>();
		if (list.size() > 0) {
			for (Car carDetail : list) {
				if (carDetail.getCarTypeId() != null) {
					CarType carType = carTypeRepository.findOne(carDetail.getCarTypeId());
					if (carType != null) {
						carDetail.setCarType(carType);
					}
				}
				if (carDetail.getCarNameId() != null) {
					CarName carName = carNameRepository.findOne(carDetail.getCarNameId());
					if (carName != null) {
						carDetail.setCarName(carName);
					}

				}

				if (carDetail.getDriId() != null) {
					Driver driver = driverRepository.findOne(carDetail.getDriId());
					if (driver != null) {
						carDetail.setDriver(driver);
					}
				}
				listn.add(carDetail);
			}
		}
		return listn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.car.CarService#findAllCarsNoDrivers
	 * (java.lang.Long)
	 */
	@Override
	public List<Car> findAllCarsNoDrivers(Long tanentID) {
		return carRepository.findAllCarsNoDrivers(tanentID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.car.CarService#
	 * findWithOutDriverById(java.lang.Long)
	 */
	@Override
	public Car findWithOutDriverById(Long carId) {
		Car carDetail = super.getById(carId);
		if (carDetail != null && carDetail.getCarTypeId() != null) {
			CarType carType = carTypeRepository.findOne(carDetail.getCarTypeId());
			carDetail.setCarType(carType);
		}
		if (carDetail != null && carDetail.getCarNameId() != null) {
			CarName carName = carNameRepository.findOne(carDetail.getCarNameId());
			carDetail.setCarName(carName);
		}
		return carDetail;
	}

	@Override
	public Car checkCarByCarNo(String carno) {
		Car car = carNameRepository.checkCarByCarNo(carno);
		if (car != null) {
			if (car.getCarTypeId() != null) {
				car.setCarType(carTypeRepository.findOne(car.getCarTypeId()));
			}
			if (car.getCarNameId() != null) {
				car.setCarName(carNameRepository.findOne(car.getCarNameId()));
			}
		}

		return car;
	}

	@Override
	public List<Car> getCarAvailableList(String picUpDateTime, String dropDate, String carType, Long tanentID) {
		List list = null;
		if (carType != null) {
			list = manager.createNamedQuery("getCarAvailableListByParam", Car.class)
					.setParameter("picUpDateTime", picUpDateTime).setParameter("dropDate", dropDate)
					.setParameter("tanentID", tanentID).setParameter("carType", carType).getResultList();
		} else {
			list = manager.createNamedQuery("getCarAvailableListForAllCarType", Car.class)
					.setParameter("picUpDateTime", picUpDateTime).setParameter("dropDate", dropDate)
					.setParameter("tanentID", tanentID).getResultList();
		}
		if (list.size() > 0) {
			Iterator var5 = list.iterator();

			while (var5.hasNext()) {
				Car car = (Car) var5.next();
				if (car.getCarNameId() != null) {
					CarName carType1 = (CarName) this.carNameRepository.findOne(car.getCarNameId());
					if (carType1 != null) {
						car.setCarName(carType1);
					}
				}

				if (car.getCarTypeId() != null) {
					CarType carType11 = (CarType) this.carTypeRepository.findOne(car.getCarTypeId());
					if (carType11 != null) {
						car.setCarType(carType11);
					}
				}
			}
		}

		return list;
	}

	@Override
	public Car getCarByCarNo(String carNo) {
		return carRepository.getCarByCarNo(carNo);
	}

	@Override
	public List<Car> listOfCarByTanent(Long tanentId) {
		return carRepository.listOfCarByTanent(tanentId);
	}

	@Override
	public int findAllCars(Long tanentID, Long packageID) {
		return carRepository.findAllCars(tanentID, packageID);
	}

	@Override
	public List<Car> findAllCarsList(Long tanentID, Long packageID) throws ServiceException {
		return carRepository.findAllCarsList(tanentID, packageID);
	}

	/*
	 * created By: Nitin Patel Date:14-03-2017 Use: set Status true if car not null
	 * 
	 * @see com.emxcel.dms.core.business.services.car.CarService#
	 * setStatusforCar(com.emxcel.dms.core.model.car.Car,javax.servlet.http.HttpSession)
	 */
	@Override
	public void setStatusforCar(Car car,HttpSession session) throws ServiceException {
		User userSession = (User) session.getAttribute("user");
		car.setStatus(Constants.TRUE_AS_STRING);
		car.setTanentID(userSession.getTanentID());
		super.update(car);
	}

	/*
	 * created By: Nitin Patel Date:14-03-2017 Use: set Status false if car deleted
	 * 
	 * @see com.emxcel.dms.core.business.services.car.CarService#
	 * setStatusforCar(com.emxcel.dms.core.model.car.Car,javax.servlet.http.HttpSession)
	 */
	@Override
	public void setStatusForCarDelete(Car car) throws ServiceException {
		car.setStatus(Constants.FALSE_AS_STRING);
		super.update(car);
	}
}