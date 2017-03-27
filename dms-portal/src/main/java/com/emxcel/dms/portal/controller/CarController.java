package com.emxcel.dms.portal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.joda.time.Duration;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.client.PreBookingService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.mapping.CarDriverMappingService;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import com.emxcel.dms.core.model.car.CarName;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.client.PreBooking;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.superadmin.TenantPackage;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;
import com.emxcel.dms.portal.model.BookCarBean;

/**
 * @author emxcelsolution
 */
@Controller
public class CarController {

	private static final Logger logger = Logger.getLogger(CarController.class);

	/**
	 * ***carTypeService**1.
	 */
	@Inject
	private CarTypeService carTypeService;

	/**
	 * **carNameService**
	 */
	@Inject
	private CarNameService carNameService;

	/**
	 * **carService**
	 */
	@Inject
	private CarService carService;

	/**
	 * **clientModelService**
	 */
	@Inject
	private ClientModelService clientModelService;

	/**
	 * ***carDriverMappingService**.
	 */
	@Inject
	private CarDriverMappingService carDriverMappingService;

	/**
	 * ***PackageCreateService**.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	@Inject
	private DriverService driverService;

	@Inject
	private PreBookingService preBookingService;

	/**
	 * Created By - Johnson Chunara. Date-14-12-2016. Use: For - Add Car Type.
	 *
	 * @param cartype
	 *            **car type id**
	 * @param id
	 *            **id**
	 * @return model
	 */
	@RequestMapping(value = UrlConstants.ADD_CARTYPE, method = RequestMethod.GET)
	public ModelAndView addCarType(@ModelAttribute("command") final CarType carType,
			@RequestParam(value = "id", required = false) final String carTypeId) {
		ModelAndView model = new ModelAndView();
		if (carTypeId != null && !("").equals(carTypeId)) {
			model.addObject("cartypeVo", carTypeService.getById(Long.valueOf(carTypeId)));
		}
		model.setViewName(ViewConstants.ADD_CARTYPE);
		return model;
	}

	/**
	 * Created By - Johnson Chunara. Date-14-12-2016. Use : to show the list of
	 * Car Type
	 *
	 * @param cartype
	 *            **cartype**
	 * @param session
	 *            **session**
	 * @return model
	 */
	@RequestMapping(value = UrlConstants.CARTYPE_LIST, method = RequestMethod.GET)
	public ModelAndView carTypeList(@ModelAttribute("command") final CarType carType, final HttpSession session) {
		ModelAndView model = new ModelAndView();
		List<CarType> listOfCarType = carTypeService.list();
		model.addObject("cartypeDetails", listOfCarType);
		model.setViewName(ViewConstants.CARTYPE_LIST);
		return model;
	}

	/**
	 * Created By - Johnson Chunara. Used For - Save Car Type. Date-14-12--2016.
	 * Use: To Save car Type
	 *
	 * @param carType
	 *            **carType**
	 * @param ra
	 *            **redirect**
	 * @return ModelAndView
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_CARTYPE, method = RequestMethod.POST)
	public final ModelAndView saveCarType(@ModelAttribute("command") final CarType carType, final BindingResult result,
			final RedirectAttributes redirect) {
		List<CarType> list = carTypeService.checkCarType(carType.getCarType(), carType.getId());
		if (!list.isEmpty()) {
			redirect.addFlashAttribute(Constants.MESSAGE, "This Car Type  exists. Please select another Car Type.");
		} else {
			if (carType.getId() != null) {
				redirect.addFlashAttribute(Constants.MESSAGE, "Car Type Update Successfully !!!");
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "New Car Type Added Successfully !!!");
			}
			try {
				carTypeService.save(carType);
			} catch (ServiceException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
			}
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
	}

	/**
	 * Created By - Johnson Chunara. Used For - Delete Car Type.
	 * Date-14-12--2016. Use: for Delete Car type
	 *
	 * @param ra
	 *            **Redirect Attribute**
	 * @param id
	 *            **id**
	 * @return ModelAndView
	 * @throws ServiceException
	 * @throws NumberFormatException
	 */
	@RequestMapping(value = UrlConstants.DELETE_CARTYPE, method = RequestMethod.GET)
	public ModelAndView deleteCarType(@RequestParam(value = "id", required = false) final String carTypeId,
			final RedirectAttributes redirect) {
		try {
			if (carTypeId != null && !("").equals(carTypeId)) {
				List<CarName> listOfCarType = carNameService.getCarNameById(Long.valueOf(carTypeId));
				if (!listOfCarType.isEmpty()) {
					redirect.addFlashAttribute(Constants.MESSAGE, "In Car Model with same Car Type is already assigned !!!");
					return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
				} else {
					CarType carType = carTypeService.getById(Long.valueOf(carTypeId));
					if (carType != null) {
						carTypeService.delete(carType);
					}
					return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
				}
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CARTYPE_LIST);
		}
	}

