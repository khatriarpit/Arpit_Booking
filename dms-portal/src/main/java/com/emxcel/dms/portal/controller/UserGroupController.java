package com.emxcel.dms.portal.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import javax.ws.rs.QueryParam;

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
import com.emxcel.dms.core.business.services.user.UserGroupService;
import com.emxcel.dms.core.business.services.user.UserPermissionService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.model.auth.Group;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

@Controller
public class UserGroupController {

	/**
	 * **Autowired service of userService **.
	 */
	@Inject
	private UserGroupService userGroupService;

	@Inject
	private UserRoleService userroleService;
	
	@Inject
	private UserPermissionService permissionService;

	private static final Logger logger = Logger.getLogger(TripDetailController.class);

	@RequestMapping(value = UrlConstants.USER_GROUP, method = RequestMethod.GET)
	private ModelAndView userGroup(@ModelAttribute("command") final User user, final BindingResult result) {
		Map<String, Object> map = new HashMap<>();

		List<UserRole> userroles = userroleService.list();
		map.put("userroles", userroles);

		List<Permission> userpermission = permissionService.list();
		map.put("userpermission", userpermission);

		return new ModelAndView(ViewConstants.USER_GROUP, map);
	}

	@RequestMapping(value = UrlConstants.SAVE_USER_GROUP, method = RequestMethod.POST)
	private ModelAndView saveUserGroup(@ModelAttribute("command") final Group group, final BindingResult result,
			final HttpSession session,final RedirectAttributes redirect ,@RequestParam("groupName") String groupName,@RequestParam ("roles") List<String> roles,@RequestParam("permissions") List<String> permission) throws ServiceException {
		System.out.println(roles.size());
		group.setName(groupName);
		User userSession = (User) session.getAttribute("user");
		Map<String, Object> map = new HashMap<>();
		Set<UserRole> userRoles=new HashSet<>();
		if(groupName != null && roles != null && permission !=null){
		for(String roleId:roles){
			userRoles.add(userroleService.getById(Long.valueOf(roleId)));
		}
		Set<Permission> permissionset=new HashSet<Permission>();
		for(String permissionId:permission){
			permissionset.add(permissionService.getById(Long.valueOf(permissionId)));
		}
		group.setRoles(userRoles);

		group.setPermissions(permissionset);
			userGroupService.save(group);
			if (group.getId() != null) {
				redirect.addFlashAttribute(Constants.MESSAGE, "User Group Successfully created !!!");
			} else {
				redirect.addFlashAttribute(Constants.MESSAGE, "User Group Successfully updated !!!");
			}
		}else {
			redirect.addFlashAttribute(Constants.MESSAGE,"Please enter valid Values !!!");
		}
		List<UserRole> userroles = userroleService.list();
		List<Permission> permissionlist=permissionService.list();
		map.put("userroles", userroles);
		map.put("permissions", permissionlist);
		return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_GROUP);
	}
}
