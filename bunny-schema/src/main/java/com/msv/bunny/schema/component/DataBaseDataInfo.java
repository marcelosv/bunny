package com.msv.bunny.schema.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * Load the infor file properties.
 * 
 */
@Component
public class DataBaseDataInfo {

	@Value("${spring.datasource.url}")
	private String urlDatabase;

	@Value("${spring.datasource.username}")
	private String userDatabase;

	@Value("${spring.datasource.password}")
	private String passDatabase;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String hibernateDialect;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	public String getUrlDatabase() {
		return urlDatabase;
	}

	public void setUrlDatabase(String urlDatabase) {
		this.urlDatabase = urlDatabase;
	}

	public String getUserDatabase() {
		return userDatabase;
	}

	public void setUserDatabase(String userDatabase) {
		this.userDatabase = userDatabase;
	}

	public String getPassDatabase() {
		return passDatabase;
	}

	public void setPassDatabase(String passDatabase) {
		this.passDatabase = passDatabase;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public String getDriverClassName() {
		return driverClassName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

}
