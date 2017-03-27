package com.emxcel.dms.core.model.auth;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "PERMISSION", schema=SchemaConstant.DMS_SCHEMA)
public class Permission extends DMSEntity<Long, Permission> {
	@Id
	@Column(name = "PERMISSION_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_PERMISSION_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PERMISSION_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_PERMISSION_GEN")
	private Long id;
	@NotNull
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "permission")
	private List<PermissionAssign> listOfPermissionAssign = new ArrayList<PermissionAssign>();


	public Permission() {
	}

	public Permission(String name) {
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

	public List<PermissionAssign> getListOfPermissionAssign() {
		return listOfPermissionAssign;
	}

	public void setListOfPermissionAssign(List<PermissionAssign> listOfPermissionAssign) {
		this.listOfPermissionAssign = listOfPermissionAssign;
	}
}
