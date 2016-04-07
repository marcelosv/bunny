package org.springframework.boot.bunny.multitenant.config;

/**
 * 
 * Interface principal que deve ser usado para identificar qual
 * entity jpa está a config dos tenent no banco.
 * 
 * @author Marcelo de Souza Vieira
 *
 */
public interface DataSourceConfig {

	Long getId();
    String getName();
    String getUrl();
	String getUsername();
	String getPassword();
    String getDriverClassName();
	boolean getInitialize();
	    
}
