/*package com.emxcel.dms.core.business.modules.auth;

import java.util.Set;

import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.user.User;
import com.emxcel.dms.core.model.user.UserRole;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

final class AuthResolver {
	boolean hasAccess(Permission permission, AccessType accessType) {
		if (accessType == null) {
			return true;
		}
		switch (accessType) {
		case READ:
			return permission.getCanRead() == Boolean.TRUE;
		case WRITE:
			return permission.getCanWrite() == Boolean.TRUE;
		case CREATE:
			return permission.getCanCreate() == Boolean.TRUE;
		case REMOVE:
			return permission.getCanRemove() == Boolean.TRUE;
		case EXPORT:
			return permission.getCanExport() == Boolean.TRUE;
		}
		return false;
	}

	private Set<Permission> filterPermissions(Set<Permission> permissions, String object, AccessType type) {
		if ((permissions == null) || (permissions.isEmpty())) {
			return Sets.newLinkedHashSet();
		}
		Set<Permission> all = Sets.newLinkedHashSet();
		for (Permission permission : permissions) {
			if ((Objects.equal(object, permission.getObject()))
					&& ((permission.getConditionTerm() == null) || (hasAccess(permission, type)))) {
				all.add(permission);
			}
		}
		if (!all.isEmpty()) {
			return all;
		}
		String pkg = object.substring(0, object.lastIndexOf('.')) + ".*";
		for (Permission permission : permissions) {
			if ((Objects.equal(pkg, permission.getObject()))
					&& ((permission.getConditionTerm() == null) || (hasAccess(permission, type)))) {
				all.add(permission);
			}
		}
		pkg = "all.*";
		for (Permission permission : permissions) {
			if ((Objects.equal(pkg, permission.getObject()))
					&& ((permission.getConditionTerm() == null) || (hasAccess(permission, type)))) {
				all.add(permission);
			}
		}
		return all;
	}

	public Set<Permission> resolve(User user, String object, AccessType type) {
		Set<Permission> all = filterPermissions(user.getPermissions(), object, type);
		if ((all.isEmpty()) && (user.getRoles() != null)) {
			for (UserRole role : user.getRoles()) {
				all.addAll(filterPermissions(role.getPermissions(), object, type));
			}
		}
		if ((all.isEmpty()) && (user.getGroup() != null)) {
			all.addAll(filterPermissions(user.getGroup().getPermissions(), object, type));
		}
		if ((all.isEmpty()) && (user.getGroup() != null) && (user.getGroup().getRoles() != null)) {
			for (UserRole role : user.getGroup().getRoles()) {
				all.addAll(filterPermissions(role.getPermissions(), object, type));
			}
		}
		return all;
	}
}
*/