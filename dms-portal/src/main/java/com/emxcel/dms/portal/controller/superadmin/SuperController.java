package com.emxcel.dms.portal.controller.superadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.user.User;
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
import com.emxcel.dms.core.business.services.superadmin.DestinationMasterService;
import com.emxcel.dms.core.business.services.superadmin.InvoicePackageService;
import com.emxcel.dms.core.business.services.superadmin.RateOfContractService;
import com.emxcel.dms.core.business.services.tax.TaxSlabService;
import com.emxcel.dms.core.model.superadmin.DestinationMaster;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.superadmin.RateOfContract;
import com.emxcel.dms.core.model.tax.TaxSlab;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;


/**
 * @author Jimit Patel
 */
@Controller
public class SuperController {

	private static final Logger logger = Logger.getLogger(SuperController.class);
	
	/**
	 * Inject TaxSlabService.
	 */
	@Inject
	private TaxSlabService taxSlabService;

	/**
	 * Inject InvoicePackageService.
	 */
	@Inject
	private InvoicePackageService invoicePackageService;

	/**
	 * Inject DestinationMasterService.
	 */
	@Inject
	private DestinationMasterService destinationMasterService;

	/**
	 * Inject RateOfContractService.
	 */
	@Inject
	private RateOfContractService rateOfContractService;

