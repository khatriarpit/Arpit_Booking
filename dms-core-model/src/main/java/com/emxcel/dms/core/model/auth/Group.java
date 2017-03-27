package com.emxcel.dms.core.model.auth;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.UserRole;

@Entity
@Table(name = "GROUP", schema=SchemaConstant.DMS_SCHEMA)
public class Group extends DMSEntity<Long, Group> {
	
	private static final long serialVersionUID = 3786127652573709701L;
	
	@Id
	@Column(name = "GROUP_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GROUP_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "GROUP_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GROUP_GEN")
	private Long id;
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUP_SEQ")
//	@SequenceGenerator(name = "GROUP_SEQ", sequenceName = "GROUP_SEQ", allocationSize = 1)
//	private Long id;
	@NotNull
	@Size(min = 2)
	private String name;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE })
	private Set<UserRole> roles;
	@ManyToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE })
	private Set<Permission> permissions;

	public Group() {
	}

	public Group(String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRole> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public void addRole(UserRole item) {
		if (this.roles == null) {
			this.roles = new HashSet();
		}
		this.roles.add(item);
	}

	public void removeRole(UserRole item) {
		if (this.roles == null) {
			return;
		}
		this.roles.remove(item);
	}

	public void clearRoles() {
		if (this.roles != null) {
			this.roles.clear();
		}
	}

	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public void addPermission(Permission item) {
		if (this.permissions == null) {
			this.permissions = new HashSet();
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

}
