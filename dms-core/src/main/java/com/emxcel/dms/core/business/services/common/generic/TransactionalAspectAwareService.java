package com.emxcel.dms.core.business.services.common.generic;

/**
 * Indicates that the service must be rendered transactionally via an aspect.
 * 
 * This simplifies the Spring configuration of the transactional part because it is enough 
 * to declare the pointcut of the appearance on 
 * this(com.emxcel.dms.core.business.services.common.generic.TransactionalAspectAwareService).
 * 
 */
public interface TransactionalAspectAwareService {

}
