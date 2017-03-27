package com.emxcel.dms.core.business.repositories.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emxcel.dms.core.model.user.LoginLogoutLogModel;

/**
 * @author emxcelsolution
 *
 */
public interface UserLogRepository extends JpaRepository<LoginLogoutLogModel, Long>, UserLogRepositoryCustom {

	@Query("select l from LoginLogoutLogModel l where l.createdBy = :username order by l.id desc")
	List<LoginLogoutLogModel> getUsernameLastData(@Param("username") String username);
}