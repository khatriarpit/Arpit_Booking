package com.emxcel.dms.core.model.guest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "guest", schema = SchemaConstant.DMS_SCHEMA)
public class Guest extends DMSEntity<Long, Guest> {

	private static final long serialVersionUID = -4347703048422672550L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GUEST_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "company_name",length=100)
	private String cName;

	@Column(name = "person_name", nullable = false, length = 100)
	private String personName;

	@Column(name = "contact_no",unique = true, nullable = false,length = 20)
	private String contactNo;

	@Column(name = "email_id", nullable = false, length = 100)
	private String emailId;

	@Column(name = "password", length = 100)
	private String password;

	@Column(name = "address")
	private String address;
	
	@Column(name = "image")
	private byte[] image;

	@Column(name = "token_id", length = 1000)
	private String tokenID;

	@Column(name = "first_name", length = 50)
	private String fname;

	@Column(name = "last_name", length = 50)
	private String lname;

	@Column(name = "middle_name", length = 25)
	private String mname;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	
}