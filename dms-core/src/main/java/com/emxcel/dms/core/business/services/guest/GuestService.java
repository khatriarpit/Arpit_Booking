package com.emxcel.dms.core.business.services.guest;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.guest.Guest;

public interface GuestService extends DMSEntityService<Long, Guest> {

	/**
	 * @param contactNo
	 * 			****.
	 * @return Guest
	 */
	Guest getGuestDetailByContactNo(String contactNo);

	/**
	 * @param contactNo
	 * 			****.
	 * @param tokenID
	 */
	void updateTokenFCMWithContactNo(String contactNo, String tokenID);

	/**
	 * @param email
	 * 			****.
	 * @param password
	 * 		
	 * @return Guest
	 */
	void changePasswordByEmail(String email, String password);

	/**
	 * @param email
	 * @param password
	 * @return Guest
	 */
	Guest loginCheck(String email, String password);

	/**
	 * @param email
	 * @return Guest
	 */
	Guest checkEmail(String email);

	/**
	 * @param emailId
	 * @return Guest
	 */
	Guest getGuestDetailByEmailID(String emailId);

	/**
	 * @return Long
	 */
	Long getLastID();

	/**
	 * @param emailId
	 * @param personName
	 * @param password
	 * @param tokenID
	 * @param mname
	 * @param lname
	 * @param fname
	 */
	void updateGuestDetailByEmail(String emailId, String personName, String password, String tokenID, String mname,
			String lname, String fname);

	Long saveGuest(Guest guest);

	Guest checkemailorcontact(String emailIDOrContactNo);
	
	List<Guest> getContactNumberList(String query);
}
