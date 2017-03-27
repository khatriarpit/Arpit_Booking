package com.emxcel.dms.core.business.repositories.guest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.model.guest.Guest;

@Transactional
public interface GuestRepository extends JpaRepository<Guest, Long>, GuestRepositoryCustom{

	/**
	 * @param contactNo
	 * @return Guest
	 */
	@Query("select g from Guest g where contactNo=?")
	public Guest getGuestDetailByContactNo(String contactNo);

	/**
	 * @param contactNo
	 * @param tokenID
	 */
	@Modifying
	@Query("update Guest set tokenID=? where contactNo=?")
	public void updateTokenFCMWithContactNo(String contactNo, String tokenID);


	/**
	 * @param email
	 * @param password
	 */
	@Modifying
	@Query("update Guest set password=?1 where emailId=?2")
	public void changePasswordByEmail(String email, String password);
	
	/**
	 * @param email
	 * @param password
	 * @return
	 */
	@Query("select gd from Guest gd where gd.emailId=? and password=?")
	public Guest loginCheck(final String email, final String password);

	/**
	 * @param email
	 * @return
	 */
	@Query("select gd from Guest gd where emailId=?")
	public Guest checkEmail(final String email);

	/**
	 * @param emailId
	 * @return
	 */
	@Query("select g from Guest g where g.emailId=?")
	Guest getGuestDetailByEmailID(String emailId);

	/**
	 * @return
	 */
	@Query("SELECT MAX(id) from Guest")
	Long getLastID();

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Guest g SET g.personName= :personName,g.password = :password,g.tokenID=:tokenID,g.lname =:lname,g.fname= :fname,g.mname= :mname WHERE g.emailId = :emailId")
	public void updateGuestDetailByEmail(String emailId, String personName, String password, String tokenID,
			String mname, String lname, String fname);

	@Query("select g from Guest g where g.emailId=:emailIDOrContactNo OR g.contactNo=:emailIDOrContactNo")
	public Guest checkemailorcontact(@Param("emailIDOrContactNo")String emailIDOrContactNo);


	@Query("select g from Guest g where g.contactNo like %:query%")
	List<Guest> getContactNumberList(@Param("query") String query);
}
