package org.neutrinocms.core.conf;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class NeutrinoCoreProperties {
	
	@Resource
	private Environment environment;
	
	private String jdbcUrl;
	private String jdbcUser;
	private String jdbcPassword;
	private String hibernateHbm2ddlAuto;
	
	private String pathBundleLabelsBack;
	private String pathBundleLabelsFront;
	
	private String tempDir;

	private String cacheDir;
	
	private Boolean cache;
	private Boolean jspCache;
	
	private String basePackage;
	private String basePackageDao;
	private String basePackageModel;
	
	private String boUrl;
	private String apiUrl;
	private String authUrl;
	
	@PostConstruct
	public void init() {
		jdbcUrl = environment.getProperty("jdbc.url");
		jdbcUser = environment.getProperty("jdbc.user");
		jdbcPassword = environment.getProperty("jdbc.password");
		hibernateHbm2ddlAuto = environment.getProperty("hibernate.hbm2ddl.auto");
	
		pathBundleLabelsBack  = environment.getProperty("path.bundle.labels.back");
		pathBundleLabelsFront  = environment.getProperty("path.bundle.labels.front");
		
		tempDir  = environment.getProperty("temp.dir");	

		cacheDir  = environment.getProperty("cache.dir");	
		
		cache  = Boolean.parseBoolean(environment.getProperty("cache"));
		jspCache  = Boolean.parseBoolean(environment.getProperty("cache.jsp"));
		
		basePackage = environment.getProperty("basepackage");	
		basePackageDao = environment.getProperty("basepackage.dao");	
		basePackageModel = environment.getProperty("basepackage.model");
		
		boUrl = environment.getProperty("bo.url");
		apiUrl = environment.getProperty("api.url");
		authUrl = environment.getProperty("auth.url");
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}
	public String getJdbcUser() {
		return jdbcUser;
	}
	public String getJdbcPassword() {
		return jdbcPassword;
	}
	public String getHibernateHbm2ddlAuto() {
		return hibernateHbm2ddlAuto;
	}
	public String getPathBundleLabelsBack() {
		return pathBundleLabelsBack;
	}
	public String getPathBundleLabelsFront() {
		return pathBundleLabelsFront;
	}
	public String getTempDir(){
		return tempDir;
	}
	public String getCacheDir() {
		return cacheDir;
	}
	public Boolean getCache() {
		return cache;
	}
	public Boolean getJspCache() {
		return jspCache;
	}
	public String getBasePackage() {
		return basePackage;
	}
	public String getBasePackageDao() {
		return basePackageDao;
	}
	public String getBasePackageModel() {
		return basePackageModel;
	}
	public String getBoUrl() {
		return boUrl;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public String getAuthUrl() {
		return authUrl;
	}
}
