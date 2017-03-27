package com.emxcel.dms.core.business.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DBConfig {

	@Autowired
	private Environment env;

	// Declare a datasource that has pooling capabilities
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		try {
			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setDriverClass(env.getRequiredProperty("db.driverClass"));
			ds.setJdbcUrl(env.getRequiredProperty("db.jdbcUrl"));
			ds.setUser(env.getRequiredProperty("db.user"));
			ds.setPassword(env.getRequiredProperty("db.password"));

			if (env.getRequiredProperty("db.initialPoolSize") != null) {
				ds.setInitialPoolSize(new Integer(env.getRequiredProperty("db.initialPoolSize")));
			}
			if (env.getRequiredProperty("db.minPoolSize") != null) {
				ds.setMinPoolSize(new Integer(env.getRequiredProperty("db.minPoolSize")));
			}
			if (env.getRequiredProperty("db.maxPoolSize") != null) {
				ds.setMaxPoolSize(new Integer(env.getRequiredProperty("db.maxPoolSize")));
			}	

			ds.setPreferredTestQuery(env.getRequiredProperty("db.preferredTestQuery"));
			ds.setTestConnectionOnCheckin(true);
			ds.setIdleConnectionTestPeriod(300);

			return ds;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}