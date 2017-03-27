package com.emxcel.dms.core.business.services.user;

import java.security.Timestamp;
import java.util.List;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.superadmin.Tenant;
import com.emxcel.dms.core.model.user.LoginLogoutLogModel;
import com.emxcel.dms.core.model.user.User;

/**
 * @author emxcelsolution
 *
 */
public interface UserService extends DMSEntityService<Long, User> {

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: update user through
	 * username when edit
	 * 
	 * @param userName
	 *            **userName**.
	 * @return String
	 */
	String update(String userName) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: get user detail by
	 * username and id
	 * 
	 * @param username
	 *            **userName**.
	 * @param id
	 *            **id**.
	 * @return User
	 */
	User getUserDetailByUsernameOrEmail(String username, Long id) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: get password through
	 * username.
	 * 
	 * @param username
	 *            **userName**.
	 * @return String
	 */
	String getPasswordByUsername(String username) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: get logoutLog by userrole
	 * 
	 * @param role
	 *            **userName**.
	 * @return List<LoginLogoutLogModel>
	 */
	List<LoginLogoutLogModel> getLogoutLog(String createdBy) throws ServiceException;
	
	List<LoginLogoutLogModel> getLogoutLogbytenant(String createdBy,
			Long tanentID);

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: to find username by
	 * username
	 * 
	 * @param name
	 *            **name**.
	 * @throws ServiceException
	 *             **ServiceException**.
	 */
	void findByUserNameAndLoginSave(String name) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: list of user detail by
	 * tanent id
	 * 
	 * @param tanentID
	 *            **userName**.
	 * @return List<User>
	 */
	List<User> listOfUserByTanentID(Long tanentID) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: get user detail by tanent
	 * id.
	 * 
	 * @param tanentID
	 *            **userName**.
	 * @return User
	 */
	List<User> userByTanentID(Long tanentID) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: find username by user id.
	 * 
	 * @param userId
	 *            **userName**.
	 * @return User
	 */
	User findById(Long userId) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: to check login by email
	 * id.
	 * 
	 * @param emailID
	 *            **userName**.
	 * @return User
	 */
	User loginEmail(String emailID) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: to check login by email
	 * id.
	 *
	 * @param contactNo
	 *            **userName**.
	 * @return User
	 */
	User loginEmailByContactNo(Long contactNo) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: to check login by email
	 * id and password.
	 * 
	 * @param email
	 *            **userName**.
	 * @param password
	 *            **userName**.
	 * @return User
	 */
	User loginEmail(String email, String password) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: update user details by
	 * emailid,fullname,password,tokenid, lastname and firstname
	 * 
	 * @param emailId
	 *            **userName**.
	 * @param fullName
	 *            **userName**.
	 * @param password
	 *            **userName**.
	 * @param tokenId
	 *            **userName**.
	 * @param middleName
	 *            **userName**.
	 * @param lastName
	 *            **userName**.
	 * @param firstName
	 *            **userName**.
	 */
	void updateUserDetailByEmail(String emailId, String fullName, String password, String tokenId, String middleName,
			String lastName, String firstName) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: update user details by
	 * email id
	 * 
	 * @param emailId
	 *            **emailId**.
	 * @param fullName
	 *            **fullName**.
	 * @param contactNo
	 *            **contactNo**.
	 * @param password
	 *            **password**.
	 */
	void updateUserDetailByEmail(String fullName,String emailId,String contactNo,String password,String mobileToken) throws ServiceException;
	
	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: change password by email
	 * id
	 * 
	 * @param email
	 *            **userName**.
	 * @param password
	 *            **userName**.
	 */
	void changePasswordByEmail(String email, String password) throws ServiceException;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: change password by email
	 * id
	 *
	 * @param contactNo
	 *            **contactNo**.
	 * @param password
	 *            **password**.
	 */
	void changePasswordByContactNo(Long contactNo, String password) throws ServiceException;


	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: list of user by id and
	 * tanentId
	 * 
	 * @param createdByID
	 *            **userName**.
	 * @param tenantID
	 *            **userName**.
	 * @return List<User>
	 */
	List<User> listOfUserByIdAndTanentID(boolean defaultUser, Long tenantID) throws ServiceException;

	List<User> listOfUserByIdAndTanentIDNotSameUser(boolean defaultUser, Long tenantID,String username) throws Exception;

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: to check companyEmail id
	 * by emailid and id
	 * 
	 * @param emailid
	 *            **userName**.
	 * @return User
	 */
	User getCompanyEmailId(String emailid) throws ServiceException;

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
	List<User> listOfUserByTanentIDWithOutGivenID(Long id, Long tanentID);

	/**
	 * Created By : Naresh Banda. Date: 07-02-2017 Use : To get User by passing
	 * username
	 * 
	 * @param username
	 * @return
	 */

	User getUserByTenantId(Long long1);

	User getUserByUsername(String username);

	List<User> userAdmnList(final String role1, final String role2) throws ServiceException;

	int getUserByTenantIdAndPackageCount(Long tanentID, Long id) throws ServiceException;

	/**Created By: Pratik Soni.
	 * Used For: Get User by role Id
	 * @param roleId **Role id of User**
	 * @return User
	 * @throws ServiceException *Exception*
	 */
	User getUserByRoleId(Long roleId) throws ServiceException;

	/**Created By: Pratik Soni.
	 * Used For: Get Users to Assign Tickets
	 * @return List of Users
	 * @throws ServiceException *Exception*
	 */

	List<User> getUsersToAssignTickets() throws ServiceException;
	List<User> getUsersToAssignTickets1(List<Long> id) throws ServiceException;

	Long getUserRoleIdById(Long Id) throws ServiceException;

	void  updateUserPassword(String selectId, String userId, String newpassword,
			User selectedUser, String password, String logginUserName);

	 void sendEmail(String username, User user);


	void saveUser(Object object,Long companyId) throws ServiceException;

	void updateUser(String newpassword, User user) throws ServiceException;


	 List<User> listOfUserWithTenantID(Long id, Long tanentID);


	User getByIdTanent(Long id);

	Long getTenantIdByUserId(Long id);
}