package com.emxcel.dms.core.model.invoice;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.guest.Guest;
import com.emxcel.dms.core.model.tax.TaxCategory;

import javax.persistence.*;

@Entity
@Table(name = "invoice", schema = SchemaConstant.DMS_SCHEMA)
public class Invoice extends DMSEntity<Long, Invoice> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4347703048422672550L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "INVOICE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "km")
	private Float km;
	
	@Column(name = "amount")
	private double amount;

	@Column(name = "tax")
	private double tax;

	@Column(name = "total_amount", nullable = false)
	private double totalAmount;

	@Column(name = "invoice_number")
	private String invoiceNo;

	@Column(name = "tax_inclusive")
	private String taxInclusive;

	@Column(name = "tripID")
	private String tripId;

	@Column(name = "status")
	private Integer status;
	
	@Column(name = "receipt_number")
	private Integer receiptNo;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "CLIENT_ID")
	private ClientModel clientModel;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "GUEST_ID")
	private Guest guest;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "TAXCATEGORY_ID")
	private TaxCategory taxCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setKm(Float km) {
		this.km = km;
	}
	
	public Float getKm() {
		return km;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getAmount() {
		return amount;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTax() {
		return tax;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTripId(String tripId) {
		this.tripId = tripId;
	}

	public String getTripId() {
		return tripId;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setTaxInclusive(String taxInclusive) {
		this.taxInclusive = taxInclusive;
	}

	public String getTaxInclusive() {
		return taxInclusive;
	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public TaxCategory getTaxCategory() {
		return taxCategory;
	}

	public void setTaxCategory(TaxCategory taxCategory) {
		this.taxCategory = taxCategory;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
}