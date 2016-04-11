package com.msv.bunny;

import java.util.Map;

import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.msv.bunny.core.config.LoadDataSourceConfig;

@Configuration
@PersistenceContext
@Component
public class ResolverConfig { // implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private ApplicationContext applicationContext; 
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public void onApplicationEvent(ContextRefreshedEvent event) {
	public LoadDataSourceConfig init(){
		Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(LoadDataSourceConfig.class);
		
		if( beansWithAnnotation == null ){
			return null;
		}
		
		for(String item : beansWithAnnotation.keySet()){
			try{
				Class bdf = beansWithAnnotation.get(item).getClass();
				
				if(bdf == null){
					continue;
				}
				
				LoadDataSourceConfig loadConfig = (LoadDataSourceConfig) bdf.getAnnotation(LoadDataSourceConfig.class);
				if(loadConfig == null){
					continue;
				}
				
				return loadConfig;
				
			}catch(ClassCastException ex){
				continue;
			}
		}
		
		return null;
	}

}
