package org.springframework.boot.bunny.multitenant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.bunny.multitenant.config.DataSourceConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TenantDataSource implements Serializable {

	private static final long serialVersionUID = 8922867349433202101L;

	@Autowired
	private ConfigTenant configTenant;
	
    private HashMap<String, DataSource> dataSources = new HashMap<>();

//    @Value("${spring.jpa.properties.hibernate.hbm2ddl.import_files}")
//    private String importFiles;
    
    //@Autowired
    //private DataSourceConfigRepository configRepo;

    /**
     * DA UM GET NO DATASOURCE, CASO NAO EXISTE, ELE CRIAR UM NOVO.
     * @param name
     * @return
     */
    public DataSource getDataSource(String name) {
    	
    	// PEGA O DATASOURCE JA EXISTENTE
        if (dataSources.get(name) != null) {
            return dataSources.get(name);
        }
        
        // SE NAO EXISTE, CRIA UM NOVO
        DataSource dataSource = createDataSource(name);
        if (dataSource != null) {
            dataSources.put(name, dataSource);
        }
        return dataSource;
    }

    /**
     * RETORNA TODOS OS DATASOURCES CARREGADO DO BANCO
     * @return
     */
    @SuppressWarnings("unchecked")
	public Map<String, DataSource> getAll() {
        List<DataSourceConfig> configList = configTenant.getDataSourceConfigRepository().findAll();
        Map<String, DataSource> result = new HashMap<>();
        for (DataSourceConfig config : configList) {
            DataSource dataSource = getDataSource(config.getName());
            result.put(config.getName(), dataSource);
        }
        return result;
    }

    /**
     * 
     * CRIA UM NOVO DATASOURCE
     * 
     * @param name
     * @return
     */
    private DataSource createDataSource(String name) {
        DataSourceConfig config = configTenant.getDataSourceConfigRepository().findByName(name);
        if (config != null) {
            DataSourceBuilder factory = DataSourceBuilder
                    .create().driverClassName(config.getDriverClassName())
                    .username(config.getUsername())
                    .password(config.getPassword())
                    .url(config.getUrl());
            DataSource ds = factory.build();
            
            /*if (config.getInitialize()) {
                initialize(ds);
            }*/
            
            return ds;
        }
        return null;
    }

    /**
     * CARREGA NO DS CRIADO OS SCRIPTS DO CARICA
     * @param dataSource
     */
    private void initialize(DataSource dataSource) {
    	
//    	if(StringUtils.isEmpty(importFiles)){
//    		return;
//    	}
//    	
//    	ClassPathResource importResource = new ClassPathResource(importFiles);
    	
//        ClassPathResource schemaResource = new ClassPathResource("schema.sql");
//        ClassPathResource dataResource = new ClassPathResource("data.sql");
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(importResource);
//        populator.execute(dataSource);
    }


}
