package com.emxcel.dms.portal.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.ticket.TicketAssignmentHistoryService;
import com.emxcel.dms.core.business.services.ticket.TicketCommentsService;
import com.emxcel.dms.core.business.services.ticket.TicketDepartmentService;
import com.emxcel.dms.core.business.services.ticket.TicketPriorityService;
import com.emxcel.dms.core.business.services.ticket.TicketService;
import com.emxcel.dms.core.business.services.ticket.TicketStatusHistoryService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.ticket.Ticket;
import com.emxcel.dms.core.model.ticket.TicketAssignmentHistory;
import com.emxcel.dms.core.model.ticket.TicketComments;
import com.emxcel.dms.core.model.ticket.TicketDepartmentType;
import com.emxcel.dms.core.model.ticket.TicketPriority;
import com.emxcel.dms.core.model.ticket.TicketStatusHistory;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;
import com.emxcel.dms.portal.constants.UrlConstants;
import com.emxcel.dms.portal.constants.ViewConstants;

import javafx.util.Pair;

/**Use: To manage tickets.
 * @author ADMIN
 */
@Controller
public class TicketController {

	/**Created By: Pratik Soni.
	 * Used for: logging
	 */
	private static final Logger logger = Logger.getLogger(TicketController.class);

	/**Created By: Pratik Soni.
	 * Used for: accessing Ticket services
	 */
	@Inject
	private TicketService ticketService;

	/**Created By: Pratik Soni.
	 * Used for: accessing Ticket Department services
	 */
	@Inject
	private TicketDepartmentService ticketDepartmentService;

	/**Created By: Pratik Soni.
	 * Used for: accessing User services
	 */
	@Inject
	private UserService userService;

	/**Created By: Pratik Soni.
	 * Used for: accessing Ticket Comment Services
	 */
	@Inject
	private TicketCommentsService ticketCommentsService;

	/**Created By: Pratik Soni.
	 * Used for: accessing Ticket Assignment History Services
	 */
	@Inject
	private TicketAssignmentHistoryService ticketAssignmentHistoryService;

	/**Created By: Pratik Soni.
	 * Used for: accessing User Role Services
	 */
	@Inject
	private UserRoleService userRoleService;

	/**Created By: Pratik Soni
	 * Used For: accessing ticket assignment history
	 */
	@Inject
	private TicketStatusHistoryService ticketStatusHistoryService;

	/**Created By: Pratik Soni
	 * Used For: accessing ticket priority
	 */
	@Inject
	private TicketPriorityService ticketPriorityService;

	@Inject
	private TenantService tenantService;



