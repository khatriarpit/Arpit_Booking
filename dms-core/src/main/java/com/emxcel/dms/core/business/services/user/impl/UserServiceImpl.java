package com.emxcel.dms.core.business.services.user.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.repositories.city.CityRepository;
import com.emxcel.dms.core.business.repositories.country.CountryRepository;
import com.emxcel.dms.core.business.repositories.state.StateRepository;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.state.State;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.constants.Constants;
import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.modules.email.Email;
import com.emxcel.dms.core.business.modules.email.HtmlEmailSender;
import com.emxcel.dms.core.business.repositories.email.EmailLogRepository;
import com.emxcel.dms.core.business.repositories.user.UserRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.superadmin.TenantService;
import com.emxcel.dms.core.business.services.user.UserLogService;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.business.services.user.UserService;
import com.emxcel.dms.core.business.utils.CommonUtil;
import com.emxcel.dms.core.model.common.EmailLog;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.user.LoginLogoutLogModel;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author emxcelsolution
 */
@Service("userService")
public class UserServiceImpl extends DMSEntityServiceImpl<Long, User> implements UserService {

	/**
	 * Inject service for UserRoleService.
	 */
	@Inject
	private UserRoleService userRoleService;

	/**
	 * **Autowired service of companyService **.
	 */
	@Inject
	private TenantService companyService;

	/**
	 * Inject service for UserLogRoleService.
	 */
	@Inject
	private UserLogService userLogService;

	/**
	 * Inject service for UserRepository.
	 */
	private UserRepository userRepository;

	/**
	 * **Autowired service of HtmlEmailSender **.
	 */
	@Inject
	private HtmlEmailSender htmlEmailSender;

	@Inject
	private CountryRepository countryRepository;

	@Inject
	private StateRepository stateRepository;

	@Inject
	CityRepository cityRepository;

