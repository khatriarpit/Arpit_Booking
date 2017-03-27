/*package com.emxcel.dms.core.business.modules.auth;

public class AuthSecurityException extends RuntimeException {
	private static final long serialVersionUID = -794508889899422879L;
	private AccessType type;
	private Class<?> model;
	private Long[] ids;

	public AuthSecurityException(AccessType type) {
		this(type, null, new Long[0]);
	}

	public AuthSecurityException(AccessType type, Class<?> model, Long... ids) {
		this.type = type;
		this.model = model;
		this.ids = ids;
	}

	public AccessType getType() {
		return this.type;
	}

	public Class<?> getModel() {
		return this.model;
	}

	public Long[] getIds() {
		return this.ids;
	}

	public String getMessage() {
		return this.type.getMessage();
	}
}
*/