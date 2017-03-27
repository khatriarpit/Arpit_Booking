package com.emxcel.dms.portal.controller;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.user.UserPermissionService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.user.UserRole;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;
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

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserRoleController {

    @Inject
    private UserRoleService userRoleService;

    @Inject
    private UserPermissionService userPermissionService;
    

    private static final Logger logger = Logger.getLogger(CarController.class);


    @RequestMapping(value = UrlConstants.USER_ROLE,method = RequestMethod.GET)
    public ModelAndView userRole(@ModelAttribute("command") final UserRole userRole,
                                 final BindingResult result){
    Map<String, Object> map = new HashMap<>();
    List<Permission> listOfPermissions = userPermissionService.list();
    map.put("Permissions", listOfPermissions);
    return new ModelAndView(ViewConstants.USER_ROLE,map);
    }
	
	@RequestMapping(value = UrlConstants.SAVE_USER_ROLE,method = RequestMethod.POST)
    public  ModelAndView saveUserRole(@ModelAttribute("command") final UserRole userRole,final BindingResult result,
                                      final RedirectAttributes redirect            ){
        try {
            if (userRole != null) {
                userRoleService.save(userRole);
                            }
        }
        catch (ServiceException e){
            logger.error(Constants.EXCEPTION_THROW, e);
            redirect.addFlashAttribute(Constants.MESSAGE, "Something Went Wrong");
        }
        return new ModelAndView(Constants.REDIRECT + UrlConstants.USER_ROLE);
    }

}
