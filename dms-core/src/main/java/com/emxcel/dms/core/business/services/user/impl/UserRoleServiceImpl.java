package com.emxcel.dms.core.business.services.user.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emxcel.dms.core.business.repositories.user.UserRoleRepository;
import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.business.services.user.UserRoleService;
import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author emxcelsolution
 *
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends DMSEntityServiceImpl<Long, UserRole> implements UserRoleService {

	private UserRoleRepository userRoleRepository;

	/**
	 * @param userRoleRepository
	 */
	@Inject
	public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
		super(userRoleRepository);
		this.userRoleRepository = userRoleRepository;
	}

    /**
     * Created By : Nitin Patel. Date: 26-01-2017.
     * Use: list Of Role not in parameter pass
     * @param role **role**.
     * @return UserRole
     */
	public List<UserRole> listOfRole(String role) {
		return userRoleRepository.listOfRole(role);
	}

    /**
     * Created By : Naresh Banda. Date: 31-01-2017.
     * Use: get user role by role string.
     * @param role **role**.
     * @return UserRole
     */
	public UserRole getUserRole(String role) {
		return userRoleRepository.getUserRole(role);
	}
}