package com.emxcel.dms.portal.restcontroller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.ws.rs.QueryParam;

import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.payment.PaymentModeService;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.payment.PaymentModel;
import org.apache.log4j.Logger;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.modules.email.Email;
import com.emxcel.dms.core.business.modules.email.HtmlEmailSender;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.client.TripDeatilsService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.email.EmailLogService;
import com.emxcel.dms.core.business.services.feedback.FeedbackService;
import com.emxcel.dms.core.business.services.guest.GuestService;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.business.services.payment.BillPaymentModelService;
import com.emxcel.dms.core.business.services.restservice.geo.GeoDataService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.OTPData;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.client.TripDetails;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.geo.GeoData;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.payment.BillPaymentModel;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.mchange.v2.cfg.PropertiesConfigSource.Parse;

/**
 * @author emxcelsolutionhello
 */
@RestController
public class FeedAppRestController {

	private static final Logger logger = Logger.getLogger(FeedAppRestController.class);

	@Inject
	private HtmlEmailSender htmlEmailSender;

	@Inject
	private EmailLogService emailLogService;
	/**
	 * carService
	 */
	@Inject
	private CarService carService;

	/**
	 * driverService
	 */
	@Inject
	private DriverService driverService;

	/**
	 * feedbackService
	 */
	@Inject
	private FeedbackService feedbackService;

	/**
	 * UserService
	 */
	@Inject
	private UserService userService;

	@Inject
	private CityService cityService;

	@Inject
	private ClientModelService clientModelService;

	@Inject
	private TenantService tenantService;

	@Inject
	private PreBookingService preBookingService;

	@Inject
	private GuestService guestService;

	@Inject
	private GeoDataService geoDataService;

	@Inject
	private TripDeatilsService tripDeatilsService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Inject
	private CarTypeService carTypeService;
	
	/**
	 * NotificationService.
	 */
	@Inject
	private NotificationService notificationService;
	
	@Inject
	private BillPaymentModelService billPaymentModelService;

	@Inject
	private PaymentModeService paymentModeService;

