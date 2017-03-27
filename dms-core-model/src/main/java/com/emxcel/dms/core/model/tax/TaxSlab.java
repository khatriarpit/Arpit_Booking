package com.emxcel.dms.core.model.tax;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.superadmin.InvoicePackage;

@Entity
@Table(name = "tax_slab_reg", schema = SchemaConstant.DMS_SCHEMA)
public class TaxSlab extends DMSEntity<Long, TaxSlab> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -3327747905105017730L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TAX_SLAB_REG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "invoice_category_id")
	private Long  invoicecatid;
	
	@Transient
	private InvoicePackage invoicePackage;
	
	@Column(name = "taxcategory_id")
	private Long taxcategoryid;
	
	@Transient
	private TaxCategory taxCategory;
	

	public Long getInvoicecatid() {
		return invoicecatid;
	}

	public void setInvoicecatid(Long invoicecatid) {
		this.invoicecatid = invoicecatid;
	}

	public InvoicePackage getInvoicePackage() {
		return invoicePackage;
	}

	public void setInvoicePackage(InvoicePackage invoicePackage) {
		this.invoicePackage = invoicePackage;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Long getTaxcategoryid() {
		return taxcategoryid;
	}

	public void setTaxcategoryid(Long taxcategoryid) {
		this.taxcategoryid = taxcategoryid;
	}

	public TaxCategory getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(TaxCategory taxCategory) {
		this.taxCategory = taxCategory;
	}

	
}