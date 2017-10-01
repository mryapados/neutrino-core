package org.neutrinocms.core.conf.security;

import javax.sql.DataSource;

import org.neutrinocms.core.conf.security.web.CsrfHeaderFilter;
import org.neutrinocms.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@Order(2)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserService userService;
	
	@Autowired
    private DataSource dataSource;

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
		.passwordEncoder( new ShaPasswordEncoder() );
	    auth
	          .jdbcAuthentication()
	          .dataSource(dataSource)
	          .usersByUsernameQuery("SELECT username, encrypted_password, enabled FROM user WHERE username=?")
	          .authoritiesByUsernameQuery("SELECT u.username, a.name FROM user u INNER JOIN user_authority ua ON ua.user_id = u.id INNER JOIN authority a ON a.id = ua.authority_id WHERE u.username=?");
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {  
      CharacterEncodingFilter filter = new CharacterEncodingFilter();
	  filter.setEncoding("UTF-8");
	  filter.setForceEncoding(true);
    	
  	  http
  	  	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
  	  	.and().authorizeRequests()
  		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//  		.antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_APP')")
  		.and().formLogin()
  			.successHandler(savedRequestAwareAuthenticationSuccessHandler())
  			.loginPage("/login")
  			.usernameParameter("username").passwordParameter("password")
  		.and().logout()
  			.logoutUrl("/logout").deleteCookies("JSESSIONID", "XSRF-TOKEN")
  		.and().rememberMe().tokenRepository(persistentTokenRepository())
  			.tokenValiditySeconds(1209600)
  		.and()
  		.addFilterBefore(filter, CsrfFilter.class)
  		.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
    }
    
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
    	  HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    	  repository.setHeaderName("X-XSRF-TOKEN");
    	  return repository;
    }
    
    @Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}
    
	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
}
