package com.emxcel.dms.core.business.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableAutoConfiguration
// @EntityScan({"com.emxcel.dms.core.model"})
@ComponentScan({ "com.emxcel.dms.core.business.configuration", "com.emxcel.dms.core.business.services", "com.emxcel.dms.core.business.utils" })
// @ImportResource("spring/dms-core-context.xml")
@PropertySources({ @PropertySource("classpath:database.properties"), @PropertySource("classpath:email.properties") })
@EnableTransactionManagement
@Import({ DBConfig.class, EhCacheConfiguration.class, JPAConfig.class, TxAdviceInterceptor.class, EmailConfig.class })
public class CoreApplicationConfiguration {

}
