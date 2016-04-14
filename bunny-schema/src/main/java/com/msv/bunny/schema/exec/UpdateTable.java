package com.msv.bunny.schema.exec;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.msv.bunny.schema.component.DataBaseDataInfo;
import com.msv.bunny.schema.component.FindEntity;
import com.msv.bunny.schema.component.IdentifyDatabase;

@Component
public class UpdateTable {

	@Autowired
	private DataBaseDataInfo dataBaseDataInfo;
	
	@Autowired
	private FindEntity findEntity;
	
	@Autowired
	private IdentifyDatabase identifyDatabase;
	
	@SuppressWarnings("rawtypes")
	public void update(String nameDataBase, String scanPackage){
		
		Configuration configuration = new Configuration();
	    configuration.setProperty("hibernate.connection.driver_class", dataBaseDataInfo.getDriverClassName());
	    configuration.setProperty("hibernate.connection.url", identifyDatabase.getUrlWitoutDataBasename(dataBaseDataInfo.getUrlDatabase()).concat(nameDataBase));
	    configuration.setProperty("hibernate.connection.username", dataBaseDataInfo.getUserDatabase());
	    configuration.setProperty("hibernate.connection.password", dataBaseDataInfo.getPassDatabase());
	    configuration.setProperty("hibernate.dialect", dataBaseDataInfo.getHibernateDialect());
	    configuration.setProperty("hibernate.show_sql", "true");
	
	    List<Class<? extends Entity>> clazz = findEntity.findAnnotatedClass(scanPackage);
	    for(Class item : clazz){
	    	configuration.addAnnotatedClass(item);
	    }

	    SchemaUpdate x = new SchemaUpdate(configuration);
		x.execute(true, true);
	}
	
 
	

}
