package org.springframework.boot.bunny.multitenant.config;

import java.util.List;

public interface DataSourceConfigRepository<T> {

    DataSourceConfig findByName(String name);
	List<T> findAll();
	
}