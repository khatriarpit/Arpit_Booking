package com.emxcel.dms.portal.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.services.auth.UserPackageService;
import com.emxcel.dms.core.business.services.user.PermissionAssignService;
import com.emxcel.dms.core.business.services.user.UserPermissionService;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

@Controller
public class PermissionController {

	/**
	 * UserPermissionService
	 */
	@Inject
	private UserPermissionService userPermissionService;

	/**
	 * PermissionAssignService
	 */
	@Inject
	private PermissionAssignService permissionAssignService;

	/**
	 * UserPackageService
	 */
	@Inject
	private UserPackageService userPackageService;

	/**
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = UrlConstants.PERMISSION_LIST, method = RequestMethod.GET)
	public ModelAndView getPermissionList(@ModelAttribute("command") Permission permission) {
		ModelAndView model = new ModelAndView();
		model.addObject("userPermissionList", userPermissionService.list());
		model.setViewName(ViewConstants.PERMISSION_LIST);
		return model;
	}

	/**
	 * @param permission
	 * @return
	 */
	@RequestMapping(value = UrlConstants.PERMISSION, method = RequestMethod.GET)
	public ModelAndView addPermition(@ModelAttribute("command") Permission permission,
			@RequestParam(value = "id", required = false) String id, final RedirectAttributes ra) {
		ModelAndView model = new ModelAndView();
		try {
			if (id != null && !id.equals("")) {
				permission = userPermissionService.getById(Long.valueOf(id));
				model.addObject("userPermission", permission);
			}
			model.addObject("listOUserPackage", userPackageService.list());
			model.setViewName(ViewConstants.USER_PERMISSION);
		} catch (Exception e) {
			e.printStackTrace();
			ra.addFlashAttribute("message", "Something Went Wrong !!!");
			return new ModelAndView(ViewConstants.PERMISSION_LIST);
		}
		return model;
	}

	/**
	 * @param permission
	 * @param permissionAssign
	 * @param canCreate
	 * @param canExport
	 * @param canRead
	 * @param canRemove
	 * @param canWrite
	 * @param ra
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = UrlConstants.SAVE_PERMISSION, method = RequestMethod.POST)
	public Map<String, String> savePermition(final HttpServletRequest request,
			final HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) session.getAttribute("user");
			Permission permissionNew = null;
			
			String params;
	        params = IOUtils.toString( request.getInputStream());
	        System.out.println(params);
			
			/*Long id = Long.valueOf((String) arrayList.get("id"));*/
			/*
			 * if (permission.getId() != null) {
			 * permission.setTanentID(user.getTanentID()); permissionNew =
			 * userPermissionService.saveReturnEntity(permission); }
			 */
			map.put(Constants.MESSAGE, "save successfully !!!");
		} catch (Exception e) {
			map.put(Constants.MESSAGE, "Data Not Saved !!!");
			return map;
		}
		return map;
	}
}
