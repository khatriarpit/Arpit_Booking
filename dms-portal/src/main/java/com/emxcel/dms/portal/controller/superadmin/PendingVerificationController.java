package com.emxcel.dms.portal.controller.superadmin;

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
import com.emxcel.dms.core.business.modules.email.Email;
import com.emxcel.dms.core.business.modules.email.HtmlEmailSender;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.country.CountryService;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.business.services.state.StateService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.superadmin.SellerService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.seller.SellerModel;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author emxcelsolution
 *
 */
/**
 * @author root
 *
 */
@Controller
public class PendingVerificationController {

	private static final Logger logger = Logger.getLogger(PendingVerificationController.class);

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
	 * Inject TenantService.
	 */
	@Inject
	private TenantService tenantService;

	/**
	 * Inject UserService.
	 */
	@Inject
	private UserService userService;

	/**
	 * HtmlEmailSender.
	 */
	@Inject
	private HtmlEmailSender htmlEmailSender;

	/**
	 * Inject PackageCreateService.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * Inject EmailLogService.
	 */
	@Inject
	private EmailLogService emailLogService;

	@Inject
	private SellerService sellerService;

	/**
	 * Created By: Naresh Banda. Date: 28-12-2016 Use: For list of pending
	 * verifications
	 *
	 * @param tenantPackage1
	 *            TenantPackage
	 * @param result
	 *            BindingResult
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.PENDING_VERIFICATION_LIST, method = RequestMethod.GET)
	public ModelAndView getPendingVerificationList(@ModelAttribute("command") final TenantPackage tenantPackage,
			final BindingResult result) {
		ModelAndView model = new ModelAndView();
		List<TenantPackage> tenantPackageLst = null;
		try {
			tenantPackageLst = packageCreateService.getPendingVerificationByTanentID();
		} catch (ServiceException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			e.printStackTrace();
		}
		List<TenantPackage> tenantPackageListNew = new ArrayList<>();
		if (tenantPackageLst != null && !tenantPackageLst.isEmpty()) {
			for (TenantPackage tenantPackageList : tenantPackageLst) {
				Tenant tenant = tenantService.getById(tenantPackageList.getTanentID());
				if (tenant != null) {
					tenantPackageList.setTenant(tenant);
				}
				tenantPackageListNew.add(tenantPackageList);
			}
		}
		model.addObject("pendingVerificationList", tenantPackageListNew);
		model.setViewName(ViewConstants.PENDING_VERIFICATION_LIST);
		return model;
	}

	/**
	 * Created By: Naresh Banda. Date: 28-12-2016 Use: To Open Pending
	 * Verification
	 *
	 * @param tenantPackage
	 *            tenantPackage
	 * @param result
	 *            result
	 * @param id
	 *            tenantPackageid
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.PENDING_VERIFICATION_PAGE, method = RequestMethod.GET)
	public ModelAndView getPendingVerificationPage(@ModelAttribute("command") TenantPackage tenantPackage,
			@ModelAttribute("command1") SellerModel sellerModel, final BindingResult result,
			@RequestParam(value = "id", required = false) final String id, final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		try {
			if (id != null && !"".equals(id)) {
				tenantPackage = packageCreateService.getById(Long.valueOf(id));
				String[] carLength = null;
				String[] driverLength = null;
				if (tenantPackage != null) {
					carLength = tenantPackage.getCarList().split(",");
					driverLength = tenantPackage.getDriverList().split(",");
					Tenant tenant = tenantService.getById(tenantPackage.getTanentID());
					if (tenant != null) {
						tenantPackage.setTenant(tenant);
					}
				}
				Map<String, Object> userLimit = getTenantPackageLimit(tenantPackage.getTanentID(),
						Constants.FALSE_AS_STRING, tenantPackage.getId());
				Integer noofUser = (Integer) userLimit.get("userLimit");
				Integer noofCar = (Integer) userLimit.get("carLimit");
				Integer noofDriver = (Integer) userLimit.get("driverLimit");
				model.addObject("userLimit", noofUser);
				model.addObject("carLimit", noofCar);
				model.addObject("driverLimit", noofDriver);
				model.addObject("carStartRow", carLength.length);
				model.addObject("driverStartRow", driverLength.length);
				model.addObject("pendingVerification", tenantPackage);
				model.addObject("carList", tenantPackage.getCarList());
				model.addObject("driverList", tenantPackage.getDriverList());
				model.addObject("tenantPackageID", tenantPackage.getId());
				model.addObject("stateLst", stateService.list());
				model.addObject("cityLst", cityService.list());
				model.addObject("countryLst", countryService.list());
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!! ");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.PENDING_VERIFICATION_LIST);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!! ");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.PENDING_VERIFICATION_LIST);
		}
		model.setViewName(ViewConstants.PENDING_VERIFICATION_PAGE);
		return model;
	}

	/**
	 * Created By: Naresh Banda. Date: 28-12-2016 Use: when pending
	 * verifications approves
	 *
	 * @param tenantPackage
	 * @param result
	 * @param idss
	 * @param car
	 * @param driver
	 * @param remarks
	 * @param carIndex
	 * @param driverIndex
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.PENDING_VERIFICATION_APPROVED, method = RequestMethod.POST)
	public Map<String, Object> approveVerification(final HttpSession session,
			@ModelAttribute("command") TenantPackage tenantPackage, final BindingResult result,
			@RequestParam(value = "id", required = false) final String id,
			@RequestParam(value = "car", required = false) final String car,
			@RequestParam("driver") final String driver, @RequestParam("remarks") final String remarks,
			@RequestParam("carIndex") final String carIndex, @RequestParam("driverIndex") final String driverIndex,
			@RequestParam("tenantPackageID") final String tenantPackageID) {
		Map<String, Object> model = new HashMap<>();
		User user = (User) session.getAttribute("user");
		try {
			if (id != null && !"".equals(id)) {
				TenantPackage tenantPackageById = packageCreateService.getById(Long.valueOf(id));
				String[] carLength = null;
				if (car != null && !"".equals(car)) {
					carLength = car.split(",");
				}
				String[] driverLength = null;
				if (driver != null && !"".equals(driver)) {
					driverLength = driver.split(",");
				}
				List<TenantPackage> tenantPackageList = packageCreateService
						.getListByNotInThisTenantID(tenantPackageById.getTanentID());
				model = CommonUtil.checkExistingCarAndDriver(tenantPackageList, carLength, driverLength, carIndex,
						driverIndex);
				String error = (String) model.get("error");
				if (Constants.TRUE_AS_STRING.equals(error)) {
					return model;
				} else {
					if (car != null && !"".equals(car)) {
						tenantPackageById.setCarList(car);
					}
					if (driver != null && !"".equals(driver)) {
						tenantPackageById.setDriverList(driver);
					}
					tenantPackageById.setRemarks(remarks);
					Tenant tenant = tenantService.getById(tenantPackageById.getTanentID());
					if (tenantPackageById.getDefaultFlag()) {
						List<User> user1 = null;
						user1 = userService.userByTanentID(tenantPackageById.getTanentID());
						if (user1 != null && user1.size() > 0) {
							user1.get(0).setEnabled(0);
							userService.update(user1.get(0));
						}
						if (tenant.getStatus() == 1) {
							tenantService.updatTenant(tenant, 0);
						}
					}
					tenantPackageById.setApprovedFlag(true);
					packageCreateService.update(tenantPackageById);
					if (tenant != null && tenant.getEmailid() != null && !("").equals(tenant.getEmailid())) {
						String subject = "User Confirmation";
						String from = Constants.COMPANY_NAME;
						String fromAddress = Constants.COMPANY_EMAIL_ID;
						String template = Constants.CUSTOM_TEMPLATE;
						String toAddress = tenant.getEmailid();
						Map<String, String> map = new HashMap<>();
						String content = "Your Verification Has been done by Super Admin Side.<br> Please Login in with your username and password."
								+ "<br><b>Username : </b>" + tenant.getEmailid() + "<br>";
						map.put("HEADER", subject);
						map.put("CONTENT", content);
						map.put("FOOTER", "Thanks<br>" + from);
						if (toAddress != null && !"".equals(toAddress)) {
							Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress,
									template, map, user.getId());
							Email email = (Email) mapEmail.get("email");
							EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
							emailLog = emailLogService.saveEmailLog(emailLog);
							htmlEmailSender.send(email, emailLog);
						}
						model.put(Constants.MESSAGE, "Successfully Approved !!!");
					} else {
						model.put(Constants.MESSAGE, "Successfully Approved. But Email Not Sent To Tenant !!!");
					}
					return model;
				}
			} else {
				model.put(Constants.MESSAGE, " Something Went Wrong !!!");
				return model;
			}
		} catch (

		Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			model.put(Constants.MESSAGE, " Something Went Wrong !!!");
			return model;
		}
	}

	/**
	 * Created By: Naresh Banda. Date: 28-12-2016
	 *
	 * @param tenantPackage
	 * @param result
	 * @param type
	 * @param ids
	 * @param id
	 */
	@RequestMapping(value = UrlConstants.PENDING_VERIFICATION_DELETE, method = RequestMethod.GET)
	public ModelAndView deletePendingVerification(final HttpSession session,
			@RequestParam(value = "id", required = false) final String id, final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		try {
			if (id != null && !"".equals(id)) {
				User userSession = (User) session.getAttribute("user");
				TenantPackage tenantPackage = packageCreateService.getById(Long.valueOf(id));
				if (tenantPackage != null) {
					packageCreateService.deletePendingVerification(tenantPackage);
					Tenant tenant = tenantService.getById(tenantPackage.getTanentID());
					if (tenant != null) {
						String subject = "User Confirmation Reject";
						String from = Constants.COMPANY_NAME;
						String fromAddress = Constants.COMPANY_EMAIL_ID;
						String template = Constants.CUSTOM_TEMPLATE;
						String toAddress = tenant.getEmailid();
						Map<String, String> map = new HashMap<>();
						String content = "Your Verification is Reject by Super Admin Side.<br> Please Contact to Super Admin.<br><br>";
						map.put("HEADER", subject);
						map.put("CONTENT", content);
						map.put("FOOTER", "Thanks<br>" + from);
						if (!toAddress.equals("")) {
							Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress,
									template, map, userSession.getId());
							Email email = (Email) mapEmail.get("email");
							EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
							emailLog = emailLogService.saveEmailLog(emailLog);
							htmlEmailSender.send(email, emailLog);
						}
						ra.addFlashAttribute(Constants.MESSAGE, "Successfully Delete !!!");
					}

				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			model.setViewName(Constants.REDIRECT + UrlConstants.PENDING_VERIFICATION_LIST);
		}
		model.setViewName(Constants.REDIRECT + UrlConstants.PENDING_VERIFICATION_LIST);
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 18-1-2017. Use: to get the tenant limit
	 * details
	 *
	 * @param tanentId
	 * @param status
	 * @return list
	 */
	private Map<String, Object> getTenantPackageLimit(final Long tanentId, String status, final Long packageTenantID) {
		Map<String, Object> model = new HashMap<>();
		List<TenantPackage> tenantPackageList = null;
		try {
			tenantPackageList = packageCreateService.getListByTenantID(tanentId, "0", packageTenantID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tenantPackageList != null && tenantPackageList.size() > 0) {
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
		}
		return model;
	}

	/**
	 * @param sellerModel
	 * @param tenant
	 * @param result
	 * @return
	 */
	@RequestMapping(value = UrlConstants.SELL_ALL_SELLER, method = RequestMethod.GET)
	public ModelAndView viewSellerDetailsPage(@ModelAttribute("command") final SellerModel sellerModel,
			final Tenant tenant, final BindingResult result) {

		Map<String, Object> model = new HashMap<>();
		if (tenant != null) {
			List<Tenant> tenantDetails = tenantService.list();
			model.put("tenantDetailsList", tenantDetails);
		}
		return new ModelAndView(ViewConstants.VIEW_SELLER, model);
	}

	/**
	 * Created By: Jimit Patel Date:9-3-2017 Use: to save seller Details
	 * 
	 * @param sellerModel
	 * @param result
	 * @param rDirectAttribute
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_SELLER, method = RequestMethod.POST)
	public ModelAndView saveSeller(@ModelAttribute("command1") final SellerModel sellerModel,
			final BindingResult result, final RedirectAttributes rDirectAttribute, final HttpSession session)
			throws ServiceException {
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		if (sellerModel != null) {
			sellerService.saveSellerDetail(sellerModel, user.getTanentID());
			rDirectAttribute.addFlashAttribute(Constants.MESSAGE, "Seller Information Saved Successfully");
		}
		if (sellerModel.getId() != null) {
			model.setViewName(Constants.REDIRECT + UrlConstants.SELL_ALL_SELLER);
		}
		return model;
	}

	/**
	 * Created By: Jimit Patel Date:10-3-2017 Use: to edit seller Details
	 * 
	 * @param sellerModel
	 * @param result
	 * @param rDirectAttribute
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.EDIT_SELLER, method = RequestMethod.GET)
	public ModelAndView editseller(@ModelAttribute("command") SellerModel sellerModel,
			@RequestParam(value = "sellerId", required = false) final String id, final BindingResult result,
			final RedirectAttributes rDirectAttribute) {
		ModelAndView model = new ModelAndView();
		Map<String, Object> map = new HashMap<>();
		try {
			if (id != null) {
				sellerModel = sellerService.getById(Long.valueOf(id));
				Tenant tenant = tenantService.getById(sellerModel.getTanentID());
				if (tenant != null) {
					map.put("tanentID", tenant.getTanentid());
					map.put("tenantName", tenant.getFirstName1());
				}
				map.put("stateLst", stateService.list());
				map.put("cityLst", cityService.list());
				map.put("countryLst", countryService.list());
				map.put("sellerModel", sellerModel);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			model.setViewName(Constants.REDIRECT + UrlConstants.SELL_ALL_SELLER);
		}
		return new ModelAndView(ViewConstants.EDIT_SELLER, map);
	}
}