package com.emxcel.dms.core.model.user;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

/**
 * @author Jimit Patel
 *
 */
@Entity
@Table(name = "user_log", schema = SchemaConstant.DMS_SCHEMA)
public class LoginLogoutLogModel extends DMSEntity<Long, LoginLogoutLogModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USER_LOG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "IPADDRESS", length = 50)
	private String ipaddress;

	@Column(name = "LOGIN_DATETIME")
	private Timestamp login_dateTime;

	@Column(name = "LOGOUT_DATETIME", length = 30)
	private Timestamp logout_dateTime;

	@Column(name = "role", length = 20)
	private String role;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Timestamp getLogin_dateTime() {
		return login_dateTime;
	}

	public void setLogin_dateTime(Timestamp login_dateTime) {
		this.login_dateTime = login_dateTime;
	}

	public Timestamp getLogout_dateTime() {
		return logout_dateTime;
	}

	public void setLogout_dateTime(Timestamp logout_dateTime) {
		this.logout_dateTime = logout_dateTime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}