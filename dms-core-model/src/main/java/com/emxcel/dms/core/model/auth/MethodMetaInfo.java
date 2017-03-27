/*
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

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.AuditableModel;
import com.emxcel.dms.core.model.user.UserRole;

@Entity
@Table(name = "DMS_METHOD_META_INFO", schema=SchemaConstant.DMS_SCHEMA)
public class MethodMetaInfo extends AuditableModel<Long, MethodMetaInfo> {
	
	@Id
	@Column(name = "METHOD_META_INFO_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_METHOD_META_INFO_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "METHOD_META_INFO_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_METHOD_META_INFO_GEN")
	private Long id;

	private String methodName;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
			javax.persistence.CascadeType.MERGE })
	private Set<UserRole> allowRoles;
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Set<UserRole> getAllowRoles() {
		return this.allowRoles;
	}

	public void setAllowRoles(Set<UserRole> allowRoles) {
		this.allowRoles = allowRoles;
	}

	public void addAllowRole(UserRole item) {
		if (this.allowRoles == null) {
			this.allowRoles = new HashSet();
		}
		this.allowRoles.add(item);
	}

	public void removeAllowRole(UserRole item) {
		if (this.allowRoles == null) {
			return;
		}
		this.allowRoles.remove(item);
	}

	public void clearAllowRoles() {
		if (this.allowRoles != null) {
			this.allowRoles.clear();
		}
	}

}
*/
