package com.emxcel.dms.portal.controller;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.emxcel.dms.core.business.services.car.LocationService;
import com.emxcel.dms.core.model.geo.GeoData;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;


/**
 * @author Ashka Thaker
 *
 */
@Controller
public class LocationController {

	@Inject
	private LocationService locationService;

	@RequestMapping(value = UrlConstants.CAR_LOCATION, method = RequestMethod.GET)
	public final ModelAndView maplocation(@ModelAttribute("command") GeoData geoData, BindingResult result,
			@RequestParam("tripId") String tripId) {
		Map<String, Object> model = new HashMap<>();
		model.put("tripId", tripId);
		return new ModelAndView(ViewConstants.CAR_LOCATION, model);
	}

	@RequestMapping(value = UrlConstants.FETCH_CAR_LOCATION, method = RequestMethod.GET)
	@ResponseBody
	public final Map<String, Object> getlocation(@ModelAttribute("command") GeoData geoData,
			@RequestParam("id") String id, @RequestParam("tripId") String tripId) {
		Map<String, Object> model = new HashMap<>();
		String lat = null;
		String lng = null;
		List<String> listOfLatitude = null;
		List<String> listOfLongitude = null;
		int i = Integer.parseInt(id);
		if (i == 1) {
			List<GeoData> geoDataLst = locationService.getTripGeoData(tripId);
			if (geoDataLst != null) {
				for (GeoData gData : geoDataLst) {
					if (lat == null && lng == null) {
						lat = gData.getLatitude();
						lng = gData.getLongitude();
					} else {
						lat = lat + gData.getLatitude();
						lng = lng + gData.getLongitude();
					}
				}
			}
		} else {
			GeoData geoDataByTripId = locationService.getgeoData(tripId);
			lat = geoDataByTripId.getLatitude();
			lng = geoDataByTripId.getLongitude();
		}
		if (lat != null) {
			listOfLatitude = new ArrayList<>(Arrays.asList(lat.split(",")));
		}
		if (lng != null) {
			listOfLongitude = new ArrayList<>(Arrays.asList(lng.split(",")));
		}
		model.put("latitude", listOfLatitude);
		model.put("longitude", listOfLongitude);
		return model;
	}

	@RequestMapping(value = "/getscreen", method = RequestMethod.GET)
	public ModelAndView getscreen(@ModelAttribute("command") GeoData geoData, BindingResult result) {
		try {
			Robot robot = new Robot();
			String format = "png";
			String fileName = "screenGeo." + format;
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
			ImageIO.write(screenFullImage, format, new File(fileName));
		} catch (AWTException | IOException ex) {
			ex.printStackTrace();
		}
		return new ModelAndView("index");
	}
}