	/**Created By: Pratik Soni.
	 * Used for: View for adding ticket
	 * @param ticketModel **bind model to save result**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.SUPPORT_TIKCKET, method = RequestMethod.GET)
	private ModelAndView getTicketPage(@ModelAttribute("command") final Ticket ticketModel) {
		Map<String, Object> map = new HashMap<>();
		List<TicketDepartmentType> departments = ticketDepartmentService.list();
		map.put("departments", departments);
		return new ModelAndView(ViewConstants.SUPPORT_TIKCKET, map);
	}

	/**Created By: Pratik Soni.
	 * Used for: Save new ticket
	 * @param ticketModel **bind model to save result**
	 * @param re **redirect attribute to save flash message**
	 * @param session **fetch current session**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.GENERATE_SUPPORT_TIKCKET, method = RequestMethod.POST)
	private ModelAndView saveTicket(@ModelAttribute("command") final Ticket ticketModel, final RedirectAttributes re, final HttpSession session) {

		try {
			User user = CommonUtil.getUser(session);
			User user1 = userService.getById(user.getId());
			if (user1 != null) {
				TicketDepartmentType departmentType = ticketDepartmentService.getById(ticketModel.getDepartmentType().getId());
				String initials = departmentType.getDepartmentName().substring(0, 2);
				Long userId = user.getId();
				Long tenantId =	userService.getTenantIdByUserId(userId);
				Tenant tenant = tenantService.getById(tenantId);
				int numberOfCount = ticketService.getNumberOfTicketsByTenantId(tenantId);
				ticketModel.setTicketId(CommonUtil.generateTicketId(tenant.getTanentid(),initials.toUpperCase(),numberOfCount+1));
				ticketModel.setTanentID(user1.getTanentID());
				ticketModel.setUser(user1);
				ticketModel.setDepartmentType(ticketDepartmentService.getById(ticketModel.getDepartmentType().getId()));
				//Ticket Default priority is medium
				ticketModel.setTicketPriority(ticketPriorityService.getTicketPriorityObj(Constants.TICKET_DEFAULT_PRIORITY));
				//By default assign ticket to ticket manager ----roleId = 6

				UserRole role = userRoleService.getUserRole("ROLE_TICKET_HANDLER");
				User ticketManager = userService.getUserByRoleId(role.getId());
				if (ticketManager != null) {
					ticketModel.setAssignedUser(ticketManager);
					ticketService.save(ticketModel);
					re.addFlashAttribute(Constants.MESSAGE, "Ticket Generated Successfully!!");
				} else {
					re.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
				}
			} else {
				re.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			}

		} catch (Exception e) {
			logger.error(Constants.EXCEPTION_THROW, e);
			re.addFlashAttribute(Constants.MESSAGE,e.getCause());
		}
		return new ModelAndView("redirect:" + UrlConstants.VIEW_SUPPORT_TICKET);
	}


	/**Created By: Pratik Soni.
	 * Used for: all the tickets of logged in user
	 * @param ticketModel **bind model to save result**
	 * @param re **redirect attribute to save flash message**
	 * @param session **fetch current session**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.VIEW_SUPPORT_TICKET, method = RequestMethod.GET)
	private ModelAndView getUserTickets(@ModelAttribute("command") final Ticket ticketModel, final HttpSession session, final RedirectAttributes re) {
		Map<String, Object> map = new HashMap<>();
		try {
			User user = CommonUtil.getUser(session);
			List<Ticket> tickets = ticketService.getTickets(user.getId());
			if (!tickets.isEmpty()) {
				map.put("tickets", tickets);
			} else {
				re.addFlashAttribute(Constants.MESSAGE, "No tickets!!");
			}
		} catch (Exception e) {
			re.addFlashAttribute(Constants.MESSAGE, e.getCause());
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return new ModelAndView(ViewConstants.VIEW_SUPPORT_TICKET, map);
	}

	/**Created By: Pratik Soni.
	 * Used for: Ticket Details from ticketid
	 * @param id **ticket id**
	 * @param session **fetch current session**
	 * @param ticketComment **bind model to save result**
	 * @param re **redirect attribute to save flash message**
	 * @throws ServiceException **exception**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.TICKET_DETAILS, method = RequestMethod.GET)
	private ModelAndView getTicketDetails(@RequestParam("ticketId") final String id, final HttpSession session, @ModelAttribute("ticketComments") final TicketComments ticketComment, final RedirectAttributes re) throws ServiceException {
		Map<String, Object> map = new HashMap<>();
		try {
			Ticket ticket = ticketService.getTicketById(Long.valueOf(id));
			UserRole r1 = userRoleService.getUserRole(Constants.TICKET_SALE_ROLE);
			UserRole r2 = userRoleService.getUserRole(Constants.TICKET_ACCOUNTANT_ROLE);
			UserRole r3 = userRoleService.getUserRole(Constants.TICKET_MANAGER_ROLE);
			List<Long> roles = new ArrayList<>();
			roles.add(r1.getId());
			roles.add(r2.getId());
			roles.add(r3.getId());
			List<User> listUsersToAssignTickets = userService.getUsersToAssignTickets1(roles);
			//List<User> listUsersToAssignTickets = userService.getUsersToAssignTickets();
			//Get the role id of logged in user
			User loggedInUser = CommonUtil.getUser(session);
			Long roleId = userService.getUserRoleIdById(loggedInUser.getId());
			UserRole userRole = userRoleService.getById(roleId);
			List<TicketPriority> ticketPriority = ticketPriorityService.getTicketPriority();
			String roleName = userRole.getRole();
			map.put("roleName", roleName);
			map.put("ticket", ticket);
			map.put("usersToAssignTickets", listUsersToAssignTickets);
			map.put("ticketPriority", ticketPriority);
		} catch (Exception ex) {
			map.put(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, ex);
		}
		//code to fetch the ticket details goes here
		return new ModelAndView(ViewConstants.TICKET_DETAILS, map);
	}

	/**Created By: Pratik Soni.
	 * Used for: All ticket of Ticket Manager
	 * @param session **fetch current session**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.VIEW_TICKET_TICKET_HANDLER, method = RequestMethod.GET)
	private ModelAndView getAllTickets(final HttpSession session) {

		Map<String, Object> map = new HashMap<>();
		User user = CommonUtil.getUser(session);
		User user1 = userService.getById(user.getId());
		try {
			if (user1 != null) {
				//check user role - if he is ticket handler, display all the tickets
				Long role = user1.getRoleID();
				List<Ticket> listOfUserTickets;
				if (role == userRoleService.getUserRole(Constants.TICKET_MANAGER_ROLE).getId()) {
					listOfUserTickets = ticketService.getAllTickets();
				} else {
					listOfUserTickets = ticketService.getTicketsByRole(user1);
				}
				map.put("tickets", listOfUserTickets);
			} else {
				map.put(Constants.MESSAGE, "Something went wrong!!");
			}
		} catch (Exception ex) {
			map.put(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, ex);
		}
		return new ModelAndView(ViewConstants.VIEW_TICKET_TICKET_HANDLER, map);
	}

	/**Created By: Pratik Soni.
	 * Used for: Add comment on ticket
	 * @param ticketComments **bind model to save result**
	 * @param session  **fetch current session**
	 * @param re **redirect attribute to save flash message**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.TICKET_COMMENT_SUPPORTER, method = RequestMethod.POST)
	private ModelAndView saveComment(@ModelAttribute("command") final TicketComments ticketComments, final HttpSession session, final RedirectAttributes re) {
		User user = CommonUtil.getUser(session);
		User user1 = userService.getById(user.getId());
		Long ticketId = null;
		try {
			if (user1 != null) {
				ticketComments.setUser(user1);
				Ticket ticket = ticketService.getTicketById(ticketComments.getTicket().getId());
				ticketComments.setTicket(ticket);
				ticketCommentsService.save(ticketComments);
				re.addFlashAttribute(Constants.MESSAGE, "Comment Added Successfully!!");
				ticketId = ticket.getId();
			} else {
				re.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			}
		} catch (Exception ex) {
			re.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, ex);
		}
		return new ModelAndView("redirect:" + UrlConstants.TICKET_DETAILS + "?ticketId=" + ticketId);
	}

	/**Created By: Pratik Soni.
	 * Used for: Assign ticket to other user
	 * @param ticketModel **bind model to save result**
	 * @param session **fetch current session**
	 * @param ra **redirect attribute to save flash message**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.ASSIGN_TICKET, method = RequestMethod.POST)
	private ModelAndView updateTicketAssignee(@ModelAttribute("command") final Ticket ticketModel, final HttpSession session, final RedirectAttributes ra) {
		//code for assigning the ticket to new user
		Long ticketId = null;
		try {
			Ticket ticketModel1 = ticketService.getTicketById(ticketModel.getId());
			ticketId = ticketModel1.getId();
			ticketModel1.setAssignedUser(userService.findById(ticketModel.getAssignedUser().getId()));
			ticketService.update(ticketModel1);
			TicketAssignmentHistory ticketAssignmentHistory = new TicketAssignmentHistory();
			//Get the assignee user
			User user = CommonUtil.getUser(session);
			ticketAssignmentHistory.setAssignedByuser(userService.findById(user.getId()));
			ticketAssignmentHistory.setAssignedToUser(userService.findById(ticketModel.getAssignedUser().getId()));
			ticketAssignmentHistory.setTicket(ticketService.getTicketById(ticketModel1.getId()));
			ticketAssignmentHistoryService.save(ticketAssignmentHistory);
			ra.addFlashAttribute(Constants.MESSAGE, "User Assignment Successful.");
		} catch (ServiceException e) {
			ra.addFlashAttribute(Constants.MESSAGE, e.getCause());
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return new ModelAndView("redirect:" + UrlConstants.TICKET_DETAILS + "?ticketId=" + ticketId);
	}
	/**Created By: Pratik Soni.
	 * Used for: View ticket Assignment History
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.VIEW_TICKET_ASSIGNMENT_HISTORY, method = RequestMethod.GET)
	private ModelAndView getTicketAssignmentHistory() {
		List<TicketAssignmentHistory> listTicketAssignmentHistoryList = ticketAssignmentHistoryService.list();
		Map<String, Object> map = new HashMap<>();
		map.put("ticketAssignmentHistoryList", listTicketAssignmentHistoryList);
		return new ModelAndView(ViewConstants.VIEW_TICKET_ASSIGNMENT_HISTORY, map);
	}

	/**Created By: Pratik Soni.
	 * Used for:Update ticket status
	 * @param ticketModel **bind model to save result**
	 * @param session **fetch current session**
	 * @param ra **redirect attribute to save flash message**
	 * @return ModelAndView
	 * @throws ServiceException **exception**
	 */
	@RequestMapping(value = UrlConstants.UPDATE_STATUS, method = RequestMethod.POST)
	private ModelAndView updateTicketStatus(@ModelAttribute("command") final Ticket ticketModel, final HttpSession session, final RedirectAttributes ra) throws ServiceException {
		Long ticketId = null;
		try {
			User user = CommonUtil.getUser(session);
			User user1 = userService.getById(user.getId());
			Long role = user1.getRoleID();
			Ticket ticketModel1 = ticketService.getTicketById(ticketModel.getId());
			ticketId = ticketModel1.getId();
			//Get the last status of ticket
			int ticketStatus = ticketModel1.getStatus();
			if (((role == userRoleService.getUserRole(Constants.TICKET_MANAGER_ROLE).getId()) || (role == userRoleService.getUserRole(Constants.TICKET_ACCOUNTANT_ROLE).getId()) || (role == userRoleService.getUserRole(Constants.TICKET_SALE_ROLE).getId())) && (ticketStatus == Constants.OPEN_TICKET)) {
				ticketModel1.setStatus(Constants.RESOLVED_TICKET);		//Resolved.
				TicketStatusHistory ticketStatusHistoryModel = new TicketStatusHistory();
				ticketStatusHistoryModel.setTicket(ticketModel1);
				ticketStatusHistoryModel.setAssignedToUser(ticketModel1.getAssignedUser());
				ticketStatusHistoryModel.setFromStatus(Constants.OPEN_TICKET);
				ticketStatusHistoryModel.setToStatus(Constants.RESOLVED_TICKET);
				ticketStatusHistoryService.save(ticketStatusHistoryModel);
			}
			else if ((role == userRoleService.getUserRole(Constants.USER_ROLE_ADMIN).getId()) && (ticketStatus == Constants.RESOLVED_TICKET)) {
				ticketModel1.setStatus(Constants.CLOSE_TICKET);		//Resolved -- Close
				TicketStatusHistory ticketStatusHistoryModel = new TicketStatusHistory();
				ticketStatusHistoryModel.setTicket(ticketModel1);
				ticketStatusHistoryModel.setAssignedToUser(ticketModel1.getAssignedUser());
				ticketStatusHistoryModel.setFromStatus(Constants.RESOLVED_TICKET);
				ticketStatusHistoryModel.setToStatus(Constants.CLOSE_TICKET);
				ticketStatusHistoryService.save(ticketStatusHistoryModel);

			}
			else if ((role == userRoleService.getUserRole(Constants.USER_ROLE_ADMIN).getId()) && (ticketStatus == Constants.OPEN_TICKET)) {
				ticketModel1.setStatus(Constants.CLOSE_TICKET);		//Open -- Close
				TicketStatusHistory ticketStatusHistoryModel = new TicketStatusHistory();
				ticketStatusHistoryModel.setTicket(ticketModel1);
				ticketStatusHistoryModel.setAssignedToUser(ticketModel1.getAssignedUser());
				ticketStatusHistoryModel.setFromStatus(Constants.OPEN_TICKET);
				ticketStatusHistoryModel.setToStatus(Constants.CLOSE_TICKET);
				ticketStatusHistoryService.save(ticketStatusHistoryModel);
			} else {
				//ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			}
			ra.addFlashAttribute(Constants.MESSAGE, "Status Updated Successfully");
			ticketService.update(ticketModel1);

		} catch (Exception ex) {
			ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, ex);
		}
		return new ModelAndView("redirect:" + UrlConstants.TICKET_DETAILS + "?ticketId=" + ticketId);
	}


