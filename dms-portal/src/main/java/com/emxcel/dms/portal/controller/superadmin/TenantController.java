package com.emxcel.dms.portal.controller.superadmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

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
import com.emxcel.dms.core.business.services.companymaster.CompanyTypeService;
import com.emxcel.dms.core.business.services.country.CountryService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.state.StateService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.trip.TripStatusService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Jimit Patel
 */
@Controller
public class TenantController {

	private static final Logger logger = Logger.getLogger(TenantController.class);

	/**
	 * Autowired CompanyService.
	 */
	@Inject
	private TenantService tenantService;

	/**
	 * Autowired UserService.
	 */
	@Inject
	private UserService userService;

	/**
	 * Autowired CountryService.
	 */
	@Inject
	private CountryService countryService;

	/**
	 * Autowired StateService.
	 */
	@Inject
	private StateService stateService;

	/**
	 * Autowired CityService.
	 */
	@Inject
	private CityService cityService;

	/**
	 * Autowired UserRoleService.
	 */
	@Inject
	private UserRoleService userRoleService;

	/**
	 * Autowired PackageCreateService.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * Autowired carService.
	 */
	@Inject
	private CarService carService;

	/**
	 * TripStatusService.
	 */
	@Inject
	private TripStatusService tripStatusService;

	/**
	 * package DriverService.
	 */
	@Inject
	private DriverService driverService;

	@Inject
	private CompanyTypeService companyTypeService;

