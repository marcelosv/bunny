package com.msv.bunny.multitenant;

import static com.msv.bunny.multitenant.MultiTenantConstants.DEFAULT_TENANT_ID;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final long serialVersionUID = 7964385451587788516L;

	@Autowired DataSource defaultDS;

	@Autowired ApplicationContext context;

	static Map<String, DataSource> map = new HashMap<String, DataSource>();

	boolean init = false;

	private static boolean forceIdentifyDefault = false;
	
	@PostConstruct
	public void load() {
		map.put(DEFAULT_TENANT_ID, defaultDS);
	}

	@Override
	protected DataSource selectAnyDataSource() {
		return map.get(DEFAULT_TENANT_ID);
	}

	/**
	 *  AQUI ELE RETORNA O DATASOURCE SELECIONADO.
	 */
	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		if (!init) {
			init = true;
			
			forceIdentifyDefault = true;
			TenantDataSource tenantDataSource = context.getBean(TenantDataSource.class);
			map.putAll(tenantDataSource.getAll());
			forceIdentifyDefault = false;
			
		}
		return map.get(tenantIdentifier);
	}
	
	@Override
	public Connection getConnection(String tenantIdentifier)
			throws SQLException {
		
		if( forceIdentifyDefault ){
			return super.getConnection(MultiTenantConstants.DEFAULT_TENANT_ID);
		}
		
		return super.getConnection(tenantIdentifier);
	}
	
}