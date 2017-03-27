package com.emxcel.dms.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.services.client.ClientModelService;
import com.emxcel.dms.core.business.services.driver.DriverService;
import com.emxcel.dms.core.business.services.mapping.CarDriverMappingService;
import com.emxcel.dms.core.model.car.CarDriverMapping;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author emxcelsolution
 */
@Controller
public class CarDriverMappingController {
	
	private static final Logger logger = Logger.getLogger(CarDriverMappingController.class);
	
	/**
	 * ***carDriverMappingService**.
	 */
	@Inject
	private CarDriverMappingService carDriverMappingService;

	@Inject
	private DriverService driverService;
	
	@Inject
	private ClientModelService clientModelService;
	
	

	/**
	 * Created By Ashka Thaker. Use For-addCar
	 *
	 * @param carDriverMapping
	 *            **carDriverMapping**
	 * @param m
	 * @return **MoelAnd View**
	 */
	@RequestMapping(value = UrlConstants.CAR_DRIVER_MAPPING, method = RequestMethod.GET)
	private ModelAndView listCarDriverMap(@ModelAttribute("command") final CarDriverMapping carDriverMapping,
			final Model m, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Map<String, Object> model = new HashMap<>();
		List<CarDriverMapping> carList = carDriverMappingService.findAllCarDriver(user.getTanentID());
		List<Driver> driverList = driverService.listOfDriver(user.getTanentID());
		model.put("msg", (String) m.asMap().get("msg"));
		model.put("cardriverlst", carList);
		model.put("driverlst", driverList);
		return new ModelAndView(ViewConstants.CAR_DRIVER_MAPPING, model);
	}

	/**
	 * Created By Ashka Thaker. Use For-update car Driver
	 * 
	 * @param session
	 *            **session**
	 * @param carId
	 *            **carId**
	 * @param carDriverId
	 *            **mapId**
	 * @param driId
	 *            **driId**
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.UPDATE_CAR_DRIVER_MAPPING, method = RequestMethod.POST)
	private void updatecarDriver(final HttpSession session,
			@RequestParam(value = "carId", required = false) String carId,
			@RequestParam(value = "mapId", required = false) String carDriverId,
			@RequestParam(value = "driId", required = false) String driId) {
		User user = (User) session.getAttribute("user");
		try {
			CarDriverMapping carDriver = new CarDriverMapping();
			if (carId != null && !"".equals(carId) && driId != null && !"".equals(driId)) {
				carDriver.setTanentID(user.getTanentID());
				carDriver.setCarId(Long.valueOf(carId));
				carDriver.setDriId(Long.valueOf(driId));
				if (carDriverId != null) {
					carDriver.setId(Long.valueOf(carDriverId));
				}
				carDriverMappingService.update(carDriver);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
	}

	/**
	 * Created By Ashka Thaker. Use For-update Remove Car Driver
	 * 
	 * @param session
	 *            **session**
	 * @param mapId
	 *            **mapId**
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.REMOVE_CAR_DRIVER_MAPPING, method = RequestMethod.POST)
	private boolean updateRemoveCarDriver(HttpSession session, @RequestParam("mapId") String mapId) {
		try {
			if (mapId != null && !"".equals(mapId)) {
				CarDriverMapping carDriver = carDriverMappingService.getById(Long.valueOf(mapId));
				if (carDriver != null) {
					Driver driver = driverService.getById(carDriver.getDriId());
					List<ClientModel> clientModel = clientModelService.getDriverByDriverId(driver);
					if(clientModel.size()>0){
						return false;
					}
					else{
					carDriver.setDriId(null);
					carDriverMappingService.update(carDriver);
					return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return false;
	}

	/**
	 * @param session
	 * @param driId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.CHECK_CAR_DRIVER_MAPPING, method = RequestMethod.POST)
	private String checkRemoveCarDriver(HttpSession session,
			@RequestParam(value = "driId", required = false) String driId) {
		User user = (User) session.getAttribute("user");
		if (driId != null && !"".equals(driId)) {
			CarDriverMapping carDriver = carDriverMappingService.checkRemoveCarDriver(user.getTanentID(),
					Long.valueOf(driId));
			if (carDriver != null) {
				return "true";
			} else {
				return "false";
			}
		} else {
			return "false";
		}
	}
}