	/**
	 * @param userRepository
	 *            **userRepository**.
	 */
	@Inject
	public UserServiceImpl(final UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	@Inject
	private EmailLogRepository emailLogRepository;

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: update username by
	 * username
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#update(java.lang.
	 * String)
	 */
	@Override
	public String update(String userName) {
		return userRepository.getPasswordByUsername(userName);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get userdetails by
	 * username or email
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * getUserDetailByUsername(java.lang.String)
	 */
	@Override
	public User getUserDetailByUsernameOrEmail(String username, Long id) {
		if (id != null) {
			return userRepository.getUserDetailByUsernameOrEmail(username, id);
		} else {
			return userRepository.getUserDetailByUsernameOrEmail(username);
		}
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get password by username
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * getPasswordByUsername(java.lang.String)
	 */
	@Override
	public String getPasswordByUsername(String username) {
		return userRepository.getPasswordByUsername(username);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get log out log
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#getLogoutLog(java.
	 * lang.String)
	 */
	@Override
	public List<LoginLogoutLogModel> getLogoutLog(String createdDate) {
		if (createdDate.contains(",")) {
			String[] roleVal = createdDate.split(",");
			if (roleVal != null && !roleVal.equals("")) {
				return userRepository.getLogoutLog(roleVal[0], roleVal[1]);
			}
		} else {
			return userRepository.getLogoutLog(createdDate);
		}
		return null;
	}

	@Override
	public List<LoginLogoutLogModel> getLogoutLogbytenant(String createdBy, Long tanentID) {
		if (tanentID != null) {
			// Tenant
			return userRepository.getLogoutLogbytenant(createdBy, tanentID);
		} else {
			// Superadmin
			return userRepository.getLogoutLogbytenant(createdBy);
		}
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to find by username
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#findByUserName(
	 * java.lang.String)
	 */
	@Override
	public void findByUserNameAndLoginSave(String username) throws ServiceException {
		User user = userRepository.getUserDetailByUsernameOrEmail(username);
		if (user != null) {
			UserRole userRole = userRoleService.getById(user.getRoleID());
			LoginLogoutLogModel llm = new LoginLogoutLogModel();
			llm.setRole(userRole.getRole());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			llm.setLogin_dateTime(timestamp);
			llm.setTanentID(user.getTanentID());
			userLogService.save(llm);
		}
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: list of user by tannetid
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * listOfUserByTanentID(java.lang.Long)
	 */
	@Override
	public List<User> listOfUserByTanentID(Long tanentID) {
		return userRepository.listOfUserByTanentID(tanentID);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to find by userid
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#findById(java.lang
	 * .Long)
	 */
	@Override
	public User findById(Long userId) {
		User userDetail = userRepository.findOne(userId);
		return userDetail;
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check login by email
	 * id
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#loginEmail(java.
	 * lang.String)
	 */
	@Override
	public User loginEmail(String emailID) {
		return userRepository.loginEmail(emailID);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check login by email
	 * id
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#loginEmail(java.
	 * lang.String)
	 */
	@Override
	public User loginEmailByContactNo(Long contactNo) {
		return userRepository.loginEmailByContactNo(contactNo);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use:to check login by email
	 * id and password
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#loginEmail(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public User loginEmail(String email, String password) {
		return userRepository.loginEmail(email, password);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: update user details by
	 * email id
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * updateUserDetailByEmail(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void updateUserDetailByEmail(String emailId, String fullName, String password, String tokenId,
			String middleName, String lastName, String firstName) {
		userRepository.updateUserDetailByEmail(emailId, fullName, password, tokenId, middleName, lastName, firstName);
	}

	/*
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: update user details by
	 * email id
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * updateUserDetailByEmail(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void updateUserDetailByEmail(String fullName, String emailId, String contactNo, String password,String mobileToken) {
		userRepository.updateUserDetailByEmail(fullName, emailId, contactNo, password,mobileToken);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: change password by email
	 * id
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * changePasswordByEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public void changePasswordByEmail(String email, String password) {
		userRepository.changePasswordByEmail(email, password);
	}

	/*
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: change password by email
	 * id
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * changePasswordByEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public void changePasswordByContactNo(Long contactNo, String password) {
		userRepository.changePasswordByContactNo(contactNo, password);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use:user by tanentId
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#userByTanentID(
	 * java.lang.Long)
	 */
	@Override
	public List<User> userByTanentID(Long tanentID) {
		return userRepository.userByTanentID(tanentID);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: list of user by id and
	 * tanentid
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * listOfUserByIdAndTanentID(java.lang.Long, java.lang.Long)
	 */
	@Override
	public List<User> listOfUserByIdAndTanentID(boolean defaultUser, Long tenantID) {
		if(tenantID != null) {
			return userRepository.listOfUserByIdAndTanentID(defaultUser, tenantID);
		} else {
			return userRepository.listOfUserByIdAndTanentID(defaultUser);	
		}
	}

	@Override
	public  List<User> listOfUserByIdAndTanentIDNotSameUser(boolean defaultUser, Long tenantID,String username)  throws Exception{
		return  userRepository.listOfUserByIdAndTanentIDNotSameUser(defaultUser, tenantID,username);
	}

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get company by email id
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#getCompanyEmailId(
	 * java.lang.String, java.lang.Long)
	 */
	@Override
	public User getCompanyEmailId(String emailid) {
		return userRepository.getCompanyEmailId(emailid);
	}

	/**
	 * Created By : Naresh Banda. Date: 06-02-2017 Use: list Of User By TanentID
	 * WithOut GivenID
	 * 
	 * @param id
	 *            **id**.
	 * @param tanentID
	 *            **tanentID**.
	 * @return List<User>
	 */
	@Override
	public List<User> listOfUserByTanentIDWithOutGivenID(Long id, Long tanentID) {
		if (tanentID != null) {
			return userRepository.listOfUserWithTenantID(id, tanentID);
		} else {
			return userRepository.listOfUserSuperAdmin(id);
		}
	}

	/**
	 * Created By : Naresh Banda. Date: 07-02-2017 Use : To get User by passing
	 * username
	 * 
	 * @param username
	 * @return
	 */
	@Override
	public User getUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#getUserByTenantId(
	 * java.lang.Long)
	 */
	@Override
	public User getUserByTenantId(Long long1) {
		return userRepository.getUserByTenantId(long1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#userAdmnList(java.
	 * lang.String, java.lang.String)
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emxcel.dms.core.business.services.user.UserService#
	 * getUserByTenantIdAndPackageCount(java.lang.Long, int, java.lang.Long)
	 */
	@Override
	public int getUserByTenantIdAndPackageCount(Long tanentID, Long id) {
		return userRepository.getUserByTenantIdAndPackageCount(tanentID, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emxcel.dms.core.business.services.user.UserService#userAdmnList(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public List<User> userAdmnList(String role1, String role2) throws ServiceException {
		return userRepository.userAdmnList(role1, role2);
	}

	/**Created By: Pratik Soni.
	 * Used For: Get User Details from the roleID
	 */
	@Override
	public User getUserByRoleId(final Long roleId) {
		return userRepository.getUserByRoleId(roleId);
	}

	/**Created By: Pratik Soni.
     * Used For: Get User List
     * @return *User list*
     */
	@Override
	public List<User> getUsersToAssignTickets1(List<Long> id) {
		return userRepository.getUsersToAssignTickets1(id);
	}

	@Override
	public List<User> getUsersToAssignTickets() {
		return userRepository.getUsersToAssignTickets();
	}

	public Long getUserRoleIdById(Long id) { return userRepository.getUserRoleIdById(id);}

	@Override
	public void updateUserPassword(String selectId, String userId, String newpassword, User selectedUser,
			String password, String logginUserName) {
			 selectedUser.setPassword(newpassword);
			selectedUser.setId(Long.valueOf(selectId));
			try {
				super.update(selectedUser);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
	}

	@Override
	 public void sendEmail(String username, User user) {
		// TODO Auto-generated method stub
		String from  = "";
		String toAddress = "";
		if (user.getTanentID() != null) {
			Tenant company = companyService.getById(user.getTanentID());
			if (company != null) {
				from = company.getCompanyname();
				toAddress = company.getEmailid();
			}
		} else {
			from = Constants.COMPANY_NAME;
			toAddress = Constants.COMPANY_EMAIL_ID;
		}
		String subject = Constants.SUBJECT_FORGETPASS;
		String fromAddress = Constants.COMPANY_EMAIL_ID;
		String template = Constants.CUSTOM_TEMPLATE;
		Map<String, String> map = new HashMap<String, String>();

		String content = "<strong>" + Constants.SUBJECT_FORGETPASS + "</strong><br>";
		content += "<strong>Password :</strong> " + user.getPassword() + "<br><br>";
		map.put("HEADER", subject);
		map.put("CONTENT", content);
		map.put("FOOTER", "Thanks<br>" + from);
		Map<String, Object> mapEmail = CommonUtil.getEmail(subject, from, fromAddress, toAddress, template, map,
				user.getId());
		Email email = (Email) mapEmail.get("email");
		EmailLog emailLog = (EmailLog) mapEmail.get("emailLog");
		emailLogRepository.save(emailLog);
		//emailLog = emailLogService.saveEmailLog(emailLog);
		try {
			htmlEmailSender.send(email, emailLog);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void saveUser(Object object, Long companyId) throws ServiceException {
			Tenant tenant = (Tenant) object;
			Country country = countryRepository.findOne(tenant.getCountry().getId());
			State state = stateRepository.findOne(tenant.getState().getId());
			City city = cityRepository.findOne(tenant.getCity().getId());
			User user = new User();
			user.setTanentID(companyId);
			user.setUsername(tenant.getEmailid());
			user.setFirstName(tenant.getCompanyname());
			user.setLastName(tenant.getCompanyname());
			user.setMiddleName(tenant.getCompanyname());
			user.setEmailId(tenant.getEmailid());
			user.setContactNo(tenant.getContactno());
			user.setPassword(CommonUtil.getPasswordFormat());
			user.setRoleID(userRoleService.getUserRole(Constants.USER_ROLE_ADMIN).getId());
			user.setEnabled(Constants.DEACTIVE);
			user.setCityId(city.getId());
			user.setStateId(state.getId());
			user.setCountryId(country.getId());
			super.save(user);

	}

	@Override
	public void updateUser(String newpassword, User user) throws ServiceException {
			user.setPassword(newpassword);
		super.update(user);
	}


	
	@Override
	public List<User> listOfUserWithTenantID(Long id, Long tanentID) {
			return userRepository.listOfUserWithTenantID(id, tanentID);
	}

	@Override

	public User getByIdTanent(Long id) {
		return userRepository.getByIdTanent(id);
	}


	public Long getTenantIdByUserId(Long id){
		return userRepository.getTenantIdByUserId(id);
	}


}