//package com.emxcel.dms.core.model.generic;
//
//
//
//import com.emxcel.dms.core.model.user.User;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//import javax.persistence.Access;
//import javax.persistence.AccessType;
//import javax.persistence.FetchType;
//import javax.persistence.ManyToOne;
//import javax.persistence.MappedSuperclass;
//
//@MappedSuperclass
//public abstract class AuditableModel<K extends Serializable & Comparable<K>, E extends DMSEntity<K, ?>>
//		extends DMSEntity<K, E> {
//	private static final long serialVersionUID = 8149288139816677543L;
//
//	private LocalDateTime createdOn;
//
//	private LocalDateTime updatedOn;
//
//	@ManyToOne(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
//			javax.persistence.CascadeType.MERGE })
//	private User createBy;
//
//	@ManyToOne(fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.PERSIST,
//			javax.persistence.CascadeType.MERGE })
//	private User updateBy;
//
//	public LocalDateTime getCreatedOn() {
//		return this.createdOn;
//	}
//
//	@Access(AccessType.FIELD)
//	private void setCreatedOn(LocalDateTime createdOn) {
//		this.createdOn = createdOn;
//	}
//
//	public LocalDateTime getUpdatedOn() {
//		return this.updatedOn;
//	}
//
//	@Access(AccessType.FIELD)
//	private void setUpdatedOn(LocalDateTime updatedOn) {
//		this.updatedOn = updatedOn;
//	}
//
//	public User getCreateBy() {
//		return this.createBy;
//	}
//
//	@Access(AccessType.FIELD)
//	private void setCreateBy(User createdBy) {
//		this.createBy = createdBy;
//	}
//
//	public User getUpdateBy() {
//		return this.updateBy;
//	}
//
//	@Access(AccessType.FIELD)
//	private void setUpdateBy(User updatedBy) {
//		this.updateBy = updatedBy;
//	}
//
//}
