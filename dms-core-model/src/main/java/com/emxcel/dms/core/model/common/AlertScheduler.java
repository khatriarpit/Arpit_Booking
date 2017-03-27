package com.emxcel.dms.core.model.common;

import java.sql.Timestamp;
import java.util.Date;

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
@Table(name = "alert_scheduler", schema = SchemaConstant.DMS_SCHEMA)
public class AlertScheduler extends DMSEntity<Long, AlertScheduler> {

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ALERT_SCHEDULER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "pick_up_time", nullable = false)
	private Date pickUpTime;

	@Column(name = "trip_id", nullable = false)
	private String tripID;

	@Column(name = "scheduler_type", nullable = false, columnDefinition = "tinyint default false")
	private int schedulerType;

	@Column(name = "status", nullable = false, columnDefinition = "tinyint default false")
	private boolean status;

	public Long getId() {
		return id;
	}

	public Date getPickUpTime() {
		return pickUpTime;
	}

	public void setPickUpTime(Date pickUpTime) {
		this.pickUpTime = pickUpTime;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getSchedulerType() {
		return schedulerType;
	}

	public void setSchedulerType(int schedulerType) {
		this.schedulerType = schedulerType;
	}
}