	/**
	 * Created By : Jimit Patel Date-14-12--2016. Use : To Add New CarName
	 *
	 * @param carName
	 *            **carname**
	 * @param carNameId
	 *            **carNameId**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.ADD_CARNAME, method = RequestMethod.GET)
	public final ModelAndView addCarName(@ModelAttribute("command") final CarName carName, final BindingResult result,
			@RequestParam(value = "carId", required = false) final String carNameId, final RedirectAttributes redirect) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("TypeDTL", carTypeService.list());
			if (carNameId != null) {
				map.put("carName", carNameService.getById(Long.valueOf(carNameId)));
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
		}
		return new ModelAndView(ViewConstants.ADD_CARNAME, map);
	}

	/**
	 * Created By Jimit Patel. USe:for list of car name
	 *
	 * @param carName
	 *            **carName**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CARNAME_LIST, method = RequestMethod.GET)
	public final ModelAndView carNameList(@ModelAttribute("command") final CarName carName, final BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		map.put("CarNameDTL", carNameService.listCarName());
		return new ModelAndView(ViewConstants.CARNAME_LIST, map);
	}

	/**
	 * Created By Jimit Patel. Date-14-12--2016. Use For-save Car name
	 *
	 * @param carName
	 *            **CarName**
	 * @param ra
	 *            **redirect Attribute**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_CARNAME, method = RequestMethod.POST)
	public final ModelAndView saveCarName(@ModelAttribute("command") final CarName carName, final BindingResult result,
			final RedirectAttributes redirect) {
		List<CarName> listOfCarName = carNameService.checkCarName(carName.getCarName(), carName.getCarTypeId(), carName.getId());
		if (!listOfCarName.isEmpty()) {
			redirect.addFlashAttribute(Constants.MESSAGE, "This car name with same car Model is already assigned !!!");
		} else {
			if (carName.getId() != null) {
				redirect.addFlashAttribute(Constants.MESSAGE, "Car Model Update Successfully !!!");
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "New Car Model Added Successfully !!!");
			}
			try {
				carNameService.save(carName);
			} catch (ServiceException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
			}
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
	}

	/**
	 * Created By Jimit Patel. Date-14-12--2016. Use For-Delete car name
	 *
	 * @param id
	 *            **car id**
	 * @param ra
	 *            **RedirectAttributes**
	 * @return ModelAndView
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.DELETE_CARNAME, method = RequestMethod.GET)
	public final ModelAndView deleteCarName(@RequestParam(value = "carId", required = false) final String carId,
			final RedirectAttributes redirect) {
		try {
			if (carId != null && !("").equals(carId)) {
				List<Car> list = carNameService.checkexistcar(Long.valueOf(carId));
				if (!list.isEmpty()) {
					redirect.addFlashAttribute(Constants.MESSAGE,
							"This car name with same car model is already assigned !!!");
					return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
				} else {

					carNameService.delete(carNameService.getById(Long.valueOf(carId)));
					return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
				}
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "Not Record Found");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
			}
		} catch (NumberFormatException | ServiceException e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.CARNAME_LIST);
		}
	}

	/**
	 * Created By Johnson Chunara. Date-14-12--2016. Use For List Of Car
	 *
	 * @param carDetail
	 *            **carDetail**
	 * @param m
	 *            **Model**
	 * @param session
	 *            **session**
	 * @return MoelAndView
	 */
	@RequestMapping(value = UrlConstants.LIST_CAR, method = RequestMethod.GET)
	private ModelAndView listOfCar(@ModelAttribute("command") final Car carDetail, final BindingResult result,
			final Model m, final HttpSession session) {
		User userSession = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		map.put("CarTypeList", carTypeService.list());
		map.put("carLst", carNameService.list());
		map.put("carDetails", carService.findAllCars(userSession.getTanentID()));
		return new ModelAndView(ViewConstants.LIST_CAR, map);
	}

