package com.emxcel.dms.core.model.trip;

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
@Table(name = "trip_rate", schema = SchemaConstant.DMS_SCHEMA)
public class TripRate extends DMSEntity<Long, TripRate> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4347703048422672550L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TRIP_RATE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "tripID", nullable = false)
	private String tripID;

	@Column(name = "hrs")
	private Double hr;

	@Column(name = "km")
	private Double km;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "additional_hrs")
	private Double additionalHRS;

	@Column(name = "additional_km")
	private Double additionalKM;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getHr() {
		return hr;
	}

	public void setHr(Double hr) {
		this.hr = hr;
	}

	public Double getKm() {
		return km;
	}

	public void setKm(Double km) {
		this.km = km;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAdditionalHRS() {
		return additionalHRS;
	}

	public void setAdditionalHRS(Double additionalHRS) {
		this.additionalHRS = additionalHRS;
	}

	public Double getAdditionalKM() {
		return additionalKM;
	}

	public void setAdditionalKM(Double additionalKM) {
		this.additionalKM = additionalKM;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}
}