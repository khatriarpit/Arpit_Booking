package com.emxcel.dms.core.model.state;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "state", schema = SchemaConstant.DMS_SCHEMA)
public class State extends DMSEntity<Long, State> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1741746803848267673L;
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "STATE_SEQ_NEXT_VAL")

	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	public State() {
	}

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "country_id")
	private Long countryId;

	@Transient
	private Country country;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
