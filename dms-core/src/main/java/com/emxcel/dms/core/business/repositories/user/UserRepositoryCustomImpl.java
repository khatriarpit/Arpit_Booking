package com.emxcel.dms.core.business.repositories.user;

import com.emxcel.dms.core.model.user.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import com.google.common.collect.Lists;

import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

	/*private static final String QS_HAS_ROLE = "SELECT self.id FROM UserRole self WHERE (self.name = :name) AND (  (self.id IN (SELECT r.id FROM User u LEFT JOIN u.roles AS r WHERE u.name = :user)) OR   (self.id IN (SELECT r.id FROM User u LEFT JOIN u.group AS g LEFT JOIN g.roles AS r WHERE u.name = :user)))";

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean hasRole(User user, String role) {
		TypedQuery<Long> query = em.createQuery(QS_HAS_ROLE, Long.class);
		query.setParameter("name", role);
		query.setParameter("user", user.getUsername());
		query.setMaxResults(1);
		query.setFlushMode(FlushModeType.COMMIT);
		List<Long> ids = query.getResultList();
		return (ids != null) && (ids.size() == 1);
	}

	@Override
	public User getUser(String code) {
		if (code == null) {
			return null;
		}
		String q1 = "SELECT self FROM User self WHERE self.name = ?1";
		try {
			Query query = em.createQuery(q1);
			query.setParameter(1, code);
			query.setMaxResults(1);
			query.setFlushMode(FlushModeType.COMMIT);
			List<User> users = Lists.newArrayList();
			users = query.getResultList();
			return (User) users.get(0);
		} catch (IndexOutOfBoundsException e) {
		}
		return null;
	}

	@Override
	public User getUser() {
		String q1 = "SELECT self FROM User self WHERE self.name = ?1";
		try {
			Query query = em.createQuery(q1);
			query.setMaxResults(1);
			query.setFlushMode(FlushModeType.COMMIT);
			List<User> users = Lists.newArrayList();
			users = query.getResultList();
			return (User) users.get(0);
		} catch (IndexOutOfBoundsException e) {
		}
		return null;
	}*/
}