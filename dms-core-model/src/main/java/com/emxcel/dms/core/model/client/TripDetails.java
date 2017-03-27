package com.emxcel.dms.core.model.client;

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
@Table(name = "tripdetails", schema = SchemaConstant.DMS_SCHEMA)
public class TripDetails extends DMSEntity<Long, TripDetails> {
	

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TRIP_DETAILS_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;
    
    @Column(name = "start_km")
    private Double startKm;

    @Column(name = "end_km")
    private Double endKm;

    @Column(name = "toll_tax")
    private Long tolltax;

    @Column(name = "parking_charges")
    private Long prkingCharges;
    
    @Column(name = "tripID")
    private String tripId;
    
    @Column(name="startdate")
    private Timestamp startDate;
    
    @Column(name="enddate")
    private Timestamp endDate;
    

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Double getStartKm() {
		return startKm;
	}

	public void setStartKm(Double startKm) {
		this.startKm = startKm;
	}

	public Double getEndKm() {
		return endKm;
	}

	public void setEndKm(Double endKm) {
		this.endKm = endKm;
	}

	public Long getTolltax() {
		return tolltax;
	}

	public void setTolltax(Long tolltax) {
		this.tolltax = tolltax;
	}

	public Long getPrkingCharges() {
		return prkingCharges;
	}

	public void setPrkingCharges(Long prkingCharges) {
		this.prkingCharges = prkingCharges;
	}

	public String getTripId() {
		return tripId;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
