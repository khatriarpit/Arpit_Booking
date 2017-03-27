package com.emxcel.dms.core.business.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.user.LoginLogoutLogModel;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author emxcelsolution
 *
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get user details by
	 * username or email
	 * 
	 * @param value
	 *            **value**.
	 * @return model
	 */
	@Query("SELECT u from User u where (u.username = :value or " + "u.emailId=:value) and u.enabled=0")
	User getUserDetailByUsernameOrEmail(@Param("value") String value);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get user role by role id
	 * 
	 * @param roleId
	 *            **roleId**.
	 * @return Model
	 */
	@Query("SELECT u from User u where u.username = ? and u.enabled=0")
	UserRole getUserRoleByID(int roleId);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get password by
	 * username.
	 * 
	 * @param userName
	 *            **username**.
	 * @return String
	 */
	@Query("SELECT u.password from User u where u.username=? ")
	String getPasswordByUsername(String userName);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get user no by username
	 * 
	 * @param un
	 *            **un**.
	 * @return User
	 */
	@Query("SELECT u from User u where u.username = ?")
	User getUserNo(String un);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get logout log by user
	 * role
	 * 
	 * @param role
	 *            **role**.
	 * @return List
	 */
	@Query("SELECT l from LoginLogoutLogModel l where l.role = :role")
	List<LoginLogoutLogModel> getLogoutLog(@Param("role") String role);

	@Query("select l from LoginLogoutLogModel l where l.createdBy != :createdBy and l.tanentID = :tanentID")
	List<LoginLogoutLogModel> getLogoutLogbytenant(@Param("createdBy") String createdDate, @Param("tanentID") Long tanentID);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: list of user by tanent
	 * id.
	 * 
	 * @param tanentID
	 *            **tanentID**.
	 * @return List of User
	 */
	@Query("SELECT u from User u where u.tanentID = :tanentID and u.roleID !='1'")
	List<User> listOfUserByTanentID(@Param("tanentID") Long tanentID);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check login by email
	 * 
	 * @param emailID
	 *            **emailIDorContactNo**.
	 * @return User
	 */
	@Query("select u from User u where emailId= :emailID")
	User loginEmail(@Param("emailID") String emailID);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check login by email
	 *
	 * @param contactNo
	 *            **emailIDorContactNo**.
	 * @return User
	 */
	@Query("select u from User u where u.contactNo= :contactNo")
	User loginEmailByContactNo(@Param("contactNo") Long contactNo);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check login by email
	 * and password
	 * 
	 * @param email
	 *            **email**.
	 * @param password
	 *            **password**.
	 * @return User
	 */
	@Query("select u from User u where emailId=? and password=?")
	User loginEmail(String email, String password);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: update user details by
	 * email id
	 * 
	 * @param emailId
	 *            **emailId**.
	 * @param fullName
	 *            **fullName**.
	 * @param password
	 *            **password**.
	 * @param tokenId
	 *            **tokenId**.
	 * @param middleName
	 *            **middleName**.
	 * @param lastName
	 *            **lastName**.
	 * @param firstName
	 *            **firstName**.
	 */
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.fullName= :fullName,u.password = :password,"
			+ "u.tokenId=:tokenId,u.lastName =:lastName," + "u.firstName= :firstName,u.middleName= :middleName "
			+ "WHERE u.emailId = :emailId")
	void updateUserDetailByEmail(@Param("emailId") String emailId, @Param("fullName") String fullName,
			@Param("password") String password, @Param("tokenId") String tokenId,
			@Param("middleName") String middleName, @Param("lastName") String lastName,
			@Param("firstName") String firstName);

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
	@Modifying(clearAutomatically = true)
	@Query("UPDATE User u SET u.fullName= :fullName, u.contactNo = :contactNo, u.password = :password, u.mobileToken = :mobileToken WHERE u.emailId = :emailId")
	void updateUserDetailByEmail(@Param("fullName") String fullName, @Param("emailId") String emailId,
			@Param("contactNo") String contactNo, @Param("password") String password, @Param("mobileToken") String mobileToken);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: change password by email
	 * id
	 * 
	 * @param email
	 *            **email**.
	 * @param password
	 *            **password**.
	 */
	@Modifying
	@Query("update User set password=:password where emailId=:email")
	void changePasswordByEmail(@Param("email") String email, @Param("password") String password);

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017 Use: change password by email
	 * id
	 *
	 * @param contactNo
	 *            **contactNo**.
	 * @param password
	 *            **password**.
	 */
	@Modifying
	@Query("update User set password=:password where contactNo=:contactNo")
	void changePasswordByContactNo(@Param("contactNo") Long contactNo, @Param("password") String password);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: user by tanentId
	 * 
	 * @param tanentID
	 *            **tanentID**.
	 * @return user
	 */
	@Query("select u from User u where u.tanentID=?")
	List<User> userByTanentID(Long tanentID);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: list of user by id and
	 * tanentid
	 * 
	 * @param id
	 *            **id**.
	 * @param tenantID
	 *            **tanentID**.
	 * @return user
	 */
	@Query("select u from User u where u.defaultFlag=:defaultFlag and u.tanentID=:tenantID")
	List<User> listOfUserByIdAndTanentID(@Param("defaultFlag") boolean defaultFlag, @Param("tenantID") Long tenantID);


	@Query("select u from User u where u.defaultFlag=:defaultFlag and u.tanentID =:tanentID and u.username != :username")
	List<User> listOfUserByIdAndTanentIDNotSameUser(@Param("defaultFlag") boolean b, @Param("tanentID") Long tanentID, @Param("username") String username);


	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get company by email id
	 * 
	 * @param emailId
	 *            **emailId**.
	 * @return int
	 */
	@Query("select u from User u where (u.username=:emailId or u.emailId=:emailId)")
	User getCompanyEmailId(@Param("emailId") String emailId);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to get user detail by
	 * username or email
	 * 
	 * @param value
	 *            **value*.
	 * @param id
	 *            **id**.
	 * @return user
	 */
	@Query("SELECT u from User u where (u.username = :value or " + "u.emailId=:value) and u.enabled=0 and u.id != :id")
	User getUserDetailByUsernameOrEmail(@Param("value") String value, @Param("id") Long id);

	/**
	 * Created By : Naresh Banda. Date: 06-02-2017 Use: list Of User By
	 * SuperAdmin
	 * 
	 * @param id
	 *            **id**.
	 * @return List<User>
	 */
	@Query("SELECT u from User u where u.id != :id and u.tanentID IS NULL")
	List<User> listOfUserSuperAdmin(@Param("id") Long id);

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
	@Query("SELECT u from User u where u.id != :id and u.tanentID = :tenantID")
	List<User> listOfUserWithTenantID(@Param("id") Long id, @Param("tenantID") Long tanentID);

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: get logout log by user
	 * role
	 * 
	 * @param role
	 *            **role**.
	 * @return List
	 */
	@Query("SELECT l from LoginLogoutLogModel l where (l.role = :role1 or l.role = :role2)")
	List<LoginLogoutLogModel> getLogoutLog(@Param("role1") String role1, @Param("role2") String role2);

	/**
	 * Created By : Naresh Banda. Date: 07-02-2017 Use : To get User by passing
	 * username
	 * 
	 * @param username
	 * @return
	 */
	@Query("select u from User u where u.username=:username")
	User getUserByUsername(@Param("username") String username);

	@Query("select us from User us where us.tanentID=?")
	User getUserByTenantId(Long tenantId);

	@Query("select u from User u where u.roleID IN (select ur.id from UserRole ur where ur.role = :role1 or ur.role = :role2)")
	List<User> userAdmnList(@Param("role1") String role1, @Param("role2") String role2);

	/**
	 * @param tanentID
	 * @param status
	 * @param id
	 * @return
	 */
	@Query("select count(u.id) from User u where u.tanentID=:tanentID and u.tenantPackageID=:packageID")
	int getUserByTenantIdAndPackageCount(@Param("tanentID") Long tanentID, @Param("packageID") Long packageID);

	//get the user by role
	@Query("select u from User u where u.roleID = ? ")
	User getUserByRoleId(Long roleId);


	@Query("select u from User u where (u.roleID in (:id)) and u.enabled = 0")
	List<User> getUsersToAssignTickets1(@Param("id") List<Long> id);

	@Query("select u from User u where (u.roleID = 4 OR u.roleID = 5 OR u.roleID = 6 OR u.roleID = 7) and u.enabled = 0")
	List<User> getUsersToAssignTickets();

	@Query("select roleID from User u where u.id = :id")
	Long getUserRoleIdById(@Param("id") Long id);


	@Query("SELECT l from LoginLogoutLogModel l where l.createdBy != :createdBy")
	List<LoginLogoutLogModel> getLogoutLogbytenant(@Param("createdBy") String createdDate);


	@Query("SELECT u from User u where u.tanentID = :tanentID")
	User getByIdTanent(@Param("tanentID") Long tanentID);


	@Query("select u from User u where u.tanentID IS NULL and u.defaultFlag=:defaultUser")
	List<User> listOfUserByIdAndTanentID(@Param("defaultUser") boolean defaultUser);

	@Query("select u.tanentID From User u where u.id = :id")
	Long getTenantIdByUserId(@Param("id") Long id);

}