	/**
	 * Created By-Johnson Chunara. Form Date-12/1/2017 Used For- Invoice Package
	 *
	 * @param invoicePackage
	 *            invoicePackage
	 * @param id
	 *            invoicePackageid
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.INVOICE_PACKAGE, method = RequestMethod.GET)
	public ModelAndView addInvoicePackage(@ModelAttribute("command") InvoicePackage invoicePackage,
			@RequestParam(value = "invoicePackageid", required = false) final String id) {
		ModelAndView model = new ModelAndView();
		try {
			if (id != null && !("").equals(id)) {
			InvoicePackage	invoicePackagenew = invoicePackageService.getById(Long.valueOf(id));
				if (invoicePackage != null) {
					model.addObject("invoice", invoicePackagenew);
				} else {
					model.setViewName(Constants.REDIRECT + UrlConstants.INVOICE_PACKAGE);
					return model;
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			model.setViewName(Constants.REDIRECT + UrlConstants.INVOICE_PACKAGE);
			return model;
		}
		model.setViewName(ViewConstants.INVOICE_PACKAGE);
		return model;
	}

	/**
	 * Created By-Johnson Chunara. Date-12/1/2017 Used For- Invoice Package List
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.LIST_IINVOICE_PACKAGE, method = RequestMethod.GET)
	public ModelAndView invoicelist() {
		ModelAndView model = new ModelAndView();
		model.setViewName(ViewConstants.LIST_INVOICE_PACKAGE);
		List<InvoicePackage> invoicePackageList = invoicePackageService.list();
		model.addObject("invoicePackageList", invoicePackageList);
		return model;
	}

	/***
	 * Created By-Johnson Chunara. Used For- Save Invoice Package Date-12/1/2017
	 *
	 * @param invoicePackage
	 *            invoicePackage
	 * @param result
	 *            BindingResult
	 * @param ra
	 *            RedirectAttributes
	 * @param session
	 *            session
	 * @return ModelAndView
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = UrlConstants.SAVE_INVOICE_PACKAGE, method = RequestMethod.POST)
	public final ModelAndView saveInvoicePackage(@ModelAttribute("command") final InvoicePackage invoicePackage,
			final BindingResult result, final RedirectAttributes rDirectAttribute, final HttpSession session) {
		List<InvoicePackage> listInvoicePackage = invoicePackageService
				.getInvoiceTypeDBStatus(invoicePackage.getCategoryname(), invoicePackage.getId());
		if (!listInvoicePackage.isEmpty()) {
			rDirectAttribute.addFlashAttribute("msg",
					"This Invoice Category  exists. Please select another Invoice Category.");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.INVOICE_PACKAGE);
		} else {
			try {
				if (invoicePackage != null) {
					rDirectAttribute.addFlashAttribute("msg", "Invoice Category Added Successfully !!!");
					invoicePackageService.save(invoicePackage);
				} else {
					rDirectAttribute.addFlashAttribute("msg", " Something Went Wrong !!!");
					return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_IINVOICE_PACKAGE);

				}

			} catch (Exception e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				rDirectAttribute.addFlashAttribute("msg", " Something Went Wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_IINVOICE_PACKAGE);
			}
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_IINVOICE_PACKAGE);
		}
	}

	/**
	 * Created By-Johnson Chunara. Date-12/1/2017 Used For- Delete Invoice
	 * Package
	 *
	 * @param id
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_INVOICE_PACKAGE, method = RequestMethod.GET)
	public ModelAndView deleteInvoicePackage(
			@RequestParam(value = "invoicePackageid", required = false) final String id, final RedirectAttributes ra) {
		try {
			if (id != null && !id.equals("")) {
				List<TaxSlab> list = taxSlabService.getTaxSlabById(Long.valueOf(id));
				if (!list.isEmpty()) {
					ra.addFlashAttribute(Constants.MESSAGE, "Tax Slab  with same Invoice Category is already exist!");
				} else {
					invoicePackageService.delete(invoicePackageService.getById(Long.valueOf(id)));
					ra.addFlashAttribute(Constants.MESSAGE, "Invoice Package Delete Successfully !!!");
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_IINVOICE_PACKAGE);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_IINVOICE_PACKAGE);
	}

	/**
	 * Created By-Johnson Chunara. Form Date-12/1/2017 Used For- Rate of
	 * contract
	 *
	 * @param rateOfContract
	 * @param id
	 *            rateOfContractid
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.RATE_OF_CONTRACT, method = RequestMethod.GET)
	public ModelAndView addRateOfContract(@ModelAttribute("command") RateOfContract rateOfContract,
			@RequestParam(value = "rateOfContractId", required = false) final String id, final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		try {
			if (id != null && !id.equals("")) {
			RateOfContract	rateOfContractnew = rateOfContractService.getById(Long.valueOf(id));
				if (rateOfContract != null) {
					model.addObject("rateofContract", rateOfContractnew);
				} else {
					ra.addAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
		}
		model.setViewName(ViewConstants.RATE_OF_CONTRACT);
		return model;
	}

	/**
	 * Created By-Johnson Chunara. Used For- Invoice Package List Date-12/1/2017
	 * Used For- RateContract List
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.LIST_RATE_OF_CONTRACT, method = RequestMethod.GET)
	public ModelAndView rateOfContractlist() {
		ModelAndView model = new ModelAndView();
		model.setViewName(ViewConstants.LIST_RATE_OF_CONTRACT);
		List<RateOfContract> rateOfContractList = rateOfContractService.list();
		model.addObject("rateOfContractList", rateOfContractList);
		return model;
	}

	/**
	 * Creaed By-Johnson Chunara. Date-12/1/2017 Use: to save rate of contract
	 *
	 * @param rateOfContract
	 * @param result
	 * @param ra
	 * @param session
	 * @return ModelAndView
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = UrlConstants.SAVE_RATE_OF_CONTRACT, method = RequestMethod.POST)
	public final ModelAndView saveRateOfContract(@ModelAttribute("command") final RateOfContract rateOfContract,
			final BindingResult result, final RedirectAttributes ra, final HttpSession session) {
		List<RateOfContract> rateOfContractList = null;
		try {
			rateOfContractList = rateOfContractService.getRateOfContractDBStatus(rateOfContract.getRateOfContract(),
					rateOfContract.getId());
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		if (rateOfContractList != null && !rateOfContractList.isEmpty()) {
			ra.addFlashAttribute("msg",
					"This Invoice Rate Of Contract  exists. Please Enter another Rate Of Contract.");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.RATE_OF_CONTRACT);
		} else {
			try {
				if(rateOfContract!=null){
					rateOfContractService.save(rateOfContract);
					ra.addFlashAttribute(Constants.MESSAGE, "Rate Of Contract Added Successfully");
				}
				else{
					ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
					return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
					
				}
				
			} catch (ServiceException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				ra.addFlashAttribute(Constants.MESSAGE, " Something went wrong !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
			}
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
		}
	}

	/**
	 * Created By-Johnson Chunara. Date-12/1/2017 Used For- Delete
	 * RateOfContract
	 *
	 * @param id
	 * @param ra
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_RATE_OF_CONTRACT, method = RequestMethod.GET)
	public ModelAndView deleteRateOfContract(@RequestParam(value = "rateOfContractId", required = false) final String id,
			final RedirectAttributes ra) {
		try {
			if (id != null && !("").equals(id)) {
				rateOfContractService.delete(rateOfContractService.getById(Long.valueOf(id)));
				ra.addFlashAttribute(Constants.MESSAGE, "Rate Of Contract Delete Successfully !!!");
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_RATE_OF_CONTRACT);
	}

	/**
	 * Date : 12-01-2017 Created By : Jimit Patel. Use: To add Source and
	 * Destination Place
	 *
	 * @param destinationMaster
	 * @param result
	 * @return Model And View
	 */
	@RequestMapping(value = UrlConstants.DESTINATION_MASTER, method = RequestMethod.GET)
	public ModelAndView destinationMaster(@ModelAttribute("command") DestinationMaster destinationMaster,
			final BindingResult result, @RequestParam(value = "destinationId", required = false) final String id,
			final RedirectAttributes ra,HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		User user=CommonUtil.getUser(session);
		try {
			map.put("destinationList", destinationMasterService.listoDestinationMaster(user.getTanentID()));
			if (id != null && !("").equals(id)) {
				DestinationMaster destinationMasternew = destinationMasterService.getById(Long.valueOf(id));
				if (destinationMasternew != null) {
					map.put("destination", destinationMasternew);
					map.put("destinationList", destinationMasterService.listoDestinationMaster(user.getTanentID()));
				} else {
					map.put(Constants.MESSAGE, " Something Went Wrong !!!");
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DESTINATION_MASTER);
		}
		return new ModelAndView(ViewConstants.DESTINATION_MASTER, map);
	}

	/**
	 * Date : 12-01-2017 Created By : Jimit Patel. Use: To save Source and
	 * Destination Place
	 *
	 * @param destinationMaster
	 * @param result
	 * @param ra
	 * @return Model And View
	 */
	@RequestMapping(value = UrlConstants.SAVE_DESTINATION, method = RequestMethod.POST)
	public ModelAndView saveDestinationMaster(@ModelAttribute("command") final DestinationMaster destinationMaster,
			final BindingResult result, final RedirectAttributes ra,HttpSession session) {
		List<DestinationMaster> list = null;
		try {
			list = destinationMasterService.checkdestination(destinationMaster.getSourcePlace(),

					destinationMaster.getDestinationPlace(), Integer.valueOf(destinationMaster.getPrice()), destinationMaster.getId());

			if (list != null && !list.isEmpty()) {
				ra.addFlashAttribute(Constants.MESSAGE,
						"This Source And Destination is exists. Please enter another Source And Destination !!!");
			} else {
				if (destinationMaster.getId() != null) {
					ra.addFlashAttribute(Constants.MESSAGE, "Source And Destination Updated Successfully !!!");
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, "Source And Destination Added Successfully !!!");
				}
				User user =CommonUtil.getUser(session);
				destinationMaster.setTanentID(user.getTanentID());
				destinationMasterService.save(destinationMaster);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DESTINATION_MASTER);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.DESTINATION_MASTER);
	}




	/**
	 * Date : 12-01-2017 Created By : Jimit Patel. Use: To delete Sorce and
	 * Destination Place
	 *
	 * @param destinationMaster
	 * @param result
	 * @return Model And View
	 */
	@RequestMapping(value = UrlConstants.DELETE_DESTINATION, method = RequestMethod.GET)
	public ModelAndView deletedestinationMaster(@ModelAttribute("command") final DestinationMaster destinationMaster,
			final BindingResult result, @RequestParam(value = "destinationId", required = false) final String id,
			final RedirectAttributes ra) {
		try {
			if (id != null && !("").equals(id)) {
				destinationMasterService.delete(destinationMasterService.getById(Long.valueOf(id)));
				ra.addFlashAttribute(Constants.MESSAGE, "Source And Destination Deleted Successfully !!!");
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.DESTINATION_MASTER);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.DESTINATION_MASTER);
	}

	/**
	 * Created By: Naresh Banda Date: 18-1-2017. Use: get term and conditions
	 * page.
	 *
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.TERM_CONDITIONS, method = RequestMethod.GET)
	public ModelAndView getTermAndConditionsPage() {
		return new ModelAndView(ViewConstants.TERM_CONDITIONS);
	}
}