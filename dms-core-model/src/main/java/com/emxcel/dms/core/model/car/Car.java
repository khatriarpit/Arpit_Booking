package com.emxcel.dms.core.model.car;

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

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.generic.DMSEntity;

/**
 * @author Jimit Patel
 */
@Entity
@Table(name = "car", schema = SchemaConstant.DMS_SCHEMA)
@NamedNativeQueries({@NamedNativeQuery(
		name = "getCarAvailableListForAllCarType",
        /*query = "select * from car c where c.id not in (\n" +
				"select cml.CAR_ID from clientmodel cml where \n" +
				"(\n" +
				"    STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') \n" +
				"    between cml.pick_up_date_time and cml.drop_up_date_time\n" +
				"    )    \t\t\t\t\n" +
				"        OR\n" +
				"        (\n" +
				"        STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s')\n" +
				"        between cml.pick_up_date_time and cml.drop_up_date_time\n" +
				"        )\n" +
				"       \n" +
				"   )\n" +
				"   and c.tanent_id=:tanentID"*/
		query = "SELECT c.* FROM car c where  c.id not in " +
				"(" +
				"SELECT cml.car_id FROM clientmodel cml where " +
				"(" +
				"(" +
				"(cml.pick_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				" OR " +
				"(cml.drop_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				" OR " +
				"(" +
				"(cml.pick_up_date_time <= STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') AND  cml.drop_up_date_time >= STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				") and cml.status_id != 4" +
				") and c.tanent_id=:tanentID and c.status='true'",
		resultClass = Car.class
), @NamedNativeQuery(
		name = "getCarAvailableListByParam",
		query = "SELECT c.* FROM car c LEFT JOIN car_type ct ON ct.id = c.carType_ID" +
				" where ct.carType=:carType " +
				" and c.id not in " +
				"(" +
				"SELECT cml.car_id FROM clientmodel cml where " +
				"(" +
				"(" +
				"(cml.pick_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				" OR " +
				"(cml.drop_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				" OR " +
				"(" +
				"(cml.pick_up_date_time <= STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') AND  cml.drop_up_date_time >= STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				") and cml.status_id != 4" +
				") and c.tanent_id=:tanentID and c.status='true'",
		resultClass = Car.class
), @NamedNativeQuery(
		name = "getCarAvailableListByParamTenant",
		query = "SELECT c.*FROM car c LEFT JOIN car_type ct ON ct.id = c.carType_ID" +
				" where ct.carType=:carType " +
				" and c.id not in " +
				"(" +
				"SELECT cml.car_id FROM clientmodel cml where " +
				"(" +
				"(" +
				"(cml.pick_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				" OR " +
				"(cml.drop_up_date_time BETWEEN STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') and STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				" OR " +
				"(" +
				"(cml.pick_up_date_time <= STR_TO_DATE(:picUpDateTime,'%d/%m/%Y %H:%i:%s') AND  cml.drop_up_date_time >= STR_TO_DATE(:dropDate,'%d/%m/%Y %H:%i:%s'))" +
				")" +
				") and cml.status_id != 4" +
				") and c.tanent_id in (:tanentID) and c.status='true'", resultClass = Car.class

)})
public class Car extends DMSEntity<Long, Car> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CAR_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	/**
	 * carType.
	 */
	@Transient
	private CarType carType;

	@Column(name = "carType_ID")
	private Long carTypeId;
/*
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "car_type_id")
	private CarType carType1;*/

	@Column(name = "car_model",length=20,nullable = false)
	private String carModel;

	/**
	 * carName.
	 */
	@Transient
	private CarName carName;

	/*@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "car_name_id")
	private CarName carName1;*/

	@Column(name = "carName_ID")
	private Long carNameId;
	/**
	 * seatingCap.
	 */
	@Column(name = "SEATING_CAP" ,nullable = false)
	private Integer seatingCap;

	@Column(name = "tenant_package_id")
	private Long tenantPackageID;
	/**
	 * month.
	 */
	/*
	 * @Column(name = "MONTH") private String month;
	 *//**
	 * year.
	 *//*
		 * @Column(name = "YEAR") private String year;
		 */
	/**
	 * carNo.
	 */
	@Column(name = "CAR_NO",length = 20)
	private String carNo;
	/**
	 * status.
	 */
	@Column(name = "STATUS" ,nullable = false)
	private String status;
	/**
	 * driId.
	 */
	@Column(name = "DRI_ID")
	private Long driId;

	@Column(name = "COLOR",length=10,nullable =false)
	private String color;

	@Transient
	private Driver driver;
	@Transient
	private Feedback feedback;

	public Feedback getFeedback() {
		return feedback;
	}


	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public CarType getCarType() {
		return carType;
	}

	public void setCarType(CarType carType) {
		this.carType = carType;
	}

	public Long getCarTypeId() {
		return carTypeId;
	}

	public void setCarTypeId(Long carTypeId) {
		this.carTypeId = carTypeId;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public CarName getCarName() {
		return carName;
	}

	public void setCarName(CarName carName) {
		this.carName = carName;
	}

	public Long getCarNameId() {
		return carNameId;
	}

	public void setCarNameId(Long carNameId) {
		this.carNameId = carNameId;
	}

	public Integer getSeatingCap() {
		return seatingCap;
	}

	public void setSeatingCap(Integer seatingCap) {
		this.seatingCap = seatingCap;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDriId() {
		return driId;
	}

	public void setDriId(Long driId) {
		this.driId = driId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/*public CarType getCarType1() {
		return carType1;
	}

	public void setCarType1(CarType carType1) {
		this.carType1 = carType1;
	}*/

	/*public CarName getCarName1() {
		return carName1;
	}

	public void setCarName1(CarName carName1) {
		this.carName1 = carName1;
	}*/

	public Long getTenantPackageID() {
		return tenantPackageID;
	}

	public void setTenantPackageID(Long tenantPackageID) {
		this.tenantPackageID = tenantPackageID;
	}
}