package com.emxcel.dms.core.model.user;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.auth.Group;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.city.City;
import com.emxcel.dms.core.model.country.Country;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.state.State;
import com.emxcel.dms.core.model.superadmin.Tenant;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = SchemaConstant.DMS_SCHEMA)
public class User extends DMSEntity<Long, User> {

	public User() {

	}

	private static final long serialVersionUID = 5401059537544058710L;

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	private Boolean blocked = Boolean.FALSE;
	@JoinColumn(name = "group_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	private Group group;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private Set<UserRole> roles;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private Set<Permission> permissions;


	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@Column(name = "username", unique = true, nullable = false, length = 45)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Transient
	private String newPassword;

	@Column(name = "enabled", nullable = false)
	private Integer enabled;

	@Column(name = "fullname")
	private String fullName;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "address")
	private String address;

	@Column(name = "contactno")
	private Long contactNo;

	@Column(name = "emailid", unique = true, nullable = false, length = 100)
	private String emailId;

	@Column(name = "userimage", nullable = true)
	private String userImage;

	@Column(name = "role_id")
	private Long roleID;

	@Column(name = "country_id")
	private Long countryId;

	@Column(name = "state_id")
	private Long stateId;

	@Column(name = "city_id")
	private Long cityId;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "color")
	private String color;

	@Column(name = "token_id")
	private String tokenId;

	@Column(name = "tenant_package_id")
	private Long tenantPackageID;

	@Column(name = "default_flag", nullable = false, columnDefinition = "tinyint default false")
	private boolean defaultFlag;

	@Column(name = "mobileToken")
	private String mobileToken;
	
	@Transient
	private Country country;

	@Transient
	private State state;

	@Transient
	private City city;

	@Transient
	private UserRole userRole;

	@Transient
	private Tenant tenant;

	/**
	 * Created by:Naresh Banda Date:30-09-2016. Use:For User
	 * 
	 * @param username
	 * @param password
	 * @param enabled
	 * @param userRole
	 * @param fullName
	 * @param emailId
	 * @param contactNo
	 */
	public User(final String username, String password, String newPassword, final Integer enabled,
			final UserRole userRole, final String fullName, final String emailId, final Long contactNo,
			final String firstName, final String middleName, final String lastName, final String address,
			final Country country, final State state, final City city, final String userImage, final Integer gender) {
		this.username = username;
		this.password = password;
		this.newPassword = newPassword;
		this.enabled = enabled;
		this.userRole = userRole;
		this.fullName = fullName;
		this.contactNo = contactNo;
		this.emailId = emailId;
		this.city = city;
		this.address = address;
		this.country = country;
		this.state = state;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userImage = userImage;
		this.gender = gender;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public Long getRoleID() {
		return roleID;
	}

	public void setRoleID(Long roleID) {
		this.roleID = roleID;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Tenant getTenant() {
		return tenant;
	}

	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getTokenId() {
		return tokenId;
	}
	public void addPermission(Permission item) {
		if (this.permissions == null) {
			this.permissions = new HashSet<>();
		}
		this.permissions.add(item);
	}

	public void removePermission(Permission item) {
		if (this.permissions == null) {
			return;
		}
		this.permissions.remove(item);
	}

	public void clearPermissions() {
		if (this.permissions != null) {
			this.permissions.clear();
		}
	}

	public Long getTenantPackageID() {
		return tenantPackageID;
	}

	public void setTenantPackageID(Long tenantPackageID) {
		this.tenantPackageID = tenantPackageID;
	}

	public boolean isDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(boolean defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public void setMobileToken(String mobileToken) {
		this.mobileToken = mobileToken;
	}
	
	public String getMobileToken() {
		return mobileToken;
	}
}