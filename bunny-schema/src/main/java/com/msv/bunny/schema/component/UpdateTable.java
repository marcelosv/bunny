package com.msv.bunny.schema.component;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UpdateTable {

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
	
	@Autowired
	private FindEntity findEntity;
	
	@Autowired
	private IdentifyDatabase identifyDatabase;
	
	@SuppressWarnings("rawtypes")
	public void atualizar(String nameDataBase, String scanPackage){
		
		Configuration configuration = new Configuration();
	    configuration.setProperty("hibernate.connection.driver_class", driverClassName);
	    configuration.setProperty("hibernate.connection.url", identifyDatabase.getUrlWitoutDataBasename(urlDatabase).concat(nameDataBase));
	    configuration.setProperty("hibernate.connection.username", userDatabase);
	    configuration.setProperty("hibernate.connection.password", passDatabase);
	    configuration.setProperty("hibernate.dialect", hibernateDialect);
	    configuration.setProperty("hibernate.show_sql", "true");
	
	    List<Class<? extends Entity>> clazz = findEntity.findAnnotatedClass(scanPackage);
	    for(Class item : clazz){
	    	configuration.addAnnotatedClass(item);
	    }

	    SchemaUpdate x = new SchemaUpdate(configuration);
		x.execute(true, true);
	}
	
 
	

}
