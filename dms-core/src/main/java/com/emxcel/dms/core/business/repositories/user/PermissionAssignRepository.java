package com.emxcel.dms.core.business.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.auth.PermissionAssign;

public interface PermissionAssignRepository  extends JpaRepository<PermissionAssign, Long>{

}
