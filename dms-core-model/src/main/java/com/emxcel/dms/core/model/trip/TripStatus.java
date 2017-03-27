package com.emxcel.dms.core.model.trip;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "trip_status", schema = SchemaConstant.DMS_SCHEMA)
public class TripStatus extends DMSEntity<Long, TripStatus> {

	private static final long serialVersionUID = 8683766777093617097L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TRIP_STATUS_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "car_id")
	private Long carID;

	/**
	 * **driID**.
	 */
	@Column(name = "dri_id")
	private Long driID;

	/**
	 * **tripID**.
	 */
	@Column(name = "trip_id")
	private String tripID;

	/**
	 * **status**.
	 */
	@Column(name = "status", length = 20)
	private String status;

	@Column(name = "is_driver", nullable = false)
	private boolean is_driver;

	@Column(name = "is_client", nullable = false)
	private boolean is_client;

	@Column(name = "otp", length = 50)
	private String otp;

	@Column(name = "read_status", length = 20)
	private boolean readstatus;

	@Column(name = "read_date_time")
	private Timestamp readDateTime;

	@Column(name = "cancel_id")
	private String cancelId;

	@Column(name = "pickup_date")
	private Date pickUpDate;

	@Column(name = "pickup_time")
	private Time pickUpTime;

	@Column(name = "drop_date")
	private Date dropDate;

	@Column(name = "drop_time")
	private Time dropTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCarID() {
		return carID;
	}

	public void setCarID(Long carID) {
		this.carID = carID;
	}

	public Long getDriID() {
		return driID;
	}

	public void setDriID(Long driID) {
		this.driID = driID;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean getIs_driver() {
		return is_driver;
	}

	public void setIs_driver(boolean is_driver) {
		this.is_driver = is_driver;
	}

	public boolean getIs_client() {
		return is_client;
	}

	public void setIs_client(boolean is_client) {
		this.is_client = is_client;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(boolean readstatus) {
		this.readstatus = readstatus;
	}

	public Timestamp getReadDateTime() {
		return readDateTime;
	}

	public void setReadDateTime(Timestamp readDateTime) {
		this.readDateTime = readDateTime;
	}

	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	public Date getPickUpDate() {
		return pickUpDate;
	}

	public void setPickUpDate(Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}

	public Time getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Time pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public Date getDropDate() {
		return dropDate;
	}

	public void setDropDate(Date dropDate) {
		this.dropDate = dropDate;
	}

	public Time getDropTime() {
		return dropTime;
	}

	public void setDropTime(Time dropTime) {
		this.dropTime = dropTime;
	}
}