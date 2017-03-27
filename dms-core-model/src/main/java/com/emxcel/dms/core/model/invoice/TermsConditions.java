package com.emxcel.dms.core.model.invoice;

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
@Table(name = "termsConditions", schema = SchemaConstant.DMS_SCHEMA)
public class TermsConditions extends DMSEntity<Long, TermsConditions> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4347703048422672550L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TERMSCONDITIONS_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "invoice_static_id")
	private Long invoice_static_id;

	@Column(name = "TermsCondition")
	private String TermsCondition;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoice_static_id() {
		return invoice_static_id;
	}

	public void setInvoice_static_id(Long invoice_static_id) {
		this.invoice_static_id = invoice_static_id;
	}

	public String getTermsCondition() {
		return TermsCondition;
	}

	public void setTermsCondition(String termsCondition) {
		TermsCondition = termsCondition;
	}
}