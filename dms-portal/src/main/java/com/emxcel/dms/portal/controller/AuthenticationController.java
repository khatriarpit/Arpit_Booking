package com.emxcel.dms.portal.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.services.superadmin.SellerService;
import com.emxcel.dms.core.model.seller.SellerModel;
import org.apache.log4j.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.modules.email.HtmlEmailSender;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.country.CountryService;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.business.services.state.StateService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.user.UserLogService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.state.State;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.LoginLogoutLogModel;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author emxcelsolutions
 *
 */
@Controller
public class AuthenticationController {

	private static final Logger logger = Logger.getLogger(AuthenticationController.class);
	/**
	 * **Autowired service of userService **.
	 */
	@Inject
	private UserService userService;

	/**
	 * **Autowired service of UserRoleService **.
	 */
	@Inject
	private UserRoleService userRoleService;

	/**
	 * **Autowired service of HtmlEmailSender **.
	 */
	@Inject
	private HtmlEmailSender htmlEmailSender;

	/**
	 * **Autowired service of CountryService **.
	 */
	@Inject
	private CountryService countryService;

	/**
	 * **Autowired service of StateService **.
	 */
	@Inject
	private StateService stateService;

	/**
	 * **Autowired service of CityService **.
	 */
	@Inject
	private CityService cityService;

	/**
	 * **Autowired service of companyService **.
	 */
	@Inject
	private TenantService companyService;

	/**
	 * **Autowired service of packageCreateService **.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * EmailLogService.
	 */
	@Inject
	private EmailLogService emailLogService;

	@Inject
	private SseController sseController;

	@Inject
	private UserLogService userLogService;

	@Inject
	private ClientModelService clientModelService;

	/**
	 * PreBookingService.
	 */
	@Inject
	private PreBookingService preBookingService;

	/**
	 * SimpMessagingTemplate.
	 */
	@Inject
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * NotificationService.
	 */
	@Inject
	private NotificationService notificationService;

