package com.emxcel.dms.portal.controller.superadmin;

import static org.junit.Assert.fail;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.repositories.user.UserRepository;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.client.TripDeatilsService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.emxcel.dms.core.business.repositories.superadmin.TenantRepository;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.portal.Application;

import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class SuperAdminControllerTest {

	@Autowired
	private TenantService tenantService;

	@Autowired
	private UserService userService;

	@Autowired
	private TripDeatilsService tripStatusService;

	@Autowired
	private DriverService driverService;

	@Autowired
	private CarService carService;

	@Autowired
	private PackageCreateService packageCreateService;

	@Test
	public void testGetCompanyList() throws ServiceException {
		List<Tenant> tenantList = getCompanyList();
		for (Tenant tenant2: tenantList) {
			System.out.println(tenant2.getCompanyname());
			System.out.println(tenant2.getFirstName1());
			System.out.println(tenant2.getLastName1());
			System.out.println("======================");
		}
	}

	@Test
	public void testAddcompany() {
		String companyID = "1";
		Tenant tenant = new Tenant();
		if (companyID != null) {
			tenant = tenantService.getById(Long.valueOf(companyID));
			System.out.println("Company Name :" +tenant.getCompanyname());
			System.out.println("Company Name :" +tenant.getFirstName1());
			System.out.println("Company Name :" +tenant.getLastName1());
		}
	}

	@Test
	public void testDeletecompany() throws  Exception{
		String cid = "50";
		if (cid != null && !cid.equals("")) {
			//tenantService.deleteCompany(50L);
			System.out.println("Tenant Deleted succesfully..!");
		}
	}

	/*@Test
	public  void getPendingVerificationList(){
		List<TenantPackage> tenantPackageList = null;
		try {
			tenantPackageList = packageCreateService.getPendingVerificationByTanentID();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		List<TenantPackage> tenantPackageListNew = new ArrayList<TenantPackage>();
		if (tenantPackageList.size() > 0) {
			for (TenantPackage tenantPackage : tenantPackageList) {
				Tenant company1 = tenantService.getById(tenantPackage.getTanentID());
				if (company1 != null) {
					tenantPackage.setTenant(company1);
				}
				tenantPackageListNew.add(tenantPackage);
				System.out.println("Hello :"+tenantPackageListNew.size());
			}
		}
	}*/

	/*@Test
	public  void getPendingVerificationPage(){
		String id ="1";
		if (id != null && !id.equals("")) {
			TenantPackage tenantPackage1 = packageCreateService.getById(Long.valueOf(id));
			String[] carLength = null;
			String[] driverLength = null;
			if (tenantPackage1 != null) {
				carLength = tenantPackage1.getCarList().split(",");
				driverLength = tenantPackage1.getDriverList().split(",");
				Tenant company1 = tenantService.getById(tenantPackage1.getTanentID());
				if (company1 != null) {
					tenantPackage1.setTenant(company1);
				}
			}
			Map<String, Object> userLimit = getTenantPackageLimit(tenantPackage1.getTanentID());
			Integer noofUser = (Integer) userLimit.get("userLimit");
			Integer noofCar = (Integer) userLimit.get("carLimit");
			Integer noofDriver = (Integer) userLimit.get("driverLimit");
			System.out.println("NoOFUser : "+noofUser);
			System.out.println("NoOFDriver : "+noofDriver);
			System.out.println("NoOFCar : "+noofCar);
		}
	}*/

	@Test
	public void approveVerification() throws ServiceException{
		String car="gj 01 ab 1111,gj 02 ab 2222";
		String id="1";
		String driver="ab11 1111 111111,xy22 2222 222222";
		String remarks ="nityin";
		String carIndex ="";
		String driverIndex ="";
		Map<String, Object> model = new HashMap<String, Object>();
		TenantPackage tenantPackage1 = packageCreateService.getById(Long.valueOf(id));
		String[] carLength = null;
		if (!car.equals("")) {
			carLength = car.split(",");
		}
		String[] driverLength = null;
		if (!driver.equals("")) {
			driverLength = driver.split(",");
		}
		List<TenantPackage> tenantPackageList = packageCreateService
				.getListByNotInThisTenantID(tenantPackage1.getTanentID());
		model = CommonUtil.checkExistingCarAndDriver(tenantPackageList, carLength, driverLength, carIndex, driverIndex);
		String error = (String) model.get("error");
		if (error.equals("true")) {
		} else {
			if (!car.equals("")) {
				tenantPackage1.setCarList(car);
			}
			if (!driver.equals("")) {
				tenantPackage1.setDriverList(driver);
			}
			tenantPackage1.setRemarks(remarks);
			Tenant company = tenantService.getById(tenantPackage1.getTanentID());
			List<User> user = userService.userByTanentID(tenantPackage1.getTanentID());
			if (user != null && user.size() > 0) {
				user.get(0).setEnabled(0);
			}
			if (company != null) {
				company.setStatus(0);
			}
			int validity = tenantPackage1.getValidity();
			if (validity > 0) {
				long fromDate = new java.util.Date().getTime();
				java.sql.Timestamp fromDateTime = new java.sql.Timestamp(fromDate);
				//tenantPackage1.setFromDate(fromDateTime);
				Calendar c = Calendar.getInstance();
				c.setTime(new Date()); // Now use today date.
				c.add(Calendar.DATE, validity); // Adding days
				c.set(Calendar.HOUR_OF_DAY, 23);
				c.set(Calendar.MINUTE, 59);
				c.set(Calendar.SECOND, 59);
				c.set(Calendar.MILLISECOND, 0);
				Date toDateFormat = c.getTime();
				long toDate = toDateFormat.getTime();
				java.sql.Timestamp toDateTime = new java.sql.Timestamp(toDate);
				//tenantPackage1.setToDate(toDateTime);
			}
			try {
				tenantPackage1.setStatus(0);
				tenantPackage1.setApproved(true);
				packageCreateService.update(tenantPackage1);
				tenantService.update(company);
				userService.update(user.get(0));
				System.out.println("success");
			} catch (ServiceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void deletePendingVerification(){
		String id = "2";
		String type ="car";
		TenantPackage tenantPackage1 = packageCreateService.getById(Long.valueOf(id));
		if (type.equals("car")) {
			String carNo = tenantPackage1.getCarList();
			carNo = carNo.replaceAll(",", "").replaceAll("", "");
			tenantPackage1.setCarList(carNo);
		} else if (type.equals("driver")) {
			String driverLicense = tenantPackage1.getDriverList();
			driverLicense = driverLicense.replaceAll(",","").replaceAll("", "");
			tenantPackage1.setDriverList(driverLicense);
		}
		try {
			packageCreateService.update(tenantPackage1);
			System.out.println("sucessfull");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	/*private Map<String, Object> getTenantPackageLimit(Long tanentId) {
		Map<String, Object> model = new HashMap<String, Object>();
		List<TenantPackage> tenantPackageList = null;
		try {
			tenantPackageList = packageCreateService.getListByTenantID(tanentId,"1", null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		if (tenantPackageList.size() > 0) {
			int userLimit = 0;
			int carLimit = 0;
			int driverLimit = 0;
			for (TenantPackage tanentPackage : tenantPackageList) {
				userLimit += tanentPackage.getUsers();
				carLimit += tanentPackage.getCars();
				driverLimit += tanentPackage.getDrivers();
			}
		}
		return model;
	}*/

	public List<Tenant> getCompanyList() throws ServiceException {
		List<Tenant> companies = tenantService.list();
		List<Tenant> newCompanyList = new ArrayList<Tenant>();
		if (companies.size() > 0) {
			for (Tenant companyBean : companies) {
				int userCount = userService.listOfUserByTanentID(companyBean.getId()).size();
				int driverCount = driverService.listOfDriver(companyBean.getId()).size();
				int carCount = carService.findAllCars(companyBean.getId()).size();
				if (userCount == 0 || driverCount == 0 || carCount == 0) {
					//companyBean.setTypeOption(true);
				} else {
					//companyBean.setTypeOption(false);
				}
				newCompanyList.add(companyBean);
			}
		}
		return newCompanyList;
	}
}
