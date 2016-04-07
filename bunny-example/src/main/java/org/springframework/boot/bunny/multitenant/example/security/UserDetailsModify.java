package org.springframework.boot.bunny.multitenant.example.security;

import java.util.List;

import org.springframework.boot.bunny.multitenant.config.DataSourceConfig;
import org.springframework.boot.bunny.multitenant.config.DataSourceConfigSecurity;
import org.springframework.boot.bunny.multitenant.example.entity.DataSourceTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserDetailsModify extends User implements DataSourceConfigSecurity {

	private static final long serialVersionUID = 3375988537129735478L;
	private DataSourceTable dataSourceTable;

	public UserDetailsModify(String login, String senha, List<GrantedAuthority> auth, DataSourceTable dataSourceTable) {
		super(login, senha, auth);
		this.dataSourceTable = dataSourceTable;
	}

	@Override
	public DataSourceConfig getDataSourceConfig() {
		return dataSourceTable;
	}

}
