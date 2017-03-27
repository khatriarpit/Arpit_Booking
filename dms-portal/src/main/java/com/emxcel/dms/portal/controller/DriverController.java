package com.emxcel.dms.portal.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.country.CountryService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.state.StateService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.ImageServletController;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

@Controller
public class DriverController extends ImageServletController {

	private static final Logger logger = Logger.getLogger(DriverController.class);

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * **Autowired service of driverService **.
	 */
	@Inject
	private DriverService driverService;

	/**
	 * **Autowired service of carService **.
	 */
	@Inject
	private CarService carService;

	/**
	 * **Autowired service of cityService **.
	 */
	@Inject
	private CityService cityService;

	/**
	 * **Autowired service of packageCreateService **.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * **Autowired service of countryService **.
	 */
	@Inject
	private CountryService countryService;

	/**
	 * **Autowired service of stateService **.
	 */
	@Inject
	private StateService stateService;

	@Inject
	private ClientModelService clientModelService;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: Method for list of
	 * driver.
	 *
	 * @param driverDetail
	 *            **Driver Model Object**.
	 * @param session
	 *            **HttpSession Object**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DRIVER_LIST, method = RequestMethod.GET)
	private ModelAndView listofdriver(@ModelAttribute("command") final Driver driverDetail, final BindingResult result,
									  final HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> model = new HashMap<>();
		model.put("DriverDTL", driverService.listOfDriver(user.getTanentID()));
		return new ModelAndView(ViewConstants.DRIVER_LIST, model);
	}

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: Method for
	 * @param id
	 *            **id of Driver Model**.
	 * @param driverDetail
	 *            **Driver Model Object**.
	 * @param session
	 *            **HttpSession Object**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DRIVER, method = RequestMethod.GET)
	private ModelAndView adddriver(@RequestParam(value = "driId", required = false) final String id,
								   @ModelAttribute("command") final Driver driverDetail, final BindingResult result, final HttpSession session,
								   final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		User user = (User) session.getAttribute("user");
		try {
			List<TenantPackage> tenantPackageList = null;
			if (id != null && !"".equals(id)) {
				tenantPackageList = packageCreateService.getTenantPackageForAdminAndUser(user.getTanentID(),
						"0", null);
				Driver driver = driverService.getById((Long.valueOf(id)));
				model.put("Driver", driver);
				model.put("countryLst", countryService.list());
				model.put("stateLst", stateService.list());
				model.put("cityLst", cityService.list());
				model.put("DriverDTL", driverService.listOfDriver(CommonUtil.getUser(session).getTanentID()));
			} else {
				tenantPackageList = packageCreateService.getListByTenantID(user.getTanentID(), "0",
						null);
			}
			if (user != null) {
				model.put("tenantPackageList", tenantPackageList);
			}
			model.put("countryLst", countryService.list());
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_LIST);
		}
		return new ModelAndView(ViewConstants.DRIVER, model);
	}

	/**
	 * created By: Nittin Patel Date:15-12-2016 Use:to save driver data.
	 *
	 * @param session
	 *            **HttpSession object**
	 * @param driverDetail
	 *            **to bind the data with JSP**
	 * @param result
	 *            **it binds with binding result**
	 * @return ModelAndView
	 * @throws IOException
	 *             **IOException**.
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_DRIVER, method = RequestMethod.POST)
	private ModelAndView saveDriver(final HttpSession session, @ModelAttribute("command") final Driver driverDetail,
									final BindingResult result, final RedirectAttributes ra) {
		User user = (User) session.getAttribute("user");
		try {
			String licenseNo = driverDetail.getLicenseNo();
			Driver driver = driverService.getDriverByLicenseNo(licenseNo,driverDetail.getId());
			if (driver == null) {
				driverService.saveDriverImages(driverDetail,session);
				int noOfDriver = 0;
				List<TenantPackage> tenantPackageList = packageCreateService
						.getTenantPackageForAdminAndUser(user.getTanentID(), "0", driverDetail.getTenantPackageID());
				List<Driver> driverList = driverService.listOfDriver(CommonUtil.getUser(session).getTanentID());
				if (tenantPackageList != null && tenantPackageList.get(0) != null
						&& tenantPackageList.get(0).getDrivers() != 0) {
					noOfDriver = tenantPackageList.get(0).getDrivers();
				}
				if (driverList.size() <= noOfDriver) {
					driverService.save(driverDetail);
				} else {
					ra.addFlashAttribute(Constants.MESSAGE,
							"You Can not Add More than" + "  " + noOfDriver + " Drivers !!!");
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "License No already exist");
				ra.addFlashAttribute("DriverDTL", driverDetail);
				return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_LIST);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_LIST);
	}

	/**
	 * created By: Nitin Patel Date:15-12-2016 Use:To delete data.
	 * @param id
	 *            **id of Druiver Model**.
	 * @param driverDetail
	 *            **to bind the data with JSP**
	 * @return ModelAndView
	 * @throws ServiceException
	 *             **ServiceException**.
	 */
	@RequestMapping(value = UrlConstants.DELETE_DRIVER, method = RequestMethod.GET)
	private ModelAndView deleteDriver(@RequestParam(value = "driId", required = false) final String id,
									   @ModelAttribute("command") final Driver driverDetail, final BindingResult result,
									   final RedirectAttributes ra) {
		try {
			if (id != null && !id.equals("")) {
				Driver driver = driverService.getById(Long.valueOf(id));
				List<ClientModel> clientModel = clientModelService.getDriverByClient(driver);
				if (clientModel.isEmpty()) {
					driverService.setStatusforDelete(driver);
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, "You Can Not Delete Driver Because of Pending Trips");
				}

			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_LIST);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_LIST);
	}

	/**
	 * created By: Nitin Patel Date:15-12-2016. Use: Method for delete driver
	 * image from database
	 *
	 * @param id
	 *            **id of driver model**.
	 * @return String
	 */
	@RequestMapping(value = { "/deleteDriverImage" }, method = RequestMethod.POST)
	@ResponseBody
	public String deleteDriverImage(@RequestParam(value = "id", required = false) final Long id) {
		String returnType = "";
		try {
			if (id != 0) {
				Driver driver = driverService.getById(id);
				String rootPath = CommonUtil.LOCATION;
				if (driver != null) {
					rootPath = rootPath + File.separator + driver.getAdharCardImage();
					File file = new File(rootPath);
					if (file.exists()) {
						file.delete();
						returnType = Constants.TRUE_AS_STRING;
					}
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			return null;
		}
		return returnType;
	}

	/**
	 * created By: Nitin Patel Date:15-12-2016
	 *
	 * Use:To change driver.
	 *
	 * @param client
	 *            **to bind the data with JSP**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DRIVER_CHANGE, method = RequestMethod.GET)
	private ModelAndView changedriver(@ModelAttribute("command") final ClientModel client, final BindingResult result) {
		Map<String, Object> model = new HashMap<>();
		return new ModelAndView(ViewConstants.CHANGE_DRIVER, model);
	}

	/**
	 * created By: Nitin Patel Date:15-12-2016
	 *
	 * Use:To change driver.
	 *
	 * @param client
	 *            **to bind the data with JSP**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CHANGE_DRIVER, method = RequestMethod.POST)
	private ModelAndView changeDriver(@ModelAttribute("command") final ClientModel client, final BindingResult result,
									  final RedirectAttributes ra) {
		String temp = "old";
		Long driid = client.getDriver().getId();
		String tripid = client.getTripId();
		Driver driver = driverService.getById(driid);
		try {
			SMSSend.sendOtp(0L, tripid, temp);
			temp = "new";
			SMSSend.sendOtp(Long.valueOf(driver.getContactNo()), tripid, temp);
		} catch (Exception ex) {
			logger.error(Constants.EXCEPTION_THROW, ex);
			ex.getCause();
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_CHANGE);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.DRIVER_CHANGE);
	}

	/**
	 * created By: Nitin Patel Date:26-09-2016 Use:for open screen of assign
	 * driver to car.
	 *
	 * @param carDetail
	 *            **to bind the data with JSP**
	 * @param session
	 *            **HttpSession object**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CAR_DRIVER, method = RequestMethod.GET)
	private ModelAndView carDriver(@ModelAttribute("command") final Car carDetail, final BindingResult result,
								   final HttpSession session) {
		Map<String, Object> model = new HashMap<>();
		model.put("carNoList", carService.findAllCarsNoDrivers(CommonUtil.getUser(session).getTanentID()));
		model.put("DriverList", driverService.listOfDriver(CommonUtil.getUser(session).getTanentID()));
		return new ModelAndView(ViewConstants.CAR_DRIVER, model);
	}

	/**
	 * created By: Nitin Patel Date:15-12-2016 Use:To check color.
	 *
	 * @param driColor
	 *            **to bind the data with JSP**
	 * @param driid
	 *            **driid of Driver Model**.
	 * @return boolean
	 */
	@RequestMapping(value = "/checkDriverColor", method = RequestMethod.POST)
	@ResponseBody
	public boolean getDriverColor(@RequestParam("driColor") final String driColor,
								  @RequestParam(value = "driid", required = false) final String driid) {
		Driver driver = driverService.getDriverColor(driColor);
		boolean status;
		if (driver != null && (driid == null || "".equals(driid))) {
			status = Constants.TRUE;
		} else if (driver != null && driver.getId() == Long.valueOf(driid)) {
			status = Constants.FALSE;
		} else if (driver == null && driid != null) {
			status = Constants.FALSE;
		} else if (driver == null && (driid == null || "".equals(driid))) {
			status = Constants.FALSE;
		} else {
			status = Constants.TRUE;
		}
		return status;
	}

	@RequestMapping(value = UrlConstants.LIST_TENANT_PACKAGE_CAR_OR_DRIVER_LIST_AJAX, method = RequestMethod.POST)
	@ResponseBody
	public List<Object> getCarNoORDriverLicense(@RequestParam("packageID") final String packageID,
												@RequestParam(value = "type", required = false) final String type, final HttpSession session,
												@RequestParam(value = "typeCheck", required = false) String typeCheck) {
		User userSession = (User) session.getAttribute("user");
		List<TenantPackage> tenantPackageList = null;
		try {
			tenantPackageList = packageCreateService.getTenantPackageForAdminAndUser(userSession.getTanentID(), "0",
					Long.valueOf(packageID));
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Object> arr = null;
		List<Car> carList = null;
		List<Driver> driverList = null;
		String[] num = null;
		if (type.equals("carNo") && tenantPackageList.size() > 0) {
			try {
				carList = carService.findAllCarsList(userSession.getTanentID(), Long.valueOf(packageID));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (tenantPackageList.get(0).getCarList() != null) {
				arr = new ArrayList<Object>();
				num = tenantPackageList.get(0).getCarList().split(",");
			}
		} else {
			driverList = driverService.findAllDriversList(userSession.getTanentID(), Long.valueOf(packageID));
			if (tenantPackageList.get(0).getDriverList() != null) {
				arr = new ArrayList<Object>();
				num = tenantPackageList.get(0).getDriverList().split(",");
			}
		}
		if (num.length > 0) {
			for (int i = 0; i < num.length; i++) {
				arr.add(num[i]);
			}
		}
		if (typeCheck.equals("true")) {
			List<Object> val = new ArrayList<Object>();
			if (arr.size() > 0) {
				if (type.equals("carNo")) {
					for (Object object : arr) {
						Car car = carService.checkCarByCarNo(String.valueOf(object));
						if (car == null) {
							val.add(object);
						}
					}
				} else {
					for (Object object : arr) {
						Driver driver = driverService.getDriverByLicenseNo(String.valueOf(object),null);
						if (driver == null) {
							val.add(object);
						}
					}
				}
			}

			return val;
		}
		return arr;
	}
}
