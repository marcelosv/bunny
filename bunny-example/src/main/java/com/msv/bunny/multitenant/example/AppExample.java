package com.msv.bunny.multitenant.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.msv.bunny.core.config.LoadDataSourceConfig;
import com.msv.bunny.multitenant.example.entity.DataSourceTable;
import com.msv.bunny.multitenant.example.repository.DataSourceTableRepository;
import com.msv.bunny.multitenant.example.security.Users;

@SpringBootApplication
@LoadDataSourceConfig(config = DataSourceTable.class, configRepository = DataSourceTableRepository.class)
@ComponentScan(basePackages = { "com.msv.bunny",
		" com.msv.bunny.multitenant.example" })
public class AppExample {

	public static void main(String[] args) {
		SpringApplication.run(AppExample.class, args);
	}

	@Configuration
    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        private Users users;        

        @Autowired
        public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
            // @formatter:off   

            auth.userDetailsService(users);

        }
    }
	
}
