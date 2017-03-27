package com.emxcel.dms.portal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.emxcel.dms.core.business.services.car.CarNameService;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.client.TripCancelHistoryService;
import com.emxcel.dms.core.business.services.companymaster.CompanyMasterService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.guest.GuestService;
import com.emxcel.dms.core.business.services.mapping.CarDriverMappingService;
import com.emxcel.dms.core.business.services.notification.AlertSchedulerService;
import com.emxcel.dms.core.business.services.notification.NotificationService;
import com.emxcel.dms.core.business.services.superadmin.DestinationMasterService;
import com.emxcel.dms.core.business.services.superadmin.InvoicePackageService;
import com.emxcel.dms.core.business.services.superadmin.RateOfContractService;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.business.utils.OTPData;
import com.emxcel.dms.core.business.utils.SMSSend;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.client.TripCancelHistory;
import com.emxcel.dms.core.model.common.AlertScheduler;
import com.emxcel.dms.core.model.common.Notification;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.superadmin.DestinationMaster;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;
import com.emxcel.dms.portal.model.BookCarBean;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

/**
 * @author Jimit Patel.
 */

@Controller
public class ClientController {

	private static final Logger logger = Logger.getLogger(ClientController.class);

	/**
	 * **We autowired it to use services of DriverService **.
	 */
	@Inject
	private DriverService driverService;

	/**
	 * **We autowired it to use services of GuestService **.
	 */
	@Inject
	private GuestService guestService;

	/**
	 * **We autowired it to use services of ClientModelService **.
	 */
	@Inject
	private ClientModelService clientModelService;

	/**
	 * **We autowired it to use services of RateOfContractService **.
	 */
	@Inject
	private RateOfContractService rateOfContractService;

	/**
	 * **We autowired it to use services of InvoicePackageService **.
	 */
	@Inject
	private InvoicePackageService invoicePackageService;

	/**
	 * **We autowired it to use services of CityService **.
	 */
	@Inject
	private CityService cityService;

	/**
	 * **We autowired it to use services of CarService **.
	 */
	@Inject
	private CarService carService;

	/**
	 * **We autowired it to use services of DestinationMasterService **.
	 */
	@Inject
	private DestinationMasterService destinationMasterService;

	/**
	 * **We autowired it to use services of CompanyMasterService **.
	 */
	@Inject
	private CompanyMasterService companyMasterService;

	/**
	 * **We autowired it to use services of TaxSlabService **.
	 */
	@Inject
	private TaxSlabService taxSlabService;

	/**
	 * **We autowired it to use services of CarTypeService **.
	 */
	@Inject
	private CarTypeService carTypeService;
	/**
	 * **We autowired it to use services of CarNameService **.
	 */
	@Inject
	private CarNameService carNameService;

	/**
	 * **We autowired it to use services of CarDriverMappingService **.
	 */
	@Inject
	private PreBookingService preBookingService;

	@Inject
	private TripCancelHistoryService tripCancelHistoryService;

	@Inject
	private UserService userService;

	@Inject
	private CarDriverMappingService carDriverMappingService;

	@PersistenceContext
	private EntityManager manager;

	/**
	 * SimpMessagingTemplate.
	 */
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	/**
	 * NotificationService.
	 */
	@Inject
	private NotificationService notificationService;

	/**
	 * AlertSchedulerService.
	 */
	@Inject
	private AlertSchedulerService alertSchedulerService;

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017 Use : To get the list of
	 * Companies from Company Master for automatic suggestions.
	 * 
	 * @param query
	 *            **Requested From Client Booking Jsp**
	 * @return comapnyList
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GETCOMPANY_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompanyMaster> getCompanyList(@RequestParam(value = "term", required = false) final String query) {
		return companyMasterService.getCompanyNameList(query);
	}

