package com.emxcel.dms.core.model.seller;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.state.State;

@Entity
@Table(name = "seller", schema = SchemaConstant.DMS_SCHEMA)
public class SellerModel extends DMSEntity<Long, SellerModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2142294688784119378L;


	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GUEST_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;


	@Column(name = "first_name",length =50)
	private String firstName;
	
	@Column(name = "last_name",length =50)
	private String lastName;
	
	@Column(name = "address1",length =100)
    private String address1;

    @Column(name = "address2",length = 100)
    private String address2;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id",nullable = false)
    private City city;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id",nullable = false)
    private State state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;
	
    @Column(name = "pinCode",length=10)
    private Integer pinCode;
    
    @Column(name="business_URL")
    private String businessurl;
    
    @Column(name = "mobile_no",length=20)
    private String mobileNo;
    
    @Column(name="seller_IFSC_code", unique=true,length=20 , nullable = false)
    private String seller_ifsc_code;
    
    @Column(name="seller_account_number" , unique=true)
    private String seller_account_number;
    
    @Column(name = "seller_emailid", unique = true,length=100)
    private String selleremailId;
    
    @Column(name="payment_mode",length=10)
    private String payoutmode;
	
	
	
	@Column(name = "sellerId" ,length=20)
	private String sellerId;
	
	@Column(name = "status" ,length=20)
	private Integer status;
	
	@Column(name = "balance",length=100)
	private String balance;
	
	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	
	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}



	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public String getBusinessurl() {
		return businessurl;
	}

	public void setBusinessurl(String businessurl) {
		this.businessurl = businessurl;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getSeller_ifsc_code() {
		return seller_ifsc_code;
	}

	public void setSeller_ifsc_code(String seller_ifsc_code) {
		this.seller_ifsc_code = seller_ifsc_code;
	}

	public String getSeller_account_number() {
		return seller_account_number;
	}

	public void setSeller_account_number(String seller_account_number) {
		this.seller_account_number = seller_account_number;
	}

	public String getSelleremailId() {
		return selleremailId;
	}

	public void setSelleremailId(String selleremailId) {
		this.selleremailId = selleremailId;
	}

	public String getPayoutmode() {
		return payoutmode;
	}

	public void setPayoutmode(String payoutmode) {
		this.payoutmode = payoutmode;
	}
	
	

}
