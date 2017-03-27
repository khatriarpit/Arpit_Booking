package com.emxcel.dms.portal.scheduler;

import java.util.List;

import javax.inject.Inject;

import com.emxcel.dms.core.business.constants.Constants;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.superadmin.TenantPackage;

/**
 * @author : Naresh Banda Date : 01/03/2017
 */
@Service
@EnableScheduling
public class PackageScheduler {

	/**
	 * **Autowired service of packageCreateService **.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * **Autowired service of CarService **.
	 */
	@Inject
	private CarService carService;

	/**
	 * **Autowired service of DriverService **.
	 */
	@Inject
	private DriverService driverService;

	/**
	 * cronTask.
	 */
	@Scheduled(cron = "0 10 01 * * *")
	public void cronTask() {
		try {
			List<TenantPackage> listOfInactivePackage = packageCreateService.getTenanatPackageInactiveFrom(1,true);
			if(listOfInactivePackage.size() > 0){
				for (TenantPackage tenantPackage : listOfInactivePackage){
					//tenantPackage.setStatus(Constants.ACTIVE);
					packageCreateService.activeTenantPackage(tenantPackage, Constants.ACTIVE);
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}

		List<TenantPackage> tenantPackageList = null;
		try {
			tenantPackageList = packageCreateService.getTenantPackageValidationFrom(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (tenantPackageList.size() > 0) {
			for (TenantPackage tenantPackage : tenantPackageList) {
				if (tenantPackage.getOldCarList() == null && tenantPackage.getOldDriverList() == null) {
					tenantPackage.setOldCarList(tenantPackage.getCarList());
					tenantPackage.setOldDriverList(tenantPackage.getDriverList());
					try {
						packageCreateService.update(tenantPackage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (tenantPackage.getCarList() != null) {
					String[] carNo = tenantPackage.getCarList().split(",");
					if (carNo.length > 0) {
						for(String car : carNo) {
							List<Car> carList = carService.checkCarNoDBStatus(car);
							if(carList != null && carList.size() > 0) {
								Car carModel = carList.get(0);
								carModel.setStatus("false");
								try {
									carService.update(carModel);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				if (tenantPackage.getDriverList() != null) {
					String[] driverNo = tenantPackage.getDriverList().split(",");
					if (driverNo.length > 0) {
						for(String driver : driverNo) {
							Driver driverModel = driverService.getDriverByLicenseNumber(driver);
							if(driverModel != null) {
								driverModel.setStatus("false");
								try {
									driverService.update(driverModel);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}
}