	/**
	 * Created By Johnson Chunara. Date-14-12--2016. Use For-add new Car
	 *
	 * @param carDetail
	 *            **Model**
	 * @param m
	 *            **Model**
	 * @param carId
	 *            **carId**
	 * @param session
	 *            session
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.CAR, method = RequestMethod.GET)
	private ModelAndView addCar(@ModelAttribute("command") final Car carDetail, final BindingResult result,
			final Model m, @RequestParam(value = "carId", required = false) final String carId,
			final HttpSession session, final RedirectAttributes redirect) {
		Map<String, Object> map = new HashMap<>();
		try {
			User userSession = (User) session.getAttribute("user");
			List<TenantPackage> tenantPackageList = null;
			if (carId != null && !("").equals(carId)) {
				tenantPackageList = packageCreateService.getTenantPackageForAdminAndUser(userSession.getTanentID(),
						"0", null);
				map.put("car", carService.findById(Long.valueOf(carId)));
			} else {
				tenantPackageList = packageCreateService.getListByTenantID(userSession.getTanentID(),
						"0", null);
			}
			if (userSession != null) {
				map.put("tenantPackageList", tenantPackageList);
			}
			map.put("carLst", carNameService.list());
			map.put("CarTypeList", carTypeService.list());
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
		}
		return new ModelAndView(ViewConstants.ADD_CAR, map);
	}

	/**
	 * created By: Johnson Chunara Date:19-12-2016 Use:To save car details.
	 *
	 * @param carDetail
	 *            carDetail
	 * @param ra
	 *            RedirectAttributes
	 * @param session
	 *            session
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_CAR, method = RequestMethod.POST)
	private ModelAndView saveTenantCar(@Valid @ModelAttribute("command") final Car carDetail,
			final BindingResult result,
			final HttpSession session, final RedirectAttributes redirect) {
		User userSession = (User) session.getAttribute("user");
		try {
			if (carDetail != null && carDetail.getId() != null && carDetail.getId() > 0) {
				redirect.addFlashAttribute(Constants.MESSAGE, "Car updated successfully");
				carService.setStatusforCar(carDetail,session);
			} else {
				if (carDetail != null) {
					List<Car> list = carService.checkCarNoDBStatus(carDetail.getCarNo());
					if (list.isEmpty()) {
						carService.setStatusforCar(carDetail,session);
						List<TenantPackage> tenantPackageList = packageCreateService
								.getTenantPackageForAdminAndUser(userSession.getTanentID(), "0", carDetail.getTenantPackageID());
						Integer noOfCar = 0;
						List<Car> carList = carService.findAllCars(userSession.getTanentID());
						if (tenantPackageList != null && tenantPackageList.get(0) != null && tenantPackageList.get(0).getCars() != 0) {
							noOfCar = tenantPackageList.get(0).getCars();
						}
						if (carList.size() <= noOfCar) {
							
							redirect.addFlashAttribute(Constants.MESSAGE, "New Car added successfully");
							carDriverMappingService.saveCarDriverMapping(carDetail, session);
						} else {
							redirect.addFlashAttribute(Constants.MESSAGE,"Please Select Another Package. You Can not Add More than " + carDetail.getCarNo()
							+ " Car in this package.");
						}
						return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
					} else {
						redirect.addFlashAttribute(Constants.MESSAGE,
								"Please Select Another Car Number. This Car Number " + carDetail.getCarNo() + " is already exist.");
						return new ModelAndView(Constants.REDIRECT + UrlConstants.CAR);
					}
				}

			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
	}

	/**
	 * created By: Jimit Patel Date:19-09-2016 Use:to Delete car Details.
	 *
	 * @param id
	 *            carid
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_CAR, method = RequestMethod.GET)
	public final ModelAndView deleteCar(@RequestParam(value = "id", required = false) final String carId,@ModelAttribute("command") final Car carDetail, final BindingResult result,
			final RedirectAttributes redirect) {
		try {
			if (carId != null && !("").equals(carId)) {
				Car car =carService.getById(Long.valueOf(carId));
				List<ClientModel> listOfClient = clientModelService.getPendingtripsofCar(car);
				if(listOfClient.isEmpty()){
					redirect.addFlashAttribute(Constants.MESSAGE, "Car deleted Succesfully !!!");
					carService.setStatusForCarDelete(car);
				}
				else{
					redirect.addFlashAttribute(Constants.MESSAGE, "You Cannot Delete Car Because Of Pending Trips.");
				}
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "No Record Found !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_CAR);
	}

	/**
	 * created By: Johnson Chunara Date:19-12-2016. Use:To check duplicate car
	 * color for same tanent.
	 *
	 * @param carid
	 *            carid
	 * @param color
	 *            color
	 * @param session
	 *            HttpSession
	 * @return boolean.
	 */
	@RequestMapping(value = UrlConstants.CAR_CHECK_COLOR, method = RequestMethod.POST)
	@ResponseBody
	public boolean getCarColor(@RequestParam("color") final String color, @RequestParam("carid") final String carId,
			final HttpSession session) {
		User userSession = CommonUtil.getUser(session);
		Car car = carService.getCarColor(color, userSession.getTanentID(), Constants.TRUE ? "1" : "0");
		boolean status;
		if (car != null && (carId == null || ("").equals(carId))) {
			status = true;
		} else if (car != null && car.getId().equals(Long.valueOf(carId))) {
			status = false;
		} else if (car == null && carId != null) {
			status = false;
		} else if (car == null && (carId == null || ("").equals(carId))) {
			status = false;
		} else {
			status = true;
		}
		return status;
	}

