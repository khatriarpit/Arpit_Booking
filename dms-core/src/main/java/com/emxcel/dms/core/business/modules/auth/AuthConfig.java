/*package com.emxcel.dms.core.business.modules.auth;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.emxcel.dms.core.model.user.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.emxcel.dms.core.business.services.common.generic.DMSEntityServiceImpl;
import com.emxcel.dms.core.model.auth.Group;
import com.emxcel.dms.core.model.auth.Permission;
import com.emxcel.dms.core.model.generic.DMSEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;


@Aspect
@Component
public class AuthConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthConfig.class);
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.emxcel.dms.core.business.services..*.*(..))";
	private static final String QS_HAS_ROLE = "SELECT self.id FROM UserRole self WHERE (self.role = :role) AND (  (self.id IN (SELECT r.id FROM User u LEFT JOIN u.roles AS r WHERE u.id = :user)) OR   (self.id IN (SELECT r.id FROM User u LEFT JOIN u.group AS g LEFT JOIN g.roles AS r WHERE u.id = :user)))";

	@PersistenceContext
	private EntityManager em;

	private AuthResolver authResolver = new AuthResolver();
	
//    @Before("execution(* com.emxcel.dms.core.business.services..*.get*(..)) || "
//    		+ "execution(* com.emxcel.dms.core.business.services..*.list*(..)) || "
//    		+ "execution(* com.emxcel.dms.core.business.services..*.count*(..)) || "
//    		+ "execution(* com.emxcel.dms.core.business.services..*.find*(..)) || "
//    		+ "execution(* com.emxcel.dms.core.business.services..*.search*(..))"
//    		+ "execution(* com.emxcel.dms.core.business.services..*.*(..))")
	@Before("execution(* com.emxcel.dms.core.business.services..*.*(..)) && " +
			"!execution(* com.emxcel.dms.core.business.services.user.MyUserDetailsService.*(..)) && " +
			"!execution(* com.emxcel.dms.core.business.services.user.UserService.getUserDetailByUsernameOrEmail(..)) &&" +
			"!execution(* com.emxcel.dms.core.business.services.user.UserRoleService.getUserRoleByID(..)) &&" +
			"!execution(* com.emxcel.dms.core.business.services..*.update*(..))")
	public void hasReadAccess(JoinPoint joinPoint) throws Exception {
    	DMSEntityServiceImpl entityService = (DMSEntityServiceImpl) joinPoint.getTarget();
    	
    	Class model = entityService.getObjectClass();
    	System.out.println("Model Class ::: " + model);
    	LOGGER.debug("Model Class ::: " + model);
    	
    	Signature methodSignature = joinPoint.getSignature();
    	System.out.println("Signature ::: " +  methodSignature.toString());
    	LOGGER.debug("Signature ::: " +  methodSignature.toString());
    	
    	//Check for read permission.
    	check(AccessType.CAN_READ, model);
	}

    @Before("execution(* com.emxcel.dms.core.business.services..*.save*(..))")
	public void hasCreateAccess(JoinPoint joinPoint) throws Exception {
    	DMSEntityServiceImpl entityService = (DMSEntityServiceImpl) joinPoint.getTarget();
    	
    	Class model = entityService.getObjectClass();
    	System.out.println("Model Class ::: " + model);
    	LOGGER.debug("Model Class ::: " + model);
    	
    	Signature methodSignature = joinPoint.getSignature();
    	System.out.println("Signature ::: " +  methodSignature.toString());
    	LOGGER.debug("Signature ::: " +  methodSignature.toString());
    	//Check for read permission.
    	check(AccessType.CAN_CREATE, model);
	}
    
    @Before("execution(* com.emxcel.dms.core.business.services..*.update*(..)) || execution(* com.emxcel.dms.core.business.services..*.remove*(..))")
	public void hasUpdateAccess(JoinPoint joinPoint) throws Exception {
    	DMSEntityServiceImpl entityService = (DMSEntityServiceImpl) joinPoint.getTarget();
    	
    	Class model = entityService.getObjectClass();
    	System.out.println("Model Class ::: " + model);
    	LOGGER.debug("Model Class ::: " + model);
    	
    	Signature methodSignature = joinPoint.getSignature();
    	System.out.println("Signature ::: " +  methodSignature.toString());
    	LOGGER.debug("Signature ::: " +  methodSignature.toString());

    	//Check for write permission.
    	check(AccessType.CAN_WRITE, model);
	}

    @Before("execution(* com.emxcel.dms.core.business.services..*.save*(..))")
	public void hasCreateOrUpdateAccess(JoinPoint joinPoint) throws Exception {
    	DMSEntityServiceImpl entityService = (DMSEntityServiceImpl) joinPoint.getTarget();
    	
    	Class model = entityService.getObjectClass();
    	System.out.println("Model Class ::: " + model);
    	
    	Signature methodSignature = joinPoint.getSignature();
    	System.out.println("Signature ::: " +  methodSignature.toString());

    	Object[] args = joinPoint.getArgs();
    	DMSEntity entity = (DMSEntity)args[0];
    	
    	//Check Default Write permission.
    	AccessType type = AccessType.CAN_WRITE; 
    	if(entity.isNew()) {
    		//Change if entity is new than check for create permission.
    		type = AccessType.CAN_CREATE;
    	}
    	check(type, model);
	}
    
    @Before(AOP_POINTCUT_EXPRESSION)
    public void doAccessCheck(JoinPoint JoinPoint) throws Exception {
//      LOGGER.debug("doAccessCheck() :: START");
//    	System.out.println("Signature ::: " + joinPoint.getSignature());
//    	
//    	Object[] signatureArgs = joinPoint.getArgs();
//		for (Object signatureArg : signatureArgs) {
//			System.out.println("Arg: " + signatureArg);
//			LOGGER.debug("Arg: " + signatureArg);
//		}
//		LOGGER.debug("doAccessCheck() :: END");
    }
    
	private User getUser() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}

		User user = getUser(username);
		Group group = user != null ? user.getGroup() : null;
		if ((user == null) || ("superadmin".equals(user.getUsername()))) {
			return null;
		}
		if ((group != null) && ("superadmins".equals(group.getName()))) {
			return null;
		}
		return user;
	}
	
	private User getUser(String code) {
		if (code == null) {
			return null;
		}
		String q1 = "SELECT self FROM User self WHERE self.username = ?1";
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
	
	private boolean hasRole(User user, String role) {
		TypedQuery<Long> query = em.createQuery(QS_HAS_ROLE, Long.class);
		System.out.println("-------------------------------->> "+role +user.getUsername());
		query.setParameter("role", role);
		query.setParameter("user", user.getUsername());
		query.setMaxResults(1);
		query.setFlushMode(FlushModeType.COMMIT);
		List<Long> ids = query.getResultList();
		return (ids != null) && (ids.size() == 1);
	}
	
	public boolean hasRole(String name) {
		User user = getUser();
		if (user == null) {
			return true;
		}
		return hasRole(user, name);
	}

	public Set<AccessType> getAccessTypes(Class<? extends DMSEntity> model, Long id) {
		Set<AccessType> types = Sets.newHashSet();
		for (AccessType type : AccessType.values()) {
			if (isPermitted(type, model, new Long[] { id })) {
				types.add(type);
			}
		}
		return types;
	}

	public boolean isPermitted(AccessType type, Class<? extends DMSEntity> model, Long... ids) {
		User user = getUser();
		if (user == null) {
			return true;
		}
		Set<Permission> permissions = this.authResolver.resolve(user, model.getName(), type);
		if (permissions.isEmpty()) {
			return false;
		}
		for (Permission permission : permissions) {
			if ((permission.getConditionTerm() == null) && (!this.authResolver.hasAccess(permission, type))) {
				return false;
			}
		}
		if ((ids == null) || (ids.length == 0)) {
			return true;
		}
		// Filter filter = getFilter(type, model, ids);
		// if (filter == null) {
		// return true;
		// }
		return true;
	}

	public void check(AccessType type, Class<? extends DMSEntity> model, Long... ids) throws Exception {
		if (isPermitted(type, model, ids)) {
			return;
		}
		AuthSecurityException cause = new AuthSecurityException(type, model, ids);
		throw new Exception(type.getMessage(), cause);
	}
}
*/