package com.emxcel.dms.core.model.superadmin;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "tenantpackage", schema = SchemaConstant.DMS_SCHEMA)
public class TenantPackage extends DMSEntity<Long, TenantPackage> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TENANTPACKAGE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "packagetype")
	private Long packageType;

	@Transient
	private PackageModel packageModel;

	@Transient
	private Tenant tenant;

	@Column(name = "name")
	private String name;

	@Column(name = "cars")
	private Integer cars;

	@Column(name = "users")
	private Integer users;

	@Column(name = "drivers")
	private Integer drivers;

	@Column(name = "validity")
	private Integer validity;

	@Column(name = "per_unit")
	private Integer per_Unit;

	@Column(name = "rate_per_car")
	private Integer carrate;

	@Column(name = "rate_per_user")
	private Integer userrate;

	@Column(name = "rate_per_driver")
	private Integer driverrate;

	@Column(name = "additional_charges")
	private Integer additionalCharges;

	@Column(name = "totalamount")
	private Integer totalAmount;

	@Column(name = "car_list", length = 500)
	private String carList;

	@Column(name = "driver_list", length = 500)
	private String driverList;

	@Column(name = "old_car_list", length = 500)
	private String oldCarList;

	@Column(name = "old_driver_list", length = 500)
	private String oldDriverList;

	@Column(name = "remarks", length = 500)
	private String remarks;

	@Column(name = "fromDate")
	private Date fromDate;

	@Column(name = "toDate")
	private Date toDate;

	@Transient
	private boolean enableRenew;

	@Column(name = "package_status")
	private Integer packageStatus;

	@Column(name = "status")
	private Integer status;

	@Column(name = "is_email", nullable = false, columnDefinition = "tinyint default false")
	private boolean emailStatus;

	@Column(name = "is_email_flag", nullable = false, columnDefinition = "tinyint default false")
	private boolean emailFlag;

	@Column(name = "is_approved", nullable = false, columnDefinition = "tinyint default false")
	private boolean approvedFlag;

	@Column(name = "is_default", nullable = false, columnDefinition = "tinyint default false")
	private boolean defaultFlag;

	/*
	 * @Column(name = "sbscription_from") private Date subscriptionFrom;
	 * 
	 * @Column(name = "sbscription_to") private Date subscriptionTo;
	 */

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCars() {
		return cars;
	}

	public void setCars(Integer cars) {
		this.cars = cars;
	}

	public Integer getUsers() {
		return users;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	public Integer getDrivers() {
		return drivers;
	}

	public void setDrivers(Integer drivers) {
		this.drivers = drivers;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public Integer getPer_Unit() {
		return per_Unit;
	}

	public void setPer_Unit(Integer per_Unit) {
		this.per_Unit = per_Unit;
	}

	public Integer getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(Integer additionalCharges) {
		this.additionalCharges = additionalCharges;
	}

	public Integer getCarrate() {
		return carrate;
	}

	public void setCarrate(Integer carrate) {
		this.carrate = carrate;
	}

	public Integer getUserrate() {
		return userrate;
	}

	public void setUserrate(Integer userrate) {
		this.userrate = userrate;
	}

	public Integer getDriverrate() {
		return driverrate;
	}

	public void setDriverrate(Integer driverrate) {
		this.driverrate = driverrate;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Long getPackageType() {
		return packageType;
	}

	public void setPackageType(Long packageType) {
		this.packageType = packageType;
	}

	public PackageModel getPackageModel() {
		return packageModel;
	}

	public void setPackageModel(PackageModel packageModel) {
		this.packageModel = packageModel;
	}

	public String getCarList() {
		return carList;
	}

	public void setCarList(String carList) {
		this.carList = carList;
	}

	public String getDriverList() {
		return driverList;
	}

	public void setDriverList(String driverList) {
		this.driverList = driverList;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setPackageStatus(Integer packageStatus) {
		this.packageStatus = packageStatus;
	}

	public Integer getPackageStatus() {
		return packageStatus;
	}

	public boolean getApprovedFlag() {
		return approvedFlag;
	}

	public void setApprovedFlag(boolean approvedFlag) {
		this.approvedFlag = approvedFlag;
	}

	public boolean getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(boolean emailStatus) {
		this.emailStatus = emailStatus;
	}

	public boolean getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(boolean emailFlag) {
		this.emailFlag = emailFlag;
	}

	public boolean isEnableRenew() {
		return enableRenew;
	}

	public void setEnableRenew(boolean enableRenew) {
		this.enableRenew = enableRenew;
	}

	public String getOldCarList() {
		return oldCarList;
	}

	public void setOldCarList(String oldCarList) {
		this.oldCarList = oldCarList;
	}

	public String getOldDriverList() {
		return oldDriverList;
	}

	public void setOldDriverList(String oldDriverList) {
		this.oldDriverList = oldDriverList;
	}

	public boolean getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	/*
	 * public Date getSubscriptionFrom() { return subscriptionFrom; }
	 * 
	 * public void setSubscriptionFrom(Date subscriptionFrom) {
	 * this.subscriptionFrom = subscriptionFrom; }
	 * 
	 * public Date getSubscriptionTo() { return subscriptionTo; }
	 * 
	 * public void setSubscriptionTo(Date subscriptionTo) { this.subscriptionTo
	 * = subscriptionTo; }
	 */
}