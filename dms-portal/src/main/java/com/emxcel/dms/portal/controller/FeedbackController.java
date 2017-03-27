package com.emxcel.dms.portal.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.services.car.CarNameService;
import com.emxcel.dms.core.business.services.car.CarService;
import com.emxcel.dms.core.business.services.car.CarTypeService;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.feedback.FeedbackService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Naresh Banda
 */

@Controller
public class FeedbackController {

	/**
	 * **We autowired it to use services of FeedbackService **.
	 */
	@Inject
	private FeedbackService feedbackService;

	/**
	 * **We autowired it to use services of ClientModelService **.
	 */
	@Inject
	private ClientModelService clientModelService;

	/**
	 * **We autowired it to use services of CarService **.
	 */
	@Inject
	private CarService carService;

	/**
	 * **We autowired it to use services of DriverService **.
	 */
	@Inject
	private DriverService driverService;

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
	 * Created By: Naresh Banda Date: 10-02-2017 Use: Go to enter tripID
	 * redirect to feedback for that tripID
	 * 
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.ENTER_FEEDBACK, method = RequestMethod.GET)
	public final ModelAndView enterFeedback(@ModelAttribute("feedback") Feedback feedback) {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			model.setViewName(ViewConstants.ENTER_FEEDBACK);
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: get Feedback by trip ID
	 * client detail, car detail by carID, driver detail by driverID by tripID
	 * 
	 * @param tripID
	 * @param feedback
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.FEEDBACK, method = RequestMethod.GET)
	public final ModelAndView feedback(@RequestParam(value = "tripID", required = false) String tripID,
			@ModelAttribute("feedback") Feedback feedback, final HttpSession session) {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			ClientModel clientModel = clientModelService.getClientModelTripStatusByTripID(tripID,
					CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_END), user.getTanentID());
			if (clientModel == null) {
				return new ModelAndView(Constants.REDIRECT + UrlConstants.ENTER_FEEDBACK);
			}
			feedback = feedbackService.getFeedbackByTripID(tripID, user.getTanentID());
			if (feedback == null) {
				feedback = new Feedback();
			}
			if (clientModel != null && clientModel.getCar() != null && clientModel.getCar().getCarNameId() != null) {
				clientModel.getCar().setCarName(carNameService.getById(clientModel.getCar().getCarNameId()));
			}
			if (clientModel != null && clientModel.getCar() != null && clientModel.getCar().getCarTypeId() != null) {
				clientModel.getCar().setCarType(carTypeService.getById(clientModel.getCar().getCarTypeId()));
			}
			feedback.setClientModel(clientModel);
			model.addObject("feedbackDetail", feedback);
			model.setViewName(ViewConstants.FEEDBACK);
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: To save feedback for that
	 * tripID
	 * 
	 * @param feedback
	 * @param result
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.SAVE_FEEDBACK, method = RequestMethod.POST)
	public final ModelAndView feedbackSave(@ModelAttribute("feedback") Feedback feedback, HttpSession session)
			throws Exception {
		ModelAndView model = new ModelAndView();
		User user = (User) session.getAttribute("user");
		if (CommonUtil.checkUserLogin()) {
			feedbackService.setfeedback(feedback);
			model.setViewName(Constants.REDIRECT + UrlConstants.ENTER_FEEDBACK);
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: Go to enter tripID
	 * redirect to feedback for that tripID
	 * 
	 * @param tripID
	 * 
	 * @return String
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = UrlConstants.CHECK_FEEDBACK_BY_TRIP_ID, method = RequestMethod.POST)
	@ResponseBody
	public final String enterFeedback(@RequestParam(value = "tripID", required = false) final String tripID,
			final HttpSession session) {
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			Feedback feedback = feedbackService.getFeedbackByTripID(tripID, user.getTanentID());
			ClientModel clientModel = clientModelService.getClientModelTripStatusByTripID(tripID,
					CommonUtil.getStatusIDByStatus(Constants.TRIP_STATUS_END), user.getTanentID());
			if (clientModel == null) {
				return "tripStatusIDError";
			}
			if (feedback != null) {
				return "feedback";
			} else {
				return "nofeedback";
			}
		} else {
			return null;
		}
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: Go to the feedback report
	 * page
	 * 
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.FEEDBACK_REPORT, method = RequestMethod.GET)
	public final ModelAndView feedbackReportListAll() {
		ModelAndView model = new ModelAndView();
		if (CommonUtil.checkUserLogin()) {
			model.setViewName(ViewConstants.FEEDBACKREPORT);
		} else {
			model.setViewName(Constants.REDIRECT + UrlConstants.LOGIN);
		}
		return model;
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: Feedback Report List by
	 * parameters
	 * 
	 * @param typeOfOperation
	 * @param carNo
	 * @param startDate
	 * @param endDate
	 * @param driName
	 * @return List
	 */
	@RequestMapping(value = UrlConstants.AJAX_FEEDBACK_LIST, method = RequestMethod.POST)
	@ResponseBody
	public final List<Feedback> feedbackReportList(@RequestParam("typeOfOperation") final String typeOfOperation,
			@RequestParam(value = "carNo", required = false) final String carNo,
			@RequestParam(value = "startDate") final String startDate,
			@RequestParam(value = "endDate", required = false) final String endDate,
			@RequestParam(value = "driName", required = false) final String driName, final HttpSession session) {
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			if (typeOfOperation.equals("SelectType")) {
				List<Feedback> feedbackList = feedbackService.getFeedbackList(user.getTanentID());
				if (!feedbackList.isEmpty()) {
					for (Feedback feedback : feedbackList) {
						ClientModel clientModel = clientModelService.getTripByTripIdTenant(feedback.getTripID(),
								user.getTanentID());
						if (clientModel != null) {
							feedback.setClientModel(clientModel);
						}
					}
				}
				return feedbackList;
			} else {
				Car car = null;
				if (carNo != null && !carNo.equals("")) {
					car = carService.checkCarByCarNo(carNo);
				}
				Driver driver = null;
				if (driName != null && !driName.equals("")) {
					driver = driverService.getDriverByDriverName(driName);
				}
				List<Feedback> feedbackList = feedbackService.getFeedbackList(startDate, endDate, car, driver);
				List<Feedback> feedbackListNew = new ArrayList<>();
				for (Feedback feedback : feedbackList) {
					/*ClientModel clientDetail = clientModelService.getTripByTripId(feedback.getTripID());
					if (clientDetail != null) {
						feedback.setClientModel(clientDetail);
						feedbackListNew.add(feedback);
					}*/
					feedbackService.setClientModelInFeedback(feedback);
					feedbackListNew.add(feedback);
				}
				return feedbackListNew;
			}
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Created By: Naresh Banda Date: 10-02-2017 Use: get client detail, car
	 * detail by carID, driver detail by driverID by tripID
	 * 
	 * @param tripID
	 * @param typeOfOperation
	 * @param startDateString
	 * @param endDateString
	 * @param carNo
	 * @param driName
	 * @return ClientDetail
	 */
	/*
	 * private List<ClientModel> getClientDetail(String tripID, String
	 * typeOfOperation, String startDateString, String endDateString, String
	 * carNo, String driName) {
	 * 
	 * 
	 * return Collections.emptyList(); }
	 */
	/**
	 * Created by : Naresh Banda Date : 10-02-2017. Use : Car Details List
	 *
	 * @return list
	 */
	@RequestMapping(value = UrlConstants.LIST_CAR_LIST_AJAX, method = RequestMethod.GET)
	@ResponseBody
	public final List<Car> getCarDetailList(final HttpSession session) {
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			return carService.findAllCarsNoDrivers(user.getTanentID());
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Created by : Naresh Banda Date : 10-02-2017. Use : driver Details List
	 *
	 * @return list
	 */
	@RequestMapping(value = UrlConstants.LIST_DRIVER_LIST_AJAX, method = RequestMethod.GET)
	@ResponseBody
	public final List<Driver> detailList(final HttpSession session) {
		if (CommonUtil.checkUserLogin()) {
			User user = (User) session.getAttribute("user");
			return driverService.listOfDriver(user.getTanentID());
		} else {
			return Collections.emptyList();
		}
	}
}