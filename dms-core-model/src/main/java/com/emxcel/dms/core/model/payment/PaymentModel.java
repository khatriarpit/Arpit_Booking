package com.emxcel.dms.core.model.payment;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "paymentmodel", schema = SchemaConstant.DMS_SCHEMA)
public class PaymentModel extends DMSEntity<Long, PaymentModel> {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PAYMENTMODEL_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long  id;
	
	@Column(name = "txid")
	private String TxId;
	
	@Column(name = "txstatus")
	private String TxStatus;
	
	@Column(name ="amount")
	private String amount;
	
	@Column(name = "pgTxnno")
	private String pgTxnNo;
	
	@Column(name = "issuerRefNo")
	private String issuerRefNo;
	
	@Column(name = "authIdCode")
	private String authIdCode;
	
	@Column(name = "firstName")
	private String firstName;
	
	@Column(name = "lastName")
	private String lastName;
	
	@Column(name = "pgRespCode")
	private String pgRespCode;
	
	@Column(name = "addressZip")
	private String addressZip;
	
	@Column(name = "signature")
	private String signature;
	
	@Column(name = "dateTime")
	private Timestamp dateTime;
	
	@Column(name = "merchantTxnId")
	private String merchantTxnId;
	
	@Column(name = "requestSignature")
	private String requestSignature;
	
	@Column(name = "merchantAccessKey")
	private String merchantAccessKey;
	
	/*@Column(name="productInfo")
	private String productInfo;
	
	@Column(name="service_provider_payUmoney")
	private String service_provider;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="hashcode")
	private String hashcode;

	@Column(name="payment_Mode")
	private String paymentMode;
	
	@Column(name="PG_TYPE")
	private String pgType;
	
	@Column(name="bank_ref_num")
	private String bankrefnum;*/
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
	this.id=id;		
	}

	public String getTxId() {
		return TxId;
	}

	public void setTxId(String txId) {
		TxId = txId;
	}

	public String getTxStatus() {
		return TxStatus;
	}

	public void setTxStatus(String txStatus) {
		TxStatus = txStatus;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPgTxnNo() {
		return pgTxnNo;
	}

	public void setPgTxnNo(String pgTxnNo) {
		this.pgTxnNo = pgTxnNo;
	}

	public String getIssuerRefNo() {
		return issuerRefNo;
	}

	public void setIssuerRefNo(String issuerRefNo) {
		this.issuerRefNo = issuerRefNo;
	}

	public String getAuthIdCode() {
		return authIdCode;
	}

	public void setAuthIdCode(String authIdCode) {
		this.authIdCode = authIdCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPgRespCode() {
		return pgRespCode;
	}

	public void setPgRespCode(String pgRespCode) {
		this.pgRespCode = pgRespCode;
	}

	public String getAddressZip() {
		return addressZip;
	}

	public void setAddressZip(String addressZip) {
		this.addressZip = addressZip;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

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

	/*public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getService_provider() {
		return service_provider;
	}

	public void setService_provider(String service_provider) {
		this.service_provider = service_provider;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}

	public String getBankrefnum() {
		return bankrefnum;
	}

	public void setBankrefnum(String bankrefnum) {
		this.bankrefnum = bankrefnum;
	}*/
	
	

}
