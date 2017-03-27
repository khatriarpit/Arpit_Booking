package com.emxcel.dms.core.model.client;

import java.sql.Timestamp;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.car.CarType;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.superadmin.RateOfContract;

/**
 * @author Jimit Patel 
 *
 */
@Entity
@Table(name = "prebooking", schema = SchemaConstant.DMS_SCHEMA)
public class PreBooking extends DMSEntity<Long, PreBooking>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6519424679771635092L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PREBOOKING_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "invoice_mode")
	private String invoiceMode;

	@Column(name = "pick_up_location")
	private String pickUpLocation;

	@Column(name = "landmark")
	private String landmark;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "pick_up_date_time")
	private Timestamp pickUpDateTime;

	@Column(name = "drop_location")
	private String drop_location;

	@Column(name = "drop_up_date_time")
	private Timestamp dropDateTime;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "hnk_kms")
	private Double hnkKms;

	@Column(name = "min_kms")
	private Double minkms;

	@Column(name = "additional_kms")
	private Double additionalKms;

	@Column(name = "hnk_hours")
	private Double hnkHours;

	@Column(name = "additional_hours")
	private Double additionalHours;

	@Column(name = "rate")
	private Integer rate;

	@Column(name = "min_rate")
	private Integer minrate;
	
	@Column(name = "driver_allownce")
	private Integer driverAllownce;

	@Column(name = "additional_charges")
	private Integer addcharges;

	@Column(name = "hnk_amount")
	private Integer hnkAmount;

	@Column(name = "grace_Hours")
	private Integer graceHours;

	@Column(name = "status_id")
	private Integer statusID;

	@Column(name = "debit")
	private boolean debit;

	@Column(name = "sourceplace")
	private String sourcePlace;

	@Column(name = "destinationplace")
	private String destinationPlace;

	@Column(name = "snd_price")
	private Integer sndPrice;

	@Column(name = "requested_boking_ID")
	private String requestedBookingId;

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	@Column(name="paymentmode")
	private String paymentMode;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GUEST_ID")
	private Guest guest;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "INVOICE_CATEGORY_ID")
	private InvoicePackage invoicePackage;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "RATE_CONTRACT_ID")
	private RateOfContract rateOfContract;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CAR_TYPE_ID")
	private CarType carType;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CITY_ID")
	private City city;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPANY_MASTER_ID")
	private CompanyMaster companyMaster;

	@Column(name = "noofpassanger")
	private Integer noofpassanger;

	@Column(name = "isfromMobile")
	private boolean fromMobile;

	public String getInvoiceMode() {
		return invoiceMode;
	}

	public void setInvoiceMode(String invoiceMode) {
		this.invoiceMode = invoiceMode;
	}

	public String getPickUpLocation() {
		return pickUpLocation;
	}

	public void setPickUpLocation(String pickUpLocation) {
		this.pickUpLocation = pickUpLocation;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public Timestamp getPickUpDateTime() {
		return pickUpDateTime;
	}

	public void setPickUpDateTime(Timestamp pickUpDateTime) {
		this.pickUpDateTime = pickUpDateTime;
	}

	public String getDrop_location() {
		return drop_location;
	}

	public void setDrop_location(String drop_location) {
		this.drop_location = drop_location;
	}

	public Timestamp getDropDateTime() {
		return dropDateTime;
	}

	public void setDropDateTime(Timestamp dropDateTime) {
		this.dropDateTime = dropDateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getHnkKms() {
		return hnkKms;
	}

	public void setHnkKms(Double hnkKms) {
		this.hnkKms = hnkKms;
	}

	public Double getMinkms() {
		return minkms;
	}

	public void setMinkms(Double minkms) {
		this.minkms = minkms;
	}

	public Double getAdditionalKms() {
		return additionalKms;
	}

	public void setAdditionalKms(Double additionalKms) {
		this.additionalKms = additionalKms;
	}

	public Double getHnkHours() {
		return hnkHours;
	}

	public void setHnkHours(Double hnkHours) {
		this.hnkHours = hnkHours;
	}

	public Double getAdditionalHours() {
		return additionalHours;
	}

	public void setAdditionalHours(Double additionalHours) {
		this.additionalHours = additionalHours;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public Integer getMinrate() {
		return minrate;
	}

	public void setMinrate(Integer minrate) {
		this.minrate = minrate;
	}

	public Integer getAddcharges() {
		return addcharges;
	}

	public void setAddcharges(Integer addcharges) {
		this.addcharges = addcharges;
	}

	public Integer getHnkAmount() {
		return hnkAmount;
	}

	public void setHnkAmount(Integer hnkAmount) {
		this.hnkAmount = hnkAmount;
	}

	public Integer getGraceHours() {
		return graceHours;
	}

	public void setGraceHours(Integer graceHours) {
		this.graceHours = graceHours;
	}

	public Integer getStatusID() {
		return statusID;
	}

	public void setStatusID(Integer statusID) {
		this.statusID = statusID;
	}

	public boolean isDebit() {
		return debit;
	}

	public void setDebit(boolean debit) {
		this.debit = debit;
	}

	public String getSourcePlace() {
		return sourcePlace;
	}

	public void setSourcePlace(String sourcePlace) {
		this.sourcePlace = sourcePlace;
	}

	public String getDestinationPlace() {
		return destinationPlace;
	}

	public void setDestinationPlace(String destinationPlace) {
		this.destinationPlace = destinationPlace;
	}

	public Integer getSndPrice() {
		return sndPrice;
	}

	public void setSndPrice(Integer sndPrice) {
		this.sndPrice = sndPrice;
	}

	public String getRequestedBookingId() {
		return requestedBookingId;
	}

	public void setRequestedBookingId(String requestedBookingId) {
		this.requestedBookingId = requestedBookingId;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public InvoicePackage getInvoicePackage() {
		return invoicePackage;
	}

	public void setInvoicePackage(InvoicePackage invoicePackage) {
		this.invoicePackage = invoicePackage;
	}

	public RateOfContract getRateOfContract() {
		return rateOfContract;
	}

	public void setRateOfContract(RateOfContract rateOfContract) {
		this.rateOfContract = rateOfContract;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public CompanyMaster getCompanyMaster() {
		return companyMaster;
	}

	public void setCompanyMaster(CompanyMaster companyMaster) {
		this.companyMaster = companyMaster;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		 this.id = id;
	}

	public Integer getNoofpassanger() {
		return noofpassanger;
	}

	public void setNoofpassanger(Integer noofpassanger) {
		this.noofpassanger = noofpassanger;
	}


	public Integer getDriverAllownce() {
		return driverAllownce;
	}

	public void setDriverAllownce(Integer driverAllownce) {
		this.driverAllownce = driverAllownce;
	}

	public boolean getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(boolean fromMobile) {
		this.fromMobile = fromMobile;
	}
}
