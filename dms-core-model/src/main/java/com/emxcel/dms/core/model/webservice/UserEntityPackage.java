package com.emxcel.dms.core.model.webservice;

import javax.persistence.*;

import com.emxcel.dms.core.constants.SchemaConstant;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.generic.DMSEntity;

@Entity
@Table(name = "user_entitypackage", schema = SchemaConstant.DMS_SCHEMA)
public class UserEntityPackage extends DMSEntity<Long,UserEntityPackage> {
	
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "DMS_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "USERENTITYPACKAGE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name="entityname")
	private String entityname;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userpackage_id", nullable = false)
	private UserPackage userPackage;

	@Override
	public Long getId() {
		return id;
	}

	public String getEntityname() {
		return entityname;
	}

	public void setEntityname(String entityname) {
		this.entityname = entityname;
	}

	public UserPackage getUserPackage() {
		return userPackage;
	}

	public void setUserPackage(UserPackage userPackage) {
		this.userPackage = userPackage;
	}

	@Override
	public void setId(Long id) {
		this.id=id;
		
	}

}
