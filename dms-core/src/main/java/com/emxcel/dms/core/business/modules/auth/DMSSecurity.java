/*package com.emxcel.dms.core.business.modules.auth;

import java.util.Set;

import com.emxcel.dms.core.model.generic.DMSEntity;

public abstract interface DMSSecurity {
	public abstract Set<AccessType> getAccessTypes(Class<? extends DMSEntity> paramClass, Long paramLong);

	public abstract boolean hasRole(String paramString);

	// public abstract Filter getFilter(AccessType paramAccessType, Class<?
	// extends Model> paramClass, Long... paramVarArgs);

	public abstract boolean isPermitted(AccessType paramAccessType, Class<? extends DMSEntity> paramClass,
			Long... paramVarArgs);

	public abstract void check(AccessType paramAccessType, Class<? extends DMSEntity> paramClass, Long... paramVarArgs)
			throws Exception;

	public static enum AccessType {
		READ("You are not authorized to read this resource."), WRITE(
				"You are not authorized to update this resource."), CREATE(
						"You are not authorized to create this resource."), REMOVE(
								"You are not authorized to remove this resource."), EXPORT(
										"You are not authorized to export the data.");

		private String message;

		private AccessType(String message) {
			this.message = message;
		}

		public String getMessage() {
			return this.message;
		}
	}

	public static final AccessType CAN_READ = AccessType.READ;
	public static final AccessType CAN_WRITE = AccessType.WRITE;
	public static final AccessType CAN_CREATE = AccessType.CREATE;
	public static final AccessType CAN_REMOVE = AccessType.REMOVE;
	public static final AccessType CAN_EXPORT = AccessType.EXPORT;

}
*/