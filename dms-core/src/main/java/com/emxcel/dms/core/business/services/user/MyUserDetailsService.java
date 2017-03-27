package com.emxcel.dms.core.business.services.user;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.emxcel.dms.core.business.exception.ServiceException;
import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author ADMIN.
 */
@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

	/**
	 * Inject service for UserService.
	 */
	@Inject
	private UserService userService;

	/**
	 * Inject service for UserRoleService.
	 */
	@Inject
	private UserRoleService userRoleService;

	/*
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to load user by user
	 * name
	 * 
	 * @see org.springframework.security.core.userdetails.UserDetailsService#
	 * loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		com.emxcel.dms.core.model.user.User user = null;
		try {
			user = userService.getUserDetailByUsernameOrEmail(username, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		List<GrantedAuthority> authorities = null;
		if (user != null) {
			UserRole userRole = userRoleService.getById(user.getRoleID());
			if (userRole != null) {
				authorities = buildUserAuthority(userRole);
			}
		}
		return buildUserForAuthentication(user, authorities);
	}

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: to check authorized user
	 * 
	 * @param user
	 * @param authorities
	 * @return User
	 */
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(
			com.emxcel.dms.core.model.user.User user, final List<GrantedAuthority> authorities) {
		boolean isEnabled = false;
		if (user.getEnabled() == 0) {
			isEnabled = true;
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), isEnabled,
				true, true, true, authorities);
	}

	/**
	 * Created By : Naresh Banda. Date: 26-01-2017 Use: set authentication by
	 * userrole
	 * 
	 * @param userRole
	 * @return List<GrantedAuthority>
	 */
	private List<GrantedAuthority> buildUserAuthority(final com.emxcel.dms.core.model.user.UserRole userRole) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}
}