	/**
	 * Created By : Jimit Patel , Date: 06-03-2017 Use : To get the list of
	 * Contact from Guest for automatic suggestions.
	 * 
	 * @param query
	 *            **Requested From Client Booking Jsp**
	 * @return comapnyList
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_CONTACT_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Guest> getContactList(@RequestParam(value = "term", required = false) final String query) {
		return guestService.getContactNumberList(query);
	}

	/**
	 * Created By : Jimit Patel , Date: 06-03-2017 Use : To get the Detail Of
	 * Customer on the basis of Mobile No.
	 * 
	 * @param query
	 *            **Requested From Client Booking Jsp**
	 * @return comapnyList
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_GUEST_DETAIL, method = RequestMethod.POST)
	public  Guest getGuestDetail(
			@RequestParam(value = "contactNo", required = false) final String contactNo) {
		return guestService.getGuestDetailByContactNo(contactNo);
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017 Use : To get the list of
	 * Source Place from Source Destination Master for automatic suggestions.
	 * 
	 * @param query
	 *            **Requested From Client Booking Jsp**
	 * @return sourcePlaceList
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.SOURCEPLACE_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DestinationMaster> getSourceList(@RequestParam("term") final String query,
			final RedirectAttributes redirect) {
		List<DestinationMaster> sourcePlaceList = null;
		try {
			sourcePlaceList = destinationMasterService.getsourcePlaceList(query);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return  Collections.emptyList();
		}
		return sourcePlaceList;
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017 Use : To get the list of
	 * Destination Place from Source Destination Master for automatic
	 * suggestions.
	 * 
	 * @param query
	 *            **Requested From Client Booking Jsp**
	 * @return destinationPlaceList
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.DESTINATIONPLACE_LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<DestinationMaster> getDestinationList(@RequestParam("term") final String query,
			final RedirectAttributes redirect) {
		List<DestinationMaster> destinationPlaceList = null;
		try {
			destinationPlaceList = destinationMasterService.getdestinationPlaceList(query);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return  Collections.emptyList();
		}
		return destinationPlaceList;
	}

	/**
	 * Created By : Jimit Patel , Date: 23-01-2017. Use : To get the list of tax
	 * Slab on the basis of selected invoice category.
	 * 
	 * @param id
	 *            **Get Invoice category id From Client Booking Jsp**
	 * @return listoftax
	 */
	@RequestMapping(value = UrlConstants.INVOICE_TAX_LIST, method = RequestMethod.POST)
	@ResponseBody
	public List<TaxSlab> getAllTaxes(
			@RequestParam(value = "invoiceCategories", required = false) final String categoryId) {
		try {
			if (categoryId != null && !("").equals(categoryId)) {
				return taxSlabService.getTaxSlabById(Long.valueOf(categoryId));
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			return Collections.emptyList();
		}
	}

	/**
	 *  Created By : Jimit Patel , Date: 23-01-2017. Use : To get the list of Booked Cars
	 * @param session`
	 * @param clientModel
	 * @param result
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.LIST_BOOKEDCARS, method = RequestMethod.GET)
	private ModelAndView bookedCarList(final HttpSession session,
			@ModelAttribute("command") final ClientModel clientModel, final BindingResult result) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		List<ClientModel> listOfClient = clientModelService.getClientAndGuestModel(user.getTanentID());
		List<ClientModel> clientArry = new ArrayList<>();
		for (ClientModel clientModel1 : listOfClient) {
			if (clientModel1.getPickUpLocation() != null && !("").equals(clientModel1.getPickUpLocation())) {
				byte[] pickUpLocation = Base64.getDecoder().decode(clientModel1.getPickUpLocation());
				clientModel1.setPickUpLocation(new String(pickUpLocation));
			} else {
				clientModel1.setPickUpLocation("");
			}
			if (clientModel1.getDrop_location() != null && !("").equals(clientModel1.getDrop_location())) {
				byte[] dropLocation = Base64.getDecoder().decode(clientModel1.getDrop_location());
				clientModel1.setDrop_location(new String(dropLocation));
			} else {
				clientModel1.setDrop_location("");
			}
			clientArry.add(clientModel1);
		}
		map.put("clientModelList", clientArry);
		return new ModelAndView(ViewConstants.LIST_BOOKEDCARS, map);
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017. Use: To open Client Booking
	 * Page through Search Car Page
	 * 
	 * @param carId
	 *            **carId**
	 * @param clientId
	 *            **clientId**
	 * @param clientModel
	 *            **clientModel**
	 * @param pickUpDateTime
	 *            **picUpDateTime**
	 * @param dropDateTime
	 *            **dropDateTime**
	 * @param result
	 *            **result**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CLIENT_PAGE, method = RequestMethod.POST)
	private ModelAndView addClient(@RequestParam(value = "carId", required = false) final String carId,
			@RequestParam(value = "clientId", required = false) final String clientId,
			@ModelAttribute("command") final ClientModel clientModel,
			@RequestParam(value = "pickUpDateTime1", required = false) final String pickUpDateTime,
			@RequestParam(value = "dropDateTime1", required = false) final String dropDateTime,
			@RequestParam(value = "driverId", required = false) final String driverId,
			@RequestParam(value = "preBooking", required = false) final String prebBookingId,
			final BindingResult result, final RedirectAttributes ra) {
		Map<String, Object> map = new HashMap<>();
		try {
			Car car = this.carService.getById(Long.valueOf(carId));
			if (car != null && !("").equals(car)) {
				map.put("carDtls", car);
				CarType carType = this.carTypeService.getById(car.getCarTypeId());
				if (carType != null) {
					map.put("carTypeDtls", carType);
				}
				CarName carName = this.carNameService.getById(car.getCarNameId());
				if (carName != null) {
					map.put("carNameDtls", carName);
				}
				Driver driverDtlS = null;
				if (driverId != null) {
					driverDtlS = this.driverService.getById(Long.valueOf(driverId));
				} else {
					ClientModel clientModelForDriver = clientModelService.getById(Long.valueOf(clientId));
					driverDtlS = this.driverService.getById(clientModelForDriver.getDriver().getId());
				}
				map.put("driverDtlS", driverDtlS);
			}
			map.put("InvoiceDTL", invoicePackageService.list());
			map.put("RateContract", rateOfContractService.list());
			map.put("CityDTL", cityService.list());
			map.put("CompanyMasterDTL", companyMasterService.list());
			map.put("pickUpDateTime", pickUpDateTime);
			map.put("dropDateTime", dropDateTime);
			if (clientId != null && !("a").equals(clientId)) {
				ClientModel clientModel1 = clientModelService.getById(Long.valueOf(clientId));
				if (clientModel1.getPickUpLocation() != null && !("").equals(clientModel1.getPickUpLocation())) {
					byte[] pickUpLocation = Base64.getDecoder().decode(clientModel1.getPickUpLocation());
					clientModel1.setPickUpLocation(new String(pickUpLocation));
				} else {
					clientModel1.setPickUpLocation("");
				}
				if (clientModel1.getDrop_location() != null && !("").equals(clientModel1.getDrop_location())) {
					byte[] dropLocation = Base64.getDecoder().decode(clientModel1.getDrop_location());
					clientModel1.setDrop_location(new String(dropLocation));
				} else {
					clientModel1.setDrop_location("");
				}
				map.put("clientModel", clientModel1);
			}
			if (prebBookingId != null && !prebBookingId.equals("")) {

				PreBooking preBooking = preBookingService.getById(Long.valueOf(prebBookingId));
				map.put("preBookingId", prebBookingId);

				ClientModel clientModel1 = new ClientModel();
				clientModel1.setId(Long.valueOf("0"));
				clientModel1.setInvoiceMode(preBooking.getInvoiceMode());
				clientModel1.setGuest(guestService.getById(preBooking.getGuest().getId()));

				byte[] pickUpLocation = Base64.getDecoder().decode(preBooking.getPickUpLocation());
				clientModel1.setPickUpLocation(new String(pickUpLocation));

				byte[] dropLocation = Base64.getDecoder().decode(preBooking.getDrop_location());
				clientModel1.setDrop_location(new String(dropLocation));

				clientModel1.setInvoiceMode(preBooking.getInvoiceMode());

				clientModel1.setCity(cityService.getById(preBooking.getCity().getId()));
				clientModel1.setLandmark(preBooking.getLandmark());
				clientModel1.setPincode(preBooking.getPincode());
				clientModel1.setRemarks(preBooking.getRemarks());
				clientModel1.setInvoicePackage(invoicePackageService.getById(preBooking.getInvoicePackage().getId()));
				clientModel1.setRateOfContract(rateOfContractService.getById(preBooking.getRateOfContract().getId()));
				clientModel1.setMinkms(preBooking.getMinkms());
				clientModel1.setMinrate(preBooking.getMinrate());
				clientModel1.setDriverAllownce(preBooking.getDriverAllownce());
				clientModel1.setGraceHours(preBooking.getGraceHours());
				clientModel1.setPaymentMode(preBooking.getPaymentMode());
				clientModel1.setHnkKms(preBooking.getHnkKms());
				clientModel1.setHnkHours(preBooking.getHnkHours());
				clientModel1.setHnkAmount(preBooking.getHnkAmount());
				clientModel1.setAdditionalHours(preBooking.getAdditionalHours());
				clientModel1.setAdditionalKms(preBooking.getAdditionalKms());
				clientModel1.setSourcePlace(preBooking.getSourcePlace());
				clientModel1.setDestinationPlace(preBooking.getDestinationPlace());
				clientModel1.setSndPrice(preBooking.getSndPrice());

				if (preBooking.getCompanyMaster() != null) {
					clientModel1.setCompanyMaster(companyMasterService.getById(preBooking.getCompanyMaster().getId()));
				}
				map.put("clientModel", clientModel1);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.SEARCH_CAR);
		}
		return new ModelAndView(ViewConstants.CLIENT_PAGE, map);
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017 Use: To Save the Booking
	 * Details Of Client.
	 * 
	 * @param pickUpDateTime
	 *            **picUpDateTime**
	 * @param dropDate
	 *            **dropDate**
	 * @param clientModel
	 *            **clientModel**
	 * @param result
	 *            **result**
	 * @param session
	 *            **session**
	 * @exception ServiceException
	 * @return ModelAndView
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = UrlConstants.SAVE_CLIENT, method = RequestMethod.POST)
	private ModelAndView saveClientDetails(
			@ModelAttribute("command") @RequestParam("pickUpDateTime") String pickUpDateTime,
			@RequestParam("dropDateTime") String dropDate,
			@RequestParam("preBookingId") String preBookingId,
			final ClientModel clientModel,
			final BindingResult result, HttpSession session, final RedirectAttributes redirect) {
		try {
			clientModelService.saveClientDetails(clientModel, session, pickUpDateTime, dropDate, preBookingId);
			SimpleDateFormat diff = new SimpleDateFormat(Constants.PORTAL_DATE_FORMAT);
			Calendar cal = Calendar.getInstance();
			cal.setTime(diff.parse(CommonUtil.convertTimestampToDateInString(clientModel.getPickUpDateTime())));
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.add(Calendar.MINUTE, -60);
			Date date1 = cal.getTime();
			Timer timer1 = new Timer(true);
			AlertScheduler alertScheduler1 = alertSchedulerService.saveAlertSchedulerReturnEntity(date1,
					clientModel.getTripId(), false, 1);
			timer1.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						ClientModel clientModelNew = null;
						// 1 hour left then Notification Alert to Admin & DE
						int hour = 1;
						String msg = "Your Trip Has Left " + hour + " Hour to start !!!";
						if (clientModel.getDriver() != null && clientModel.getDriver().getTokenID() != null) {
							CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(), msg + " ,tripID="
									+ clientModel.getTripId() + "," + "latlong=" + clientModel.getPickUpLatLong() + "", "driverApp");
						}
						// Sleep for 10 sec
						Thread.sleep(10000);
						clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
						msg = getMessageForTripAlert(clientModelNew);
						getAlerts(clientModelNew, hour, msg);
						alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler1);
						timer1.cancel();
						timer1.purge();
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}, date1);
			cal = Calendar.getInstance();
			cal.setTime(diff.parse(CommonUtil.convertTimestampToDateInString(clientModel.getPickUpDateTime())));
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.add(Calendar.MINUTE, -58);
			Date date2 = cal.getTime();
			AlertScheduler alertScheduler2 = alertSchedulerService.saveAlertSchedulerReturnEntity(date2,
					clientModel.getTripId(), false, 2);
			Timer timer2 = new Timer(true);
			timer2.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						ClientModel clientModelNew = null;
						// 45 min left then Notification Alert to Admin & DE
						int fortyFiveMinutes = 45;
						String msg = "Your Trip Has Left " + fortyFiveMinutes + " minutes to start !!!";
						if (clientModel.getDriver() != null && clientModel.getDriver().getTokenID() != null) {
							CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(), msg + " ,tripID="
									+ clientModel.getTripId() + "," + "latlong=" + clientModel.getPickUpLatLong() + "", "driverApp");
						}
						// Sleep for 10 sec
						Thread.sleep(10000);
						clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
						msg = getMessageForTripAlert(clientModelNew);
						getAlerts(clientModelNew, fortyFiveMinutes, "minutes");
						alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler2);
						timer2.cancel();
						timer2.purge();
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}, date2);
			cal = Calendar.getInstance();
			cal.setTime(diff.parse(CommonUtil.convertTimestampToDateInString(clientModel.getPickUpDateTime())));
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.add(Calendar.MINUTE, -56);
			Date date3 = cal.getTime();
			AlertScheduler alertScheduler3 = alertSchedulerService.saveAlertSchedulerReturnEntity(date3,
					clientModel.getTripId(), false, 3);
			Timer timer3 = new Timer(true);
			timer3.schedule(new TimerTask() {
				@Override
				public void run() {
					try {
						ClientModel clientModelNew = null;

						// 30 min left then Notification Alert to Admin & DE
						int thrityMinutes = 30;
						String msg = "Your Trip Has Left " + thrityMinutes + " minutes to start !!!";
						if (clientModel.getDriver() != null && clientModel.getDriver().getTokenID() != null) {
							CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(), msg + " ,tripID="
									+ clientModel.getTripId() + "," + "latlong=" + clientModel.getPickUpLatLong() + "", "driverApp");
						}
						// Sleep for 10 sec
						Thread.sleep(10000);
						clientModelNew = clientModelService.getTripByTripId(clientModel.getTripId());
						msg = getMessageForTripAlert(clientModel);
						if (clientModelNew != null && clientModelNew.getDriverDistanceMin() != null
								&& thrityMinutes < clientModelNew.getDriverDistanceMin()) {
							getAlerts(clientModelNew, thrityMinutes, msg);
							final String ACCOUNT_SID = "AC4e1f4bb93ef57dc18d1ad9cbb4acea65";
							final String AUTH_TOKEN = "c893e27cd8f65850a3e384e59de4f1d6";
							Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
							Call call = null;
							try {
								call = Call
										.creator(new PhoneNumber("+917405799970"), new PhoneNumber("+18583465930"),
												new URI("https://handler.twilio.com/twiml/EHa8565f08cfa0860cd2f067271a9682ce"))
										.create();
							} catch (URISyntaxException e) {
								e.printStackTrace();
							}
						}
						alertSchedulerService.updateAlertSchedulerReturnEntity(true, alertScheduler3);
						timer3.cancel();
						timer3.purge();
					} catch (Exception e) {
						e.printStackTrace();
					}
				};
			}, date3);
			if (clientModel.getDriver() != null && clientModel.getDriver().getTokenID() != null) {
				// if (clientModel.getGuest() != null && token != null) {
				String message = "One Trip has been Booked !! ./n";
				CommonUtil.getTokenByContactNo(clientModel.getDriver().getTokenID(), message, "driverApp");
			}
			if (clientModel.getGuest() != null && clientModel.getGuest().getTokenID() != null) {
				// if (clientModel.getGuest() != null && token != null) {
				String message = "Your Trip has been Booked !! ./n";
				CommonUtil.getTokenByContactNo(clientModel.getGuest().getTokenID(), message, "feedApp");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
	}

	/**
	 * Created By : Jimit Patel , Date: 20-01-2017 Use: To Update Booking Detail
	 * @param pickUpDateTime
	 * @param dropDate
	 * @param clientModel
	 * @param result
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = UrlConstants.UPDATE_CLIENT, method = RequestMethod.POST)
	private ModelAndView updateClient(@ModelAttribute("command") @RequestParam("pickUpDateTime") String pickUpDateTime,
			@RequestParam("dropDateTime") String dropDate, final ClientModel clientModel, final BindingResult result,
			final RedirectAttributes redirect) {
		try {
			clientModelService.updateClientDetsils(clientModel, pickUpDateTime, dropDate);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
	}

	/**
	 * @param tripId
	 * @param result
	 * @return
	 */
	@RequestMapping(value = UrlConstants.CANCELED_TRIP, method = RequestMethod.GET)
	private ModelAndView cancelBookedCarTrip(@RequestParam(value = "tripId", required = false) final String tripId,
			final ClientModel clientModel, final BindingResult result, final RedirectAttributes ra) {
		try {
			clientModelService.setSatusCancel(tripId);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
	}

    /**
     * @param cartype
     * @param clientId
     * @param changeRequestBy
     * @return
     */
    @RequestMapping(value = UrlConstants.CHANGE_REQUEST, method = RequestMethod.POST)
    public ModelAndView changeRequestPage(@ModelAttribute("command") final CarType cartype,
                                          @RequestParam(value = "clientId", required = false) final String clientId,
										  @RequestParam(value = "flag", required = false) final String changeRequestBy) {
        ModelAndView model = new ModelAndView();
        model.addObject("changeRequestBy",changeRequestBy);
        if (clientId != null && !("").equals(clientId)) {
            ClientModel clientModel=clientModelService.getById(Long.valueOf(clientId));
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<Driver> driverList=manager.createNamedQuery("getAvailableDrivers", Driver.class)
                    .setParameter("picUpDateTime", dateFormat.format(clientModel.getPickUpDateTime()))
                    .setParameter("dropDate", dateFormat.format(clientModel.getDropDateTime()))
                    .setParameter("tanentID", clientModel.getTanentID()).getResultList();

            List<Car> carList = manager.createNamedQuery("getCarAvailableListForAllCarType", Car.class)
                    .setParameter("picUpDateTime", dateFormat.format(clientModel.getPickUpDateTime()))
                    .setParameter("dropDate",dateFormat.format(clientModel.getDropDateTime()))
                    .setParameter("tanentID", clientModel.getTanentID()).getResultList();
			List<BookCarBean> listOfBookCar=new ArrayList<>();
			Car carModel=carService.getById(clientModel.getCar().getId());
			carList.add(carModel);
			for (Car car : carList) {
				BookCarBean bookCarBean = new BookCarBean();
				bookCarBean.setCarId(car.getId());
				bookCarBean.setCarNumber(car.getCarNo());

				CarType carType = carTypeService.getById(car.getCarTypeId());
				bookCarBean.setCarType(carType.getCarType());

				CarName carName = carNameService.getById(car.getCarNameId());
				bookCarBean.setCarName(carName.getCarName());

				listOfBookCar.add(bookCarBean);

			}
			model.addObject("driverList", driverList);
			if (changeRequestBy != null) {
				if (changeRequestBy.equals("driver")) {
					model.addObject("changeRequestBy", changeRequestBy);
				}
			} else {
				model.addObject("listOfBookCar", listOfBookCar);
				model.addObject("changeRequestBy", null);
			}
			model.addObject("clientId", clientId);
			model.addObject("carNo", carModel.getId());

        }
        model.setViewName(ViewConstants.CHANGE_REQUEST);
        return model;
    }

    /**
     * @param cartype
     * @param clientId
     * @param carId
     * @param driverId
     * @param changeRequestBy
     * @param ra
     * @return
     * @throws ServiceException
     */
    @RequestMapping(value = UrlConstants.CHANGE_REQUEST_ACCEPT, method = RequestMethod.POST)
    public ModelAndView changeRequestAccepted(@ModelAttribute("command") final CarType cartype,
                                              @RequestParam(value = "clientId", required = false) final String clientId,
                                              @RequestParam(value = "carId", required = false) final String carId,
                                              @RequestParam(value = "driverId", required = false) final String driverId ,
											  @RequestParam(value = "changeRequestBy", required = false) final String changeRequestBy,
											  final RedirectAttributes ra) throws ServiceException {
        ClientModel clientModel = clientModelService.getById(Long.valueOf(clientId));
        if(driverId!=null && !("0").equals(driverId)) {
			Driver driver = driverService.getById(Long.valueOf(driverId));
			clientModel.setDriver(driver);
		}
		if (carId != null && !("0").equals(carId) && !carId.equals("")) {
			Car car = carService.getById(Long.valueOf(carId));
			clientModel.setCar(car);
		}
		TripCancelHistory tripCancelHistory = copyClientObjectToTrip(clientModel);
		tripCancelHistoryService.save(tripCancelHistory);
		clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_PENDING));
        clientModelService.update(clientModel);

		ra.addFlashAttribute(Constants.MESSAGE, "Change(s) Request Accepted & Updated Successfully !!!");
		if (changeRequestBy == null) {
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CANCEL_REQUEST_LIST);
		} else {
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_BOOKEDCARS);
		}
    }
	/**
	 * @param cartype
	 * @param clientId
	 * @param carId
	 * @param driverId
	 * @param ra
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.CHANGE_REQUEST_REJECT, method = RequestMethod.POST)
	public ModelAndView changeRequestRejected(@ModelAttribute("command") final CarType cartype,
			@RequestParam(value = "clientId", required = false) final String clientId,
			@RequestParam(value = "carId", required = false) final String carId,
			@RequestParam(value = "driverId", required = false) final String driverId, final RedirectAttributes ra)
			throws ServiceException {
		ClientModel clientModel = clientModelService.getById(Long.valueOf(clientId));
		clientModel.setStatusID(CommonUtil.getStatusIDByStatus("Pending"));
		clientModelService.update(clientModel);
		ra.addFlashAttribute(Constants.MESSAGE, "Sorry , Change Request Rejected ... !!!");
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CANCEL_REQUEST_LIST);
	}

	/**
	 * Created By: Jimit Patel Date:22/02/2017 Use: To fetch the list of Driver
	 * Cancel Request List
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CANCEL_REQUEST_LIST, method = RequestMethod.GET)
	public ModelAndView cancelrequestList(@ModelAttribute("command") final ClientModel clientModel,
			final RedirectAttributes ra) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(auth.getName());
		List<ClientModel> cancelRequestList = clientModelService.getDriverCancelList(user.getTanentID());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (cancelRequestList != null) {
				List<ClientModel> clientArry = new ArrayList<>();
				for (ClientModel clientModel1 : cancelRequestList) {
					if (clientModel1.getPickUpLocation() != null && !("").equals(clientModel1.getPickUpLocation())) {
						byte[] pickUpLocation = Base64.getDecoder().decode(clientModel1.getPickUpLocation());
						clientModel1.setPickUpLocation(new String(pickUpLocation));
					} else {
						clientModel1.setPickUpLocation("");
					}
					if (clientModel1.getDrop_location() != null && !("").equals(clientModel1.getDrop_location())) {
						byte[] dropLocation = Base64.getDecoder().decode(clientModel1.getDrop_location());
						clientModel1.setDrop_location(new String(dropLocation));
					} else {
						clientModel1.setDrop_location("");
					}
					clientArry.add(clientModel1);
				}
				map.put("CancelList", clientArry);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.BOOK_CAR_LIST);
		}
		return new ModelAndView(ViewConstants.LIST_CANCEL_REQUEST, map);
	}

	/**
	 * Created By: Jimit Patel Date:23/02/2017 Use: Driver Cancel Request
	 * Approve Or reject
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DRIVER_REQUEST_OPERATION, method = RequestMethod.GET)
	public ModelAndView cancelOperation(@ModelAttribute("command") final ClientModel clientModel,
			@RequestParam(value = "clientId", required = false) final String clientId, final RedirectAttributes ra) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("clientId", Long.valueOf(clientId));
		return new ModelAndView(ViewConstants.DRIVER_REQUEST_OPERATION, map);

	}

	/**
	 * Created By: Jimit Patel Date:23/02/2017 Use: Driver Cancel Request reject
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.REJECT_DRIVER_CANCEL_REQUEST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ClientModel rejectdriverrequest(@RequestParam(value = "clientId", required = false) final String clientId)
			throws ServiceException {
		ClientModel clientModel = clientModelService.getById(Long.valueOf(clientId));
		clientModel.setStatusID(CommonUtil.getStatusIDByStatus("Pending"));
		clientModelService.update(clientModel);
		return clientModel;
	}

	/**
	 * Created By: Jimit Patel Date:23/02/2017 Use: Driver Cancel Request
	 * approve
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.APPROVE_CHANGE_LATER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ClientModel approveChangeLater(@RequestParam(value = "clientId", required = false) final String clientId)
			throws ServiceException {

		ClientModel clientModel = clientModelService.getById(Long.valueOf(clientId));
		TripCancelHistory tripCancelHistory = copyClientObjectToTrip(clientModel);
		tripCancelHistoryService.save(tripCancelHistory);

		clientModel.setStatusID(CommonUtil.getStatusIDByStatus("Unassign"));
		clientModel.setDriver(null);
		clientModelService.update(clientModel);
		return clientModel;
	}

	/**
	 * Created By: Jimit Patel Date:23/02/2017 Use: to maintain trip history
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	public TripCancelHistory copyClientObjectToTrip(ClientModel clientModel)
	{
		TripCancelHistory tripHistory = new TripCancelHistory();
		if (clientModel != null) {
			tripHistory.setInvoiceMode(clientModel.getInvoiceMode());
			tripHistory.setPickUpLocation(clientModel.getPickUpLocation());
			tripHistory.setPickUpLatLong(clientModel.getPickUpLatLong());
			tripHistory.setLandmark(clientModel.getLandmark());
			tripHistory.setPincode(clientModel.getPincode());
			tripHistory.setPickUpDateTime(clientModel.getPickUpDateTime());
			tripHistory.setDrop_location(clientModel.getDrop_location());
			tripHistory.setDropDateTime(clientModel.getDropDateTime());
			tripHistory.setDropLatLong(clientModel.getDropLatLong());
			tripHistory.setRemarks(clientModel.getRemarks());
			tripHistory.setHnkKms(clientModel.getHnkKms());
			tripHistory.setHnkHours(clientModel.getHnkHours());
			tripHistory.setHnkAmount(clientModel.getHnkAmount());
			tripHistory.setAdditionalHours(clientModel.getAdditionalHours());
			tripHistory.setAdditionalKms(clientModel.getAdditionalKms());
			tripHistory.setMinkms(clientModel.getMinkms());
			tripHistory.setMinrate(clientModel.getMinrate());
			tripHistory.setGraceHours(clientModel.getGraceHours());
			tripHistory.setAddcharges(clientModel.getAddcharges());
			tripHistory.setStatusID(clientModel.getStatusID());
			tripHistory.setSourcePlace(clientModel.getSourcePlace());
			tripHistory.setDestinationPlace(clientModel.getDestinationPlace());
			tripHistory.setSndPrice(clientModel.getSndPrice());
			tripHistory.setTripId(clientModel.getTripId());
			tripHistory.setGuest(clientModel.getGuest());
			tripHistory.setInvoicePackage(clientModel.getInvoicePackage());
			tripHistory.setRateOfContract(clientModel.getRateOfContract());
			tripHistory.setCar(clientModel.getCar());
			tripHistory.setDriver(clientModel.getDriver());
			tripHistory.setCity(clientModel.getCity());
			tripHistory.setCompanyMaster(clientModel.getCompanyMaster());
			tripHistory.setCanceledId(clientModel.getCanceledId());
			tripHistory.setOtp(clientModel.getOtp());
			tripHistory.setBillableAmount(clientModel.getBillableAmount());
			tripHistory.setDriverDistanceMin(clientModel.getDriverDistanceMin());
			return tripHistory;
		}
		return tripHistory;
	}

	/**
	 * Created By: Jimit Patel Date:28/02/2017 Use: To fetch the list of
	 * Customer Cancel Request List
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CUSTOMER_CANCEL_REQUEST, method = RequestMethod.GET)
	public ModelAndView customerCancelrequestList(@ModelAttribute("command") final ClientModel clientModel,
			final RedirectAttributes ra) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.getUserByUsername(auth.getName());
		List<ClientModel> cancelRequestList = clientModelService.getCustomerCancelList(user.getTanentID());
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (cancelRequestList != null) {
				List<ClientModel> clientArry = new ArrayList<>();
				for (ClientModel clientModel1 : cancelRequestList) {
					if (clientModel1.getPickUpLocation() != null && !("").equals(clientModel1.getPickUpLocation())) {
						byte[] pickUpLocation = Base64.getDecoder().decode(clientModel1.getPickUpLocation());
						clientModel1.setPickUpLocation(new String(pickUpLocation));
					} else {
						clientModel1.setPickUpLocation("");
					}
					if (clientModel1.getDrop_location() != null && !("").equals(clientModel1.getDrop_location())) {
						byte[] dropLocation = Base64.getDecoder().decode(clientModel1.getDrop_location());
						clientModel1.setDrop_location(new String(dropLocation));
					} else {
						clientModel1.setDrop_location("");
					}
					clientArry.add(clientModel1);
				}
				map.put("CustomerCancelList", clientArry);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.BOOK_CAR_LIST);
		}
		return new ModelAndView(ViewConstants.LIST_CUSTOMER_CANCEL_REQUEST, map);
	}

	/**
	 * Created By: Jimit Patel Date:28/02/2017 Use: To approve Customer Cancel
	 * Request
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CUSTOMER_CANCEL_APPROVE, method = RequestMethod.GET)
	public ModelAndView customerCancelrequestApprove(final RedirectAttributes ra,
			@RequestParam(value = "tripId", required = false) final String tripId) throws ServiceException {
		try {
			if (tripId != null) {
				ClientModel clientModel = clientModelService.getTripByTripId(tripId);
				Guest guest = guestService.getById(clientModel.getGuest().getId());
				int otp = OTPData.generateOtp();
				if (guest != null) {
					SMSSend.sendSms(guest, otp);
				}
				clientModel.setOtp(otp);
				clientModelService.update(clientModel);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CUSTOMER_CANCEL_REQUEST);

	}

	/**
	 * Created By: Jimit Patel Date:28/02/2017 Use: To approve Customer Cancel
	 * Request
	 * 
	 * @param clientModel
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CUSTOMER_CANCEL_REJECT, method = RequestMethod.GET)
	public ModelAndView customerCancelrequestReject(@ModelAttribute("command") ClientModel clientModel,
			final RedirectAttributes ra, @RequestParam(value = "tripId", required = false) final String tripId,
			@RequestParam(value = "pickupdate", required = false) final String pickupdate) throws ServiceException {
		try {
			if (tripId != null) {
				clientModel = clientModelService.getTripByTripId(tripId);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				DateFormat formatter;
				formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = formatter.parse(pickupdate);
				if (new Timestamp(date.getTime()).after(timestamp)) {
					clientModel.setStatusID(CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_PENDING));
					clientModelService.update(clientModel);
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, "You can Not Reject This trip");
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CUSTOMER_CANCEL_REQUEST);

	}

	/**
	 * @param carId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_DRIVER_BY_CAR, method = RequestMethod.POST)
	private Driver getDriverByCar(@RequestParam("carId") final String carId) {
		CarDriverMapping carDriver = null;
		Driver driver = null;
		if (carId != null && !"".equals(carId)) {
			carDriver = carDriverMappingService.getCarDriverByCarId(Long.valueOf(carId));
			if (carDriver.getDriId() != null) {
				driver = driverService.getById(carDriver.getDriId());
			}
		}
		return driver;
	}

	/**
	 * @param clientModel
	 * @param distance
	 * @param msg
	 */
	public void getAlerts(ClientModel clientModel, int distance, String msg) {
		try {
			if (clientModel != null && clientModel.getDriverDistanceMin() != null
					&& distance < clientModel.getDriverDistanceMin()) {
				if (clientModel.getCreatedBy() != null) {
					Notification notification = notificationService.saveAlertSchedulerNotification(msg,
							clientModel.getTanentID());
					User user = userService.getUserByUsername(clientModel.getUpdatedBy());
					List<User> userList = userService.listOfUserByIdAndTanentID(true, clientModel.getTanentID());
					if (user != null) {
						if (distance == 45) {
							if (user.getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf("" + user.getContactNo()), msg);
							}
							if (clientModel.getDriver() != null && clientModel.getDriver().getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf(clientModel.getDriver().getContactNo()), msg);
							}
						}
					}
					if (userList != null && userList.size() > 0) {
						if (distance == 45) {
							if (userList.get(0).getContactNo() != null) {
								SMSSend.sendSms(Long.valueOf("" + userList.get(0).getContactNo()), msg);
							}
						}
						if (userList.get(0).getUsername() != null && !userList.get(0).getUsername().equals("")) {
							simpMessagingTemplate.convertAndSendToUser(userList.get(0).getUsername(), "/queue/notify",
									notification);
						}
					}
					if (user != null && user.getUsername() != null) {
						simpMessagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notify", notification);						
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param clientModel
	 * @return
	 */
	public String getMessageForTripAlert(ClientModel clientModel) {
		String msg = "";
		msg = "Driver is beyond the reach of upcoming trip.";
		msg += "<br>Trip : " + clientModel.getTripId() + ".";
		msg += "<br>Car No : " + clientModel.getCar().getCarNo() + ".";
		msg += "<br>Driver Name : " + clientModel.getDriver().getFullName() + ".";
		msg += "<br>Driver Contact Number : " + clientModel.getDriver().getContactNo() + ".";
		msg += "<br>Pick Up Location : " + clientModel.getPickUpLocation() + ".";
		msg += "<br>Please Contact to Driver or change the driver.";
		msg += "<br><a style='color:red;' href='" + UrlConstants.CHANGE_REQUEST
				+ "'>Click On Link to Change Request Page</a>";
		return msg;
	}

	/**
	 * @param date1
	 * @param date2
	 * @param timeUnit
	 * @return
	 */
	public long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}
}