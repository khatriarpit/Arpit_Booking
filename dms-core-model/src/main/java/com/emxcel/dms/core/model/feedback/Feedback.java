package com.emxcel.dms.core.model.feedback;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.car.Car;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.driver.Driver;
import com.emxcel.dms.core.model.generic.DMSEntity;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author emxcelsolution
 */
@Entity
@Table(name = "feedback", schema = SchemaConstant.DMS_SCHEMA)
@NamedNativeQueries({
	@NamedNativeQuery(name = "checkFeedbackByCarId", query = "select f.* from feedback f where f.car_id=:id and f.CREATED_DATE between :fromDate and :toDate and f.tanent_id=:tanentID", resultClass = Feedback.class),
	@NamedNativeQuery(name = "checkFeedbackByDriverId", query = "select f.* from feedback f where f.driver_id=:id and f.CREATED_DATE between :fromDate and :toDate and f.tanent_id=:tanentID", resultClass = Feedback.class)
})
public class Feedback extends DMSEntity<Long, Feedback> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "FEEDBACK_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	/**
	 * **drivingRating**.
	 */
	@Column(name = "driving", length = 5,nullable = false)
	private Float drivingRating;

	/**
	 * **driverBehaviourRating**.
	 */
	@Column(name = "driverbehaviour", length = 5,nullable = false)
	private Float driverBehaviourRating;

	/**
	 * **driverTestingRating**.
	 */
	@Column(name = "drivertesting", length = 5,nullable = false)
	private Float driverTestingRating;

	/**
	 * **carConditionRating**
	 */
	@Column(name = "carcondition", length = 5,nullable = false)
	private Float carConditionRating;

	/**
	 * **overallServiceRating**
	 */
	@Column(name = "overallservice", length = 5,nullable = false)
	private Float overallServiceRating;

	/**
	 * **overallServiceRating**
	 */
	@Column(name = "averagerating", length = 5,nullable = false)
	private Float averageRating;

	/**
	 * **remark**.
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * **tripID**.
	 */
	@Column(name = "tripid",nullable = false,length = 100)
	private String tripID;

	@Transient
	private ClientModel clientModel;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "car_id")
	private Car car;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "driver_id")
	private Driver driver;

	/*@Transient
	private Date startDate;

	@Transient
	private Date endDate;*/
	
	@Column(name = "pick_up_date_time")
	private Timestamp pickUpDateTime;
	
	@Column(name = "drop_up_date_time")
	private Timestamp dropDateTime;


	public Timestamp getPickUpDateTime() {
		return pickUpDateTime;
	}

	public void setPickUpDateTime(Timestamp pickUpDateTime) {
		this.pickUpDateTime = pickUpDateTime;
	}

	public Timestamp getDropDateTime() {
		return dropDateTime;
	}

	public void setDropDateTime(Timestamp dropDateTime) {
		this.dropDateTime = dropDateTime;
	}

	

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

	public Float getDrivingRating() {
		return drivingRating;
	}

	public void setDrivingRating(Float drivingRating) {
		this.drivingRating = drivingRating;
	}

	public Float getDriverBehaviourRating() {
		return driverBehaviourRating;
	}

	public void setDriverBehaviourRating(Float driverBehaviourRating) {
		this.driverBehaviourRating = driverBehaviourRating;
	}

	public Float getDriverTestingRating() {
		return driverTestingRating;
	}

	public void setDriverTestingRating(Float driverTestingRating) {
		this.driverTestingRating = driverTestingRating;
	}

	public Float getCarConditionRating() {
		return carConditionRating;
	}

	public void setCarConditionRating(Float carConditionRating) {
		this.carConditionRating = carConditionRating;
	}

	public Float getOverallServiceRating() {
		return overallServiceRating;
	}

	public void setOverallServiceRating(Float overallServiceRating) {
		this.overallServiceRating = overallServiceRating;
	}

	public Float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Float averageRating) {
		this.averageRating = averageRating;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	/*public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}*/

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}
}