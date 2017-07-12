package org.neutrinocms.core.conf;

import javax.sql.DataSource;

import org.neutrinocms.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
    private LoginService loginService;
	
	@Autowired
    private DataSource dataSource;

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginService)
		.passwordEncoder( new ShaPasswordEncoder() );
	    auth
	          .jdbcAuthentication()
	          .dataSource(dataSource)
	          .usersByUsernameQuery("SELECT username, encrypted_password, enabled FROM user WHERE username=?")
	          .authoritiesByUsernameQuery("SELECT username, role FROM user WHERE username=?");
	}
    
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//                "/resources/*/**"
//                , "*/js/*/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {    	
  	  http
  	  	.csrf().csrfTokenRepository(csrfTokenRepository())
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
  		.and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class);
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