package com.emxcel.dms.core.business.repositories.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class UserRoleRepositoryCustomImpl implements UserRoleRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

}