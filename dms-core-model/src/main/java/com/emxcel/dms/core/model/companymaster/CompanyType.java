package com.emxcel.dms.core.model.companymaster;

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
@Table(name = "company_type", schema = SchemaConstant.DMS_SCHEMA)
public class CompanyType extends DMSEntity<Long, CompanyType>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4917581613277180370L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "COMPANY_TYPE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name = "companytype")
	private String companyType;
	
	@Column(name = "companystatus")
	private boolean companyStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public boolean getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(boolean companyStatus) {
		this.companyStatus = companyStatus;
	}

}