	@Inject
	private SellerService sellerService;

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: default page.
	 *
	 * @param request
	 *            **request**.
	 * @param session
	 *            **session**.
	 * @return ModelAndView
	 * @throws ServiceException
	 *             **ServiceException**.
	 */
	@RequestMapping(value = UrlConstants.USER_LOGS, method = RequestMethod.GET)
	private ModelAndView userLogsForAdmin(final HttpSession session, final RedirectAttributes ra) {
		Map<String, Object> map = new HashMap<>();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			List<LoginLogoutLogModel> logListData = null;
			try {
				logListData = userService.getLogoutLogbytenant(user.getUsername(), user.getTanentID());
				map.put("logListData", logListData);
			} catch (Exception e) {
				logger.error("error " + e.getMessage());
				ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.INDEX);
			}
		}
		return new ModelAndView(ViewConstants.USERLOGS, map);
	}

	@RequestMapping(value = { UrlConstants.INDEX }, method = RequestMethod.GET)
	private ModelAndView defaultPage(final HttpServletRequest request, final HttpServletResponse response,
			final HttpSession session) throws ServiceException {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			model.addObject("title", "Desk Management System");
			model.addObject(Constants.MESSAGE, "Welcome");
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String color = (String) session.getAttribute("themeColor");
			if (!(auth instanceof AnonymousAuthenticationToken)) {
				User user = userService.getUserDetailByUsernameOrEmail(auth.getName(), null);
				if (user != null) {
					UserRole userRole = userRoleService.getById(user.getRoleID());
					User userBean = (User) session.getAttribute("user");
					if (userBean == null) {
						userService.findByUserNameAndLoginSave(auth.getName());
						logger.debug(" Debug log entry from log4j");
						logger.info("log entry from log4j");
						logger.error(" Error log entry from log4j");
						String html = "<html><h2>Log4j has been initialized successfully!</h2></html>";
						try {
							response.getWriter().println(html);
						} catch (IOException e) {
							logger.error(Constants.EXCEPTION_THROW, e);
						}
						user.setUserRole(userRole);
						if (user.getTanentID() != null) {
							Tenant company = companyService.getById(user.getTanentID());
							if (company != null) {
								user.setTenant(company);
							}
						}
						session.setAttribute("user", user);
						if (color == null || color.equals("defaultColor")) {
							color = user.getColor() != null && !"".equals(user.getColor()) ? user.getColor()
									: "defaultColor";
						} else {
							color = color != null ? color : "defaultColor";
						}
						session.removeAttribute("themeColor");
						session.setAttribute("themeColor", color);
					}
					if (userRole.getRole() != null && userRole.getRole().equalsIgnoreCase(Constants.USER_ROLE_ADMIN)) {
						List<LoginLogoutLogModel> logListData = userService.getLogoutLog(Constants.USER_ROLE_USER);
						model.addObject("logListData", logListData);
					}
				}
			}
			if (color == null) {
				color = "defaultColor";
				session.setAttribute("themeColor", color);
			}
			model.setViewName(ViewConstants.INDEX);
			return model;
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
			return model;
		}
	}

	/**
	 * Created By : Naresh Banda Date:26-01-2017 Use: Admin Page.
	 *
	 * @return model.
	 */
	@RequestMapping(value = UrlConstants.ADMIN_LOGIN + "**", method = RequestMethod.GET)
	private ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Desk Management System");
		model.addObject(Constants.MESSAGE, "This page is for " + Constants.USER_ROLE_ADMIN + " only!");
		model.setViewName(ViewConstants.ADMIN);
		return model;
	}

	/**
	 * Created By : Naresh Banda Date:26-01-2017 Use: ajax call for add color
	 * when new user added.
	 *
	 * @param request
	 *            **request**.
	 * @param session
	 *            **session**.
	 * @param colorString
	 *            **colorString**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CHANGE_COLOR, method = RequestMethod.GET)
	private ModelAndView changeColor(HttpServletRequest request, HttpSession session,
			@RequestParam("color") final String colorString) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			try {
				if (colorString != null & !colorString.equals("")) {
					User user = userService.getUserByUsername(auth.getName());
					if (user != null) {
						user.setColor(colorString);
						userService.update(user);
					}
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
		}
		String color = (String) session.getAttribute("themeColor");
		if (color == null) {
			session.setAttribute("themeColor", colorString);
		} else {
			session.removeAttribute("themeColor");
			session.setAttribute("themeColor", colorString);
		}
		String referer = request.getHeader("Referer");
		return new ModelAndView(Constants.REDIRECT + referer);
	}

	/**
	 * Created By : Naresh Banda Date:26-01-2017 Use: login method for user
	 * login.
	 *
	 * @param session
	 *            **session**.
	 * @param error
	 *            **error**.
	 * @param logout
	 *            **logout**.
	 * @param request
	 *            **request**.
	 * @return model
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = { UrlConstants.ROOT, UrlConstants.LOGIN + "**" }, method = RequestMethod.GET)
	private ModelAndView login(final HttpSession session,
			@RequestParam(value = "error", required = false) final String error,
			@RequestParam(value = "logout", required = false) final String logout, final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException, IOException {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		String color = (String) session.getAttribute("themeColor");
		if (logout != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			try {
				List<LoginLogoutLogModel> loginLogoutLogModel = userLogService.getUsernameLastData(auth.getName());
				LoginLogoutLogModel loginLogoutLogModel2;
				if (!loginLogoutLogModel.isEmpty()) {
					loginLogoutLogModel2 = loginLogoutLogModel.get(0);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					loginLogoutLogModel2.setLogout_dateTime(timestamp);
					userLogService.update(loginLogoutLogModel2);
					User user = userService.getUserByUsername(auth.getName());
					if (user != null) {
						user.setColor(color);
						userService.update(user);
					}
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					cookie.setValue(null);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
			session.removeAttribute("user");
			model.addObject("msg", "You've been logged out successfully.");
			session.invalidate();
		}
		if (CommonUtil.checkUserLogin()) {
			model.setViewName(Constants.REDIRECT + UrlConstants.INDEX);
			return model;
		} else {
			color = ((color != null) ? color : "defaultColor");
			session.setAttribute("themeColor", color);
			model.setViewName(ViewConstants.LOGIN);
			return model;
		}
	}

	/**
	 * Created By : Naresh Banda Date:26-01-2017 Use: to get error message.
	 *
	 * @param request
	 *            **request**.
	 * @param key
	 *            **key**.
	 * @return error.
	 */
	private String getErrorMessage(final HttpServletRequest request, final String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error;
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username or password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = " Invalid username or password!";
		}
		return error;
	}

	/**
	 * Created By : Jimit Patel Date:26-01-2017 Use: To redirect on Forget
	 * password page.
	 *
	 * @param user
	 *            **user**.
	 * @param result
	 *            **result**.
	 * @param username
	 *            **username**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.FORGOTTEN_PASSWORD_PAGE, method = RequestMethod.GET)
	private ModelAndView forgotPassword(@ModelAttribute("command") final User user, final BindingResult result,
			@RequestParam(value = "username", required = false) final String username) {
		Map<String, Object> map = new HashMap<>();
		return new ModelAndView(ViewConstants.FORGOTTEN_PASSWORD, map);
	}

	/**
	 * Created By : Vaibhav Tank Date:23-02-2017 Use: To redirect on Resend
	 * password page.
	 * 
	 * @param user
	 * @param result
	 * @param username
	 * @return
	 */
	@RequestMapping(value = UrlConstants.RESENDPASSWORD, method = RequestMethod.GET)
	private ModelAndView resendPasswordPage(@ModelAttribute("command") final User user, final BindingResult result,
			final String username) {
		Map<String, Object> map = new HashMap<>();
		return new ModelAndView(ViewConstants.RESEND_PASSWORD_PAGE, map);
	}

	/**
	 * Created By : Jimit Patel. Date: 26-01-2017 Use: To change password when
	 * user is alredy logged in.
	 *
	 * @param user
	 *            **user**.
	 * @param result
	 *            **result**.
	 * @return model
	 */
	@RequestMapping(value = UrlConstants.CHANGE_PASSWORD, method = RequestMethod.GET)
	private ModelAndView changePassword(@ModelAttribute("command") final User user, final BindingResult result
	/*
	 * @RequestParam(value = "username", required = false) final String username
	 */) {
		Map<String, Object> map = new HashMap<>();
		User userVo = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.toString();
		try {
			// userVo = userService.getUserByUsername(username);
			userVo = userService.getUserDetailByUsernameOrEmail(username, null);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		map.put("userVo", userVo != null ? userVo : null);
		map.put("userDTL", userService.list());
		return new ModelAndView(ViewConstants.CHANGE_PASSWORD, map);
	}

	/**
	 * Created By : Vaibhav Tank. Date: 27-02-2017 Use: To change password when
	 * user is alredy logged in.
	 * 
	 * @param session
	 * @param user
	 * @param password
	 * @return
	 * @throws ServiceException 
	 */
	@RequestMapping(value = UrlConstants.CHANGEPASSWORDFORDE_PAGE, method = RequestMethod.GET)
	private ModelAndView changePasswordPageForOtherUsers(final HttpSession session,
			@ModelAttribute("command") final User user, final String password) throws ServiceException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User userSession = (User) session.getAttribute("user");
			List<User> listOfUsers = userService.listOfUserWithTenantID(userSession.getId(),userSession.getTanentID());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginId", userSession.getId());
		
		map.put("userlists", listOfUsers);
		return new ModelAndView(ViewConstants.CHANGEPASSWORDFORDE, map);
	}

	/**
	 * Created By : Vaibhav Tank. Date: 27-02-2017 Use:To update password of
	 * other user that tanent admin wants to change.
	 * 
	 * @param session
	 * @param userId
	 * @param newpassword
	 * @param selectId
	 * @param loggeduser
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.CHANGEPASSWORDFORDE_ACCEPTED, method = RequestMethod.POST)
	private ModelAndView UpdatePasswordForOtherUsers(final HttpSession session,
			@RequestParam(value = "userId", required = false) final String userId,
			@RequestParam(value = "newPassword", required = false) final String newpassword,
			@RequestParam(value = "selectId", required = false) final String selectId,
			@RequestParam(value = "loggeduser", required = false) final String logginUserName) throws ServiceException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = (User) session.getAttribute("user");
		String password = userService.getPasswordByUsername(username);
		List<User> userList = userService.list();
		Map<String, Object> map = new HashMap<String, Object>();
		if (logginUserName.equals(password)) {
			if (userId != null) {
				User userDetailsById = userService.getById(Long.valueOf(userId));
				if (userDetailsById.getPassword().equals(user.getPassword())) {
					if (newpassword != null && userId != null && selectId != null) {
						User selectedUser = userService.getById(Long.valueOf(selectId));
						userService.updateUserPassword(selectId, userId, newpassword, selectedUser, password,
								logginUserName);
						if (logginUserName.equalsIgnoreCase(password)) {
							map.put(Constants.MESSAGE, "Password Change Successfully");
						}
					}
				}
			}
		} else {
			map.put(Constants.MESSAGE, "Please enter correct Password !!");
		}
		for (int i = 0; i < userList.size(); i++) {
			if (userList.get(i).getUsername().equalsIgnoreCase(auth.getName())) {
				userList.remove(i);
			}
		}
		map.put("loginId", user.getId());
		map.put("userlists", userList);
		return new ModelAndView(ViewConstants.CHANGEPASSWORDFORDE, map);
	}

	/**
	 * Created By : Jimit Patel Date : 26-01-2017. Use: For forget password it
	 * will send email to user
	 *
	 * @param session
	 *            **session**.
	 * @param username
	 *            **username**.
	 * @param request
	 *            **request**.
	 * @return model.
	 * @throws Exception
	 *             **Exception**.
	 */

	@RequestMapping(value = UrlConstants.FORGOTTEN_PASSWORD, method = RequestMethod.POST)
	private ModelAndView forgetPassword(final HttpSession session, @RequestParam("username") final String username,
			final HttpServletRequest request) throws Exception {
		ModelAndView model = new ModelAndView();
		User user = userService.getUserDetailByUsernameOrEmail(username, null);
		String color = (String) session.getAttribute("themeColor");
		if (user != null) {
			userService.sendEmail(username, user);
			model.setViewName(ViewConstants.RESEND_PASSWORD_PAGE);
		} else {
			model.addObject("error", "Wrong Email or Username !!");
			model.addObject("themeColor", color);
		}
		model.setViewName(ViewConstants.RESEND_PASSWORD_PAGE);
		return model;
	}

	/**
	 * Created By: Jimit Patel Date :26-01-2017. Use: To update Password of
	 * user.
	 *
	 * @param user
	 *            **user**.
	 * @param result
	 *            **result**.
	 * @param redirectAttrs
	 *            **redirectAttrs**.
	 * @return model
	 * @throws ServiceException 
	 */
	@RequestMapping(value = UrlConstants.UPDATE_PASSWORD, method = RequestMethod.POST)
	private ModelAndView updatePassword(@ModelAttribute("command") User user, final BindingResult result,
										final RedirectAttributes redirectAttrs) throws ServiceException {
		String pass = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName().toString();
		try {
			pass = userService.getPasswordByUsername(username);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		if (pass != null && pass.equals(user.getPassword())) {
			String uname = username;
			String newpassword = user.getNewPassword();
			try {
				user = userService.getUserByUsername(uname);
				if (user != null) {
					userService.updateUser(newpassword,user);
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			user.setUpdatedDate(timestamp);
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Password change successfully");
		} else {
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Please enter valid old password");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CHANGE_PASSWORD);
	}

	/**
	 * Created By : Vaibhav Tank. Date: 27-02-2017 Use: To update password for
	 * other users.
	 * 
	 * @param user
	 * @param result
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = UrlConstants.UPDATE_PASSWORD_FOR_DE, method = RequestMethod.POST)
	private ModelAndView updatePasswordForOtherUsers(@ModelAttribute("command") User user, final BindingResult result,
			final RedirectAttributes redirectAttrs) {
		String password = null;
		try {
			password = userService.getPasswordByUsername(user.getUsername());
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);

		}
		if (password != null) {
			String username = user.getUsername();
			String newpassword = user.getNewPassword();
			try {
				user = userService.getUserByUsername(username);
				if (user != null) {
					//user.setPassword(newpassword);
					//userService.update(user);
					userService.updateUser(newpassword,user);
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			user.setUpdatedDate(timestamp);
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Password change successfully");
		} else {
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Password did not change, Please Try Again");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CHANGEPASSWORDFORDE_PAGE);
	}

	/**
	 * Created By: Ashka Thaker.
	 *
	 * @param result
	 *
	 * @return model
	 */

	/**
	 * Created By: Ashka Thaker Date :26-01-2017. Use: to update user when edit.
	 *
	 * @param user
	 *            **user**.
	 * @param result
	 *            **result**.
	 * @param tanentId
	 *            **tanentId**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.USER_VERIFICATION, method = RequestMethod.GET)
	private ModelAndView updateUser(@ModelAttribute("command") SellerModel sellerModel, final BindingResult result,
									@RequestParam(value = "tenantID", required = false) final String tanentId, final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		if (tanentId != null && !tanentId.equals("")) {
			final byte[] decoded = Base64.getDecoder().decode(tanentId);
			String decodeToken;
			try {
				decodeToken = new String(decoded, "UTF-8");
				String[] newToken = decodeToken.split(" ");
				String companyEmail = newToken[0] != null && !"".equals(newToken[0]) ? newToken[0] : "";
				String tenantIDVal = newToken[1] != null && !"".equals(newToken[1]) ? newToken[1] : "";
				String tenantPackageID = newToken[2] != null && !"".equals(newToken[2]) ? newToken[2] : "";
				String tenantPackageCount = newToken[3] != null && !"".equals(newToken[3]) ? newToken[3] : "";
				if (!"".equals(companyEmail) && !"".equals(tenantIDVal)) {
					Tenant company = companyService.getById(Long.valueOf(tenantIDVal));
					if (company != null) {
						model.put("companyname", company.getCompanyname());
						model.put("tanentID", tenantIDVal);
						Map<String, Object> userLimit = getTenantPackageLimit(Long.valueOf(tenantIDVal),
								Constants.FALSE_AS_STRING, Long.valueOf(tenantPackageID));
						boolean emailFlag = (boolean) userLimit.get("email-flag");
						if (!emailFlag) {
							Integer noOfUsers = (Integer) userLimit.get(Constants.USER_LIMIT);
							Integer noOfCars = (Integer) userLimit.get(Constants.CAR_LIMIT);
							Integer noOfDrivers = (Integer) userLimit.get(Constants.DRIVER_LIMIT);
							model.put(Constants.USER_LIMIT, noOfUsers != null ? noOfUsers : 0);
							model.put(Constants.CAR_LIMIT, noOfCars != null ? noOfCars : 0);
							model.put(Constants.DRIVER_LIMIT, noOfDrivers != null ? noOfDrivers : 0);
							model.put("carStartRow", noOfCars != null ? 1 : 0);
							model.put("driverStartRow", noOfDrivers != null ? 1 : 0);
							model.put("carList", null);
							model.put("driverList", null);
							model.put("tenantPackageID", tenantPackageID);
							model.put("stateLst", stateService.list());
							model.put("cityLst", cityService.list());
							model.put("countryLst", countryService.list());
							// If seller account exists with active status then false other true flag is set
							model.put("sellerAccountFlag", sellerService.getIdByTenantID(Long.valueOf(tenantIDVal), 0));
							if (tenantPackageCount.equals("0")) {
								model.put("tenantPackageAddon", "false");
							} else {
								model.put("tenantPackageAddon", "true");
							}
						} else {
							model.put(Constants.MESSAGE, "User already used this link !!!");
						}
					} else {
						model.put(Constants.MESSAGE, " Oops Something went Wrong !!! ");
					}
				} else {
					model.put(Constants.MESSAGE, " Oops Something went Wrong !!!");
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				ra.addFlashAttribute(Constants.MESSAGE, " Oops Something went Wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_VERIFICATION);
			}
		} else {
			model.put(Constants.MESSAGE, "Oops Something went Wrong !!!");
		}
		return new ModelAndView(ViewConstants.USER_VERIFICATION, model);
	}

	/**
	 * Created By :Ashka Thaker. Date: 26-01-2017 Use: To change password when
	 * user is alredy logged in.
	 *
	 * @param user
	 *            **user**.
	 * @param tanentId
	 *            **tanentId**.
	 * @param carlst
	 *            **carlst**.
	 * @param driverlst
	 *            **driverlst**.
	 * @param confirmPassword
	 *            **confirmPassword**.
	 * @param carIndex
	 *            **carIndex**.
	 * @param driverIndex
	 *            **driverIndex**.
	 * @param result
	 *            **result**.
	 * @param ra
	 *            **redirectAttrs**.
	 * @return responseBody
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.PASSWORD_VERIFICATION, method = RequestMethod.POST)
	private Map<String, Object> updatepassword(@ModelAttribute("command") final SellerModel sellerModel,
			@RequestParam(value = "password", required = false) final String password,
			@RequestParam(value = "tanentID", required = false) final String tanentId,
			@RequestParam(value = "carlst", required = false) final String carlst,
			@RequestParam(value = "driverlst", required = false) final String driverlst,
			@RequestParam(value = "cpass", required = false) final String confirmPassword,
			@RequestParam("carListRowWithComma") String carIndex,
			@RequestParam("driverListRowWithComma") String driverIndex,
			@RequestParam("isAddOnPackage") String isAddOnPackage,
			@RequestParam("tenantPackageID") String tenantPackageID,
			@RequestParam("sellerAccountFlag") String sellerAccountFlag) {

		Map<String, Object> model = new HashMap<>();
		if (isAddOnPackage.equals("false") && password != null
				&& !password.equals(confirmPassword)) {
			model.put(Constants.MESSAGE, "Password and confirm password does not match !!!");
			return model;
		}
		String[] carLength = null;
		if (!"".equals(carlst)) {
			carLength = carlst.split(",");
		}
		String[] driverLength = null;
		if (!"".equals(driverlst)) {
			driverLength = driverlst.split(",");
		}
		List<TenantPackage> tenantPackageLst = null;
		try {
			tenantPackageLst = packageCreateService.getTenantPackageWithOutCountThisPackageID(Long.valueOf(tanentId),
					Long.valueOf(tenantPackageID));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (isAddOnPackage.equals("false")) {
				List<User> userVo = userService.listOfUserByIdAndTanentID(true, Long.parseLong(tanentId));
				if (userVo != null && userVo.size() > 0) {
					userVo.get(0).setPassword(password);
					userService.save(userVo.get(0));
				}
			}
			if(sellerAccountFlag.equals("true")) {
				if (sellerModel != null) {
					sellerService.saveSellerDetail(sellerModel, Long.valueOf(tanentId));
				}			
			}
			model = CommonUtil.checkExistingCarAndDriver(tenantPackageLst, carLength, driverLength, carIndex,
					driverIndex);
			String error = (String) model.get("error");
			if (Constants.FALSE_AS_STRING.equals(error)) {
				List<TenantPackage> tenantPackageList = packageCreateService.getListByTenantID(Long.parseLong(tanentId),
						"0", Long.parseLong(tenantPackageID));
				if (tenantPackageList != null && tenantPackageList.size() > 0) {
					try {
						TenantPackage tenantPackage = tenantPackageList.get(0);
						if (carLength.length <= tenantPackage.getCars()) {
							tenantPackage.setCarList(carlst);
						}
						if (driverLength.length <= tenantPackage.getDrivers()) {
							tenantPackage.setDriverList(driverlst);
						}
						tenantPackage.setEmailFlag(true);
						packageCreateService.update(tenantPackage);
						String msg = "User Verification Request Recieved From Tenant";
						Notification notification = notificationService.saveAlertSchedulerNotification(msg, null);
						List<User> userList = userService.listOfUserByIdAndTanentID(true, null);
						if (userList.size() > 0) {
							simpMessagingTemplate.convertAndSendToUser(userList.get(0).getUsername(), "/queue/notify",
									notification);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				model.put(Constants.MESSAGE, "Details Updated successfully !!!");
			} else {
				return model;
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			model.put(Constants.MESSAGE, "Try Again");
		}
		return model;
	}

	/**
	 * Created By: Jimit Patel.
	 *
	 * @param result
	 * @return model
	 */

	/**
	 * Created By: Jimit Patel Date: 26-01-2017. Use: to user add new user.
	 *
	 * @param user
	 *            **user**.
	 * @param result
	 *            **result**.
	 * @param session
	 *            **session**.
	 * @param userId
	 *            **userId**.
	 * @return ModelAndView.
	 */
	@RequestMapping(value = UrlConstants.USER, method = RequestMethod.GET)
	private ModelAndView user(@ModelAttribute("command") User user, final BindingResult result,
			final HttpSession session, @RequestParam(value = "userId", required = false) final String userId,
			final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		try {
			User userSession = (User) session.getAttribute("user");
			model.put("countryLst", countryService.list());
			model.put("userROLE", userRoleService.list());
			List<TenantPackage> tenantPackageList = null;
			if (userId != null && !"".equals(userId)) {
				tenantPackageList = packageCreateService.getListByTenantID(userSession.getTanentID(), "0", null);
				model = getUserData(model, session);
				user = userService.findById(Long.valueOf(userId));
				if (user != null) {
					UserRole role = userRoleService.getById(user.getRoleID());
					if (role != null) {
						user.setUserRole(role);
					}
					model.put("userDetails", user);
				}
			} else {
				tenantPackageList = packageCreateService.getTenantPackageForAdminAndUser(userSession.getTanentID(), "0",
						null);
			}
			if (!userSession.getUserRole().getRole().equals(Constants.USER_ROLE_SUPERADMIN)) {
				model.put("tenantPackageList", tenantPackageList);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			ra.addFlashAttribute("userDetails", user);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
		}
		return new ModelAndView(ViewConstants.USER, model);
	}

	/**
	 * Created By: Jimit Patel Date: 26-01-2017 Use: to save user data.
	 *
	 * @param imgFile
	 *            **imgFile**.
	 * @param user
	 *            **user**.
	 * @param request
	 *            **request**.
	 * @param session
	 *            **session**.
	 * @param redirectAttrs
	 *            **redirectAttrs**.
	 * @return ModelAndView
	 * @throws IOException
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_USER, method = RequestMethod.POST)
	private ModelAndView saveUser(@RequestParam(value = "imgFile", required = false) final MultipartFile imgFile,
			@ModelAttribute("command") final User user, final HttpServletRequest request, final HttpSession session,
			final RedirectAttributes redirectAttrs) {
		User userSession = (User) session.getAttribute("user");
		User userName = null;
		User userEmail = null;
		try {
			userName = userService.getUserDetailByUsernameOrEmail(user.getUsername(), user.getId());
			userEmail = userService.getUserDetailByUsernameOrEmail(user.getEmailId(), user.getId());
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		if (userName != null) {
			map.put("errorMsg", "username");
		} else if (userEmail != null) {
			map.put("errorMsg", "email");
		}
		if (userName != null || userEmail != null) {
			map = getUserData(map, session);
			map.put("userDetails", user);
			if (userSession != null) {
				try {
					map.put("tenantPackageList",
							packageCreateService.getListByTenantID(userSession.getTanentID(), "0", null));
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}
			return new ModelAndView(ViewConstants.USER, map);
		}
		if (user.getId() == null) {
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "New user added successfully");
		} else {
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "User updated successfully");
		}
		String fileName;
		String path = CommonUtil.LOCATION + "user/tenants/"+ userSession.getTanentID() + "/" + user.getUsername();
		fileName = CommonUtil.getFileName(imgFile, path);
		if (!"".equals(fileName)) {
			user.setUserImage(fileName);
		}
		try {
			if (user.getId() != null) {
				userService.update(user);
				return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
			} else {
				user.setTanentID(userSession.getTanentID());
				if (userSession.getUserRole().getRole().equals(Constants.USER_ROLE_SUPERADMIN)) {
					userService.save(user);
				} else {
					int size = userService.getUserByTenantIdAndPackageCount(userSession.getTanentID(),
							user.getTenantPackageID());
					Map<String, Object> userLimit = getTenantPackageLimit(userSession.getTanentID(),
							Constants.TRUE_AS_STRING, user.getTenantPackageID());
					Integer noOfUsers = (Integer) userLimit.get(Constants.USER_LIMIT);
					if (noOfUsers > size) {
						userService.save(user);
					} else {
						redirectAttrs.addFlashAttribute(Constants.MESSAGE,
								"Please Select Another Package. You Can not Add More than " + noOfUsers
										+ " User in this package.");
						redirectAttrs.addFlashAttribute("userDetails", user);
						return new ModelAndView(Constants.REDIRECT + UrlConstants.USER);
					}
				}
			}
		} catch (Exception e) {
			redirectAttrs.addFlashAttribute("error", e);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.ERROR);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
	}

	/**
	 * Created By: Nitin Patel Date :26-01-2017. Use: To get user list.
	 *
	 * @param session
	 *            **session**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.USER_LIST, method = RequestMethod.GET)
	private ModelAndView userList(final HttpSession session) {
		Map<String, Object> model = new HashMap<>();
		try {
			User userSession = (User) session.getAttribute("user");
			List<User> userLst = null;
			userLst = userService.listOfUserByIdAndTanentIDNotSameUser(false, userSession.getTanentID(),userSession.getUsername());
			model.put("userList", userLst);
		} catch (Exception e) {
			e.getCause();
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return new ModelAndView(ViewConstants.USER_LIST, model);
	}

	/**
	 * Created By: Nitin Patel Date : 26-01-2017. Use: to delete user data.
	 *
	 * @param id
	 *            **id**.
	 * @param redirectAttrs
	 *            **redirectAttrs**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_USER, method = RequestMethod.GET)
	private ModelAndView deleteUser(@RequestParam(value = "userId", required = false) final String userId,
			final RedirectAttributes redirectAttrs) {
		if (userId != null && !"".equals(userId)) {
			try {
				userService.delete(userService.getById(Long.valueOf(userId)));
				redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Use deleted successfully !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
			} catch (NumberFormatException | ServiceException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
			}
		} else {
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, "User Not Found !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_LIST);
		}
	}

	/**
	 * created By: Naresh Banda Date :26-01-2017. Use: to user data.
	 *
	 * @param map
	 *            **map**.
	 * @param session
	 *            **session**.
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getUserData(final Map<String, Object> map, final HttpSession session) {
		User userSession = (User) session.getAttribute("user");
		if (userSession.getUserRole().getRole().equals(Constants.USER_ROLE_ADMIN)) {
			map.put("userROLE", userRoleService.listOfRole(Constants.USER_ROLE_SUPERADMIN));
		} else {
			map.put("userROLE", userRoleService.list());
		}
		map.put("countryLst", countryService.list());
		map.put("stateLst", stateService.list());
		map.put("cityLst", cityService.list());
		return map;
	}

	/**
	 * created By: Jimit Patel Date:30-11-2016 Use:To fetch data of states.
	 * respected to country
	 *
	 * @param countryID
	 *            **countryID**>
	 * @return List<State>
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_STATE, method = RequestMethod.POST)
	private List<State> getState(@RequestParam(value = "countryId", required = false) final String countryId) {
		List<State> stateLst = null;
		if (countryId != null && !"".equals(countryId)) {
			stateLst = stateService.getstate(Long.valueOf(countryId));
		}
		return stateLst;
	}

	/**
	 * created By: Jimit Patel Date:30-11-2016. Use:To fetch data of city
	 * respected to state.
	 *
	 * @param stateId
	 *            **stateId**.
	 * @return List<City>
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_CITY, method = RequestMethod.POST)
	private List<City> getCity(@RequestParam("stateId") final String stateId) {
		List<City> cityLst = null;
		if (stateId != null && !"".equals(stateId)) {
			cityLst = cityService.listCity(Long.valueOf(stateId));
		}
		return cityLst;
	}

	/**
	 * created By: Naresh Banda Date:30-11-2016. Use: to fetch file.
	 *
	 * @param request
	 *            **request**.
	 * @param response
	 *            **response**.
	 */
	@RequestMapping(value = UrlConstants.FETCH_IMAGE, method = RequestMethod.GET)
	private void getFetchFile(final HttpServletRequest request, final HttpServletResponse response) {
		String filename = request.getParameter("imageName");
		if (filename != null && !"".equals(filename)) {
			String rootPath = CommonUtil.LOCATION;
			File dir = new File(rootPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(rootPath + filename);
			if (file.exists()) {
				response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(file.getName()));
				response.setHeader("Content-Length", String.valueOf(file.length()));
				response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
				try {
					Files.copy(file.toPath(), response.getOutputStream());
				} catch (IOException e) {
					logger.error(Constants.EXCEPTION_THROW, e);
				}
			}
		}
	}

	/**
	 * created By: Naresh Banda Date:26-01-2017. Use: to check limit from tanent
	 * package.
	 *
	 * @param tanentId
	 *            **tanentId**.
	 * @param status
	 *            **status**.
	 * @return Map<String, Object>
	 */
	private Map<String, Object> getTenantPackageLimit(final Long tanentId, final String status,
			final Long packageTenantID) {
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
			model.put("email-flag", tenantPackageList.get(0).getEmailFlag());
		}
		return model;
	}

	/**
	 * Created By: Jimit Patel Date:27-02-2017 Use: to open dashboard
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DASHBOARD, method = RequestMethod.GET)
	public ModelAndView dashboard(final HttpServletRequest request, final HttpServletResponse response,
			final HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(auth.getName());
		Map<String, Object> map = new HashMap<>();
		Long tenantId = user.getTanentID();
		List<ClientModel> drivertrips = clientModelService.getCancelTripByDriver(tenantId);
		map.put("totalDriverCancelTrips", drivertrips.size());
		map.put("canceltrips", drivertrips);
		List<ClientModel> customertrips = clientModelService.getCancelTripByCustomer(tenantId);
		map.put("totalCustomerCancelTrips", customertrips.size());
		map.put("canceltripsCustomer", customertrips);
		Integer totalCancelRequests = drivertrips.size() + customertrips.size();
		map.put("totalCancelRequests", totalCancelRequests);
		List<PreBooking> prebookinglist = preBookingService.getPreBooking(tenantId);
		map.put("totalPrebooking", prebookinglist.size());
		map.put("prebooking", prebookinglist);
		List<ClientModel> listOfBookingforNextWeek = clientModelService.getWeekTrips(tenantId);
		map.put("totalFutureTrips", listOfBookingforNextWeek.size());
		map.put("futureTrips", listOfBookingforNextWeek);
		List<ClientModel> listofCanceledTrips = clientModelService.getCanceledTrips(tenantId);
		map.put("totalCanceledTrips", listofCanceledTrips.size());
		List<ClientModel> listofEndTrips = clientModelService.getEndTrips(tenantId);
		map.put("totalEndTrips", listofEndTrips.size());
		List<ClientModel> listOfLiveTrips = clientModelService.getCurrentTrips(tenantId);
		map.put("liveTrips", listOfLiveTrips.size());
		return new ModelAndView(ViewConstants.DASHBOARD, map);
	}


	@RequestMapping(value = UrlConstants.LOAD_CASH, method = RequestMethod.GET)
	private ModelAndView loadcashAndroid() {
		return new ModelAndView(ViewConstants.LOAD_CASH);
		}
}