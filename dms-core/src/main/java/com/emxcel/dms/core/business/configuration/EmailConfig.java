package com.emxcel.dms.core.business.configuration;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import freemarker.template.TemplateException;

@Configuration
public class EmailConfig {

	@Autowired
	private Environment env;

	@Bean
	public JavaMailSenderImpl MailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setProtocol(env.getRequiredProperty("mailSender.protocol"));
		mailSender.setHost(env.getRequiredProperty("mailSender.host"));
		mailSender.setPort(new Integer(env.getRequiredProperty("mailSender.port")));

		mailSender.setUsername(env.getRequiredProperty("mailSender.username"));
		mailSender.setPassword(env.getRequiredProperty("mailSender.password"));
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", env.getRequiredProperty("mailSender.mail.smtp.auth"));
		javaMailProperties.put("mail.smtp.starttls.enable", env.getRequiredProperty("mail.smtp.starttls.enable"));
		javaMailProperties.put("mail.debug", env.getRequiredProperty("mail.debug"));
		javaMailProperties.put("mail.smtp.dsn.notify", env.getRequiredProperty("mail.smtp.dsn.notify"));
		javaMailProperties.put("mail.smtp.reportsuccess", env.getRequiredProperty("mail.smtp.reportsuccess"));
		javaMailProperties.put("mail.smtp.sendpartial", env.getRequiredProperty("mail.smtp.sendpartial"));
		javaMailProperties.put("mail.smtp.noop.strict", env.getRequiredProperty("mail.smtp.noop.strict"));
		javaMailProperties.put("mail.smtp.from", env.getRequiredProperty("mailSender.username"));
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

	@Bean
	public freemarker.template.Configuration freemarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean factoryBean = new FreeMarkerConfigurationFactoryBean();
		factoryBean.setPreferFileSystemAccess(false);
		factoryBean.setTemplateLoaderPath("classpath:/templates/email");
		try {
			factoryBean.afterPropertiesSet();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return factoryBean.getObject();
	}

	@Bean
	public com.emxcel.dms.core.business.modules.email.HtmlEmailSender HtmlEmailSender() {
		com.emxcel.dms.core.business.modules.email.HtmlEmailSenderImpl htmlEmailSenderImpl = new com.emxcel.dms.core.business.modules.email.HtmlEmailSenderImpl();
		htmlEmailSenderImpl.setMailSender(MailSender());
		htmlEmailSenderImpl.setFreemarkerMailConfiguration(freemarkerConfiguration());
		return htmlEmailSenderImpl;
	}
}