	/**
	 * Crated By : Nittin Patel Date:29-12-2016 Use: get Validate contact Number
	 *
	 * @param fullName
	 * @param emailId
	 * @param contactNo
	 * @param password
	 * @return mpa **map**.
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_REGISTER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> register(@QueryParam("fullName") final String fullName,
			@QueryParam("emailId") final String emailId, @QueryParam("contactNo") final String contactNo,
			@QueryParam("password") final String password, @QueryParam("mobileToken") final String mobileToken)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = null;
		if (contactNo != null) {
			user = userService.loginEmailByContactNo(Long.parseLong(contactNo));
		}
		if (user != null && user.getPassword() == null) {
			userService.updateUserDetailByEmail(fullName, emailId, contactNo, password, mobileToken);
			map.put("status", true);
			map.put("data", user);
		} else if (user != null && user.getPassword() != null) {
			map.put("status", false);
			map.put("message", "User already exits");
		} else {
			if ((fullName != null && !fullName.equals("")) && (contactNo != null && !contactNo.equals(""))
					&& (emailId != null && !emailId.equals("")) && (password != null && !password.equals(""))
					&& mobileToken != null) {
				User user1 = new User();
				user1.setUsername(emailId);
				user1.setFullName(fullName);
				user1.setContactNo(Long.valueOf(contactNo));
				user1.setEmailId(emailId);
				user1.setPassword(password);
				user1.setMobileToken((mobileToken));
				user1.setEnabled(0);
				try {
					userService.save(user1);
				} catch (ServiceException e) {
					logger.error(Constants.EXCEPTION_THROW, e);
				}
				map.put("status", true);
				map.put("message", "User seccusfully saved.");
			} else {
				map.put("status", false);
				map.put("message", "Name or Email or password missing.");
			}
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:30-12-2016 Use:To check the existing email
	 * and password in database for forgotten password.
	 *
	 * @param email
	 * @param password
	 * @param userToken
	 *            bml0aW5AZ21haWwuY29tLTEyMzQ1Ng== ODM0NzYzNjM1OS0xMjM0NTY=
	 *            MTIzNDU2
	 * @return map
	 *
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_LOGIN_CHECK, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> loginCheck(@QueryParam("contactNo") final String contactNo,
			@QueryParam("password") final String password) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		User user = null;

		user = userService.loginEmailByContactNo(Long.parseLong(contactNo));
		if (user != null) {
			if (user.getPassword().equals(password)) {
				map.put("status", true);
				map.put(Constants.MESSAGE, "Login SuccessFully");
				map.put("user", user);
			} else {
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
				map.put("status", false);
			}
		} else {
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			map.put("status", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:30-12-2016 Use: Password send To your email
	 * id
	 *
	 * @param email
	 * @param contactNo
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_FORGET_PASSWORD, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> forgetPassword(@QueryParam("email") final String email,
			@QueryParam("contactNo") final String contactNo) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		User user;
		String emailId = "";
		String contact = "";
		if (email != null && !"".equals(email)) {
			final byte[] decoded = Base64.getDecoder().decode(email);
			try {
				emailId = new String(decoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			user = userService.loginEmail(emailId);
		} else {
			final byte[] decoded = Base64.getDecoder().decode(contactNo);
			try {
				contact = new String(decoded, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			user = userService.loginEmailByContactNo(Long.parseLong(contact));
		}
		if (user != null) {
			String otp = Integer.toString(OTPData.generateOtp());
			if (email != null && !"".equals(email)) {
				String subject = Constants.SUBJECT_FORGETPASS;
				String from = Constants.COMPANY_NAME;
				String fromAddress = Constants.COMPANY_EMAIL_ID;
				// Template : "email_forget_password.ftl"
				String template = Constants.TEMPLATE_FORGETPASS;
				String toAddress = user.getEmailId();
				Map<String, String> map1 = new HashMap<>();
				String content = "Your username and password." + "<br><b>Username : </b>" + emailId
						+ "<br><b>OTP : </b>" + otp;
				map1.put("HEADER", subject);
				map1.put("CONTENT", content);
				map1.put("FOOTER", "Thanks<br>" + from);
				try {
					Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress, template,
							map1, user.getId());
					Email email1 = (Email) mapEmail.get("email");
					EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
					emailLog = emailLogService.saveEmailLog(emailLog);
					htmlEmailSender.send(email1, emailLog);
				} catch (Exception e) {
					logger.error(Constants.EXCEPTION_THROW, e);
				}
				map.put("status", true);
				map.put("OTP", otp);
			} else {
				try {
					SMSSend.sendOtp(user, Integer.parseInt(otp));
				} catch (Exception e) {
					logger.error(Constants.EXCEPTION_THROW, e);
				}
				map.put("status", true);
				map.put("otp", otp);
			}
		} else {
			map.put("message", "Please Enter Valid userName");
			map.put("status", false);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:30-12-2016 Use: To Change password through
	 * old password
	 *
	 * @param emailId
	 * @param contactNo
	 * @param newPassword
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_CHANGE_PASSWORD, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> changePassword(@QueryParam("emailId") final String emailId,
			@QueryParam("contactNo") final String contactNo, @QueryParam("newPassword") final String newPassword)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = null;

		if (emailId != null && !emailId.equals("")) {
			user = userService.loginEmail(emailId);
			if (user != null) {
				userService.changePasswordByEmail(user.getEmailId(), newPassword);
				map.put("status", true);
				map.put("message", "Password changed succesfully..!");
			} else {
				map.put("message", "Please Enter Valid Email Address");
				map.put("status", false);
			}
		} else {
			user = userService.loginEmailByContactNo(Long.parseLong(contactNo));
			if (user != null) {
				userService.changePasswordByContactNo(user.getContactNo(), newPassword);
				map.put("status", true);
				map.put("message", "Password changed succesfully..!");
			} else {
				map.put("message", "Please Enter Valid Contact Number");
				map.put("status", false);
			}
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:15-02-2017 Use: search Car by
	 * carNo,tanentId,fromDate and toDate.
	 *
	 * @param carNo
	 * @param tenantID
	 * @param fromDate
	 * @param toDate
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_SEARCH_CAR, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> searchCar(@QueryParam("carNo") final String carNo,
			@QueryParam("tenantID") final String tenantID, @QueryParam("fromDate") final String fromDate,
			@QueryParam("toDate") final String toDate) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();

		if (carNo != null && !carNo.equals("")) {
			Car car = carService.getCarByCarNo(carNo);
			if (car != null) {
				List<Feedback> feedbackList = feedbackService.checkFeedbackByCarId(car.getId(),
						Long.parseLong(tenantID), fromDate, toDate);
				if (feedbackList != null) {
					map.put("status", true);
					map.put("data", feedbackList);
				} else {
					map.put("status", false);
					map.put("message", "Please Enter Valid Car Number");
				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Car Number");
			}

		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:15-02-2017 Use: search Driver by
	 * drivername,tanentId,fromDate and toDate.
	 *
	 * @param driverName
	 * @param tenantID
	 * @param fromDate
	 * @param toDate
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_SEARCH_DRIVER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> searchDriver(@QueryParam("driverName") final String driverName,
			@QueryParam("tenantID") final String tenantID, @QueryParam("fromDate") final String fromDate,
			@QueryParam("toDate") final String toDate, @QueryParam("userToken") final String userToken)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (driverName != null && !driverName.equals("")) {
			Driver driver = driverService.getDriverByDriverName(driverName);
			if (driver != null) {
				List<Feedback> feedbackList = feedbackService.checkFeedbackByDriverId(driver.getId(),
						Long.parseLong(tenantID), fromDate, toDate);
				if (feedbackList != null) {
					map.put("status", true);
					map.put("data", feedbackList);
				} else {
					map.put("status", false);
					map.put("message", "Please Enter Valid Driver Name");
				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Driver Name");
			}
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:15-02-2017 Use: driver list of by tanent
	 * id.
	 *
	 * @param tanentId
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_DRIVER_BY_TANENT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> driverByTanent(@QueryParam("tanentId") final String tanentId)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (tanentId != null && !tanentId.equals("")) {
			List<Driver> driverList = driverService.listOfDriverByTanent(Long.parseLong(tanentId));
			if (driverList != null) {
				map.put("status", true);
				map.put("data", driverList);
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Tanent Id");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid Tanent ");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:15-02-2017 Use: car list of by tanent id.
	 *
	 * @param tanentId
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_CAR_BY_TANENT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> carByTanent(@QueryParam("tanentId") final String tanentId)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		if (tanentId != null && !tanentId.equals("")) {
			List<Car> carList = carService.listOfCarByTanent(Long.parseLong(tanentId));
			if (carList != null) {
				map.put("status", true);
				map.put("data", carList);
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid Tanent ");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid Tanent ");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Nittin Patel Date:15-02-2017 Use: car list of by tanent id.
	 *
	 * @param tanentId
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_PENDING_TRIP, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> pendingTrip(@QueryParam("tanentId") final String tanentId)
			throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		if (tanentId != null && !tanentId.equals("")) {
			List<ClientModel> clientModelList = clientModelService.getPendingTrip(Long.parseLong(tanentId));
			if (clientModelList != null) {
                map.put("status", true);
				map.put("data", clientModelList);
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid Tanent ");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid Tanent ");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Jimit Patel Date:15-02-2017 Use: for list of past trips with
	 * cancelled trips.
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_PAST_TRIPS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> listOfPastTrips(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
				List<ClientModel> list = clientModelService.getTripsByStatus(guest);
				List<ClientModel> updateList = new ArrayList<>();
				if (list.size() > 0) {
					for (ClientModel client : list) {
						Feedback feedBacknew = new Feedback();
						Driver driver = client.getDriver();
						List<ClientModel> listOfTrips = clientModelService.getDriverByClient(driver);
						if (!listOfTrips.isEmpty()) {
							float drivereating = 0, driverbehaviourrating = 0, drivingrating = 0, driveroverall = 0;
							int drivereatingcount = 0, driverbehaviourratingcount = 0, drivingratingcount = 0;
							for (ClientModel clientModel : listOfTrips) {
								if (feedbackService.getFeedbackByTripID(clientModel.getTripId(),
										driver.getTanentID()) != null) {
									Feedback feedBack = feedbackService.getFeedbackByTripID(clientModel.getTripId(),
											Long.valueOf(driver.getTanentID()));
									if (feedBack.getDrivingRating() != null) {
										drivereating += feedBack.getDrivingRating();
										drivereatingcount = drivereatingcount + 1;
									}
									if (feedBack.getDriverBehaviourRating() != null) {
										driverbehaviourrating += feedBack.getDriverBehaviourRating();
										driverbehaviourratingcount = driverbehaviourratingcount + 1;
									}
									if (feedBack.getDriverTestingRating() != null) {
										drivingrating += feedBack.getDriverTestingRating();
										drivingratingcount = drivingratingcount + 1;
									}

								}
							}
							drivereating /= drivereatingcount;
							driverbehaviourrating /= driverbehaviourratingcount;
							drivingrating /= drivingratingcount;
							driveroverall = drivereating + driverbehaviourrating + drivingrating;
							driveroverall = driveroverall / 3;
							feedBacknew.setDrivingRating(drivereating);
							feedBacknew.setDriverTestingRating(drivingrating);
							feedBacknew.setDriverBehaviourRating(driverbehaviourrating);
							feedBacknew.setAverageRating(driveroverall);
							driver.setFeedback(feedBacknew);
						}
						client.setDriver(driver);
						updateList.add(client);
					}
					map.put("pastTrips", list);
					map.put("status", true);

				} else {
					map.put("message", "no trips available");
					map.put("status", false);

				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid userName or Password");
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use: Get All Tenant For The
	 * City.
	 * 
	 * @param city
	 *            **city**
	 * @param fromdatetime
	 *            **fromdate**
	 * @param todatetime
	 *            **todatetime**
	 * @param userToken
	 *            **userToken**
	 * @return **json**
	 * @throws ServiceException
	 *             **service Exeption**
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_GET_TENANT_FROM_CITY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getTenantFromCity(@QueryParam("city") String city,
			@QueryParam("fromdatetime") String fromdatetime, @QueryParam("todatetime") String todatetime,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (city != null && fromdatetime != null && todatetime != null) {
					if (cityService.getCityByCityName(city) != null) {
						City cityData = cityService.getCityByCityName(city);
						if (cityData.getId() != null) {
							List<Tenant> tenantList = tenantService.getTenantByCityId(cityData.getId());
							List<Tenant> tenantlistnew = new ArrayList<>();
							if (!tenantList.isEmpty()) {
								float carcon = 0, overallservice = 0, driverperformence = 0, averageall = 0;
								int carcount = 0, overallcount = 0, drivercount = 0;
								for (Tenant tenant : tenantList) {
									List<Feedback> listOfFeedBack = feedbackService.carFeedBackByTanent(tenant.getId());
									Feedback feedBack = new Feedback();
									if (!listOfFeedBack.isEmpty()) {
										for (Feedback feedback : listOfFeedBack) {
											if (feedback.getCarConditionRating() != null) {
												carcon += feedback.getCarConditionRating();
												carcount = carcount + 1;
											}
											if (feedback.getOverallServiceRating() != null) {
												overallservice += feedback.getOverallServiceRating();
												overallcount = overallcount + 1;
											}
											if (feedback.getDrivingRating() != null) {
												driverperformence += feedback.getDrivingRating();
												drivercount = drivercount + 1;
											}

										}
										carcon /= carcount;
										overallservice /= overallcount;
										driverperformence /= drivercount;
										averageall = carcon + overallservice + driverperformence;
										averageall = averageall / 3;

										feedBack.setCarConditionRating(carcon);
										feedBack.setDrivingRating(driverperformence);
										feedBack.setOverallServiceRating(overallservice);
										feedBack.setAverageRating(averageall);
									}

									tenant.setFeedBack(feedBack);
									tenantlistnew.add(tenant);

								}
								map.put("tenantList", tenantlistnew);
								map.put("status", true);

							} else {
								map.put("status", false);
								map.put("message", "No Tenant Available for this City");
							}
						}
					} else {
						map.put("status", false);
						map.put("message", "No City Available");

					}

				} else {
					map.put("status", false);
					map.put("message", "Invalid Parameters");

				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid userName or Password");
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**Created By: Johnson Chunara Date:22-02-2017 Use: Get All City From Database. 
	 * @param userToken **encrypted token**
	 * @return **json response**
	 * @throws ServiceException **service Exception**
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_GET_CITY_NAME, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getCityName(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();

		List<City> listofCity = cityService.list();
		if (!listofCity.isEmpty()) {
			map.put("citylist", listofCity);
			map.put("status", true);
		} else {
			map.put("message", "No city Available");
			map.put("status", false);
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**Created By: Johnson Chunara Date:22-02-2017 Use:Send OTP To Guest Mobile No For Cancel OTP.
	 * @param tripID **tripID**
	 * @param userToken **Encoded Token**
	 * @return **json Response**
	 * @throws ServiceException **service Exception**
	 */
	@RequestMapping(value = UrlConstants.FEDD_USER_PENDING_CANCELED_MYTRIPS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> pendingCancelMyTrips(@QueryParam("tripID") String tripID,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				ClientModel clientModel = clientModelService.getTripByTripId(tripID);
				if (clientModel != null) {
					clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CE_CANCEL));
					clientModelService.update(clientModel);
					map.put("status", true);
					map.put("data", user);
				} else {
					map.put("status", false);
					map.put("data", user);
					map.put("message", "No Trips Available for this Trip Id");
				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**Created By: Johnson Chunara Date:23-02-2017 Use:Check OTP With Database For Cancel Request.
	 * @param tripID **tripID**
	 * @param otp **otp**
	 * @param userToken **encoded Token**
	 * @return **json response**
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_PENDING_CANCELED_MYTRIPS_OTP, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> pendingCancelMyTripsOtp(@QueryParam("tripID") String tripID,
			@QueryParam("otp") String otp, @QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<String, Object>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}

		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				ClientModel clientModel = clientModelService.getTripByTripId(tripID);
				if (clientModel != null && clientModel.getOtp() != null && otp != null) {
					if (otp.equals(clientModel.getOtp().toString())) {
						clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CANCEL));
						clientModelService.update(clientModel);
						map.put("status", true);
						map.put("data", user);
						map.put("message", "OTP Matched");
						try {
							String msg = "Customer Request for Cancel Trip. Trip : "+clientModel.getTripId();
							Notification notification = notificationService.saveAlertSchedulerNotification(msg , clientModel.getTanentID());
							List<User> userList = userService.listOfUserByIdAndTanentID(true, clientModel.getTanentID());
							if (userList.size() > 0) {
								simpMessagingTemplate.convertAndSendToUser(userList.get(0).getUsername(), "/queue/notify", notification);
							}
							simpMessagingTemplate.convertAndSendToUser(clientModel.getUpdatedBy(), "/queue/notify", notification);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						map.put("status", false);
						map.put("data", user);
						map.put("message", "Invalid OTP");
					}
				} else {
					map.put("status", false);
					map.put("data", user);
					map.put("message", "No Trips Available for this Trip Id");
				}
			} else {
				map.put("status", false);
				map.put("message", "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put("message", "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**Created By: Johnson Chunara Date:24-02-2017 Use:Get Tenant Fom City With Review.
	 * @param city **city**
	 * @param userToken **encoded token**
	 * @return **json response**
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_GET_TENANT_BY_REVIEW, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getTenantByReview(@QueryParam("city") String city,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (city != null) {
					if (cityService.getCityByCityName(city) != null) {
						City cityData = cityService.getCityByCityName(city);
						if (cityData.getId() != null) {
							List<Tenant> tenantList = tenantService.getTenantByCityId(cityData.getId());
							List<Tenant> tenantListnew = new ArrayList<>();
							if (!tenantList.isEmpty()) {
								float carcon = 0, overallservice = 0, driverperformence = 0, averageall = 0;
								int carcount = 0, overallcount = 0, drivercount = 0;
								for (Tenant tenant : tenantList) {
									List<Feedback> listOfFeedBack = feedbackService.carFeedBackByTanent(tenant.getId());
									Feedback feedBack = new Feedback();
									if (!listOfFeedBack.isEmpty()) {
										for (Feedback feedback : listOfFeedBack) {
											if (feedback.getCarConditionRating() != null) {
												carcon += feedback.getCarConditionRating();
												carcount = carcount + 1;
											}
											if (feedback.getOverallServiceRating() != null) {
												overallservice += feedback.getOverallServiceRating();
												overallcount = overallcount + 1;
											}
											if (feedback.getDrivingRating() != null) {
												driverperformence += feedback.getDrivingRating();
												drivercount = drivercount + 1;
											}

										}
										carcon /= carcount;
										overallservice /= overallcount;
										driverperformence /= drivercount;
										averageall = carcon + overallservice + driverperformence;
										averageall = averageall / 3;

										feedBack.setCarConditionRating(carcon);
										feedBack.setDrivingRating(driverperformence);
										feedBack.setOverallServiceRating(overallservice);
										feedBack.setAverageRating(averageall);
									}

									tenant.setFeedBack(feedBack);
									tenantListnew.add(tenant);

								}
								map.put("tenantList", tenantListnew);
								map.put("status", true);

							} else {
								map.put("status", false);
								map.put(Constants.MESSAGE, "No Tenant Available for this City");
							}
						}
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No City Available");

					}

				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Parameters");

				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**Created By: Johnson Chunara Date:22-02-2017 Use:Send OTP To Guest Mobile No For Cancel OTP.
	 * @param tenantID
	 * @param userToken
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_GET_CAR_FOR_REVIEW, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getCarForReview(@QueryParam("tenantID") String tenantID,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (tenantID != null) {
					if (tenantService.getById(Long.valueOf(tenantID)) != null) {
						float carPerformance = 0, allcarPerformance = 0;
						int carcount = 0;
						Feedback feedBacknew = new Feedback();
						List<Car> listOfcar = carService.listOfCarByTanent(Long.valueOf(tenantID));
						List<Car> listOfNewCar = new ArrayList<>();
						if (!listOfcar.isEmpty()) {
							for (Car car : listOfcar) {
								List<ClientModel> listOfTrips = clientModelService.getByTripStatusByCar(car);
								if (!listOfTrips.isEmpty()) {
									for (ClientModel clientModel : listOfTrips) {
										if (feedbackService.getFeedbackByTripID(clientModel.getTripId(),
												Long.valueOf(tenantID)) != null) {
											Feedback feedBack = feedbackService.getFeedbackByTripID(
													clientModel.getTripId(), Long.valueOf(tenantID));
											if (feedBack.getCarConditionRating() != null)
												carPerformance += feedBack.getCarConditionRating();
											carcount = carcount + 1;

										}

									}
								}

								carPerformance /= carcount;
								feedBacknew.setCarConditionRating(carPerformance);
								car.setFeedback(feedBacknew);
								listOfNewCar.add(car);
								allcarPerformance += carPerformance;
							}

							allcarPerformance /= listOfcar.size();
							map.put("status", true);
							map.put("allCarConditionRating", allcarPerformance);
							map.put("List Of Car", listOfNewCar);

						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No Car Available For This Car");

						}

					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No Tenant Available For This");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Parameters");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);

	}
	/**Created By: Johnson Chunara Date:22-02-2017 Use:Get Driver From Tenant ID with review.
	 * @param tenantID **tenantId**
	 * @param userToken **encoded token**
	 * @return **json Response**
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_GET_DRIVER_FOR_REVIEW, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getDriverForReview(@QueryParam("tenantID") String tenantID,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (tenantID != null) {
					if (tenantService.getById(Long.valueOf(tenantID)) != null) {
						@SuppressWarnings("unused")
						float drivereating = 0, driverbehaviourrating = 0, allcarPerformance = 0, drivingrating = 0,
								driveroverall = 0, alldriverrating = 0;
						int drivereatingcount = 0, driverbehaviourratingcount = 0, drivingratingcount = 0;
						Feedback feedBacknew = new Feedback();
						List<Driver> listOfdriver = driverService.listOfDriverByTanent(Long.valueOf(tenantID));
						List<Driver> listOfdrivernew = new ArrayList<>();
						if (!listOfdriver.isEmpty()) {
							for (Driver driver : listOfdriver) {
								List<ClientModel> listOfTrips = clientModelService.getDriverByDriverId(driver);
								if (!listOfTrips.isEmpty()) {
									for (ClientModel clientModel : listOfTrips) {
										if (feedbackService.getFeedbackByTripID(clientModel.getTripId(),
												Long.valueOf(tenantID)) != null) {
											Feedback feedBack = feedbackService.getFeedbackByTripID(
													clientModel.getTripId(), Long.valueOf(tenantID));
											if (feedBack.getDrivingRating() != null) {
												drivereating += feedBack.getDrivingRating();
												drivereatingcount = drivereatingcount + 1;
											}
											if (feedBack.getDriverBehaviourRating() != null) {
												driverbehaviourrating += feedBack.getDriverBehaviourRating();
												driverbehaviourratingcount = driverbehaviourratingcount + 1;
											}
											if (feedBack.getDriverTestingRating() != null) {
												drivingrating += feedBack.getDriverTestingRating();
												drivingratingcount = drivingratingcount + 1;
											}

										}

									}
									drivereating /= drivereatingcount;
									driverbehaviourrating /= driverbehaviourratingcount;
									drivingrating /= drivingratingcount;
									driveroverall = drivereating + driverbehaviourrating + drivingrating;
									driveroverall = driveroverall / 3;
									feedBacknew.setDrivingRating(drivereating);
									feedBacknew.setDriverTestingRating(drivingrating);
									feedBacknew.setDriverBehaviourRating(driverbehaviourrating);
									feedBacknew.setAverageRating(driveroverall);
									driver.setFeedback(feedBacknew);
									alldriverrating += driveroverall;

								}
								listOfdrivernew.add(driver);

							}

							alldriverrating /= listOfdrivernew.size();
							map.put("status", true);
							map.put("alldriverRating", alldriverrating);
							map.put("List Of Car", listOfdrivernew);

						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No Driver Available For This tenants");

						}

					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No Tenant Available For This");

					}

				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Parameters");

				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use:Save Customer Details For PreBooking
	 * @param tenantID **tenantId**
	 * @param firstname **firstname**
	 * @param lastname **lastname**
	 * @param middlename **middlename**
	 * @param address **address**
	 * @param contactnumber *contact No***
	 * @param emailid **emailId**
	 * @param numberofpassanger **No of Passanger**
	 * @param pickupplace **pickupPlace**
	 * @param destinationplace **destination**
	 * @param paymentmode **paymentMode**
	 * @param userToken **encoded Token**
	 * @return Json Response
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_SAVE_CLIENT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveclient(@QueryParam("tenantID") String tenantID,
			@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname,
			@QueryParam("middlename") String middlename, @QueryParam("address") String address,
			@QueryParam("contactnumber") String contactnumber, @QueryParam("emailid") String emailid,
			@QueryParam("numberofpassanger") String numberofpassanger, @QueryParam("pickupplace") String pickupplace,
			@QueryParam("destinationplace") String destinationplace, @QueryParam("paymentmode")String paymentmode,
			@QueryParam("fromdatetime") String fromdatetime,@QueryParam("todatetime") String todatetime,
			@QueryParam("carType") String carType,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				PreBooking preBooking = new PreBooking();
				String pickupEncode;
				try {
					pickupEncode = Base64.getEncoder().encodeToString(pickupplace.getBytes("utf-8"));
					preBooking.setPickUpLocation(pickupEncode);
					String dropEncode = Base64.getEncoder().encodeToString(destinationplace.getBytes("utf-8"));
					preBooking.setDrop_location(dropEncode);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String bookingId = randomString(8);
				preBooking.setTanentID(Long.valueOf(tenantID));
				preBooking.setStatusID(Constants.ACTIVE);
				preBooking.setRequestedBookingId(bookingId);
				preBooking.setNoofpassanger(Integer.parseInt(numberofpassanger));
				if(carType!=null){
					CarType carType1=carTypeService.getCarTypeByCarTypeName(carType);
					if(carType1!=null){
						preBooking.setCarType(carType1);
					}
				}
				preBooking.setPickUpDateTime(CommonUtil.convertStringToTimestamp(fromdatetime));
				preBooking.setDropDateTime(CommonUtil.convertStringToTimestamp(todatetime));
				Guest guest = guestService.getGuestDetailByContactNo(contactnumber);
				if(guest!=null){
					preBooking.setGuest(guest);
				}
				else{
					Guest guestNew = new Guest();
					guestNew.setFname(firstname);
					guestNew.setLname(lastname);
					guestNew.setMname(middlename);
					guestNew.setPersonName(firstname+""+middlename+""+lastname);
					guestNew.setContactNo(contactnumber);
					guestNew.setEmailId(emailid);
					guestNew.setAddress(address);
					preBooking.setGuest(guestNew);

				}
				preBooking.setFromMobile(true);
				preBookingService.save(preBooking);
				map.put("status", true);
				map.put("requestId", preBooking.getRequestedBookingId());
				map.put(Constants.MESSAGE, "Client Saved Successfully");
				try {
					String msg = "Customer Request for Car Booking. Requested Booking Id : "+bookingId;
					Notification notification = notificationService.saveAlertSchedulerNotification(msg , Long.valueOf(tenantID));
					List<User> userListDefaultFlag = userService.listOfUserByIdAndTanentID(true, Long.valueOf(tenantID));
					if (userListDefaultFlag.size() > 0) {
						simpMessagingTemplate.convertAndSendToUser(userListDefaultFlag.get(0).getUsername(), "/queue/notify", notification);
					}
					List<User> userListNotDefaultFlag = userService.listOfUserByIdAndTanentID(false, Long.valueOf(tenantID));
					if(userListNotDefaultFlag.size() > 0) {
						for(User userNew : userListNotDefaultFlag) {
							simpMessagingTemplate.convertAndSendToUser(userNew.getUsername(), "/queue/notify", notification);	
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use:Get Available Car Details Using City And Tenant
	 * @param tenant **tenant**
	 * @param fromdatetime **fromdatetime**
	 * @param todatetime **todatetime**
	 * @param city **city**
	 * @param userToken **encoded Token**
	 * @return Json response
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_BOOK_CAR_TENANT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> bookCarByTenant(@QueryParam("tenant") String tenant,
			@QueryParam("fromdatetime") String fromdatetime, @QueryParam("todatetime") String todatetime,
			@QueryParam("city") String city, @QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (cityService.getCityByCityName(city) != null) {
					City citynew = cityService.getCityByCityName(city);
					if (tenantService.getTenantByCityTenant(tenant, citynew.getId()) != null) {
						Tenant tenantnew = tenantService.getTenantByCityTenant(tenant, citynew.getId());
						float carcon = 0, overallservice = 0, driverperformence = 0, averageall = 0;
						int carcount = 0, overallcount = 0, drivercount = 0;
						List<Feedback> listOfFeedBack = feedbackService.carFeedBackByTanent(tenantnew.getId());
						Feedback feedBack = new Feedback();
						if (!listOfFeedBack.isEmpty()) {
							for (Feedback feedback : listOfFeedBack) {
								if (feedback.getCarConditionRating() != null) {
									carcon += feedback.getCarConditionRating();
									carcount = carcount + 1;
								}
								if (feedback.getOverallServiceRating() != null) {
									overallservice += feedback.getOverallServiceRating();
									overallcount = overallcount + 1;
								}
								if (feedback.getDrivingRating() != null) {
									driverperformence += feedback.getDrivingRating();
									drivercount = drivercount + 1;
								}
							}
							carcon /= carcount;
							overallservice /= overallcount;
							driverperformence /= drivercount;
							averageall = carcon + overallservice + driverperformence;
							averageall = averageall / 3;
							feedBack.setCarConditionRating(carcon);
							feedBack.setDrivingRating(driverperformence);
							feedBack.setOverallServiceRating(overallservice);
							feedBack.setAverageRating(averageall);
						}
						tenantnew.setFeedBack(feedBack);
						map.put("status", true);
						map.put("Tenant", tenantnew);
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No Tenant Available");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "City Not Available");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**Created By: Jimit  Patel Date:22-02-2017 Use:To Get Past Rides
	 * @param userToken **encoded Token**
	 * @return **json Response**
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.PAST_RIDE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getPostRide(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = null;
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));

		List<String> message = new ArrayList<String>();
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
				List<ClientModel> client = clientModelService.getTripsByStatus(guest);
				map.put("status", true);
				map.put("message", message);
				map.put("client", client);
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**Created By: Darshan Limbachiya Date:22-02-2017 Use:Get Upcoming Trips
	 * @param userToken **encoded Token**
	 * @return Json Response
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.UPCOMING_RIDES, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getUpComingRides(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = null;
		user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
		Map<String, Object> map = new HashMap<>();
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
				List<ClientModel> client = clientModelService.getTripsBypendingStatus(guest);
                if (!client.isEmpty()) {
					map.put("status", true);
					map.put("message", "Trip Available");
					map.put("client", client);
				} else {
					map.put("status", false);
					map.put("message", " No Trip Available");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use:Request For Cancel Trip
	 * @param tripID **Trip ID**
	 * @param userToken **encoded Token**
	 * @return jsonResponse
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.CANCLE_REQUEST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> cancelRequest(@QueryParam("tripID") String tripID,
			@QueryParam("userToken") final String userToken) throws ServiceException {

		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = null;
		user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
		Map<String, Object> map = new HashMap<>();
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				ClientModel client = clientModelService.checkTripId(tripID);
				if (client != null) {
					Integer otp = OTPData.generateOtp();
					client.setOtp(otp);
					clientModelService.update(client);
					try {
						SMSSend.sendSms(client.getGuest(), otp);
					} catch (Exception e) {

					}
					map.put("status", true);
					map.put("message", "Otp Send SuccessFully");
					map.put("otp", otp);
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Trip is not Avalable ");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:27-02-2017 Use:Check OTP For Cancel Trips Request
	 * @param tripID **tripId**
	 * @param otp **otp**
	 * @param userToken **encoded Token**
	 * @return json response
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_CAR_CANCEL_OTP, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> bookCarByTenantCartype(@QueryParam("tripID") String tripID,
			@QueryParam("otp") String otp, @QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (clientModelService.getTripByTripId(tripID) != null) {
					ClientModel client = clientModelService.getTripByTripId(tripID);
					if (Integer.valueOf(otp).equals(client.getOtp())) {
						map.put("status", true);
						map.put(Constants.MESSAGE, "Request For Cancel");
						client.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CE_CANCEL));
						clientModelService.update(client);
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "OTP is mismatch");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "No trip Available");
				}

			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use:Car Booking From Tenant, City, Cartype.
	 * @param tenant **tenant**
	 * @param fromdatetime **fromdatetime**
	 * @param todatetime **todatetime**
	 * @param city **city**
	 * @param cartype **carType**
	 * @param userToken **Encoded Token**
	 * @return Json Response
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_USER_BOOK_CAR_TENANT_CAR_TYPE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> bookCarByTenantCartype(@QueryParam("tenant") String tenant,
			@QueryParam("fromdatetime") String fromdatetime, @QueryParam("todatetime") String todatetime,
			@QueryParam("city") String city, @QueryParam("cartype") String cartype,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (cityService.getCityByCityName(city) != null) {
					City citynew = cityService.getCityByCityName(city);
					if (tenantService.getTenantByCityTenant(tenant, citynew.getId()) != null) {
						float allcarPerformance = 0;
						float carcon = 0, overallservice = 0, driverperformence = 0, averageall = 0;
						int carcounttennt = 0, overallcount = 0, drivercount = 0;
						Tenant tenantnew = tenantService.getTenantByCityTenant(tenant, citynew.getId());
						List<Feedback> listOfFeedBack = feedbackService.carFeedBackByTanent(tenantnew.getId());
						Feedback feedBack = new Feedback();
						if (!listOfFeedBack.isEmpty()) {
							for (Feedback feedback : listOfFeedBack) {
								if (feedback.getCarConditionRating() != null) {
									carcon += feedback.getCarConditionRating();
									carcounttennt = carcounttennt + 1;
								}
								if (feedback.getOverallServiceRating() != null) {
									overallservice += feedback.getOverallServiceRating();
									overallcount = overallcount + 1;
								}
								if (feedback.getDrivingRating() != null) {
									driverperformence += feedback.getDrivingRating();
									drivercount = drivercount + 1;
								}

							}
							carcon /= carcounttennt;
							overallservice /= overallcount;
							driverperformence /= drivercount;
							averageall = carcon + overallservice + driverperformence;
							averageall = averageall / 3;

							feedBack.setCarConditionRating(carcon);
							feedBack.setDrivingRating(driverperformence);
							feedBack.setOverallServiceRating(overallservice);
							feedBack.setAverageRating(averageall);
						}
						tenantnew.setFeedBack(feedBack);
						List<Car> carList = carService.getCarAvailableList(fromdatetime, todatetime, cartype,
								tenantnew.getId());
						List<Car> listOfNewCar = new ArrayList<>();
						if (!carList.isEmpty()) {
							Feedback feedBacknew = new Feedback();
							for (Car car : carList) {
								float carPerformance = 0;
								int carcount = 0;
								List<ClientModel> listOfTrips = clientModelService.getByTripStatusByCar(car);
								if (!listOfTrips.isEmpty()) {
									for (ClientModel clientModel : listOfTrips) {
										if (feedbackService.getFeedbackByTripID(clientModel.getTripId(),
												car.getTanentID()) != null) {
											Feedback feedBackcar = feedbackService
													.getFeedbackByTripID(clientModel.getTripId(), car.getTanentID());
											if (feedBackcar.getCarConditionRating() != null)
												carPerformance += feedBackcar.getCarConditionRating();
											carcount = carcount + 1;
										}
									}
								}
								carPerformance /= carcount;
								feedBacknew.setCarConditionRating(carPerformance);
								car.setFeedback(feedBacknew);
								listOfNewCar.add(car);
								allcarPerformance += carPerformance;
							}

							allcarPerformance /= carList.size();
							map.put("status", true);
							map.put("allCarConditionRating", allcarPerformance);
							map.put("List Of Car", listOfNewCar);
							map.put("Tenant", tenantnew);

						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No Car Available");

						}
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No Tenant Available");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "City Not Available");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
		
	/**
	 * Created By: Johnson Chunara Date:22-02-2017 Use:Car Booking From City, Cartype.
	 * @param fromdatetime **fromdatetime**
	 * @param todatetime **todatetime**
	 * @param city **city**
	 * @param cartype **carType**
	 * @param userToken **Encoded Token**
	 * @return Json Response
	 * @throws ServiceException
	 */
	
	@RequestMapping(value = UrlConstants.FEED_USER_BOOK_CAR_CITY_CAR_TYPE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> bookCarByTenantCartype(
			@QueryParam("fromdatetime") String fromdatetime, @QueryParam("todatetime") String todatetime,
			@QueryParam("city") String city, @QueryParam("cartype") String cartype,
			@QueryParam("userToken") final String userToken) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				City citynew = cityService.getCityByCityName(city);
				if (citynew!=null) {
					List<Tenant> tenantList = tenantService.getTenantByCityId(citynew.getId());
					List<String> listoftenantId = new ArrayList<>();
					for (Tenant tenant : tenantList) {
						listoftenantId.add(String.valueOf(tenant.getId()));
					}
					if(!listoftenantId.isEmpty()){
						List<Car> catList = clientModelService.getCarAvailableListByParamTenant(listoftenantId,
								fromdatetime, todatetime, cartype);
						HashSet<Long> tenantListset = new HashSet<Long>();
						for (Car car : catList) {
							tenantListset.add(car.getTanentID());
						}
						List<Tenant> tenantListGet = new ArrayList<>();

						for (Long tenantId : tenantListset) {
							float carcon = 0, overallservice = 0, driverperformence = 0, averageall = 0;
							int carcount = 0, overallcount = 0, drivercount = 0;
							Tenant tenant = tenantService.getById(tenantId);
							List<Feedback> listOfFeedBack = feedbackService.carFeedBackByTanent(tenant.getId());
							Feedback feedBack = new Feedback();
							if (!listOfFeedBack.isEmpty()) {
								for (Feedback feedback : listOfFeedBack) {
									if (feedback.getCarConditionRating() != null) {
										carcon += feedback.getCarConditionRating();
										carcount = carcount + 1;
									}
									if (feedback.getOverallServiceRating() != null) {
										overallservice += feedback.getOverallServiceRating();
										overallcount = overallcount + 1;
									}
									if (feedback.getDrivingRating() != null) {
										driverperformence += feedback.getDrivingRating();
										drivercount = drivercount + 1;
									}

								}
								carcon /= carcount;
								overallservice /= overallcount;
								driverperformence /= drivercount;
								averageall = carcon + overallservice + driverperformence;
								averageall = averageall / 3;

								feedBack.setCarConditionRating(carcon);
								feedBack.setDrivingRating(driverperformence);
								feedBack.setOverallServiceRating(overallservice);
								feedBack.setAverageRating(averageall);
							}

							tenant.setFeedBack(feedBack);
							tenantListGet.add(tenant);
						}
						map.put("tenantList", tenantListGet);


					}else{

						map.put("status", false);
						map.put(Constants.MESSAGE, "No Tenant Available For This City");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "City Not Available");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Enter Valid userName or Password");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	/**
	 * reated By : Jimit Patel , Date: 20-01-2017. Use : To generate Random
	 * Number For TripId return String
	 */
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * rnd **for random number**.
	 */
	private static SecureRandom rnd = new SecureRandom();

	/**
	 * @param len
	 *            **Length**
	 * @return String
	 */

	public String randomString(final int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		return sb.toString();
	}

	/**
	 * Created By: Jimit Patel Date:24-02-2017 Use: Save User Profile Image
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_USER_PROFILE, method = RequestMethod.GET)
	public final ResponseEntity<FileInfo> saveuserprofile(@QueryParam("userToken") final String userToken,
			@QueryParam("userImage") final byte[] userImage) throws ServiceException {

		Map<String, Object> map = new HashMap<>();
		/*
		 * try{
		 *//*
			 * File fi = new
			 * File("/root/Pictures/Screenshot from 2016-12-12 11-57-57.png");
			 * byte[] fileContent = Files.readAllBytes(fi.toPath());
			 * System.out.println(fileContent);
			 * 
			 * BufferedImage bufferedImage = ImageIO.read(new
			 * ByteArrayInputStream(fileContent)); ImageIO.write(bufferedImage,
			 * "png", new File("/root/Pictures/johnson.png"));
			 * 
			 * 
			 * }catch(Exception e){
			 * 
			 * }
			 */
		if (userImage != null) {
			final byte[] decoded = Base64.getDecoder().decode(userToken);
			String emailIDOrContactNo = "";
			String pass = "";

			try {
				String decodeToken = new String(decoded, "UTF-8");
				String[] newToken = decodeToken.split("-");
				emailIDOrContactNo = newToken[0];
				pass = newToken[1];
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			User user = null;
			if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
				 user= userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));

				if (user != null) {
					if (user.getPassword().equals(pass)) {
						Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
						if (guest != null) {
							guest.setImage(userImage);
							guestService.update(guest);
							map.put(Constants.MESSAGE, "Image Uploaded Successfully");
							map.put("status", true);
						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No user Available");
						}
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Invalid Login");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Login");
				}
			}
		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Please Insert Image");
		}
		return new ResponseEntity<FileInfo>(HttpStatus.OK);
	}

	/**
	 * Created By: Jimit Patel Date:24-02-2017 Use: list of trips of single User
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.RATE_USER_TRIPS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> rateusertrips(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		Map<String, Object> map = new HashMap<>();
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = null;
		if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));

			if (user != null) {
				if (user.getPassword().equals(pass)) {
					Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
					List<ClientModel> triplist = clientModelService.getEndTrips(guest);
                    if (triplist != null) {
						map.put("trip id list", triplist);
						map.put(Constants.MESSAGE, "Feedback list");
						map.put("status", true);
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Unsuccessfull");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Unsuccessfull");
				}
			}
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Jimit Patel Date:24-02-2017 Use: Save FeedBack
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	 @RequestMapping(value = UrlConstants.SAVE_CUSTOMER_FEEDBACK, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public final ResponseEntity<Map<String, Object>> savefeedback(@QueryParam("userToken") final String userToken,
				@QueryParam("tripId") final String tripId, @QueryParam("driving") final String driving,
				@QueryParam("driverBehaviour") final String driverBehaviour,
				@QueryParam("performance") final String performance,
				@QueryParam("carCondition") final String carCondition,
				@QueryParam("overallServiceRating") final String overallServiceRating,
				@QueryParam("remark") final String remark)
				throws ServiceException {
			final byte[] decoded = Base64.getDecoder().decode(userToken);
			String emailIDOrContactNo = "";
			String pass = "";
			Map<String, Object> map = new HashMap<>();
			try {
				String decodeToken = new String(decoded, "UTF-8");
				String[] newToken = decodeToken.split("-");
				emailIDOrContactNo = newToken[0];
				pass = newToken[1];
			} catch (UnsupportedEncodingException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
			User user = null;
			if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
				user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));

				if (user != null) {
					if (user.getPassword().equals(pass)) {
						Feedback feedback1 = null;
						feedback1 = feedbackService.getFeedbackByTripID(tripId);
						if (feedback1 != null) {
							map.put(Constants.MESSAGE, "Feedback Is Already Exists");
	                        map.put("status", false);
						} else {
							Float drivingRating = Float.valueOf(driving);
							Float driverBehaviourRating = Float.valueOf(driverBehaviour);
							Float performanceRating = Float.valueOf(performance);
							Float carConditionRating = Float.valueOf(carCondition);
							Float totalRating = drivingRating + driverBehaviourRating + performanceRating
									+ carConditionRating;
							Float avarageRating = totalRating / 4;
							Float overallServiceRatingValue= Float.valueOf(overallServiceRating);
							Feedback feedback = new Feedback();
							feedback.setTripID(tripId);
							feedback.setAverageRating(avarageRating);
							feedback.setCarConditionRating(carConditionRating);
							feedback.setDriverBehaviourRating(driverBehaviourRating);
							feedback.setDrivingRating(drivingRating);
							feedback.setDriverTestingRating(performanceRating);
	                        ClientModel clientModel = clientModelService.getTripByTripId(tripId);
							feedback.setPickUpDateTime(clientModel.getPickUpDateTime());
							feedback.setDropDateTime(clientModel.getDropDateTime());
							feedback.setOverallServiceRating(overallServiceRatingValue);

	                        feedback.setRemark(remark);
							feedbackService.save(feedback);
							map.put(Constants.MESSAGE, "Feedback list");
							map.put("status", true);
						}
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Unsuccessfull");
					}
				}

			}
			return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}

	 /**
		 * Created By: Johnson Chunara Date:4-03-02-2017 Use:Get Geo Graphic Data .
		 * @param tripID **tripID**
		 * @param userToken **Encoded Token**
		 * @return Json Response
		 * @throws ServiceException
		 */
	 
	@RequestMapping(value = UrlConstants.FEED_USER_GET_GEO_DATA_FEED, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> getGeoDatafeed(@QueryParam("tripID") final String tripID,
			@QueryParam("userToken") String userToken) throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		User user = null;
		Map<String, Object> map = new HashMap<>();
		user = userService.loginEmailByContactNo(Long.valueOf(emailIDOrContactNo));
		if (user != null) {
			if (user.getPassword().equals(pass)) {
				if (tripID != null) {
					GeoData geoData = geoDataService.getgeoData(tripID);
					ClientModel client = clientModelService.getTripByTripId(tripID);
					if (geoData != null) {
						map.put("geodata", geoData);
						map.put("DriverDetails", client.getDriver());
						map.put("status", true);
						map.put(Constants.MESSAGE, "Fetch SuccessFully");
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Fetch UnSuccessFully");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Parameter");
				}
		} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Invalid login");
			}

		} else {
			map.put("status", false);
			map.put(Constants.MESSAGE, "Invalid login");
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Jimit Patel Date:28-02-2017 Use: when customer Send request
	 * for Cancelation
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_CUSTOMER_CANCEL_REQUEST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> customercanceltrip(@QueryParam("userToken") final String userToken,
			@QueryParam("tripId") final String tripId) throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					ClientModel clientModel = clientModelService.getTripByTripId(tripId);
					if (clientModel != null) {
						clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CE_CANCEL));
						clientModelService.update(clientModel);
						map.put(Constants.MESSAGE, "Request Send Successfully");
						map.put("status", true);
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Unsuccessfull");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Unsuccessfull");
				}
			}

		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	/**
	 * Created By: Jimit Patel Date:28-02-2017 Use: when customer verify otp
	 *
	 * @param userToken
	 * @return map
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_OTP_CUSTOMER_CANCEL_VERIFICATION, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> customercanceltripverification(
			@QueryParam("userToken") final String userToken, @QueryParam("tripId") final String tripId,
			@QueryParam("otp") final String otp) throws ServiceException {
		int Otp = Integer.parseInt(otp);
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					ClientModel clientModel = clientModelService.getTripByTripId(tripId);
					if (clientModel != null) {
						if (clientModel.getOtp() == Otp) {
							clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_CANCEL));
							clientModelService.update(clientModel);
						}
						map.put(Constants.MESSAGE, "Trip Canceled Successfully");
						map.put("status", true);
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Unsuccessfull");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Unsuccessfull");
				}
			}

		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}


	 /**
		 * Created By: Johnson Chunara Date:5-03-2017 Use:Get Driver Traking For Live Trips .
		 * @param userToken **Encoded Token**
		 * @return Json Response
		 * @throws ServiceException
		 */
	
	@RequestMapping(value = UrlConstants.FEED_DRIVER_TRAKING, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> driverTaracking(@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					Guest guest = guestService.checkemailorcontact(emailIDOrContactNo);
					if (guest != null) {
						ClientModel client = clientModelService.getTripsByStatusByGuest(guest,
								CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_LIVE));
						if (client != null) {
                            map.put("tripsDetails", client);
							TripDetails tripDetails = tripDeatilsService.getTripDetailsByTripId(client.getTripId());
							if (tripDetails != null) {
								map.put("startKm", tripDetails.getStartKm());
								map.put("startDate",tripDetails.getStartDate());
							}
						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No Trips Available");
						}
					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "No Customer available");
					}

				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}

			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Invalid login");
			}
		}

		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

	 /**
		 * Created By: Johnson Chunara Date:8-03-2017 Use:Get Reamark List .
		 * @param tenantID **tenantID**
		 * @param userToken **Encoded Token**
		 * @return Json Response
		 * @throws ServiceException
		 */
	@RequestMapping(value = UrlConstants.FEED_REMARK_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> remarkList(@QueryParam("tenantID")final String tenantID,@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !emailIDOrContactNo.equals("")) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					List<Feedback> listOfFeedBack = feedbackService.getFeedbackList(Long.valueOf(tenantID));
					if(!listOfFeedBack.isEmpty()) {
						map.put("status",true);
						map.put(Constants.MESSAGE, "feedBack Available");
						map.put("feedBacks", listOfFeedBack);
					}else{
						map.put("status",false);
						map.put(Constants.MESSAGE, "No feedBack Available");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Invalid login");
			}
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = UrlConstants.FEED_SAVE_BILL_PAYMENT_MODE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveBillPaymentMode(@QueryParam("amount")final String amount,@QueryParam("tripID")final String tripID,@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)){
					 String accessKey = "HSHOC3Q8U6RGJG5WX7QG";
				     String returnUrl = "35.154.98.128:8080/PaymentIntegration/return_emxcel.jsp";
				     String txnID = String.valueOf(System.currentTimeMillis());
				     String dataString = "merchantAccessKey=" + accessKey  + "&amount=" + amount;
				 	SecretKeySpec secretKeySpec = new SecretKeySpec(Constants.SECRETKEY.getBytes(), "HmacSHA1");
				 	
				 	try {
				 		Mac mac = Mac.getInstance("HmacSHA1");
						mac.init(secretKeySpec);
						byte []hmacArr = mac.doFinal(dataString.getBytes());
						StringBuilder build = new StringBuilder();
					 	for (byte b : hmacArr) {
					 	    build.append(String.format("%02x", b));
					 	}
					 	String hmac = build.toString();
					 	BillPaymentModel billPaymentModel= billPaymentModelService.saveBillPaymentMode(amount,accessKey,returnUrl,txnID,hmac,tripID);
					 	map.put("PaymentDeails", billPaymentModel);
					 	map.put("status", true);
					 	
					} catch (InvalidKeyException | NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						map.put("status", false);
						map.put(Constants.MESSAGE, e.getCause());
					}
				 	
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Invalid login");
			}
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}


	@RequestMapping(value = UrlConstants.FEED_SAVE_PAYMENT_MODE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveBillPaymentMode(@QueryParam("txId")final String txId,
																		 @QueryParam("txStatus")final String txStatus,
																		 @QueryParam("pgTxnNo")final String pgTxnNo,
																		 @QueryParam("issuerRefNo")final String issuerRefNo,
																		 @QueryParam("authIdCode")final String authIdCode,
																		 @QueryParam("firstName")final String firstName,
																		 @QueryParam("lastName")final String lastName,
																		 @QueryParam("pgRespCode")final String pgRespCode,
																		 @QueryParam("addressZip")final String addressZip,
																		 @QueryParam("amount")final String amount,
																		 @QueryParam("requestSignature")final String requestSignature,
																		 @QueryParam("userToken") final String userToken) throws Exception {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					String accessKey = "HSHOC3Q8U6RGJG5WX7QG";
					String dataString = "merchantAccessKey=" + accessKey + "&amount=" + amount;
					SecretKeySpec secretKeySpec = new SecretKeySpec(Constants.SECRETKEY.getBytes(), "HmacSHA1");

						Mac mac = Mac.getInstance("HmacSHA1");
						mac.init(secretKeySpec);
						byte[] hmacArr = mac.doFinal(dataString.getBytes());
						StringBuilder build = new StringBuilder();
						for (byte b : hmacArr) {
							build.append(String.format("%02x", b));
						}
						String hmac = build.toString();

						if (hmac.equals(requestSignature)) {
							PaymentModel paymentModel = paymentModeService.savePaymentMode(txId, txStatus, pgTxnNo, issuerRefNo, authIdCode, firstName, lastName, pgRespCode, addressZip, amount, requestSignature);
							map.put("paymentModel", paymentModel);
							map.put("status", true);

						} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "Transaction Failed  - Reason-Signature Verfication Failed");
						}
					} else{
						map.put("status", false);
						map.put(Constants.MESSAGE, "Invalid login");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	
	/**
	 * Created By: Vaibhav Tank Date:20-03-2017 Use: Save Bill payment of PayUMoney
	 * @param amount
	 * @param tenantId
	 * @param tripId
	 * @param userToken
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.FEED_SAVE_BILL_PAYMENT_MODE_PAYUMONEY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveBillPaymentModePayUMoney(@QueryParam("amount")final String amount,@QueryParam("tripId")final String tripId,/*@QueryParam("paymentId")final String paymentId,*/@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		//Long LongtenantId = Long.parseLong(tenantId);
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			if(amount !=null && tripId !=null ){
				TripDetails tripDetailsByTripId = tripDeatilsService.getTripDetailsByTripId(tripId);
				user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
				if (user != null) {
					if (user.getPassword().equals(pass)){
						 String accessKey = "4825049";
					     String secretKey = "BC50nb";
					     String Salt = "Bwxo1cPe";
					     String returnUrl = "35.154.98.128:8080/PaymentIntegration/return_emxcel.jsp";
					     String txnID = String.valueOf(System.currentTimeMillis());
					     String dataString = "merchantAccessKey=" + accessKey + "&transactionId=" + txnID + "&amount=" + amount;
					 	SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");

					 	try {
					 		Mac mac = Mac.getInstance("HmacSHA1");
							mac.init(secretKeySpec);
							byte []hmacArr = mac.doFinal(dataString.getBytes());
							StringBuilder build = new StringBuilder();
						 	for (byte b : hmacArr) {
						 	    build.append(String.format("%02x", b));
						 	}
						 	String hmac = build.toString();
						 	BillPaymentModel billPaymentModel= billPaymentModelService.saveBillPaymentMode(amount,accessKey,returnUrl,txnID,hmac,tripId);
						 	map.put("PaymentDeails", billPaymentModel);
						 	//map.put("userDetailsByTenantId", userDetails);
						 	//map.put("tripDetails", tripDetailsByTripId);
						 	map.put("status", true);

						} catch (InvalidKeyException | NoSuchAlgorithmException e) {
							// TODO Auto-generated catch block
							map.put("status", false);
							map.put("message", e.getCause());
						}

					} else {
						map.put("status", false);
						map.put(Constants.MESSAGE, "Invalid login");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			}
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	
	/**
	 * Created By: Vaibhav Tank Date:20-03-2017 Use: 
	 * @param txId
	 * @param txStatus
	 * @param productInfo
	 * @param email
	 * @param phone
	 * @param firstName
	 * @param lastName
	 * @param service_provider
	 * @param addressZip
	 * @param amount
	 * @param hashcode
	 * @param pgType
	 * @param paymentMode
	 * @param userToken
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.FEED_SAVE_PAYMENT_MODE_PAYUMONEY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveBillPaymentModePayUMoney(@QueryParam("txId")final String txId,
																					@QueryParam("txStatus")final String txStatus,
																					 @QueryParam("productInfo")final String productInfo,
																					 @QueryParam("email")final String email,
																					 @QueryParam("phone")final String phone,
																					 @QueryParam("firstName")final String firstName,
																					 @QueryParam("lastName")final String lastName,
																					 @QueryParam("service_provider")final String service_provider,
																					 @QueryParam("addressZip")final String addressZip,
																					 @QueryParam("amount")final String amount,
																					 @QueryParam("hashcode")final String hashcode,
																					 //@QueryParam("bankrefnum")final String bankrefnum,
																					 @QueryParam("pgType")final String pgType,
																					 @QueryParam("paymentMode")final String paymentMode,
																					 @QueryParam("userToken") final String userToken) throws Exception {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
					String accessKey = "4825049";
				     String secretKey = "BC50nb";
					String Salt = "Bwxo1cPe";
					String dataString = "merchantAccessKey=" + accessKey + "&amount=" + amount;
					SecretKeySpec secretKeySpec = new SecretKeySpec(Constants.SECRETKEY.getBytes(), "HmacSHA1");

						Mac mac = Mac.getInstance("HmacSHA1");
						mac.init(secretKeySpec);
						byte[] hmacArr = mac.doFinal(dataString.getBytes());
						StringBuilder build = new StringBuilder();
						for (byte b : hmacArr) {
							build.append(String.format("%02x", b));
						}
						String hmac = build.toString();

						//if (hmac.equals(hashcode)) {
							PaymentModel paymentModel = paymentModeService.savePaymentModePayUMoney(txId, txStatus, productInfo, email, phone, firstName, lastName, service_provider, addressZip, amount, hashcode,pgType,paymentMode);
							map.put("paymentModel", paymentModel);
							map.put("status", true);

							/*} else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "Transaction Failed  - Reason-Signature Verfication Failed");
						}*/
					} else{
						map.put("status", false);
						map.put(Constants.MESSAGE, "Invalid login");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	/*@RequestMapping(value = UrlConstants.FEED_SAVE_PAYMENT_MODE_PAYUMONEY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> saveBillPaymentModePayUMoney(@QueryParam("txId")final String txId,
																		 @QueryParam("txStatus")final String txStatus,
																		 @QueryParam("productInfo")final String productInfo,
																		 @QueryParam("email")final String email,
																		 @QueryParam("phone")final String authIdCode,
																		 @QueryParam("firstName")final String firstName,
																		 @QueryParam("lastName")final String lastName,
																		 @QueryParam("service_provider")final String service_provider,
																		 @QueryParam("addressZip")final String addressZip,
																		 @QueryParam("amount")final String amount,
																		 //@QueryParam("requestSignature")final String requestSignature,
																		 @QueryParam("userToken") final String userToken) throws Exception {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)){
					
					MessageDigest md = MessageDigest.getInstance("SHA-512"); FileInputStream fis = new
							FileInputStream("/root/loging.log");
					byte[] dataBytes = new byte[1024];
					int nread = 0;
					while ((nread = fis.read(dataBytes)) != -1)
					{ md.update(dataBytes, 0, nread);
					};
					byte[] mdbytes = md.digest();
					//convert the byte to hex format method
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < mdbytes.length; i++) {
						sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
					}
					System.out.println("Hex format : " + sb.toString());
						
						
						map.put("status",true);

					}else{
						map.put("status",false);
						map.put(Constants.MESSAGE,"Transaction Failed  - Reason-Signature Verfication Failed");
					}
				} else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid login");
				}
			} else {
				map.put("status", false);
				map.put(Constants.MESSAGE, "Invalid login");
			}
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}*/
	
	


	/**
	 * Created By: Jimit Patel Date:16-03-2017 Use:For Emergency Contact .
	 * @param amount
	 * @param userToken
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.EMERGENCY_CONTACT, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> sendEmergencyContactDetail(@QueryParam("driverName")final String driverName,
																				@QueryParam("carNo")final String carNo,
																				@QueryParam("driverContact")final String driverContact,
																				@QueryParam("startendUrl")final String startendUrl,
																				@QueryParam("emergencyContacts")final String emergencyContacts,
																				@QueryParam("userToken") final String userToken)
			throws ServiceException {
		final byte[] decoded = Base64.getDecoder().decode(userToken);
		String emailIDOrContactNo = "";
		String pass = "";
		try {
			String decodeToken = new String(decoded, "UTF-8");
			String[] newToken = decodeToken.split("-");
			emailIDOrContactNo = newToken[0];
			pass = newToken[1];
		} catch (UnsupportedEncodingException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		Map<String, Object> map = new HashMap<>();
		User user = null;
		if (emailIDOrContactNo != null && !("").equals(emailIDOrContactNo)) {
			user = userService.loginEmailByContactNo(Long.parseLong(emailIDOrContactNo));
			if (user != null) {
				if (user.getPassword().equals(pass)) {
                        String[] contacts = emergencyContacts.split(",");
                        if(contacts.length > 0){
                        	for (int i=0;i< contacts.length;i++) {
								SMSSend.emergencyMSG(driverContact, driverName, carNo, startendUrl,contacts[i]);
							}
							map.put("status", true);
							map.put(Constants.MESSAGE, "Successfully send message");
						}
						else {
							map.put("status", false);
							map.put(Constants.MESSAGE, "No Contacts Found");
						}
				}
				else {
					map.put("status", false);
					map.put(Constants.MESSAGE, "Invalid Login");
				}
			}

		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(value = UrlConstants.IMAGE, method = RequestMethod.GET,produces= MediaType.APPLICATION_JSON_VALUE)
	public final ResponseEntity<Map<String, Object>> byteImage(@QueryParam("imageName")final String imageName)
			throws ServiceException, IOException {
		Map<String, Object> map = new HashMap<>();
		File file = new File("/home/emxcelsolution/dmsImage/user/tenants/"+imageName);
		if(file.exists() && !file.isDirectory()) {
			BufferedImage bImage = ImageIO.read(file);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			String extention = imageName.split("\\.")[1];
			ImageIO.write(bImage, extention, outputStream);
			byte[] img= outputStream.toByteArray();
			String base64String = Base64.getEncoder().encodeToString(img);
		    outputStream.close();
		    System.out.println("extention :"+extention);
		    map.put("imageByteArray", base64String);
		    map.put("status", true);
		}else {
			map.put("status", false);
			map.put("message", "Image not found");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
