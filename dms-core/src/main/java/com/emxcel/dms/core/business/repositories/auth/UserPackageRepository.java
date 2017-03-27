package com.emxcel.dms.core.business.repositories.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emxcel.dms.core.model.webservice.UserPackage;

public interface UserPackageRepository extends JpaRepository<UserPackage, Long> {

}
