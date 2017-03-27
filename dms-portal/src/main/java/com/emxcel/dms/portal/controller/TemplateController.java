package com.emxcel.dms.portal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.map.HashedMap;
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
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.templates.EmailTemplateService;
import com.emxcel.dms.core.business.services.templates.SMSTemplateService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.model.templates.EmailTemplate;
import com.emxcel.dms.core.model.templates.SMSTemplate;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

/**
 * @author Vaibhav Tank
 * 
 */

@Controller
public class TemplateController {
	
	@Inject
	private EmailTemplateService emailTemplateService;
	
	@Inject
	private SMSTemplateService smsTemplateService;
	
	@Inject
	private UserService userService;
	
	private static final Logger logger = Logger.getLogger(PreBookingController.class);
	
	/**
	 * @param emailTemplate
	 * @param result
	 * @return
	 */
	@RequestMapping(value = UrlConstants.EMAIL_TEMPLATE, method = RequestMethod.GET)
	private ModelAndView emailtemplate(@ModelAttribute("command") final EmailTemplate emailTemplate){
		Map<String, Object> map = new HashMap<>();
		
		return new ModelAndView(ViewConstants.EMAIL_TEMPLATE_PAGE,map);
	}

	/**
	 * @param emailTemplate
	 * @return
	 */
	@RequestMapping(value = UrlConstants.EMAIL_TEMPLATE_LIST, method = RequestMethod.GET)
	private ModelAndView emailtemplatelist(@ModelAttribute("command") final EmailTemplate emailTemplate){
		Map<String, Object> map = new HashMap<>();

		List<EmailTemplate> templateList = emailTemplateService.list();
		map.put("emailTemplateList", templateList);
		return new ModelAndView(ViewConstants.EMAIL_TEMPLATE_LIST,map);
	}
	
	/**
	 * @param emailTemplate
	 * @param result
	 * @param redirect
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value=UrlConstants.SAVE_EMAIL_TEMPLATE, method= RequestMethod.POST)
	private ModelAndView savemailtemplate(@ModelAttribute("command")final EmailTemplate emailTemplate,final BindingResult result,
									RedirectAttributes redirect,final HttpSession session) throws ServiceException{
		Map<String, Object> map = new HashMap<>();
		User user = (User) session.getAttribute("user");
		User userName = userService.getUserDetailByUsernameOrEmail(user.getUsername(), null);
		user.setTanentID(userName.getTanentID());
		if(emailTemplate != null){
			emailTemplate.setTanentID(userName.getTanentID());
			emailTemplateService.save(emailTemplate);
			redirect.addFlashAttribute(Constants.MESSAGE, "Template Successfully created !!");
		}else {
			redirect.addFlashAttribute(Constants.MESSAGE, "Please try again !!");
		}
		return new ModelAndView(Constants.REDIRECT+UrlConstants.EMAIL_TEMPLATE_LIST);
	}
	
	/**
	 * @param smsTemplate
	 * @return
	 */
	@RequestMapping(value=UrlConstants.SMS_TEMPLATE, method = RequestMethod.GET)
	private ModelAndView smstemplate(@ModelAttribute("command") final SMSTemplate smsTemplate){
		Map<String, Object> map = new HashMap<>();
		
		return new ModelAndView(ViewConstants.SMS_TEMPLATE_PAGE,map);
	}
	
	/**
	 * @param smsTemplate
	 * @return
	 */
	@RequestMapping(value = UrlConstants.SMS_TEMPLATE_LIST, method = RequestMethod.GET)
	private ModelAndView smstemplatelist(@ModelAttribute("command") final SMSTemplate smsTemplate){
		Map<String, Object> map = new HashMap<>();

		List<SMSTemplate> templateList = smsTemplateService.list();
		map.put("smsTemplateList", templateList);
		return new ModelAndView(ViewConstants.SMS_TEMPLATE_LIST,map);
	}

	
	/**
	 * @param smsTemplate
	 * @param result
	 * @param redirect
	 * @param session
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value=UrlConstants.SAVE_SMS_TEMPLATE, method= RequestMethod.POST)
	private ModelAndView savesmstemplate(@ModelAttribute("command")final SMSTemplate smsTemplate,final BindingResult result,
									RedirectAttributes redirect,final HttpSession session) throws ServiceException{
		Map<String, Object> map = new HashMap<>();
		User user = (User) session.getAttribute("user");
		User userName = userService.getUserDetailByUsernameOrEmail(user.getUsername(), null);
		user.setTanentID(userName.getTanentID());
		if(smsTemplate != null){
			smsTemplate.setTanentID(userName.getTanentID());
			smsTemplateService.save(smsTemplate);
			redirect.addFlashAttribute(Constants.MESSAGE, "Template Successfully created !!");
		}else {
			redirect.addFlashAttribute(Constants.MESSAGE, "Please try again !!");
		}
		return new ModelAndView(Constants.REDIRECT+	UrlConstants.SMS_TEMPLATE);
	}
}
