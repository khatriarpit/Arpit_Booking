package com.emxcel.dms.core.model.superadmin;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.client.ClientModel;
import com.emxcel.dms.core.model.companymaster.CompanyType;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.feedback.Feedback;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.seller.SellerModel;
import com.emxcel.dms.core.model.state.State;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tenant", schema = SchemaConstant.DMS_SCHEMA)
@NamedNativeQueries({
        @NamedNativeQuery(name = "getTenantByCityId", query = "SELECT * FROM DMS.tenant t where t.city_id= :cityID", resultClass = Tenant.class),
        @NamedNativeQuery(name = "getTenantByCityTenant", query = "SELECT * FROM tenant tn where (tn.tanentid=:tenant OR tn.companyname=:tenant) AND tn.city_id=:cityID", resultClass = Tenant.class)
})
public class Tenant extends DMSEntity<Long, Tenant> {

    private static final long serialVersionUID = 1L;

    /*
     * private static final long serialVersionUID = 5401059537544058710L;
     */
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "TENANT_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "companytypeID")
    private CompanyType companytypename;

    @Column(name = "companyname",nullable = false,length=100)
    private String companyname;


    public CompanyType getCompanytypename() {
        return companytypename;
    }

    public void setCompanytypename(CompanyType companytypename) {
        this.companytypename = companytypename;
    }

    @Column(name = "contactno", unique = true,nullable = false,length=20)
    private Long contactno;

    @Column(name = "contactno1")
    private Long contactno1;

    @Column(name = "website",nullable = false,length=150)
    private String website;

    @Column(name = "first_name1",nullable = false)
    private String firstName1;

    @Column(name = "first_name2")
    private String firstName2;

    @Column(name = "last_name1",nullable = false,length=50)
    private String lastName1;

    @Column(name = "last_name2",length=50)
    private String lastName2;

    @Column(name = "mobile_no1",nullable = false,length=20)
    private String mobileNo1;

    @Column(name = "mobile_no2",length=20)
    private String mobileNo2;

    @Column(name = "mobile_no3",length=20)
    private String mobileNo3;

    @Column(name = "landline_number",length=20)
    private Long landlineNumber;

    @Column(name = "mobile_no4")
    private String mobileNo4;

    @Column(name = "email1",length=100)
    private String email1;

    @Column(name = "email2",length=100)
    private String email2;

    @Column(name = "emailid", unique = true,nullable = false,length=100)
    private String emailid;

    @Column(name = "servicetaxno", unique = true,nullable = false,length=20)
    private String servicetaxno;

    @Column(name = "panno", unique = true,length=20)
    private String panno;

    @Column(name = "packageid")
    private Long packageid;

    @Column(name = "paymentid")
    private Long paymentid;

    @Column(name = "amount")
    private double amount;

    @Column(name = "tanentid",nullable = false,length = 100)
    private String tanentid;

    @Column(name = "status")
    private int status;

    @Column(name = "owner_name",nullable = false,length=50)
    private String ownerName;

    @Column(name = "address1",nullable = false,length =100)
    private String address1;

    @Column(name = "address2",nullable = false,length = 50)
    private String address2;

    @Column(name = "landmark",length=50)
    private String landmark;

    @Column(name = "pinCode",nullable = false)
    private Integer pinCode;

    @Column(name = "is_delete", nullable = false)
    private boolean isDeleted;

    @Column(name = "email_status", nullable = false)
    private int emailStatus;

    @Column(name = "email_flag", nullable = false)
    private boolean emailFlag;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id",nullable = false)
    private City city;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id",nullable = false)
    private State state;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id",nullable = false)
    private Country country;
    
    /*@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="sellerId",nullable=false)
	private SellerModel sellerModel;*/

    @Transient
    private boolean typeDeleteOption;

    @Transient
    private boolean packageEnable;

    @Transient
    private Feedback feedBack;

    public Feedback getFeedBack() {
        return feedBack;
    }


    public void setFeedBack(Feedback feedBack) {
        this.feedBack = feedBack;
    }

    public Long getLandlineNumber() {
        return landlineNumber;
    }

    public void setLandlineNumber(Long landlineNumber) {
        this.landlineNumber = landlineNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public Long getContactno() {
        return contactno;
    }

    public void setContactno(Long contactno) {
        this.contactno = contactno;
    }

    public Long getContactno1() {
        return contactno1;
    }

    public void setContactno1(Long contactno1) {
        this.contactno1 = contactno1;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFirstName1() {
        return firstName1;
    }

    public void setFirstName1(String firstName1) {
        this.firstName1 = firstName1;
    }

    public String getFirstName2() {
        return firstName2;
    }

    public void setFirstName2(String firstName2) {
        this.firstName2 = firstName2;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public String getMobileNo1() {
        return mobileNo1;
    }

    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }

    public String getMobileNo2() {
        return mobileNo2;
    }

    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }

    public String getMobileNo3() {
        return mobileNo3;
    }

    public void setMobileNo3(String mobileNo3) {
        this.mobileNo3 = mobileNo3;
    }

    public String getMobileNo4() {
        return mobileNo4;
    }

    public void setMobileNo4(String mobileNo4) {
        this.mobileNo4 = mobileNo4;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getServicetaxno() {
        return servicetaxno;
    }

    public void setServicetaxno(String servicetaxno) {
        this.servicetaxno = servicetaxno;
    }

    public String getPanno() {
        return panno;
    }

    public void setPanno(String panno) {
        this.panno = panno;
    }

    public Long getPackageid() {
        return packageid;
    }

    public void setPackageid(Long packageid) {
        this.packageid = packageid;
    }

    public Long getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(Long paymentid) {
        this.paymentid = paymentid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTanentid() {
        return tanentid;
    }

    public void setTanentid(String tanentid) {
        this.tanentid = tanentid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public boolean isTypeDeleteOption() {
        return typeDeleteOption;
    }

    public void setTypeDeleteOption(boolean typeDeleteOption) {
        this.typeDeleteOption = typeDeleteOption;
    }

    public boolean isPackageEnable() {
        return packageEnable;
    }

    public void setPackageEnable(boolean packageEnable) {
        this.packageEnable = packageEnable;
    }

    public int getEmailStatus() {
        return emailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        this.emailStatus = emailStatus;
    }

    public boolean getEmailFlag() {
        return emailFlag;
    }

    public void setEmailFlag(boolean emailFlag) {
        this.emailFlag = emailFlag;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

}