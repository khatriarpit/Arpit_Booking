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
import com.emxcel.dms.core.business.services.superadmin.InvoicePackageService;
import com.emxcel.dms.core.business.services.tax.TaxCategoryService;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

@Controller
public class TaxSlabController {
   
	private static final Logger logger = Logger.getLogger(TaxSlabController.class);
	
	/**
     * @Inject service for TaxSlabService
     */
    @Inject
    private TaxSlabService taxSlabService;

    /**
     * @Inject service for TaxCategoryService
     */
    @Inject
    private TaxCategoryService taxCategoryService;

    /**
     * @Inject service for InvoicePackageService
     */
    @Inject
    private InvoicePackageService invoicePackageService;


    /**
     * created by : Jimit Patel Date: 26-1-2017.
     * use : use for  TaxDetails and  invoiceCategory list.
     *
     * @param taxSlabModel **taxSlabModel**.
     * @param id           **id**.
     * @return ModelAndView
     */
    @RequestMapping(value = UrlConstants.TAX_SLAB, method = RequestMethod.GET)
    public ModelAndView addTaxSlab(@ModelAttribute("command") TaxSlab taxSlabModel, @RequestParam(value = "taxslabId", required = false) String taxslabId, final RedirectAttributes ra) {
        Map<String, Object> model = new HashMap<>();
        try {
        	model.put("TaxDetails", taxCategoryService.list());
            model.put("invoiceCategory", invoicePackageService.list());
            if (taxslabId != null && !"".equals(taxslabId)) {
                TaxSlab taxSlabById = taxSlabService.getById(Long.valueOf(taxslabId));
                if (taxSlabById != null) {                    
                    model.put("taxSlab", taxSlabById);
                } else {
                	ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
                	return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
                }
            } 
        } catch (Exception e) {
        	logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
        }
        model.put("SlabDetails", taxSlabService.list());
        return new ModelAndView(ViewConstants.TAXSLAB, model);
    }

    /**
     * created by : Jimit Patel Date: 26-1-2017.
     * use : use for save operation .
     *
     * @param taxSlabModel **taxSlabModel**.
     * @return ModelAndView
     * @throws ServiceException
     */
    @RequestMapping(value = UrlConstants.SAVE_TAXSLAB, method = RequestMethod.POST)
    public ModelAndView saveTaxSlab(@ModelAttribute("command") final TaxSlab taxSlabModel, final RedirectAttributes ra) {
        try {
            taxSlabService.save(taxSlabModel);
        } catch (Exception e) {
        	logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
        }
        return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
    }

    /**
     * created by : Jimit Patel Date: 26-1-2017.
     *
     * @param taxSlabModel **taxSlabModel**
     * @param id           **id**
     * @return ModelAndView
     * @throws NumberFormatException **NumberFormatException**
     * @throws ServiceException      **ServiceException**
     */
    @RequestMapping(value = UrlConstants.DELETE_TAXSLAB, method = RequestMethod.GET)
    public ModelAndView deleteTaxSlab(@ModelAttribute("command") TaxSlab taxSlabModel, @RequestParam(value = "taxslabId", required = false) String taxslabId, final RedirectAttributes ra) {
        try {
        	if(taxslabId != null && !taxslabId.equals("")) {
        		taxSlabService.delete(taxSlabService.getById(Long.valueOf(taxslabId)));
        	} else {
        		ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
        	}
        } catch (Exception e) {
        	logger.error(Constants.EXCEPTION_THROW, e);
            ra.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
            return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
        }
        return new ModelAndView(Constants.REDIRECT + UrlConstants.TAX_SLAB);
    }
}
