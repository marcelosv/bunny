package org.springframework.boot.bunny.multitenant;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceContext;
import javax.servlet.Filter;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

//import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.bunny.multitenant.config.DataSourceConfig;
import org.springframework.boot.bunny.multitenant.config.DataSourceConfigRepository;
import org.springframework.boot.bunny.multitenant.config.LoadDataSourceConfig;
import org.springframework.boot.bunny.multitenant.exception.BunnyNotConfigException;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;

@PropertySource("classpath:application.properties")
@EnableConfigurationProperties
@Configuration
@PersistenceContext
@Component
public class ConfigTenant {

	@Autowired
	private ResolverConfig resolverConfig;
	
	private LoadDataSourceConfig loadConfig;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;

	@Autowired
	TenantIdentifierResolver tenantResolver;

	@Autowired
	AutowireCapableBeanFactory beanFactory;

	@Bean
	public FilterRegistrationBean myFilter() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		Filter tenantFilter = new MultiTenantFilter();
		beanFactory.autowireBean(tenantFilter);
		registration.setFilter(tenantFilter);
		registration.addUrlPatterns("/*");
		return registration;
	}

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		// Generate DDL is not supported in Hibernate to multi-tenancy features
		// https://hibernate.atlassian.net/browse/HHH-7395
		hibernateJpaVendorAdapter.setGenerateDdl(false);
		return hibernateJpaVendorAdapter;
	}

	@PersistenceContext
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
		Map<String, Object> props = new HashMap<>();
		props.put("hibernate.multiTenancy", MultiTenancyStrategy.DATABASE.name());
		props.put("hibernate.multi_tenant_connection_provider", dsProvider);
		props.put("hibernate.tenant_identifier_resolver", tenantResolver);

		LocalContainerEntityManagerFactoryBean result = builder.dataSource(dataSource())
				.persistenceUnit(MultiTenantConstants.TENANT_KEY)
				.properties(props)
				.packages(getDataSourceConfig()).build();
		
		result.setJpaVendorAdapter(jpaVendorAdapter());
		return result;
	}

	@Bean
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@SuppressWarnings("unchecked")
	public Class<DataSourceConfig> getDataSourceConfig() {
		
		if( loadConfig == null ){
			loadConfig = resolverConfig.init();
		}
		
		if( loadConfig == null ){
			throw new BunnyNotConfigException();
		}
		
		return (Class<DataSourceConfig>) loadConfig.config();
	}

	@SuppressWarnings("rawtypes")
	public DataSourceConfigRepository getDataSourceConfigRepository() {
		
		if( loadConfig == null ){
			loadConfig = resolverConfig.init();
		}
		
		if( loadConfig == null ){
			throw new BunnyNotConfigException();
		}
		
		return applicationContext.getBean(loadConfig.configRepository());
	}

}
