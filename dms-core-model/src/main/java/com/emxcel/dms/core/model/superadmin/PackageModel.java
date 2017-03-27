package com.emxcel.dms.core.model.superadmin;
import javax.persistence.Column;


import javax.persistence.Entity;


import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

import javax.persistence.*;

/**
 * Created by root on 1/3/17.
 */
@Entity@Table(name = "package", schema = SchemaConstant.DMS_SCHEMA)
public class PackageModel extends DMSEntity<Long, PackageModel> {

	/**
	 *
	 */
	private static final long serialVersionUID = 5305370300167739061L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PACKAGEMODEL_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "name")
	private String name;

	/*@Column(name = "packagetype")
	private Integer packageTypeId;*/
	
	@Column(name = "cars")
	private Integer cars;

	@Column(name = "users")
	private Integer users;

	@Column(name = "drivers")
	private Integer drivers;

	@Column(name = "totalamount")
	private Integer totalAmount;

	@Column(name = "validity")
	private Integer validity;

	@Column(name = "status")
	private Integer status;

	@Column(name = "rate_per_car")
	private Integer carrate;

	@Column(name = "rate_per_user")
	private Integer userrate;

	@Column(name = "rate_per_driver")
	private Integer driverrate;

	@Column(name = "additional_charges")
	private Integer additionalCharges;

	@Column(name="defult")
	private Integer defult;

	@Transient
	private Tenant tenant;

	public Integer getDeflaut() {
		return defult;
	}

	public void setDeflaut(Integer deflaut) {
		this.defult = deflaut;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	
	/*public Integer getPackageTypeId() {
		return packageTypeId;
	}

	public void setPackageTypeId(Integer packageTypeId) {
		this.packageTypeId = packageTypeId;
	}
*/
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public void setCars(Integer cars) {
		this.cars = cars;
	}

	public Integer getCars() {
		return cars;
	}

	public void setDrivers(Integer drivers) {
		this.drivers = drivers;
	}

	public Integer getDrivers() {
		return drivers;
	}

	public void setUsers(Integer users) {
		this.users = users;
	}

	public Integer getUsers() {
		return users;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
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

	public Integer getAdditionalCharges() {
		return additionalCharges;
	}

	public void setAdditionalCharges(Integer additionalCharges) {
		this.additionalCharges = additionalCharges;
	}
}
