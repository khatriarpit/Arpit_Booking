package com.emxcel.dms.core.model.client;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.companymaster.CompanyMaster;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;
import com.emxcel.dms.core.model.superadmin.RateOfContract;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by root on 2/23/17.
 */
@Entity
@Table(name = "tripcancelhistory", schema = SchemaConstant.DMS_SCHEMA)
public class TripCancelHistory extends DMSEntity<Long, TripCancelHistory> {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TRIPCANCEL_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Column(name = "invoice_mode")
    private String invoiceMode;

    @Column(name = "pick_up_location")
    private String pickUpLocation;

    @Column(name = "pick_up_lat_long")
    private String pickUpLatLong;

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

    @Column(name = "drop_lat_long")
    private String dropLatLong;

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

    @Column(name = "tripID")
    private String tripId;

    @Column(name = "canceled_ID")
    private String canceledId;

    @Column(name="otp")
    private Integer otp;

    @Column(name="billable_Amount")
    private Double billableAmount;

    @Column(name = "driver_distance")
    private Integer driverDistanceMin;

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
    @JoinColumn(name = "CAR_ID")
    private Car car;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY_ID")
    private City city;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "DRIVER_ID")
    private Driver driver;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_MASTER_ID")
    private CompanyMaster companyMaster;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getCanceledId() {
        return canceledId;
    }

    public void setCanceledId(String canceledId) {
        this.canceledId = canceledId;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Double getBillableAmount() {
        return billableAmount;
    }

    public void setBillableAmount(Double billableAmount) {
        this.billableAmount = billableAmount;
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public CompanyMaster getCompanyMaster() {
        return companyMaster;
    }

    public void setCompanyMaster(CompanyMaster companyMaster) {
        this.companyMaster = companyMaster;
    }

    public String getPickUpLatLong() {
        return pickUpLatLong;
    }

    public void setPickUpLatLong(String pickUpLatLong) {
        this.pickUpLatLong = pickUpLatLong;
    }

    public String getDropLatLong() {
        return dropLatLong;
    }

    public void setDropLatLong(String dropLatLong) {
        this.dropLatLong = dropLatLong;
    }

    public Integer getDriverDistanceMin() {
        return driverDistanceMin;
    }

    public void setDriverDistanceMin(Integer driverDistanceMin) {
        this.driverDistanceMin = driverDistanceMin;
    }
}