	/**Created By: Pratik Soni.
	 * Used for:Add ticket department type
	 * @param ticketDepartmentType **bind model to save result**
	 * @param session **fetch current session**
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.ADD_DEPARTMENT_TYPE, method = RequestMethod.GET)
	private ModelAndView addTicketDepartments(@ModelAttribute("command") final TicketDepartmentType ticketDepartmentType, final HttpSession session) {
		List<TicketDepartmentType> listTicketDepartmentTypes = ticketDepartmentService.list();
		Map<String, Object> map = new HashMap<>();
		map.put("ticketDepartmentTypes", listTicketDepartmentTypes);
		return new ModelAndView(ViewConstants.ADD_DEPARTMENT_TYPE,map);
	}

	/**Created By: Pratik Soni.
	 * Used for: Save ticket department type
	 * @param ticketDepartmentType **bind model to save result**
	 * @param ra **redirect attribute to save flash message**
	 * @return ModelAndView
	 * @throws ServiceException **exception**
	 */
	@RequestMapping(value = UrlConstants.SAVE_DEPARTMENT_TYPE, method = RequestMethod.POST)
	private ModelAndView saveTicketDepartment(@ModelAttribute("command") final TicketDepartmentType ticketDepartmentType, final RedirectAttributes ra) throws ServiceException {
		if (ticketDepartmentType.getDepartmentName() != null) {
			ticketDepartmentService.save(ticketDepartmentType);
			ra.addFlashAttribute(Constants.MESSAGE, "Department Saved.");
		} else {
			ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
		}
		return new ModelAndView("redirect:" + UrlConstants.VIEW_DEPARTMENT_TYPE);
	}