	/**
	 * created By: Johnson Chunara Date:19-12-2016. Use:To check duplicate car
	 * color.
	 *
	 * @param carTypeId
	 *            carTypeId
	 * @return boolean.
	 */
	@RequestMapping(value = UrlConstants.GET_CARNAME, method = RequestMethod.POST)
	@ResponseBody
	public List<CarName> getCarName(@RequestParam("carTypeId") final String carTypeId) {
		List<CarName> listOfCarName = carNameService.getCarNameById(Long.valueOf(carTypeId));
		if (listOfCarName != null) {
			return listOfCarName;
		} else {
			return Collections.emptyList();
		}
	}

	/**
	 * Created By: Arpit Khatri Date:23-1-2017. Use: To open Search car Page
	 *
	 * @param clientModel
	 *            clientModel
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SEARCH_CAR, method = RequestMethod.GET)
	private ModelAndView searchCar(@ModelAttribute("command") final ClientModel clientModel,
			final BindingResult result) {
		Map<String, Object> map = new HashMap<>();
		map.put("CarTypeDTL", carTypeService.list());
		return new ModelAndView(ViewConstants.SEARCH_CAR, map);
	}

	/**
	 * Created By: Arpit Khatri Date:23-1-2017. Use: For list of available Car
	 * between Two Dates
	 *
	 * @param pickUpDateTime
	 *            picUpDateTime
	 * @param dropDate
	 *            dropDate
	 * @param clientModel
	 *            clientModel
	 * @param result
	 *            BindingResult
	 * @param redirectAttrs
	 *            RedirectAttributes
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.BOOK_CAR_LIST, method = RequestMethod.POST)
	private ModelAndView getListOfAvailableCar(final HttpSession session,
			@RequestParam(value = "pickUpDateTime", required = false) final String pickUpDateTime,
			@RequestParam(value = "dropDateTime", required = false) final String dropDate,
			@RequestParam(value = "prebookingid", required = false) final String preBookingId,
			final ClientModel clientModel, final BindingResult result, final RedirectAttributes redirectAttrs) {
		try {
			PreBooking preBooking = null;
			if (preBookingId != null) {
				preBooking = preBookingService.getById(Long.valueOf(preBookingId));
			}
			User userSession = (User) session.getAttribute("user");
			Long carTypeId = Long.valueOf("0");
			if (preBookingId != null) {
				if (preBooking.getCarType() != null) {
					carTypeId = preBooking.getCarType().getId();
				}
			} else {
				carTypeId = clientModel.getCar().getCarType().getId();

			}
			CarType carType = carTypeService.getById(carTypeId);
			List<Car> listOfCar = carService.getCarAvailableList(pickUpDateTime, dropDate,
					carType != null ? carType.getCarType() : null, userSession.getTanentID());
			// List of Driver which is available on PickupDateTime dropDateTime
			List<Driver> listOfAvailableDrivers = driverService.getAvailableDrivers(pickUpDateTime, dropDate,
					userSession.getTanentID());
			ArrayList<BookCarBean> listOfCarDetails = new ArrayList<BookCarBean>();
			Iterator<Car> var9 = listOfCar.iterator();
			while (var9.hasNext()) {
				Car car = (Car) var9.next();
				BookCarBean beanofCarDetails = new BookCarBean();
				beanofCarDetails.setCarId(car.getId());
				// Check Car Driver Mapping against Car . If it is having Driver
				// id then assign driver as current driver else dropdown of
				// Driver
				CarDriverMapping carDriverMapping = carDriverMappingService.getCarDriverByCarId(car.getId());
				if (carDriverMapping != null) {
					beanofCarDetails.setDriverId(carDriverMapping.getDriId());
					if (beanofCarDetails.getDriverId() != null) {
						Driver driver = driverService.getById(beanofCarDetails.getDriverId());
						beanofCarDetails.setDriverName(driver.getFullName());
					}
				}

				beanofCarDetails.setCarNumber(car.getCarNo());
				beanofCarDetails.setCarType(car.getCarType().getCarType());
				beanofCarDetails.setCarName(car.getCarName() == null ? " " : car.getCarName().getCarName());
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
				ClientModel clientModelForPreviousTrip = this.clientModelService.getPreviousTripForCar(car.getId(),
						pickUpDateTime);
				if (clientModelForPreviousTrip != null) {
					beanofCarDetails.setPreviousStartDate(
							CommonUtil.convertTimestampToDate(clientModelForPreviousTrip.getPickUpDateTime()));
					beanofCarDetails.setPreviousEndDate(
							CommonUtil.convertTimestampToDate(clientModelForPreviousTrip.getDropDateTime()));
					beanofCarDetails.setPreviousStartTime(
							CommonUtil.convertTimestampToTimeOnly(clientModelForPreviousTrip.getPickUpDateTime()));
					beanofCarDetails.setPreviousEndTime(
							CommonUtil.convertTimestampToTimeOnly(clientModelForPreviousTrip.getDropDateTime()));
					String clientModelForNextTrip = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"))
							.format(clientModelForPreviousTrip.getDropDateTime());
					Duration nextStartdateString = new Duration(formatter.parseDateTime(clientModelForNextTrip),
							formatter.parseDateTime(pickUpDateTime));
					beanofCarDetails.setPreviousBufferHours(Long.valueOf(nextStartdateString.getStandardHours()));
				}
				ClientModel clientModelForNextTrip1 = this.clientModelService.getNextTripForCar(car.getId(), dropDate);
				beanofCarDetails.setNextStartDate(clientModelForNextTrip1 == null ? null
						: CommonUtil.convertTimestampToDate(clientModelForNextTrip1.getPickUpDateTime()));
				beanofCarDetails.setNextEndDate(clientModelForNextTrip1 == null ? null
						: CommonUtil.convertTimestampToDate(clientModelForNextTrip1.getDropDateTime()));
				if (clientModelForNextTrip1 != null) {
					beanofCarDetails.setNextStartTime(
							CommonUtil.convertTimestampToTimeOnly(clientModelForNextTrip1.getPickUpDateTime()));
					beanofCarDetails.setNextEndTime(
							CommonUtil.convertTimestampToTimeOnly(clientModelForNextTrip1.getDropDateTime()));
					String nextStartdateString1 = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"))
							.format(clientModelForNextTrip1.getDropDateTime());
					Duration nextDuration = new Duration(formatter.parseDateTime(dropDate),
							formatter.parseDateTime(nextStartdateString1));
					beanofCarDetails.setNextBufferHours(Long.valueOf(nextDuration.getStandardHours()));
				}
				beanofCarDetails.setPickupDate(
						CommonUtil.convertTimestampToDate(CommonUtil.convertStringToTimestamp(pickUpDateTime)));
				beanofCarDetails
						.setDropDate(CommonUtil.convertTimestampToDate(CommonUtil.convertStringToTimestamp(dropDate)));
				beanofCarDetails.setPickupTime(
						CommonUtil.convertTimestampToTimeOnly(CommonUtil.convertStringToTimestamp(pickUpDateTime)));
				beanofCarDetails.setDropTime(
						CommonUtil.convertTimestampToTimeOnly(CommonUtil.convertStringToTimestamp(dropDate)));
				beanofCarDetails.setListOfDriver(listOfAvailableDrivers);
				listOfCarDetails.add(beanofCarDetails);
			}
			redirectAttrs.addFlashAttribute("carTypeId", carTypeId);
			redirectAttrs.addFlashAttribute("pickUpDateTime", pickUpDateTime);
			redirectAttrs.addFlashAttribute("dropDate", dropDate);
			redirectAttrs.addFlashAttribute("preBooking", preBookingId != null ? preBooking : null);
			redirectAttrs.addFlashAttribute("listOfCarDetails", listOfCarDetails);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(Constants.EXCEPTION_THROW, e);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.SEARCH_CAR);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.SEARCH_CAR);
	}

	/**
	 * Created By : Johnson Chunara.
	 *
	 * @param carId
	 *            carId
	 * @return Car
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_CARNO, method = RequestMethod.POST)
	public final Car getCarByID(@RequestParam(value = "carId", required = false) final String carId) {
		try {
			if (carId != null && !("").equals(carId)) {
				return carService.findById(Long.valueOf(carId));
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			return null;
		}
	}

}