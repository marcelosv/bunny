package com.msv.bunny.exception;

public class BunnyNotConfigException extends RuntimeException{

	private static final long serialVersionUID = -4128011627520775886L;

	public BunnyNotConfigException() {
		super("Banner is not configured correctly. You are missing @LoadDataSourceConfig configuration.");
	}
	
}
