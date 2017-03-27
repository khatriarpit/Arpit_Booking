package com.emxcel.dms.core.business.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.user.UserRole;

/**
 * @author emxcelsolution
 *
 */
public interface UserRoleRepository extends 
		JpaRepository<UserRole, Long>, UserRoleRepositoryCustom {

	/**
	 * Created By : Nitin Patel. Date: 26-01-2017.
	 * Use: list of role by superadminrole.
	 * @param superAdminRole **superAdminRole**.
	 * @return List of UserRole
	 */
	@Query("select ur from UserRole ur where ur.role not in (:role)")
	List<UserRole> listOfRole(@Param("role") String role);

    /**
     * Created By : Naresh Banda. Date: 31-01-2017.
     * Use: get user role by role string.
     * @param role **role**.
     * @return UserRole
     */
	@Query("select ur from UserRole ur where ur.role = :role")
	UserRole getUserRole(@Param("role") String role);
}