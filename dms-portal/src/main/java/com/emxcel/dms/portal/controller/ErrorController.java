package com.emxcel.dms.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Emxcel Solutions
 *
 */
@Controller
public class ErrorController {

	/**
	 * Created for : For Error Page
	 * 
	 * @return ModelAndView.
	 */

	/*@RequestMapping(value = UrlConstants.ERROR, method = RequestMethod.GET)
	public ModelAndView accesssDenied(@RequestParam(value = "error", required = false) String error) {
		ModelAndView model = new ModelAndView();
		model.addObject("error", error);
		model.addObject("content", "Something went wrong !!!");
		model.setViewName(ViewConstants.ERROR);
		return model;
	}*/

	/**
	 * @param req
	 * @param ex
	 * @return
	 */
	/*@RequestMapping(value = "/{[path:[^\\.]*}")
	@ExceptionHandler(Exception.class)
	public ModelAndView error(HttpServletRequest req, Exception ex) {
		ModelAndView model = new ModelAndView();
		model.addObject("error", ex.fillInStackTrace());
		model.addObject("content", req.getRequestURL());
		model.setViewName(ViewConstants.ERROR);
		return model;
	}*/
}