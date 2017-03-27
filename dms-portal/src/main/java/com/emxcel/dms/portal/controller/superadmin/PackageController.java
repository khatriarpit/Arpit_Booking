package com.emxcel.dms.portal.controller.superadmin;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.services.superadmin.PackageCreateService;
import com.emxcel.dms.core.business.services.superadmin.PackageService;
import com.emxcel.dms.core.model.superadmin.PackageModel;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Jimit Patel Date: 3/1/2017
 */
@Controller
public class PackageController {

	private static final Logger logger = Logger.getLogger(PackageController.class);
	/**
	 * PackageService.
	 */
	@Inject
	private PackageService packageService;

	/**
	 * PackageCreateService.
	 */
	@Inject
	private PackageCreateService packageCreateService;

	/**
	 * Created By: Jimit Patel Date: 3-1-2017. Use:To show thw list of packages
	 *
	 * @param packageModel
	 * @param result
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.PACKAGE_LIST, method = RequestMethod.GET)
	private ModelAndView listofPackage(@ModelAttribute("command") final PackageModel packageModel,
			final BindingResult result,final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		try {
			model.put("PackageDTL", packageService.list());
		}
		catch (Exception e){
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE, Constants.WRONG);
		}
		return new ModelAndView(ViewConstants.LIST_PACKAGE, model);
	}

	/**
	 * Crated By:Jimit Patel Date:3-1-2017. Use: TO add new Packages
	 *
	 * @param packageModel
	 * @param result
	 * @param packageId
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.PACKAGE, method = RequestMethod.GET)
	private ModelAndView newPackage(@ModelAttribute("command") PackageModel packageModel, final BindingResult result,
			@RequestParam(value = "packageId", required = false) String packageId, final RedirectAttributes ra) {
		Map<String, Object> model = new HashMap<>();
		try {
			if (packageId != null && !"".equals(packageId)) {
				PackageModel packageModelById = packageService.getById(Long.valueOf(packageId));
				if (packageModel != null) {
					model.put("packages", packageModelById);
					model.put("amount1", Integer.toString(packageModelById.getCars() * packageModelById.getCarrate()));
					model.put("amount2",
							Integer.toString(packageModelById.getUsers() * packageModelById.getUserrate()));
					model.put("amount3",
							Integer.toString(packageModelById.getDrivers() * packageModelById.getDriverrate()));
				} else {
					ra.addFlashAttribute(Constants.MESSAGE, Constants.WRONG);
					return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE,Constants.WRONG);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
		}
		return new ModelAndView(ViewConstants.NEW_PACKAGE, model);
	}

	/**
	 * Created By: Jimit Patel Date:3-1-2017 Use:To Save Package Detail and edit
	 *
	 * @param packageModel
	 * @param result
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SAVE_PACKAGE, method = RequestMethod.POST)
	private ModelAndView savepackage(@ModelAttribute("command") final PackageModel packageModel,
			final BindingResult result, final RedirectAttributes redirectAttrs) {
		try {
			PackageModel packageName = packageService.checkPackageNameWithOutStatusCheck(packageModel.getName(), packageModel.getId());
			if (packageName != null) {
				redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Package Name is already in the record !!!");
				redirectAttrs.addFlashAttribute("packages", packageModel);
				return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE);
			}
			if (packageModel.getId() != null) {
				packageService.save(packageModel);
				redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Package Updated succesfully !!!");
			} else {
				packageService.saveStatus(packageModel);
				packageService.save(packageModel);
				redirectAttrs.addFlashAttribute(Constants.MESSAGE, "Package Save succesfully !!!");
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			redirectAttrs.addFlashAttribute(Constants.MESSAGE, Constants.WRONG);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
		}

		return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
	}

	/**
	 * Created: Jimit Patel Date:3-1-2017 Use: To delete Packages from database
	 *
	 * @param packageModel
	 * @param result
	 * @param packageId
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.DELETE_PACKAGE, method = RequestMethod.GET)
	private ModelAndView deletePackage(@ModelAttribute("command") PackageModel packageModel, final BindingResult result,
			@RequestParam(value = "packageId", required = false) String packageId, final RedirectAttributes ra) {
		try {
			if (packageId != null && !"".equals(packageId)) {
				PackageModel packageModelById = packageService.getById(Long.valueOf(packageId));
				if (packageModelById != null) {
					int tenantPackageSize = packageCreateService.checkPackageName(packageModelById.getName());
					if (tenantPackageSize > 0) {
						ra.addFlashAttribute(Constants.MESSAGE,
								packageModelById.getName() + " package is already assigned to Tenant !!!");
						return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
					} else {
						packageService.delete(packageModelById);
					}
				}
			} else {
				ra.addFlashAttribute(Constants.MESSAGE, "No Record Found !!!");
				return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			ra.addFlashAttribute(Constants.MESSAGE,Constants.WRONG);
			return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
	}

	/**
	 * Created For : get Model by id from Package Model bean
	 *
	 * @param tenantPackage
	 * @param packageID
	 * @return PackageModel
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.GET_PACKAGE, method = RequestMethod.POST)
	private PackageModel getPackage(@RequestParam(value = "packageName", required = false) final String packageName) {
		try {
			if (packageName != null && !"".equals(packageName)) {
				String[] packageIDAndName = packageName.split(",");
				return packageService.checkPackageName(packageIDAndName[0]);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			return null;
		}
	}

	/**
	 * Created By: Jimit Patel
	 * Use: TO active and Inactive Packages
	 * @param tripId
	 * @param result
	 * @return
	 */
	@RequestMapping(value = UrlConstants.INACTIVE_PACKAGE_MASTER, method = RequestMethod.GET)
	private ModelAndView activeOrInactivePackageMaster(
			@RequestParam(value = "packageId", required = false) final String packageId,
			final RedirectAttributes ra) {
		try {
			if (packageId != null && !("").equals(packageId)) {
				PackageModel packageModel = packageService.getById(Long.valueOf(packageId));
				if (packageModel.getStatus() != Constants.ACTIVE) {
					packageService.setStatusActive(packageModel);
				} else {
					packageService.setStatusInactive(packageModel);
				}
			}
		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			e.printStackTrace();
		}
		return new ModelAndView(Constants.REDIRECT + UrlConstants.PACKAGE_LIST);
	}

}