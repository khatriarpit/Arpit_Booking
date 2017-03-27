package com.emxcel.dms.core.model.driver;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.state.State;

/**
 * @author emxcelsolution
 *
 */

@Entity
@Table(name = "driver", schema = SchemaConstant.DMS_SCHEMA)
@NamedNativeQueries({
		@NamedNativeQuery(name = "getAvailableDrivers", query = " Select * from driver d where d.id not in " + "("
				+ "SELECT cml.DRIVER_ID FROM clientmodel cml where " + "(" + "("
				+ "(cml.pick_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))"
				+ " OR "
				+ "(cml.drop_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))"
				+ ")" + " OR " + "("
				+ "(cml.pick_up_date_time <= STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') AND  cml.drop_up_date_time >= STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))"
				+ ")" + ") and cml.status_id != 4 and cml.DRIVER_ID IS NOT NULL"
				+ ") and d.tanent_id=:tanentID and d.status='true'", resultClass = Driver.class) })
public class Driver extends DMSEntity<Long, Driver> {

	private static final long serialVersionUID = 5891478805658108595L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "DRIVER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "firstname", nullable = false, length = 50)
	private String firstName;

	@Column(name = "middlename", nullable = false, length = 50)
	private String middleName;

	@Column(name = "lastname", nullable = false, length = 50)
	private String lastName;

	@Column(name = "fullname", nullable = false, length = 150)
	private String fullName;

	@Column(name = "contact", nullable = false, length = 20)
	private String contactNo;

	@Column(name = "address")
	private String address;

	@Column(name = "city_id", nullable = false)
	private Long cityID;

	@Column(name = "state_id", nullable = false)
	private Long stateID;

	@Column(name = "country_id", nullable = false)
	private Long countryID;

	@Column(name = "tenant_package_id")
	private Long tenantPackageID;

	@Transient
	private Feedback feedback;

	public Long getStateID() {
		return stateID;
	}

	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}

	public Long getCountryID() {
		return countryID;
	}

	public void setCountryID(Long countryID) {
		this.countryID = countryID;
	}

	public State getStateDetail() {
		return stateDetail;
	}

	public void setStateDetail(State stateDetail) {
		this.stateDetail = stateDetail;
	}

	public Country getCountryDetail() {
		return countryDetail;
	}

	public void setCountryDetail(Country countryDetail) {
		this.countryDetail = countryDetail;
	}

	@Column(name = "aadhar_card_no", nullable = false, length = 20)
	private String aadharCardNO;

	@Column(name = "license_no", nullable = false, length = 20)
	private String licenseNo;

	@Column(name = "license_no_validity_from")
	private Timestamp licenseNoValidityFrom;

	@Column(name = "license_no_validity_to")
	private Timestamp licenseNoValidityTo;

	@Column(name = "color", nullable = false, length = 10)
	private String color;

	@Column(name = "image", length = 150)
	private String image;

	@Column(name = "adhar_card_image", length = 150)
	private String adharCardImage;

	@Column(name = "license_card_image", length = 150)
	private String licenseImage;

	@Column(name = "otp", length = 50)
	private String otp;

	@Column(name = "token_id")
	private String tokenID;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "password", length = 50)
	private String password;

	@Column(name = "imei_no", nullable = false, length = 20)
	private Long imeino;

	public Long getImeino() {
		return imeino;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setImeino(Long imeino) {
		this.imeino = imeino;
	}

	@Transient
	private MultipartFile imgFile;

	@Transient
	private MultipartFile imgFile1;

	@Transient
	private MultipartFile imgFile2;

	@Transient
	private City cityDetail;

	@Transient
	private State stateDetail;

	@Transient
	private Country countryDetail;

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getCityID() {
		return cityID;
	}

	public void setCityID(Long cityID) {
		this.cityID = cityID;
	}

	public String getAadharCardNO() {
		return aadharCardNO;
	}

	public void setAadharCardNO(String aadharCardNO) {
		this.aadharCardNO = aadharCardNO;
	}

	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public void setAdharCardImage(String adharCardImage) {
		this.adharCardImage = adharCardImage;
	}

	public String getAdharCardImage() {
		return adharCardImage;
	}

	public void setLicenseImage(String licenseImage) {
		this.licenseImage = licenseImage;
	}

	public String getLicenseImage() {
		return licenseImage;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}

	public void setImgFile1(MultipartFile imgFile1) {
		this.imgFile1 = imgFile1;
	}

	public MultipartFile getImgFile1() {
		return imgFile1;
	}

	public void setImgFile2(MultipartFile imgFile2) {
		this.imgFile2 = imgFile2;
	}

	public MultipartFile getImgFile2() {
		return imgFile2;
	}

	public void setCityDetail(City cityDetail) {
		this.cityDetail = cityDetail;
	}

	public City getCityDetail() {
		return cityDetail;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public String getAddress() {
		return address;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public Long getTenantPackageID() {
		return tenantPackageID;
	}

	public void setTenantPackageID(Long tenantPackageID) {
		this.tenantPackageID = tenantPackageID;
	}
}