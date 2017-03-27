package com.emxcel.dms.core.model.invoice;

import java.util.List;

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
@Table(name = "invoice_static", schema = SchemaConstant.DMS_SCHEMA)
public class Invoice_Static extends DMSEntity<Long, Invoice_Static> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4347703048422672550L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "INVOICE_STATIC_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "companyname")
	private String companyName;
	
	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "address3")
	private String address3;

	@Column(name = "companyimage")
	private String image;

	@Column(name = "emailid")
	private String emailid;

	@Column(name = "landlineNo")
	private Long landlineNo;

	@Column(name = "note")
	private String note;
	
	@Column(name = "phoneNo")
	private Long phoneNo;
	
	@Transient
	private List<String> termsConditions;
	
	@Transient
	private MultipartFile imgFile;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmailid() {
		return emailid;
	}

	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	public Long getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(Long landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public List<String> getTermsConditions() {
		return termsConditions;
	}

	public void setTermsConditions(List<String> termsConditions) {
		this.termsConditions = termsConditions;
	}
	
	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}
	
	public MultipartFile getImgFile() {
		return imgFile;
	}
	
}