package com.emxcel.dms.core.model.geo;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

/**
 * @author Ashka Thaker
 *
 */
@Entity
@Table(name = "geodata",schema = SchemaConstant.DMS_SCHEMA)
public class GeoData extends DMSEntity<Long,GeoData> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * **Geo Data**.
	 */
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GEO_DATA_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	/**
	 * **drivingRating**.
	 */
	@Column(name = "latitude")
	private String latitude;

	/**
	 * **driverBehaviourRating**.
	 */
	@Column(name = "longitude")
	private String longitude;

	/**
	 * **tripID**.
	 */
	@Column(name = "TRIP_ID",length = 100)
	private String tripID;

	@Column(name = "TIME")
	private Date dateTime;

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTripID() {
		return tripID;
	}

	public void setTripID(String tripID) {
		this.tripID = tripID;
	}

	@Override
	public String toString() {
		return "GeoData [id=" + id + ", latitude=" + latitude + ", longitude=" + longitude + ", tripID=" + tripID
				+ ", dateTime=" + dateTime + "]";
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}
}
