package com.emxcel.dms.portal.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.services.car.CarNameService;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.TripDeatilsService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.TripDetails;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Naresh Banda
 */

@Controller
public class TripDetailController {
	
	private static final Logger logger = Logger.getLogger(TripDetailController.class);

	/**
	 * **We autowired it to use services of ClientModelService **.
	 */
	@Inject
	private ClientModelService clientModelService;

	/**
	 * **We autowired it to use services of CarNameService **.
	 */
	@Inject
	private CarNameService carNameService;

	/**
	 * **We autowired it to use services of CarTypeService **.
	 */
	@Inject
	private CarTypeService carTypeService;

	/**
	 * **We autowired it to use services of TripDeatilsService **.
	 */
	@Inject
	private TripDeatilsService tripDetailsService;

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: redirect transaction
	 * search page
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.GET_TRIP_DETAIL, method = RequestMethod.GET)
	public final ModelAndView enterTripPage() {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			model.setViewName(ViewConstants.GET_TRIP_DETAIL);
		} else {
			model.setViewName(Constants.REDIRECT + ViewConstants.GET_TRIP_DETAIL);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: carNo or tripID search it
	 * will get the ClientDetail and if null then return search type it will
	 * through error.
	 * 
	 * @param searchType
	 * @param tripID
	 * @param carNo
	 * @return String
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = UrlConstants.GET_TRIP_BY_ID_IN_DETAIL, method = RequestMethod.POST)
	@ResponseBody
	public final String tripIdPage(@RequestParam("searchType") final String searchType,
			@RequestParam(value = "tripID", required = false) final String tripID,
			@RequestParam(value = "carNo", required = false) final String carNo, final HttpSession session) {
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			ClientModel clientDetail = null;
			List<ClientModel> clientDetailList = null;
			if (searchType.equals(Constants.TRIP)) {
				if (tripID != null && !"".equals(tripID)) {
					clientDetail = clientModelService.getTripByTripIdTenant(tripID, user.getTanentID());
				}
			} else {
				if (carNo != null && !"".equals(carNo)) {
					clientDetailList = clientModelService.getSortByType("CarNo", "", "", carNo, user.getTanentID());
				}
			}
			if (clientDetail != null || clientDetailList != null) {
				return searchType;
			} else if (clientDetail == null) {
				return "tripError";
			} else {
				return "carNoError";
			}
		} else {
			return "carNoError";
		}
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: get client detail, car
	 * detail by carID, driver detail by driverID by transactionID
	 * 
	 * @param tripID
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.GET_TRIP_IN_DETAIL, method = RequestMethod.GET)
	public final ModelAndView endTrip(@RequestParam(value = "action", required = false) final String action,
			@RequestParam(value = "tripID", required = false) final String tripID, final HttpSession session,
			final RedirectAttributes ra,
			@RequestParam(value = "tripStartKm", required = false) final String tripStartKm,
			@RequestParam(value = "tripEndKm", required = false) final String tripEndKm,
			@RequestParam(value = "tripTolltax", required = false) final String tripTolltax) {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			try {
				User user = (User) session.getAttribute("user");
				ClientModel clientDetail = clientModelService.getTripByTripIdTenant(tripID, user.getTanentID());
				model.addObject("tripDetails", tripDetailsService.getTripDetailsByTripId(tripID));
				if (action != null && action.equals(Constants.END_TRIP)) {
					// update Client Status
					clientDetail.setStatusID(3);
					Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
					clientDetail.setDropDateTime(timeStamp);
					clientModelService.update(clientDetail);
					// Trip Detail update
					TripDetails tripDetails = tripDetailsService.getTripDetailsByTripId(tripID);
					if (tripDetails == null) {
						tripDetails = new TripDetails();
					}
					tripDetails.setEndDate(timeStamp);
					tripDetails.setEndKm(Double.parseDouble(tripEndKm));
					tripDetails.setTolltax(Long.valueOf(tripTolltax));
					tripDetailsService.update(tripDetails);
					Double amount = 0.0;
					if (clientDetail.getInvoiceMode().equalsIgnoreCase(Constants.AUTO)
							&& !clientDetail.getInvoicePackage().getCategoryname().equalsIgnoreCase(Constants.COMPANY)) {
						if (clientDetail.getRateOfContract().getRateOfContract().equalsIgnoreCase(Constants.MINIMUM_KM)) {
							amount = clientModelService.countMinimumKm(clientDetail, tripDetails);
						} else if (clientDetail.getRateOfContract().getRateOfContract()
								.equalsIgnoreCase(Constants.HOURS_AND_KM)) {
							amount = clientModelService.countHoursandKm(clientDetail, tripDetails);
						} else if (clientDetail.getRateOfContract().getRateOfContract() 
								.equalsIgnoreCase(Constants.SOURCE_AND_DESTINATION)) {
									amount = (double) clientDetail.getSndPrice();
						}
					}
					if (clientDetail.getGuest() != null && clientDetail.getGuest().getTokenID() != null) {
						String message = "Your Trip has been completed./n";
						message += "Trip Total Amount Charge = "+ amount + "./n";
						message += "Please submit your feedback.";
						CommonUtil.getTokenByContactNo(clientDetail.getGuest().getTokenID(), message, "feedApp");
					}
					model.setViewName(Constants.REDIRECT + UrlConstants.FEEDBACK + "?tripID=" + tripID);
				} else if (action != null && action.equals(Constants.START_TRIP)) {
					clientDetail.setStatusID(2);
					clientModelService.update(clientDetail);
					TripDetails tripDetails = new TripDetails();
					Timestamp startDate = new Timestamp(System.currentTimeMillis());
					tripDetails.setTanentID(user.getTanentID());
					tripDetails.setTripId(tripID);
					tripDetails.setStartDate(startDate);
					tripDetails.setStartKm(Double.parseDouble(tripStartKm));;
					tripDetailsService.save(tripDetails);
					model.setViewName(Constants.REDIRECT + UrlConstants.GET_TRIP_IN_DETAIL + "?tripID=" + tripID);
				} else {
					if (clientDetail != null) {
						if (clientDetail != null && clientDetail.getCar() != null
								&& clientDetail.getCar().getCarNameId() != null) {
							model.addObject("carName", carNameService.getById(clientDetail.getCar().getCarNameId()));
						}
						if (clientDetail != null && clientDetail.getCar() != null
								&& clientDetail.getCar().getCarTypeId() != null) {
							model.addObject("carType", carTypeService.getById(clientDetail.getCar().getCarTypeId()));
						}
						model.addObject("tripDetail", clientDetail);
						model.setViewName(ViewConstants.GET_TRIP_IN_DETAIL);
					} else {
						model.setViewName(Constants.REDIRECT + UrlConstants.GET_TRIP_DETAIL);
					}
				}
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: get client transaction
	 * list page by carno and current-future date only
	 * 
	 * @param carNo
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.GET_TRIPS_DETAIL_CLIENTLIST, method = RequestMethod.GET)
	public final ModelAndView clientListByCarNo(@RequestParam(value = "carNo", required = false) String carNo,
			final HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			try {
				User user = (User) session.getAttribute("user");
				List<ClientModel> clientDetailList = null;
				if (carNo != null && !"".equals(carNo)) {
					clientDetailList = clientModelService.getSortByType("CarNo", "CurrentDate", "", carNo,
							user.getTanentID());
				}
				List<ClientModel> clientModelListNew = new ArrayList<>();
				if (clientDetailList != null && !clientDetailList.isEmpty()) {
					for (ClientModel clientModel : clientDetailList) {
						if (clientModel != null && clientModel.getCar() != null
								&& clientModel.getCar().getCarNameId() != null) {
							clientModel.getCar()
									.setCarName(carNameService.getById(clientModel.getCar().getCarNameId()));
						}
						if (clientModel != null && clientModel.getCar() != null
								&& clientModel.getCar().getCarTypeId() != null) {
							clientModel.getCar()
									.setCarType(carTypeService.getById(clientModel.getCar().getCarTypeId()));
						}
						clientModelListNew.add(clientModel);
					}
				}
				model.addObject("clientDetailList", clientModelListNew);
				model.setViewName(ViewConstants.GET_TRIPS_DETAIL_CLIENTLIST);
			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
			}
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}
}