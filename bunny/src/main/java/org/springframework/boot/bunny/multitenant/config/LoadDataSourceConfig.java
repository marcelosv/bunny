package org.springframework.boot.bunny.multitenant.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LoadDataSourceConfig {
	
	Class<? extends DataSourceConfig> config();
	
	@SuppressWarnings("rawtypes")
	Class<? extends DataSourceConfigRepository> configRepository();
	
}
