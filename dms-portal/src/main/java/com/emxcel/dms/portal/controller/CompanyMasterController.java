package com.emxcel.dms.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

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
import com.emxcel.dms.core.business.services.companymaster.CompanyMasterService;
import com.emxcel.dms.core.business.services.companymaster.CompanyTypeService;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

@Controller
public class CompanyMasterController {

	private static final Logger logger = Logger.getLogger(CompanyMasterController.class);

	/**
	 * companyMasterService
	 */
	@Inject
	private CompanyMasterService companyMasterService;

	@Inject
	private CompanyTypeService companyTypeService;
	
	@Inject
	private TenantService tenantService;

	@RequestMapping(value = UrlConstants.COMAPANY_TYPE, method = RequestMethod.GET)
	public final ModelAndView listOfCompanyMaster(@ModelAttribute("command")

	final CompanyType companyType, @RequestParam(value = "companyTypeid", required = false) final String companyTypeid) {
		Map<String, Object> map = new HashMap<>();
		if (companyTypeid != null) {
			map.put("companytypeVo", companyTypeService.getById(Long.valueOf(companyTypeid)));
		}
		return new ModelAndView(ViewConstants.COMPANY_TYPE, map);
	}

	@RequestMapping(value = UrlConstants.SAVE_COMPANY_TYPE, method = RequestMethod.POST)
	public final ModelAndView savecompanytype(@ModelAttribute("command") final CompanyType companyType,
			final RedirectAttributes redirect) {
		List<CompanyType> list = companyTypeService.checkcompanytype(companyType.getCompanyType(), companyType.getId());
		if (!list.isEmpty()) {
			redirect.addFlashAttribute(Constants.MESSAGE, "This Company Type  exists. Please Add another Company Type.");
		} else {
			try {
				if (companyType.getId() != null) {
					redirect.addFlashAttribute(Constants.MESSAGE, "Company Update Successfully ");
				} else {
					redirect.addFlashAttribute(Constants.MESSAGE, "Company Type Added Successfully ");
				}
				companyTypeService.saveCompanytypeWithStatus(companyType);

			} catch (ServiceException e) {
				logger.error(Constants.EXCEPTION_THROW, e);
				redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!! ");
			}
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.COMPANY_TYPE_LIST);
	}

