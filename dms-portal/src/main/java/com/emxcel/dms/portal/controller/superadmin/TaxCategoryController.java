package com.emxcel.dms.portal.controller.superadmin;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.tax.TaxCategoryService;
import com.emxcel.dms.core.model.tax.TaxCategory;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Jimit Patel 
 *
 */
@Controller
public class TaxCategoryController {

	private static final Logger logger = Logger.getLogger(TaxCategoryController.class);
	
	/**
	 * to Use TaxCategoryService.
	 */
	@Inject
	private TaxCategoryService taxCategoryService;


	/**
	 * Created By: Jimit Patel Date: 24-01-2017.
	 * Use: To add new tax Category and view list of Tax category
	 * @param taxCategory taxCategory
	 * @param id TaxCategoryid
	 * @return ModelAndView
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.TAX_CATEGORY,
			method = RequestMethod.GET)
	public ModelAndView newTaxCategory(
			@ModelAttribute("command") TaxCategory taxCategory,
			@RequestParam(value = "taxId", required = false) final String taxId, final RedirectAttributes ra)
			throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		try {
			if (taxId != null && !"".equals(taxId)) {
				TaxCategory taxCategoryById = taxCategoryService.getById(Long.valueOf(taxId));
				if(taxCategoryById != null) {
					map.put("Tax", taxCategoryById);
				} else {
					map.put("TaxDetails", taxCategoryService.list());
				}
			} else {
				map.put("TaxDetails", taxCategoryService.list());
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			map.put("TaxDetails", taxCategoryService.list());
		}
		return new ModelAndView(ViewConstants.TAXCATEGORY, map);
	}

	/**
	 * Created By: Jimit Patel Date: 24-01-2017.
	 * Use: To save TaxCategory Details
	 * @param taxCategory taxCategory
	 * @return ModelAndView
	 * @exception ServiceException
	 */
	@RequestMapping(value = UrlConstants.SAVE_TAXCATEGORY,
			method = RequestMethod.POST)
	public ModelAndView saveTaxCategory(
			@ModelAttribute("command") final TaxCategory taxCategory, final RedirectAttributes ra) {
		try {
			taxCategoryService.save(taxCategory);
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_CATEGORY);
	}

	
	/**
	 * Created By: Jimit Patel Date: 24-01-2017.
	 * @param id tax category id
	 * @return ModelAndView
	 * @throws ServiceException
	 */
	@RequestMapping(value = UrlConstants.DELETE_TAXCATEGORY,
			method = RequestMethod.GET)
	public ModelAndView deleteTax(@RequestParam(value = "taxId", required = false) final String taxId, final RedirectAttributes ra) {
		try {
			if(taxId != null && !taxId.equals("")) {
				TaxCategory taxCategory = taxCategoryService.getById(Long.valueOf(taxId));
				if(taxCategory != null) {
					taxCategoryService.delete(taxCategory);
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_CATEGORY);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_CATEGORY);
	}
}