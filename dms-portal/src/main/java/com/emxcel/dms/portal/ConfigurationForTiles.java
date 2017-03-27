package com.emxcel.dms.portal;

import java.util.Properties;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.emxcel.dms.config.SecurityConfig;
import com.emxcel.dms.core.business.configuration.CoreApplicationConfiguration;

/**
 * Tiles configuration.
 * 
 * References:
 * http://docs.spring.io/spring/docs/4.0.6.RELEASE/spring-framework-reference/html/view.html#view-tiles-integrate
 * 
 * @author Emxcel Solutions
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@Import({ CoreApplicationConfiguration.class, SecurityConfig.class })
public class ConfigurationForTiles extends WebMvcConfigurerAdapter {
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
	 * #addResourceHandlers(org.springframework.web.servlet.config.annotation.
	 * ResourceHandlerRegistry)
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/");
	}

	/**
	 * Initialise Tiles on application startup and identify the location of the
	 * tiles configuration file, tiles.xml.
	 * 
	 * @return tiles configurer
	 */
	@Bean
	public TilesConfigurer tilesConfigurer() {
		final TilesConfigurer configurer = new TilesConfigurer();
		configurer.setDefinitions(new String[] { "WEB-INF/tiles/tiles.xml" });
		configurer.setCheckRefresh(true);
		return configurer;
	}

	/**
	 * Introduce a Tiles view resolver, this is a convenience implementation
	 * that extends URLBasedViewResolver.
	 * 
	 * @return tiles view resolver
	 */
	@Bean
	public TilesViewResolver tilesViewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
		resolver.setViewClass(TilesView.class);
		return resolver;
	}

	/**
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver resolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(100000000);
		return multipartResolver;
	}
	
	/**
	 * @return SimpleMappingExceptionResolver.
	 */
	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");
		mappings.setProperty("InvalidCreditCardException", "creditCardError");
		r.setExceptionMappings(mappings); // None by default
		r.setDefaultErrorView("error"); // No default
		r.setExceptionAttribute("ex"); // Default is "exception"
		return r;
	}
}