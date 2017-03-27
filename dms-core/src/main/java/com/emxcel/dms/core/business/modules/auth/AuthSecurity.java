/*
package com.emxcel.dms.core.business.modules.auth;

import com.emxcel.dms.core.business.repositories.user.UserRepository;

import java.util.Set;

import javax.inject.Inject;

import com.emxcel.dms.core.business.repositories.user.UserRepository;
import com.emxcel.dms.core.model.auth.Group;
import com.emxcel.dms.core.model.auth.Permission;

import com.emxcel.dms.core.model.generic.DMSEntity;
import com.emxcel.dms.core.model.user.User;
import com.google.common.collect.Sets;


class AuthSecurity implements DMSSecurity {

	@Inject
	private UserRepository userRepository;

	private AuthResolver authResolver = new AuthResolver();

	private User getUser() {
		User user = userRepository.getUser();
		*/
/*Group group = user != null ? user.getGroup() : null;
		if ((user == null) || ("admin".equals(user.getUserRole()))) {
			return null;
		}
		if ((group != null) && ("admins".equals(group.getRoles()))) {
			return null;
		}*//*

		return user;
	}

	public boolean hasRole(String name) {
		User user = getUser();
		if (user == null) {
			return true;
		}
		return userRepository.hasRole(user, name);
	}

	@Override
	public boolean isPermitted(AccessType paramAccessType, Class<? extends DMSEntity> paramClass, Long... paramVarArgs) {
		User user = getUser();
		if (user == null) {
			return true;
		}
		Set<Permission> permissions = this.authResolver.resolve(user, paramClass.getName(), paramAccessType);
		if (permissions.isEmpty()) {
			return false;
		}
		for (Permission permission : permissions) {
			if ((permission.getCondition() == null) && (!this.authResolver.hasAccess(permission, paramAccessType))) {
				return false;
			}
		}
		if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
			return true;
		}

		return true;
	}

	@Override
	public void check(com.emxcel.dms.core.business.modules.auth.AccessType paramAccessType, Class<? extends DMSEntity> paramClass, Long... paramVarArgs) throws Exception {
		if (isPermitted(paramAccessType, paramClass, paramVarArgs)) {
			return;
		}
		AuthSecurityException cause = new AuthSecurityException(paramAccessType, paramClass, paramVarArgs);
		throw new Exception(paramAccessType.getMessage(), cause);
	}

	public Set<AccessType> getAccessTypes(Class<? extends DMSEntity> model, Long id) {
		Set<AccessType> types = Sets.newHashSet();
		for (AccessType type : AccessType.values()) {
			if (isPermitted(type, model, new Long[] { id })) {
				types.add(type);
			}
		}
		return types;
	}


	public DMSSecurity get() {
		return new AuthSecurity();
	}
}
*/