	/**Created By: Pratik Soni.
	 * Used for: View all the ticket departments
	 * @return ModelAndView
	 */
	@RequestMapping(value = UrlConstants.VIEW_DEPARTMENT_TYPE, method = RequestMethod.GET)
	private ModelAndView getTicketDepartments() {
		List<TicketDepartmentType> listTicketDepartmentTypes = ticketDepartmentService.list();
		Map<String, Object> map = new HashMap<>();
		map.put("ticketDepartmentTypes", listTicketDepartmentTypes);
		return new ModelAndView(ViewConstants.VIEW_DEPARTMENT_TYPE, map);
	}

	/**Created By: Pratik Soni.
	 * Used for: Delete department type
	 * @param id **Department ID**
	 * @param ra **redirect attribute to save flash message**
	 * @return ModelAndView
	 * @throws ServiceException **exception**
	 */
	@RequestMapping(value = UrlConstants.DELETE_DEPARTMENT_TYPE, method = RequestMethod.GET)
	private ModelAndView deleteTicketDepartmentType(@RequestParam("id") final String id, final RedirectAttributes ra) throws ServiceException {
		try {
			//check weather this department is assigned any ticket or not
			//if not, then delete the ticket.
			TicketDepartmentType ticketDepartmentType = ticketDepartmentService.getById(Long.valueOf(id));
			List<Ticket> tickets = ticketService.getTicketByDepartmentType(ticketDepartmentType);
			if (tickets.isEmpty()) {
				ticketDepartmentService.delete(ticketDepartmentType);
				ra.addFlashAttribute(Constants.MESSAGE, "Deleted Successfully");
			} else {
				//Can't delete --- a ticket is associated with this department
				ra.addFlashAttribute(Constants.MESSAGE, "Ticket is associated with this type. Can't Delete!!");
			}
		} catch (Exception e) {
			ra.addFlashAttribute(Constants.MESSAGE, e.getCause());
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return new ModelAndView("redirect:" + UrlConstants.VIEW_DEPARTMENT_TYPE);
	}

	/**Created By: Pratik Soni.
	 * Used for: Open closed ticket
	 * @param ticketModel **bind model to save result**
	 * @param ra **redirect attribute to save flash message**
	 * @return ModelAndView
	 * @throws ServiceException **Exception**
	 */
	@RequestMapping(value = UrlConstants.OPEN_CLOSED_TICKET, method = RequestMethod.POST)
	private ModelAndView updateTicketStatus(@ModelAttribute("command") final Ticket ticketModel, final RedirectAttributes ra) throws ServiceException {
		Long ticketId = null;
		try {
			Ticket ticketModel1 = ticketService.getTicketById(ticketModel.getId());
			ticketId = ticketModel1.getId();
			ticketModel1.setStatus(Constants.OPEN_TICKET);		//Resolved-Open
			ticketService.update(ticketModel1);
			TicketStatusHistory ticketStatusHistoryModel = new TicketStatusHistory();
			ticketStatusHistoryModel.setTicket(ticketModel1);
			ticketStatusHistoryModel.setAssignedToUser(ticketModel1.getAssignedUser());
			ticketStatusHistoryModel.setFromStatus(Constants.RESOLVED_TICKET);
			ticketStatusHistoryModel.setToStatus(Constants.OPEN_TICKET);
			ticketStatusHistoryService.save(ticketStatusHistoryModel);
		} catch (Exception ex) {
			ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, ex);
		}
		return new ModelAndView("redirect:" + UrlConstants.TICKET_DETAILS + "?ticketId=" + ticketId);
	}


