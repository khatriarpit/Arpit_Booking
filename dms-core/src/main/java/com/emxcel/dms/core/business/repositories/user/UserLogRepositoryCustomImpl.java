package com.emxcel.dms.core.business.repositories.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author emxcelsolution
 *
 */
public class UserLogRepositoryCustomImpl implements 
		UserLogRepositoryCustom {

	/**
	 * 
	 */
	@PersistenceContext
	private EntityManager em;

}