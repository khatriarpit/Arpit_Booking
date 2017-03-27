package com.emxcel.dms.core.business.configuration;

import java.util.Collections;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import com.emxcel.dms.core.business.exception.ServiceException;

@Aspect
@Configuration
public class TxAdviceInterceptor {

	private static final String TX_METHOD_NAME = "*";
	// private static final int TX_METHOD_TIMEOUT = 3;
	private static final String AOP_POINTCUT_EXPRESSION = "this(com.emxcel.dms.core.business.services.common.generic.TransactionalAspectAwareService)";

	/**
	 * PlatformTransactionManager.
	 */
	@Autowired
	private PlatformTransactionManager transactionManager;

	/**
	 * @return TransactionInterceptor
	 */
	@Bean
	public TransactionInterceptor txAdvice() {
		NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
		RuleBasedTransactionAttribute transactionGetMethodAttribute = new RuleBasedTransactionAttribute();
		transactionGetMethodAttribute.setReadOnly(true);
		source.addTransactionalMethod("get*", transactionGetMethodAttribute);

		RuleBasedTransactionAttribute transactionListMethodAttribute = new RuleBasedTransactionAttribute();
		transactionListMethodAttribute.setReadOnly(true);
		source.addTransactionalMethod("list*", transactionListMethodAttribute);

		RuleBasedTransactionAttribute transactionSearchMethodAttribute = new RuleBasedTransactionAttribute();
		transactionSearchMethodAttribute.setReadOnly(true);
		source.addTransactionalMethod("search*", transactionSearchMethodAttribute);

		RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
		transactionAttribute.setReadOnly(false);
		transactionAttribute
				.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(ServiceException.class)));
		// transactionAttribute.setTimeout(TX_METHOD_TIMEOUT);
		source.addTransactionalMethod(TX_METHOD_NAME, transactionAttribute);

		TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
		return txAdvice;
	}

	/**
	 * @return Advisor.
	 */
	@Bean
	public Advisor txAdviceAdvisor() {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}

}