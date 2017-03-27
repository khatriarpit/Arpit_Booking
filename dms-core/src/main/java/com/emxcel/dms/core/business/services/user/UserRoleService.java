package com.emxcel.dms.core.business.services.user;

import java.util.List;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityService;
import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author emxcelsolution
 *
 */
public interface UserRoleService extends DMSEntityService<Long, UserRole> {

    /**
     * Created By : Nitin Patel. Date: 26-01-2017.
     * Use: list Of Role not in parameter pass
     * @param role **role**.
     * @return UserRole
     */
	List<UserRole> listOfRole(String role);

	/**
	 * Created By : Naresh Banda. Date: 31-01-2017.
	 * Use: get user role by role string.
	 * @param role **role**.
	 * @return UserRole
	 */
	UserRole getUserRole(String role);
}