	/*This will display all the closed tickets.-------------Not in use for now.
	private ModelAndView viewCloseTickets(){
		List<Ticket> ticketModel = ticketService.getClosedTickets();
		return null;
	}*/
	@RequestMapping(value = UrlConstants.UPDATE_TICKET_PRIORITY, method = RequestMethod.POST)
	private ModelAndView updateTicketPriority(@ModelAttribute("command") final Ticket ticketModel, final HttpSession session, final RedirectAttributes ra){

		Ticket ticketModel1 = ticketService.getTicketById(ticketModel.getId());

		ticketModel1.setTicketPriority(ticketPriorityService.getById(ticketModel.getTicketPriority().getId()));
		Long ticketId = null;
		try {
			ticketService.update(ticketModel1);
			ra.addFlashAttribute(Constants.MESSAGE, "Ticket priority updated successfully");
			ticketId = ticketModel1.getId();
		} catch (ServiceException e) {
			ra.addFlashAttribute(Constants.MESSAGE, "Something went wrong!!");
			logger.error(Constants.EXCEPTION_THROW, e);
		}
		return new ModelAndView("redirect:" + UrlConstants.TICKET_DETAILS + "?ticketId=" + ticketId);
	}

	@RequestMapping(value = UrlConstants.USER_TICKET_STATUS,method = RequestMethod.GET)
	private ModelAndView getUserTicketStatus(final HttpSession session) {

		User user = CommonUtil.getUser(session);
		User user1 = userService.getById(user.getId());
		List<TicketStatusHistory> tsh = ticketStatusHistoryService.TicketStatusFromUserId(user1.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("tsh", tsh);
		return new ModelAndView(ViewConstants.USER_TICKET_STATUS, map);
	}
	@RequestMapping(value = UrlConstants.TICKET_TIMELINE,method = RequestMethod.GET)
	private ModelAndView getTicketTimeline(@RequestParam("ticketId") final String id) {
		Ticket ticket = ticketService.getTicketById(Long.valueOf(id));
		List<TicketStatusHistory> ticketStatusHistories = ticketStatusHistoryService.TicketStatusFromTicketId(Long.valueOf(id));
		List<TicketComments> ticketComments = ticketCommentsService.TicketCommentsFromTicketId(Long.valueOf(id));
		int tshLength = ticketStatusHistories.size();
		int tcLength = ticketComments.size();

		Map<String, Object> map = new HashMap<>();
		List<Object> myList  = new ArrayList<>();
		Pair<String, Object> simplePair1 = new Pair<>("ticket", ticket);
		myList.add(simplePair1);
		int index = 0, commentFlag = 0, commentStart = 0, statusStart = 0;
		if(tcLength==0 && tshLength==0) {
			map.put("list", myList);
			//return list
		} else if(tcLength == 0) {
			//return status list
			while(index<tshLength){
				Pair<String, Object> simplePair = new Pair<>("status", ticketStatusHistories.get(index));
				myList.add(simplePair);
				index++;
			}
			map.put("list", myList);
			System.out.println(map.get("list"));
		} else if(tshLength ==0) {
			//return comment list
			while(index<tcLength){
				Pair<String, Object> simplePair = new Pair<>("comment", ticketComments.get(index));
				myList.add(simplePair);
				index++;
			}
			map.put("list", myList);
		} else {
			String d1,d2;
				while(index<tcLength+tshLength){

						d1 = ticketStatusHistories.get(statusStart).getCreatedDate().toString();
						d2 = ticketComments.get(commentStart).getCreatedDate().toString();


					SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
					SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
					try{

						Date dateOne = s1.parse(d1);
						Date dateTwo = s2.parse(d2);

						if(dateOne.compareTo(dateTwo)==-1) {

							//add d1
							Pair<String, Object> simplePair = new Pair<>("status", ticketStatusHistories.get(statusStart));
							myList.add(simplePair);
							if(statusStart<tshLength-1)
								statusStart++;
							else{
								commentFlag = 1;
								break;
							}
						} else {

							//add d2
							Pair<String, Object> simplePair = new Pair<>("comment", ticketComments.get(commentStart));
							myList.add(simplePair);
							if(commentStart<tcLength-1)
								commentStart++;
							else {
								commentFlag =2;
								break;
							}
						}
						index++;
					}catch (Exception ex){}
				}
				if(commentFlag==1) {
					while(commentStart<tcLength){
						Pair<String, Object> simplePair = new Pair<>("comment", ticketComments.get(commentStart));
						myList.add(simplePair);
						commentStart++;
					}
				} else if(commentFlag==2){
					while(statusStart<tshLength) {
						Pair<String, Object> simplePair = new Pair<>("status", ticketStatusHistories.get(statusStart));
						myList.add(simplePair);
						statusStart++;
					}
				}
				map.put("list", myList);

		}

		return new ModelAndView(ViewConstants.TICKET_TIMELINE,map);
	}
}

