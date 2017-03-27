package com.emxcel.dms.portal.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Emxcel Solutions
 */
@ControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// Convert a predefined exception to an HTTP Status code
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ModelAndView conflict(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", ex);
		mav.addObject("content", req.getRequestURL());
		mav.setViewName(ViewConstants.ERROR);
		return mav;
	}

	// Specify name of a specific view that will be used to display the error:
	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public ModelAndView databaseError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", ex);
		mav.addObject("content", req.getRequestURL());
		mav.setViewName(ViewConstants.ERROR);
		return mav;
	}

	// Total control - setup a model and return the view name yourself. Or
	// consider subclassing ExceptionHandlerExceptionResolver (see below).
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Data integrity violation") // 409
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("error", ex);
		mav.addObject("content", req.getRequestURL());
		mav.setViewName(ViewConstants.ERROR);
		return mav;
	}
}