	@RequestMapping(value = UrlConstants.UPDATE_COMPANY_TYPE, method = RequestMethod.GET)
	public final ModelAndView updateCompanyType(@ModelAttribute("command") CompanyType companyType,
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "status", required = false) String status, final RedirectAttributes redirect) {
		try {
			if (id != null && !"".equals(id) && status != null && !"".equals(status)) {
				companyType = companyTypeService.getById(Long.valueOf(id));
				if (status.equals(Constants.FALSE_AS_STRING)) {
					companyTypeService.updatecompanyType(companyType,Constants.TRUE);
				} else {
					int tenant = tenantService.getCompanyTypeStatus(companyType);
					if(tenant > 0){
						redirect.addFlashAttribute(Constants.MESSAGE, " CompanyType already used in Tenant");
						return new ModelAndView(Constants.REDIRECT + UrlConstants.COMPANY_TYPE_LIST);
					}else{
						companyTypeService.updatecompanyType(companyType,Constants.FALSE);	
					}
				}
			}
			redirect.addFlashAttribute(Constants.MESSAGE, "Company Type Successfully "
					+ (Constants.FALSE_AS_STRING.equals(status) ? "Active" : "Inactive") + " !!!");
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.COMPANY_TYPE_LIST);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.COMPANY_TYPE_LIST);
	}

	@RequestMapping(value = UrlConstants.COMPANY_TYPE_LIST, method = RequestMethod.GET)
	public ModelAndView listOfCarType(@ModelAttribute("command") final CompanyType companyType) {
		Map<String, Object> map = new HashMap<>();
		List<CompanyType> companyList = companyTypeService.list();
		map.put("companytypeDetails", companyList);
		return new ModelAndView(ViewConstants.COMPANY_TYPE_LIST, map);
	}

	/**
	 * Created By: Nitin Patel. Date:12-01-2017 Use to :get list of Company
	 * Master
	 *
	 * @param companyMaster
	 *            **companyMaster**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.LIST_COMAPANY_MASTER, method = RequestMethod.GET)
	public final ModelAndView companymasterList(@ModelAttribute("command") final CompanyMaster companyMaster) {
		Map<String, Object> model = new HashMap<>();
		model.put("companyMasterList", companyMasterService.listOfCompanyMaster());
		return new ModelAndView(ViewConstants.LIST_COMPANY_MASTER, model);
	}

	/**
	 * Created By: Nitin Patel. Date:12-01-2017 Use to :add/edit Company Master
	 *
	 * @param companyMaster
	 *            **companyMaster**.
	 * @param id
	 *            **companyMasterId**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.ADD_COMPANY_MASTER, method = RequestMethod.GET)
	public final ModelAndView addCompanyMaster(@ModelAttribute("command") final CompanyMaster companyMaster,
			@RequestParam(value = "companyMasterId", required = false) final String id, final RedirectAttributes redirect) {
		Map<String, Object> model = new HashMap<>();
		try {
			if (id != null && !"".equals(id)) {
				CompanyMaster companyMasterNew = companyMasterService.getCompanyMasterByStatus(Long.valueOf(id), 1);
				if (companyMasterNew != null) {
					model.put("companyMasterDetails", companyMasterNew);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, " Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_COMAPANY_MASTER);
		}
		return new ModelAndView(ViewConstants.ADD_COMPANY_MASTER, model);
	}

	/**
	 * Created By: Nitin Patel. Date:12-01-2017 Use to :save Company Master in
	 * database
	 *
	 * @param companyMaster
	 *            **companyMaster**.
	 * @param session
	 *            **session**.
	 * @param redirect
	 *            **redirectAttrs**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_COMAPANY_MASTER, method = RequestMethod.POST)
	public final ModelAndView saveCompanyMaster(@ModelAttribute("command") final CompanyMaster companyMaster,
			final HttpSession session, final RedirectAttributes redirect) {
		try {
			User user = CommonUtil.getUser(session);
			//companyMaster.setStatus(Constants.DEACTIVE);
			if (companyMaster.getId() != null) {
				redirect.addFlashAttribute(Constants.MESSAGE, "Company Master updated successfully");
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "New Company Master added successfully");
			}
			//companyMasterService.save(companyMaster);

			companyMasterService.saveCompanyMaster(companyMaster,user.getTanentID(),Constants.DEACTIVE);

		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_COMAPANY_MASTER);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_COMAPANY_MASTER);
	}

	/**
	 * Created By: Nitin Patel. Date:12-01-2017 Use to : delete Company Master
	 * row
	 *
	 * @param companyMasterId
	 *            **companyMasterId**.
	 * @param redirect
	 *            **redirectAttrs**.
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_COMAPANY_MASTER, method = RequestMethod.GET)
	public final ModelAndView deleteCompanyMaster(
			@RequestParam(value = "companyMasterId", required = false) final String companyMasterId,
			final RedirectAttributes redirect) {
		try {
			CompanyMaster companyMaster = companyMasterService.getById(Long.valueOf(companyMasterId));
			if (companyMaster != null) {
				//companyMaster.setStatus(Constants.ACTIVE);
				//companyMasterService.update(companyMaster);
				companyMasterService.updateCompanyMaster(companyMaster,Constants.ACTIVE);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong !!!");
			return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_COMAPANY_MASTER);
		}
		redirect.addFlashAttribute(Constants.MESSAGE, "Company Master deleted successfully");
		return new ModelAndView(Constants.REDIRECT + UrlConstants.LIST_COMAPANY_MASTER);
	}
}