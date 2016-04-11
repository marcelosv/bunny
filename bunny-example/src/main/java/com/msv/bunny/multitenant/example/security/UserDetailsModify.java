package com.msv.bunny.multitenant.example.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.msv.bunny.core.config.DataSourceConfig;
import com.msv.bunny.core.config.DataSourceConfigSecurity;
import com.msv.bunny.multitenant.example.entity.DataSourceTable;

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
