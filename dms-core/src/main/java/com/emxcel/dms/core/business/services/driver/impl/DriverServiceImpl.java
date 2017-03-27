package com.emxcel.dms.core.business.services.driver.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.repositories.driver.DriverRepository;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.user.User;

/**
 * @author emxcelsolution
 *
 */
@Service("driverService")
public class DriverServiceImpl extends DMSEntityServiceImpl<Long, Driver> implements DriverService {

	@PersistenceContext
	private EntityManager manager;
	/**
	 * driverRepository.
	 */
	private DriverRepository driverRepository;

	/**
	 * **cityService**.
	 */
	@Autowired
	private CityService cityService;

	/**
	 * @param driverRepository
	 *            **driverRepository**
	 */
	@Inject
	public DriverServiceImpl(final DriverRepository driverRepository) {
		super(driverRepository);
		this.driverRepository = driverRepository;
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016
	 * 
	 * @see com.mvmlabs.springboot.service.
	 * DriverService#getDriverDetailBytokenID(java.lang.String)
	 */
	@Override
	public boolean getDriverDetailBytokenID(final String otp) {
		Driver d = driverRepository.getDriverDetailBytokenID(otp);
		return d != null;
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016
	 * 
	 * @see com.mvmlabs.springboot.service.
	 * DriverService#updateDriverDetailBytokenID (java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void updateDriverDetailBytokenID(final String tokenID, final String otp) {
		driverRepository.updateDriverDetailBytokenID(tokenID, otp);
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016 Use:To check color.
	 * 
	 * @see com.mvmlabs.springboot.service.
	 * DriverService#getDriverColor(java.lang.String)
	 */
	@Override
	public Driver getDriverColor(final String driColor) {
		Driver driver = driverRepository.getDriverColor(driColor);
		return driver;
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016 Use: get driver otp.
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.driver.DriverService#getDriverOtp(
	 * java.lang.String)
	 */
	@Override
	public Driver getDriverOtp(String carNo) {
		return driverRepository.getDriverOtp(carNo);
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016 Use: set Driver otp.
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.driver.DriverService#setDriverOtp(
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public void setDriverOtp(String otp, Long driID) {
		driverRepository.setDriverOtp(otp, driID);
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016 Use: Driver list.
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl
	 * #list()
	 */
	@Override
	public List<Driver> list() {
		List<Driver> driverList = super.list();
		List<Driver> driverListNew = new ArrayList<Driver>();
		if (driverList.size() > 0) {
			for (Driver driver : driverList) {
				if (driver.getCityID() != null) {
					City city = cityService.getById(driver.getCityID());
					driver.setCityDetail(city);
				}
				driverListNew.add(driver);
			}
		}
		return driverListNew;
	}

	/*
	 * created By: Nitin Patel Date:15-12-2016 Use: get driver otp.
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.driver.DriverService#listOfDriver(
	 * java.lang.Long)
	 */
	@Override
	public List<Driver> listOfDriver(final Long tanentId) {
		List<Driver> list = driverRepository.listOfDriver(tanentId);
		List<Driver> listd = new ArrayList<Driver>();
		if (list.size() > 0) {
			for (Driver driver : list) {
				if (driver.getCityID() != null) {
					City city = cityService.getById(driver.getCityID());
					if (city != null) {
						driver.setCityDetail(city);
					}
				}
				listd.add(driver);
			}
		}
		return listd;

	}

	@Override
	public List<Driver> getAvailableDrivers(String pickUpDateTime, String dropDate, Long tanentID) {
		List<Driver> driverList = manager.createNamedQuery("getAvailableDrivers", Driver.class)
				.setParameter("picUpDateTime", pickUpDateTime).setParameter("dropDate", dropDate)
				.setParameter("tanentID", tanentID).getResultList();
		return driverList;
	}

	@Override
	public Driver getDriverByDriverName(String driverName) {
		return driverRepository.getDriverByDriverName(driverName);
	}

	@Override
	public List<Driver> listOfDriverByTanent(Long tanentId) {
		return driverRepository.listOfDriverByTanent(tanentId);
	}

	@Override
	public Driver getDriverByImeinoTanent(Long tenantId, Long imeino) {
		return driverRepository.getDriverByImeinoTanent(tenantId, imeino);
	}

	@Override
	public int listOfDriver(Long tanentID, Long packageID) {
		return driverRepository.listOfDriver(tanentID, packageID);
	}

	@Override
	public List<Driver> findAllDriversList(Long tanentID, Long packageID) {
		return driverRepository.findAllDriversList(tanentID, packageID);
	}

	@Override
	public Driver getDriverByLicenseNumber(String driverLicense) {
		return driverRepository.getDriverByLicenseNumber(driverLicense);
	}

	@Override
	public Driver getDriverByLicenseNo(String license,Long id) {
		if (id != null) {
			return driverRepository.getDriverByLicenseNo(license,id);


		}else{
			return driverRepository.getDriverByLicenseNo(license);
		}

	}

	/*
	 * created By: Nitin Patel Date:14-03-2017 Use: set Status false if driver
	 * deleted Successfully.
	 * 
	 * @see com.emxcel.dms.core.business.services.driver.DriverService#
	 * setStatusforDelete(com.emxcel.dms.core.model.driver.Driver)
	 */
	@Override
	public void setStatusforDelete(Driver driver) throws Exception {
		driver.setStatus(Constants.FALSE_AS_STRING);
		super.update(driver);
	}

	/*
	 * created By: Nitin Patel Date:14-03-2017 Use: save driver lisence and
	 * adharcar images
	 * 
	 * @see com.emxcel.dms.core.business.services.driver.DriverService#
	 * saveDriverImages(com.emxcel.dms.core.model.driver.Driver,
	 * javax.servlet.http.HttpSession)
	 */
	@Override
	public void saveDriverImages(Driver driverDetail, HttpSession session) throws Exception {
		User userSession = (User) session.getAttribute("user");
		String fileName;
		String fileName1;
		String fileName2;
		String path = CommonUtil.LOCATION;
		fileName = CommonUtil.getFileName(driverDetail.getImgFile(), path);
		fileName1 = CommonUtil.getFileName(driverDetail.getImgFile1(), path);
		fileName2 = CommonUtil.getFileName(driverDetail.getImgFile2(), path);
		if (!"".equals(fileName)) {
			driverDetail.setImage(fileName);
		}
		if (!"".equals(fileName1)) {
			driverDetail.setLicenseImage(fileName1);
		}
		if (!"".equals(fileName2)) {
			driverDetail.setAdharCardImage(fileName2);
		}
		String fName = driverDetail.getFirstName();
		String mName = driverDetail.getMiddleName();
		String lName = driverDetail.getLastName();
		String fullName = fName + " " + mName + " " + lName;
		driverDetail.setFullName(fullName);
		driverDetail.setTanentID(userSession.getTanentID());
		if (driverDetail.getId() != null) {
			Driver driverOld = driverRepository.findOne(driverDetail.getId());
			if (driverOld != null) {
				if ("".equals(fileName)) {
					driverDetail.setImage(driverOld.getImage());
				}
				if ("".equals(fileName1)) {
					driverDetail.setLicenseImage(driverOld.getLicenseImage());
				}
				if ("".equals(fileName2)) {
					driverDetail.setAdharCardImage(driverOld.getAdharCardImage());
				}
			}
		}
		super.save(driverDetail);
	}
}
