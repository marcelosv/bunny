package com.msv.bunny;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.bunny.package")
public class Properties {
	
	private String entity;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

}
