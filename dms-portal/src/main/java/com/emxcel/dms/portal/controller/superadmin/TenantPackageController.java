
package com.emxcel.dms.portal.controller.superadmin;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.superadmin.PackageService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.superadmin.PackageModel;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Jimit Patel
 *
 */
@Controller
public class TenantPackageController {

	private static final Logger logger = Logger.getLogger(TenantPackageController.class);

	/**
	 * Autowired PackageService.
	 */
	@Inject
	private PackageService packageService;
	/**
	 * Autowired PackageService.
	 */
	@Inject
	private PackageCreateService packageCreateService;
	/**
	 * Autowired HtmlEmailSender.
	 */
	@Inject
	private HtmlEmailSender htmlEmailSender;
	/**
	 * Autowired TenantService.
	 */
	@Inject
	private TenantService tenantService;

	/**
	 * EmailLogService.
	 */
	@Inject
	private EmailLogService emailLogService;

	/**
	 * UserService.
	 */
	@Inject
	private UserService userService;

	/**
	 * UserService.
	 */
	@Inject
	private CarService carService;

	/**
	 * UserService.
	 */
	@Inject
	private DriverService driverService;

	/**
	 * Created For : List Create Package by Tenant ID
	 *
	 * @param tenantPackage
	 * @param result
	 * @param tanentID
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CREATE_PACKAGE_LIST, method = RequestMethod.GET)
	private ModelAndView listOfPackage(@ModelAttribute("command") final TenantPackage tenantPackage,
			final BindingResult result, @RequestParam(value = "tanentID", required = false) final String tenantId) {
		Map<String, Object> map = new HashMap<>();
		if (tenantId != null && !tenantId.equals("")) {
			String status = "0";
			try {
				List<TenantPackage> tenantPackageList = packageCreateService.getListByTenantID(Long.valueOf(tenantId), status, null);
				if (tenantPackageList != null && tenantPackageList.size() > 0) {
					for (TenantPackage tenantPackage1 : tenantPackageList) {
						List<TenantPackage> tenantPackage2 = packageCreateService
								.getTenantPackageValidationFrom(tenantPackage1.getId());
						if (tenantPackage2.size() == 0) {
							tenantPackage1.setEnableRenew(true);
						}
					}
				}
				map.put("PackageDTL", tenantPackageList);
				map.put("tanentID", tenantId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView(ViewConstants.LIST_CREATED_PACKAGE, map);
	}

	/**
	 * Created For : Added Create Package or (Edit Create Package by packageId)
	 *
	 * @param tenantPackage **tenantPackage**
	 * @param result **Binding Results**
	 * @param tanentID **tenantID**
	 * @return ModelAndView **ModelAndView**
	 */
	@RequestMapping(value = UrlConstants.PACKAGE_CREATE, method = RequestMethod.GET)
	private ModelAndView createTenantPackage(@ModelAttribute("command") final TenantPackage tenantPackage,
			final BindingResult result, @RequestParam(value = "tanentID", required = false) String tanentID,
			@RequestParam(value = "packageId", required = false) final String packageID, final RedirectAttributes redirect) {
		Map<String, Object> model = new HashMap<>();
		Tenant tenant = tenantService.getById(Long.valueOf(tanentID));
		try {
			if (tenant != null && !tenant.getCompanyname().equals("")) {
				if (packageID != null) {
					model.put("packages", packageCreateService.getById(Long.valueOf(packageID)));
				}
				String status = "0";
				List<TenantPackage> tenantPackageList = packageCreateService.getListByTenantID(Long.valueOf(tanentID),
						status, null);
				List<PackageModel> packageModelList = packageService.getlistByStatus();
				List<PackageModel> packageModelNewList = new ArrayList<>();
				if (packageModelList != null && !packageModelList.isEmpty()) {
					for (PackageModel packageModel : packageModelList) {
						if (tenantPackageList.size() > 0) {
							for (TenantPackage tenantPackageVo : tenantPackageList) {
								if (!packageModel.getName().equals(tenantPackageVo.getName())) {
									packageModelNewList.add(packageModel);
								}
							}
						} else {
							packageModelNewList.add(packageModel);
						}
					}
				}
				model.put("packageList", packageModelList);
				model.put("tanentID", tanentID);
				model.put("companyName", tenant.getCompanyname());
			} else {
				model.put(Constants.MESSAGE, " Something Went Wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tanentID);
		}
		return new ModelAndView(ViewConstants.PACKAGE_CREATE, model);
	}

	/**
	 * Created For : save Created Package Model
	 *
	 * @param tenantPackage
	 * @param result
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_CREATE_PACKAGE, method = RequestMethod.POST)
	private ModelAndView saveTenantPackage(final HttpSession session,
			@RequestParam(value = "fromDate", required = false) final String subFrom,
			@RequestParam(value = "toDate", required = false) final String subTo,
			@RequestParam(value = "packageId", required = false) final String packageId,
			@RequestParam(value = "tanentID", required = false) final String tanentID,
			@ModelAttribute("command") final TenantPackage tenantPackage, final BindingResult result,
			final RedirectAttributes redirect, final HttpServletRequest httpServletRequest) {
		ModelAndView model = new ModelAndView();
		String[] packageIDAndName;
		String tenantPackageName = "";
		String packageType = null;
		if (tenantPackage.getName().contains(",")) {
			packageIDAndName = tenantPackage.getName().split(",");
			tenantPackageName = packageIDAndName[0] != null ? packageIDAndName[0] : "";
			packageType = packageIDAndName[1] != null ? packageIDAndName[1] : null;
		} else {
			tenantPackageName = tenantPackage.getName();
		}
		int createPackageName = 0;
		try {
			if (tenantPackage.getPackageStatus() == 1) {
				createPackageName = packageCreateService.checkPackageName(tenantPackageName,
						tenantPackage.getId(), tenantPackage.getTanentID());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (createPackageName > 0) {
			redirect.addFlashAttribute(Constants.MESSAGE, "Package Name is already in the record !!!");
			redirect.addFlashAttribute("packages", tenantPackage);
			model.setViewName(
					Constants.REDIRECT + UrlConstants.PACKAGE_CREATE + "?tanentID=" + tenantPackage.getTanentID());
			return model;
		}
		try {
			Long tenantPackageID=null;
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date fromDate = new java.sql.Date(df.parse(subFrom).getTime());
			Date toDate = new java.sql.Date(df.parse(subTo).getTime());
			int createPackageCount = packageCreateService.checkPackageCount(tenantPackage.getTanentID());
			tenantPackageID = packageCreateService.saveTenantPackage(tenantPackage,tenantPackageName,fromDate,toDate,createPackageCount == 0, packageType);
			if (tenantPackage != null && tenantPackage.getTanentID() != null) {
				Tenant company = tenantService.getById(tenantPackage.getTanentID());
				if (company != null && company.getEmailid() != null) {
					tenantService.updateTenantAsPerPackage(company,company.getEmailFlag(),1,company.getStatus());
					tenantPackage.setId(tenantPackageID);
					createEmailContentToSend(session, company, httpServletRequest, tenantPackage);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			model.setViewName(
					Constants.REDIRECT + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tenantPackage.getTanentID());
			return model;
		}
		model.setViewName(
				Constants.REDIRECT + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tenantPackage.getTanentID());
		return model;
	}

	/**
	 * Created Use: To delete Create Packages from database
	 *
	 * @param packageModel
	 * @param result
	 * @param packageId
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_PACKAGE_CREATE, method = RequestMethod.GET)
	private ModelAndView deletePackage(@ModelAttribute("command") final PackageModel packageModel,
			final BindingResult result, @RequestParam(value = "packageId", required = false) String packageId,
			@RequestParam(value = "tanentID", required = false) String tanentID, final RedirectAttributes ra) throws Exception{
		Map<String, Object> model = new HashMap<>();
		try {
			TenantPackage tenantPackage = packageCreateService.getById(Long.valueOf(packageId));
			if (tenantPackage != null) {
				packageCreateService.updateTenantPackage(tenantPackage,1);
				model.put(Constants.MESSAGE, "Package Delete Successfully !!!");
				Tenant tenant = tenantService.getById(tenantPackage.getTanentID());
				if (tenant != null) {
					tenantService.updateTenantAsPerPackage(tenant,false,0,1);
				}
			}
		} catch (ServiceException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tanentID);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tanentID);
	}

	/**
	 * Created For : get Model by id from Package Model bean
	 *
	 * @param tenantPackage
	 * @param packageID
	 * @return PackageModel
	 */
	@RequestMapping(value = UrlConstants.GET_CREATE_PACKAGE, method = RequestMethod.POST)
	@ResponseBody
	private TenantPackage getPackage(@RequestParam(value = "packageId", required = false) final String packageID) {
		if (packageID != null) {
			return packageCreateService.getById(Long.valueOf(packageID));
		} else {
			return null;
		}
	}

	/**
	 * Created By: Naresh Banda Date: 18-1-2017. Use: to resend user resend
	 * verification url for tenant package
	 *
	 * @param id
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.RESEND_EMAIL, method = RequestMethod.POST)
	public String resendEmail(final HttpServletRequest req, final HttpSession session,
			@RequestParam(value = "id", required = false) final String id) {
		try {
			if (id != null && !"".equals(id)) {
				TenantPackage tenantPackage = packageCreateService.getById(Long.valueOf(id));
				if (tenantPackage != null) {
					Tenant company = tenantService.getById(tenantPackage.getTanentID());
					if (company != null) {
						tenantPackage.setEmailFlag(false);
						packageCreateService.update(tenantPackage);
						createEmailContentToSend(session, company, req, tenantPackage);
					}
					return Constants.TRUE_AS_STRING;
				} else {
					return Constants.FALSE_AS_STRING;
				}
			} else {
				return Constants.FALSE_AS_STRING;
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			return Constants.FALSE_AS_STRING;
		}
	}

	/**
	 * Created By: Naresh Banda Date: 31-01-2017. Use: create Email Content to
	 * Send
	 *
	 * @param company
	 * @return String
	 */
	public void createEmailContentToSend(final HttpSession session, Tenant company, final HttpServletRequest req,
			TenantPackage tenantPackage) {
		User userLogin = (User) session.getAttribute("user");
		String subject = "Verified User Confirmation";
		String from = Constants.COMPANY_NAME;
		String fromAddress = Constants.COMPANY_EMAIL_ID;
		String template = Constants.CUSTOM_TEMPLATE;
		String toAddress = company.getEmailid();
		String companyEmailAndTenantID = company.getEmailid() + " " + company.getId() + " " + tenantPackage.getId();
		companyEmailAndTenantID += " " + (tenantPackage.getDefaultFlag() ? 0 : 1);
		if (tenantPackage.getDefaultFlag()) {
			try {
				User user = userService.getCompanyEmailId(company.getEmailid());
				if (user != null && user.getTenantPackageID() == null) {
					user.setTenantPackageID(tenantPackage.getId());
					userService.update(user);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		String base64encodedString;
		String url = "";
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fromDate = "";
		String toDate = "";
		try {
			fromDate = df.format(tenantPackage.getFromDate());
			toDate = df.format(tenantPackage.getToDate());
			base64encodedString = Base64.getEncoder().encodeToString(companyEmailAndTenantID.getBytes("utf-8"));
			url = "<a target='_blank' href='"+"https://"+ req.getServerName() + ":" + req.getServerPort()
					+ UrlConstants.USER_VERIFICATION + "?tenantID=" + base64encodedString + "'>https://" + req.getServerName()
					+ ":" + req.getServerPort() + UrlConstants.USER_VERIFICATION + "?tenantID=" + base64encodedString
					+ "</a>";

			System.out.print("URL________________"+url);
		} catch (Exception e) {
			e.printStackTrace();
        }
		Map<String, String> map = new HashMap<>();
		String content = "<strong>Authentication Credentials</strong><br>";
		content += "Your Package Name : <strong>" + tenantPackage.getName() + "</strong><br>";
		content += "Your Package Validity : <strong>" + fromDate + " - " + toDate + "</strong><br>";
		content += "<strong>Please User Password and Car Details and Driver Details.</strong><br><br>";
		content += url;
		map.put("HEADER", subject);
		map.put("CONTENT", content);
		map.put("FOOTER", "Thanks<br><strong>" + from + "</strong>");
		if (!toAddress.equals("")) {
			try {
				Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress, template, map,
						userLogin.getId());
				Email email = (Email) mapEmail.get("email");
				EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
				emailLog = emailLogService.saveEmailLog(emailLog);
				htmlEmailSender.send(email, emailLog);
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
		}
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017. Use : For Converting String
	 * To TimeStamp
	 * 
	 * @param strDate
	 *            **String date**
	 * @return Timestamp
	 */
	public static Timestamp convertStringToTimestamp(final String strDate) {
		try {
			DateFormat formatter;
			formatter = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date date = formatter.parse(strDate);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return null;
	}

	/**
	 * Created For : get Model by id from Package Model bean
	 *
	 * @param tenantPackage
	 * @param packageID
	 * @return PackageModel
	 */
	@RequestMapping(value = UrlConstants.RENEW_PACKAGE, method = RequestMethod.GET)
	private ModelAndView getRenewTenantPackage(@ModelAttribute("command") TenantPackage tenantPackage,
			final BindingResult result, @RequestParam(value = "packageId", required = false) final String packageID,
			final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		try {
			if (packageID != null && !packageID.equals("")) {
				tenantPackage = packageCreateService.getById(Long.valueOf(packageID));
				if (tenantPackage != null && tenantPackage.getTanentID() != null) {
					Tenant tenant = tenantService.getById(tenantPackage.getTanentID());
					if (tenant != null) {
						model.addObject("tenantCompanyName", tenant.getCompanyname());
					}
				}
				model.addObject("tenantPackage", tenantPackage);
				model.setViewName(ViewConstants.RENEW_PACKAGE);
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
				model.setViewName("redirect:" + UrlConstants.CREATE_PACKAGE_LIST);
			}
		} catch (Exception e) {
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			model.setViewName("redirect:" + UrlConstants.CREATE_PACKAGE_LIST);
			return model;
		}
		return model;
	}

	/**
	 * Created For : get Model by id from Package Model bean
	 *
	 * @param tenantPackage
	 * @param packageID
	 * @return PackageModel
	 */
	@RequestMapping(value = UrlConstants.SAVE_RENEW_PACKAGE, method = RequestMethod.POST)
	private ModelAndView saveRenewTenantPackage(@ModelAttribute("command") final TenantPackage tenantPackage,
			final BindingResult result, final RedirectAttributes ra,
			@RequestParam(value = "fromDate", required = false) final String fromDate,
			@RequestParam(value = "toDate", required = false) final String toDate,
			final HttpSession session) {
		ModelAndView model = new ModelAndView();
		User userSession = (User) session.getAttribute("user");
		try {
			TenantPackage package1 = packageCreateService.getById(tenantPackage.getId());
			if (package1 != null) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date from = new java.sql.Date(df.parse(fromDate).getTime());
				Date to = new java.sql.Date(df.parse(toDate).getTime());
				package1.setAdditionalCharges(tenantPackage.getAdditionalCharges());
				package1.setCarrate(tenantPackage.getCarrate());
				package1.setDriverrate(tenantPackage.getDriverrate());
				if (tenantPackage.getCarList() != null) {
					package1.setOldCarList(package1.getCarList());
					String[] carNoLength = tenantPackage.getCarList().split(",");
					if (carNoLength.length > 0) {
						for (String carNo : carNoLength) {
							Car carDetail = carService.getCarByCarNo(carNo);
							if (carDetail != null) {
								carDetail.setStatus("true");
								carService.update(carDetail);
							}
						}
					}
					package1.setCarList(tenantPackage.getCarList());
				}
				if (tenantPackage.getDriverList() != null) {
					package1.setOldDriverList(package1.getDriverList());
					String[] driverLength = tenantPackage.getDriverList().split(",");
					if (driverLength.length > 0) {
						for (String driverLicense : driverLength) {
							Driver driverDetail = driverService.getDriverByLicenseNo(driverLicense,null);
							if (driverDetail != null) {
								driverDetail.setStatus("true");
								driverService.update(driverDetail);
							}
						}
					}
					package1.setDriverList(tenantPackage.getDriverList());
				}
				package1.setFromDate(from);
				package1.setToDate(to);
				String fromSubadmin = Constants.COMPANY_NAME;
				String toAddress = Constants.COMPANY_EMAIL_ID;
				packageCreateService.update(package1);
				String subject = Constants.SUBJECT_RENEW_PACKAGE;
				String fromAddress = Constants.COMPANY_EMAIL_ID;
				String template = Constants.CUSTOM_TEMPLATE;
				Map<String, String> map = new HashMap<String, String>();
				String content = "<strong>Renew Package</strong><br><br>";
				content += "<strong>Active Car Number And Driver Number</strong><br><br>";
				content += "<strong>Car Number :</strong>&nbsp;" + package1.getCarList() + "<br>";
				content += "<strong>Driver License Number :</strong>&nbsp;" + package1.getDriverList() + "<br><br>";
				map.put("HEADER", subject);
				map.put("CONTENT", content);
				map.put("FOOTER", "Thanks<br>" + fromSubadmin);
				Map<String, Object> mapEmail = CommonUtil.getEmail(subject, fromSubadmin, fromAddress, toAddress, template, map,
						userSession.getId());
				Email email = (Email) mapEmail.get("email");
				EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
				emailLog = emailLogService.saveEmailLog(emailLog);
				htmlEmailSender.send(email, emailLog);
			}
			model.setViewName(
					"redirect:" + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tenantPackage.getTanentID());
		} catch (Exception e) {
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			model.setViewName(
					"redirect:" + UrlConstants.CREATE_PACKAGE_LIST + "?tanentID=" + tenantPackage.getTanentID());
			return model;
		}
		return model;
	}
}