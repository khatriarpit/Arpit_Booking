package com.emxcel.dms.portal.controller;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.guest.Guest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.city.CityService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.companymaster.CompanyMasterService;
import com.emxcel.dms.core.business.services.guest.GuestService;
import com.emxcel.dms.core.business.services.superadmin.InvoicePackageService;
import com.emxcel.dms.core.business.services.superadmin.RateOfContractService;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.superadmin.RateOfContract;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Jimit Patel
 *
 */


@Controller
public class PreBookingController {
	
	private static final Logger logger = Logger.getLogger(PreBookingController.class);

	/**
	 * **We autowired it to use services of PreBookingService **.
	 */
	@Inject
	private PreBookingService preBookingService;
	
	/**
	 * **We autowired it to use services of GuestService **.
	 */
	@Inject
	private GuestService guestService;
	
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
	 * **We autowired it to use services of CompanyMasterService **.
	 */
	@Inject
	private CompanyMasterService companyMasterService;

	/**
	 * **We autowired it to use services of CarTypeService **.
	 */
	@Inject
	private CarTypeService carTypeService;
	
	
	/**
	 * Created By : Jimit Patel , Date: 18-02-2017. Use: To open Client pre Booking
	 * Page 
	 * @param prebookingId
	 * @param preBooking
	 * @param result
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.PRE_BOOKING, method = RequestMethod.GET)
	private ModelAndView addPreBooking(@RequestParam(value = "prebookingId", required = false) final String prebookingId,
			@ModelAttribute("command") final PreBooking preBooking,
			final BindingResult result, final RedirectAttributes ra) {
		Map<String, Object> map = new HashMap<>();

		try {
			map.put("InvoiceDTL", invoicePackageService.list());
			map.put("RateContract", rateOfContractService.list());
			map.put("CityDTL", cityService.list());
			map.put("CompanyMasterDTL", companyMasterService.list());
			map.put("CarTypeDTL", carTypeService.list());
			if(prebookingId != null){
				PreBooking preBookingListById = preBookingService.getById(Long.valueOf(prebookingId));
				String pickup = preBookingListById.getPickUpLocation();
				byte[] pickupEncode = Base64.getDecoder().decode(pickup);
				preBookingListById.setPickUpLocation(new String(pickupEncode));
				String dropLocation = preBookingListById.getDrop_location();
				byte[] dropLocationEncode = Base64.getDecoder().decode(dropLocation);
				preBookingListById.setDrop_location(new String(dropLocationEncode));
				map.put("preBooking",preBookingListById);
				
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.INDEX);
		}
		return new ModelAndView(ViewConstants.PRE_BOOIKING_CAR, map);
	}

	
	/**
	 * Created By : Jimit Patel , Date: 18-02-2017. Use: To Save Client pre Booking
	 * @param pickUpDateTime
	 * @param dropDate
	 * @param clientModel
	 * @param result
	 * @param session
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_PRE_BOOKING, method = RequestMethod.POST)
	private ModelAndView savePreBooking(
			  @RequestParam("pickUpDateTime") final String pickUpDateTime,
			@RequestParam("dropDateTime") final String dropDate, @ModelAttribute("command")  PreBooking preBooking,
			final BindingResult result, final HttpSession session, final RedirectAttributes ra) {
		User user = (User) session.getAttribute("user");
		try {
			String pickup = preBooking.getPickUpLocation();
			String pickupEncode = Base64.getEncoder().encodeToString(pickup.getBytes("utf-8"));
			preBooking.setPickUpLocation(pickupEncode);
			String drop = preBooking.getDrop_location();
			String dropEncode = Base64.getEncoder().encodeToString(drop.getBytes("utf-8"));
			preBooking.setDrop_location(dropEncode);
			preBooking.setTanentID(user.getTanentID());
			preBooking.setPickUpDateTime(convertStringToTimestamp(pickUpDateTime));
			preBooking.setStatusID(Constants.REQUEST_PENDING);
			preBooking.setDropDateTime(convertStringToTimestamp(dropDate));
			preBooking.setRequestedBookingId(randomString(8));
			
			CarType carType = carTypeService.getById(preBooking.getCarType().getId());
			preBooking.setCarType(carType);

			City city = cityService.getById(preBooking.getCity().getId());
			preBooking.setCity(city);

			InvoicePackage invoice = invoicePackageService.getById(preBooking.getInvoicePackage().getId());
			preBooking.setInvoicePackage(invoice);

			RateOfContract rateOfContract = rateOfContractService.getById(preBooking.getRateOfContract().getId());
			preBooking.setRateOfContract(rateOfContract);

			Guest guestDtls=guestService.getGuestDetailByContactNo(preBooking.getGuest().getContactNo());
			if(guestDtls == null) {
				Long guestId = guestService.saveGuest(preBooking.getGuest());
				preBooking.setGuest(guestService.getById(guestId));
			}else {
				preBooking.setGuest(guestService.getById(guestDtls.getId()));
			}

			if(!preBooking.getCompanyMaster().getComapanyName().equals(""))
			{
				CompanyMaster companyMaster=companyMasterService.getCompanyMasterByName(preBooking.getCompanyMaster().getComapanyName(), user.getTanentID());
				if(companyMaster !=null){
					preBooking.setCompanyMaster(companyMaster);
				}
			}
		    preBookingService.save(preBooking);
		
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.PRE_BOOKING);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_PRE_BOOKING);
	}

	
	/**
	 ** Created By : Jimit Patel , Date: 18-02-2017. Use: For List of Client pre Booking
	 * @param session
	 * @param clientModel
	 * @param result
	 * @return
	 */
	@RequestMapping(value = UrlConstants.LIST_PRE_BOOKING, method = RequestMethod.GET)
	private ModelAndView listofPrebookedClient(final HttpSession session,
			@ModelAttribute("command") final PreBooking preBooking, final BindingResult result) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		List<PreBooking> preBookingByTanent = preBookingService.getPreBookingAndGuestModel(user.getTanentID());
		List<PreBooking> preBookingArry = new ArrayList<>();
		for (PreBooking preBookingList : preBookingByTanent) {
			if (preBookingList.getPickUpLocation() != null && !("").equals(preBookingList.getPickUpLocation())) {
				byte[] pickUpLocation = Base64.getDecoder().decode(preBookingList.getPickUpLocation());
				preBookingList.setPickUpLocation(new String(pickUpLocation));
			} else {
				preBookingList.setPickUpLocation("");
			}
			if (preBookingList.getDrop_location() != null && !("").equals(preBookingList.getDrop_location())) {
				byte[] dropLocation = Base64.getDecoder().decode(preBookingList.getDrop_location());
				preBookingList.setDrop_location(new String(dropLocation));
			} else {
				preBookingList.setDrop_location("");
			}
			preBookingArry.add(preBookingList);
		}
		map.put("preBookingList", preBookingArry);
		return new ModelAndView(ViewConstants.LIST_PRE_BOOKED, map);
	}
	
	
	/**
	 * Created By - Jimit Patel. Used For - Delete Booking.
	 * Date-18-2--2017. 
	 *
	 * @param ra
	 *            **Redirect Attribute**
	 * @param id
	 *            **id**
	 * @return ModelAndView
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	@RequestMapping(value = UrlConstants.DELETE_PRE_BOOKING, method = RequestMethod.GET)
	public ModelAndView deleteCarType(@RequestParam(value = "prebookingId", required = false) final String id,
			final RedirectAttributes ra) {
		try {
			if(id != null){
				PreBooking preBooking = preBookingService.getById(Long.valueOf(id));
				if (preBooking != null) {
					preBooking.setStatusID(Constants.REQUEST_CANCELED);
					preBookingService.update(preBooking);
					return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_PRE_BOOKING);
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something went Wrong !!!");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_PRE_BOOKING);
	}

	/**
	 * reated By : Jimit Patel , Date: 20-01-2017. Use : To generate Random
	 * Number For TripId return String
	 */
	static final String ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
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
			sb.append(ab.charAt(rnd.nextInt(ab.length())));
		}
		return sb.toString();
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
			formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
			Date date =  formatter.parse(strDate);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return null;
	}


}
