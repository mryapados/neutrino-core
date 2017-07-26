package org.neutrinocms.core.conf.security;

import javax.sql.DataSource;

import org.neutrinocms.core.conf.CsrfHeaderFilter;
import org.neutrinocms.core.conf.security.api.security.JwtAuthenticationEntryPoint;
import org.neutrinocms.core.conf.security.api.security.JwtAuthenticationTokenFilter;
import org.neutrinocms.core.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Configuration
    @Order(1)
    public static class UriWebConfigurationAdapter extends WebSecurityConfigurerAdapter {

    	@Autowired
        private LoginService loginService;
    	
    	@Autowired
        private DataSource dataSource;


        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
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
        protected void configure(HttpSecurity http) throws Exception {    	
      	  http
      	  	.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      	  	.and().authorizeRequests()
      		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//      		.antMatchers("/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER') or hasRole('ROLE_APP')")
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

    @Configuration
    @Order(2)
    public static class UriApiConfigurationAdapter extends WebSecurityConfigurerAdapter {

    	
    	
    	
    	
    	@Autowired
        private JwtAuthenticationEntryPoint unauthorizedHandler;

    	@Autowired
        private LoginService loginService;

        @Autowired
        public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder
                    .userDetailsService(this.loginService)
                    .passwordEncoder(new ShaPasswordEncoder());
        }

        @Bean
        public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
            return new JwtAuthenticationTokenFilter();
        }

        @Override
        protected void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity
                    // we don't need CSRF because our token is invulnerable
                    .csrf().disable()

                    .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                    // don't create session
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                    .authorizeRequests()
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .anyRequest().authenticated();

            // Custom JWT based security filter
            httpSecurity
                    .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

            // disable page caching
            httpSecurity.headers().cacheControl();
        }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
 
    }
}