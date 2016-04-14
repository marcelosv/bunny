package com.msv.bunny.schema.component;

import org.springframework.stereotype.Component;

@Component
public class IdentifyDatabase {

	public String getDatabaseName(String url){
		String[] sep = url.split("/");
		return sep[sep.length-1];
	}
	
	public String getUrlWitoutDataBasename(String url){
		String[] sep = url.split("/");
		
		StringBuilder sb = new StringBuilder(); 
		for(int loop=0; loop < sep.length-2; loop++){
			sb.append(sep[loop]);
		}
		return sb.toString();
	}
}
