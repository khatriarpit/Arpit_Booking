package com.emxcel.dms.core.model.superadmin;

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
@Table(name="rate_of_contract",schema = SchemaConstant.DMS_SCHEMA)
public class RateOfContract extends DMSEntity<Long, RateOfContract> {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*jj*/
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "RATEOFCONTRACT")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name="rate_of_contract")
	private String rateOfContract;


	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
	}
	
	public String getRateOfContract() {
		return rateOfContract;
	}

	public void setRateOfContract(String rateOfContract) {
		this.rateOfContract = rateOfContract;
	}

	
	

}
