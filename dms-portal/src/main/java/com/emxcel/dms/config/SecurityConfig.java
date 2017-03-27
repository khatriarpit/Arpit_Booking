package com.emxcel.dms.config;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Inject
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;



	//private CsrfMatcher csrfRequestMatcher = new CsrfMatcher();

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and().formLogin()
				.loginPage("/login").failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and().rememberMe().rememberMeParameter("remember-me").rememberMeCookieName("remember-me")
				.and().logout().logoutSuccessUrl("/login?logout").and().csrf().and()
				.exceptionHandling().accessDeniedPage("/403");

	/*	http.authorizeRequests().antMatchers("/login*//**").permitAll().antMatchers("*//**").authenticated();*/


	/*	http.sessionManagement().maximumSessions(20).expiredUrl("/").maxSessionsPreventsLogin(false).and()
		/*
		http.sessionManagement().maximumSessions(20).expiredUrl("/").maxSessionsPreventsLogin(false).and()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/");*/


		http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')").and().formLogin()
				.loginPage("/login").failureUrl("/login?error").usernameParameter("username")
				.passwordParameter("password").and().rememberMe().rememberMeParameter("remember-me")
				.rememberMeCookieName("remember-me").and().logout().logoutSuccessUrl("/login?logout").and().csrf().and()
				.exceptionHandling().accessDeniedPage("/403");

/*http.authorizeRequests().antMatchers("/login*//**").permitAll().antMatchers("*//**").authenticated();*/

		http.authorizeRequests().antMatchers("/login/**", "/user-verification/**", "/password-verification/**",
				"/terms-and-conditions/**","/forgot-password/**","/frgt-password/**","/update-password/**").permitAll().antMatchers("/**").authenticated();


		http.authorizeRequests().antMatchers("/rest/**").permitAll().and().csrf().disable();

		http.sessionManagement().maximumSessions(20).expiredUrl("/").maxSessionsPreventsLogin(false).and()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).invalidSessionUrl("/");

	}

	 @Override
	 @Bean(name = "authenticationManager")
	 protected AuthenticationManager authenticationManager() throws Exception {
	  return super.authenticationManager();
	 }

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}