package com.emxcel.dms.core.model.user;

import javax.persistence.*;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.generic.DMSEntity;

import java.util.Set;

/**
 * @author Jimit Patel
 *
 */
@Entity
@Table(name = "user_role", schema = SchemaConstant.DMS_SCHEMA)
public class UserRole extends DMSEntity<Long, UserRole> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1746670176577489041L;

	@Id
	@Column(name = "ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USER_ROLE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "role", unique = true, nullable = false, length = 45)
	private String role;

	private String description;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE })
	private Set<Permission> permissions;
	public UserRole() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