	/**
	 * Created By: Johnson Chunara Date:26-1-2017 Use: for list of companies
	 *
	 * @param company
	 * @param result
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.TENANT_LIST, method = RequestMethod.GET)
	public ModelAndView getCompanyList(@ModelAttribute("command") final Tenant company, final BindingResult result) {
		Map<String, Object> model = new HashMap<>();
		List<Tenant> tenantList = getCompanyList();
		if (tenantList != null) {
 			model.put("tenantList", tenantList);
		}
		return new ModelAndView(ViewConstants.TENANT_LIST, model);
	}

	/**
	 * Created By: Naresh Banda Date 26-1-2017. Use: get company List
	 *
	 * @return company list
	 */
	public List<Tenant> getCompanyList() {
		List<Tenant> tenants = tenantService.list();
		List<Tenant> newCompanyList = new ArrayList<>();
		if (!tenants.isEmpty()) {
			for (Tenant tenantBean : tenants) {
				int tenantPackageSize = 0;
				try {
					tenantPackageSize = packageCreateService.checkPackageCount(tenantBean.getId());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (tenantPackageSize > 0) {
					tenantBean.setPackageEnable(true);
				} else {
					tenantBean.setPackageEnable(false);
				}
				int userCount = 0;
				try {
					if (tenantBean.getId() != null) {
						userCount = userService.listOfUserByTanentID(tenantBean.getId()).size();
					}
				} catch (Exception e) {
					logger.error(Constants.EXCEPTION_THROW, e);
				}
				int tripStatusCount = 0;
				if (tenantBean.getId() != null) {
					tripStatusCount = tripStatusService
							.listOfStatusWithTanentIDCountOfLiveAndPending(tenantBean.getId());
				}
				int driverCount = 0;
				if (tenantBean.getId() != null) {
					driverCount = driverService.listOfDriver(tenantBean.getId()).size();
				}
				int carCount = 0;
				if (tenantBean.getId() != null) {
					carCount = carService.findAllCars(tenantBean.getId()).size();
				}
				if (userCount == 0 || tripStatusCount == 0 || driverCount == 0 || carCount == 0) {
					tenantBean.setTypeDeleteOption(true);
				} else {
					tenantBean.setTypeDeleteOption(false);
				}
				newCompanyList.add(tenantBean);
			}
		}
		return newCompanyList;
	}

	/**
	 * Created By: Johnson Chunara Date:26-1-2017. Use: To add new company
	 *
	 * @param company
	 * @param result
	 * @param id
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.TENANT, method = RequestMethod.GET)
	public ModelAndView addTenant(@ModelAttribute("command") Tenant tenant, final BindingResult result,
			@RequestParam(value = "id", required = false) String id, final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		try {
			if (id != null && !"".equals(id)) {
				Tenant tenantById = tenantService.getById(Long.valueOf(id));
				if (tenantById != null) {
					model.put("tenant", tenantById);
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, "No Record !!!");
					return new ModelAndView(Constants.REDIRECT + UrlConstants.TENANT_LIST);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, e.getCause());
			return new ModelAndView(Constants.REDIRECT + UrlConstants.TENANT_LIST);
		}
		model.put("stateLst", stateService.list());
		model.put("cityLst", cityService.list());
		model.put("countryLst", countryService.list());
		model.put("companylist", companyTypeService.getcompanyListByStatus(true));
		return new ModelAndView(ViewConstants.TENANT, model);
	}

	/**
	 * Created By: Johnson Chunara Date:26-1-2017. Use: To save Tenant Details
	 *
	 * @param tenant
	 * @param result
	 * @param submitType
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_TENANT, method = RequestMethod.POST)
	public ModelAndView saveTenant(@ModelAttribute("command") final Tenant tenant, final BindingResult result,
			@RequestParam(value = "submit", required = false) String submitType, final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		int serviceNo = 0;
		int panNo = 0;
		int emailId = 0;
		User useremailId = null;
		int emailId1 = 0;
		int emailId2 = 0;
		int contactNo = 0;
		int companyName = 0;
		Long companyId = null;
		try {
			serviceNo = tenantService.getCompanyServiceNo(tenant.getServicetaxno(), tenant.getId());
			panNo = tenantService.getCompanyPanNo(tenant.getPanno(), tenant.getId());
			emailId = tenantService.getCompanyEmailId(tenant.getEmailid(), tenant.getId());
			useremailId = userService.getCompanyEmailId(tenant.getEmailid());
			emailId1 = tenantService.getCompanyEmailId(tenant.getEmail1(), tenant.getId());
			emailId2 = tenantService.getCompanyEmailId(tenant.getEmail2(), tenant.getId());
			contactNo = tenantService.getCompanyContactNo(tenant.getContactno(), tenant.getId());
			companyName = tenantService.checkCompanyName(tenant.getCompanyname(), tenant.getId());
			if (companyName > 0 || contactNo > 0 || serviceNo > 0 || panNo > 0 || useremailId != null || emailId > 0
					|| emailId1 > 0 || emailId2 > 0) {
				ra.addFlashAttribute("tenant", tenant);
				ra.addFlashAttribute("countryLst", countryService.list());
				ra.addFlashAttribute("stateLst", stateService.list());
				ra.addFlashAttribute("cityLst", cityService.list());

				model.setViewName(Constants.REDIRECT + UrlConstants.TENANT);
			}
			if (companyName > 0) {
				ra.addFlashAttribute("companyNameErrorMsg", "Company Name is already in the record !!!");
				return model;
			} else if (contactNo > 0) {
				ra.addFlashAttribute("contactNoErrorMsg", "Contact No is already in the record !!!");
				return model;
			} else if (tenant.getId() == null && useremailId != null) {
				ra.addFlashAttribute("emailIdErrorMsg", "Email Id is already in the record !!!");
				return model;
			} else if (emailId > 0) {
				ra.addFlashAttribute("emailIdErrorMsg", " Email Id is already in the record !!!");
				return model;
			} else if (emailId1 > 0) {
				ra.addFlashAttribute("emailId1ErrorMsg", "Email Id 1 is already in the record !!!");
				return model;
			} else if (emailId2 > 0) {
				ra.addFlashAttribute("emailId2ErrorMsg", "Email Id 2 is already in the record !!!");
				return model;
			} else if (serviceNo > 0) {
				ra.addFlashAttribute("serviceErrorMsg", "Service Tax No is already in the record !!!");
				return model;
			} else if (panNo > 0) {
				ra.addFlashAttribute("panErrorMsg", "Pan No is already in the record !!!");
				return model;
			}
			if (tenant.getId() != null) {
				if (submitType != null && ("Create,Create").equals(submitType)) {
					tenantService.updatTenant(tenant,1);
					ra.addFlashAttribute(Constants.MESSAGE, "Tenant Created Successfully !!!");
				} else {
					tenantService.updatTenant(tenant, tenant.getStatus());
					ra.addFlashAttribute(Constants.MESSAGE, "Update Tenant Successfully !!!");
				}
			} else {
				if (submitType != null && submitType.equals("SaveDraft,SaveDraft")) {
				 companyId = tenantService.savetenant(tenant,2);
				} else {
				 companyId = tenantService.savetenant(tenant,1);
				}
				userService.saveUser(tenant,companyId);
				ra.addFlashAttribute(Constants.MESSAGE, "New Tenant Added Successfully !!!");
				ra.addFlashAttribute("support", "new");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, e.getCause());
			ra.addFlashAttribute("tenantList", getCompanyList());
			model.setViewName(Constants.REDIRECT + UrlConstants.TENANT_LIST);
			return model;
		}
		ra.addFlashAttribute("tenantList", getCompanyList());
		model.setViewName(Constants.REDIRECT + UrlConstants.TENANT_LIST);
		return model;
	}

	/**
	 * Created By: Johnson Chunara Date:26-1-2017. Use: To delete Tenant
	 *
	 * Details
	 *
	 * @param tenant
	 * @param result
	 * @param id
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = UrlConstants.DELETE_TENANT, method = RequestMethod.GET)
	public ModelAndView deleteTenant(@ModelAttribute("command") Tenant tenant, final BindingResult result,
			@RequestParam(value = "id", required = false) String id, @RequestParam(value = "status", required = false) String status,final RedirectAttributes ra) {
		try {
			if (id != null && !"".equals(id) && status != null && !"".equals(status)) {
				Tenant tenantById = tenantService.getById(Long.valueOf(id));
				if (tenantById.getStatus() == 2) {
					User user = userService.getByIdTanent(tenantById.getId());
					if(user != null){
						userService.delete(user);
						tenantService.delete(tenantById);
					}
					ra.addFlashAttribute(Constants.MESSAGE, tenantById.getTanentid()+" Draft Tenant Deleted Successfully !!!");
				}else{
					tenantService.deleteTenant(tenantById,1);
					ra.addFlashAttribute(Constants.MESSAGE, "Tenant Inactive Successfully !!!");
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, e.getCause());
			return new ModelAndView(Constants.REDIRECT + UrlConstants.TENANT_LIST);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.TENANT_LIST);
	}

	/**
	 * Created By: Naresh Banda Date: 18-1-2017. Use: to get the tenant limit
	 * details
	 *
	 * @param tanentId
	 * @param status
	 * @return list
	 */
	private Map<String, Object> getTenantPackageLimit(final Long tanentId, String status) {
		Map<String, Object> model = new HashMap<>();
		List<TenantPackage> tenantPackageList = null;
		if (tanentId != null) {
			if (status.equals(Constants.FALSE_AS_STRING)) {
				try {
					tenantPackageList = packageCreateService.getByTenantIDLimitOneWithOutStatus(tanentId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				String statusNew = "1";
				try {
					tenantPackageList = packageCreateService.getListByTenantID(tanentId, statusNew, null);
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
			if (tenantPackageList != null && !tenantPackageList.isEmpty()) {
				int userLimit = 0;
				int carLimit = 0;
				int driverLimit = 0;
				for (TenantPackage tanentPackage : tenantPackageList) {
					userLimit += tanentPackage.getUsers();
					carLimit += tanentPackage.getCars();
					driverLimit += tanentPackage.getDrivers();
				}
				model.put("userLimit", userLimit);
				model.put("carLimit", carLimit);
				model.put("driverLimit", driverLimit);
				model.put("size", tenantPackageList.size());
			}
		}
		return model;
	}
	@RequestMapping(value = UrlConstants.CITY_CODE, method = RequestMethod.POST)
	@ResponseBody
	public City getcityCode(@RequestParam(value = "cityId", required = false) final String cityId) {
		return cityService.getById(Long.valueOf(cityId));
	}
}