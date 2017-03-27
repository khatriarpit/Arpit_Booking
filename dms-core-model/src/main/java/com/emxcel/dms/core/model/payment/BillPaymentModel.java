package com.emxcel.dms.core.model.payment;

import javax.persistence.*;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
@Entity
@Table(name = "bill_payment_model", schema = SchemaConstant.DMS_SCHEMA)
public class BillPaymentModel extends DMSEntity<Long,BillPaymentModel> {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "BILLPAYMENTMODEL_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long  id;
	
	@Column(name="merchantTransactionId")
	private String merchantTxnId;
	
	@Column(name="requestSignature")
	private String requestSignature;
	
	@Column(name="merchantAccessKey")
	private String merchantAccessKey;
	
	@Column(name="amount")
	private String amount;

	@Column(name="returnUrl")
	private String returnUrl;

	@Column(name="trupId")
	private String tripID;

	public String getMerchantTxnId() {
		return merchantTxnId;
	}
	public void setMerchantTxnId(String merchantTxnId) {
		this.merchantTxnId = merchantTxnId;
	}
	public String getRequestSignature() {
		return requestSignature;
	}
	public void setRequestSignature(String requestSignature) {
		this.requestSignature = requestSignature;
	}
	public String getMerchantAccessKey() {
		return merchantAccessKey;
	}
	public void setMerchantAccessKey(String merchantAccessKey) {
		this.merchantAccessKey = merchantAccessKey;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getTripID() {
		return tripID;
	}
	public void setTripID(String tripID) {
		this.tripID = tripID;
	}
	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

}
