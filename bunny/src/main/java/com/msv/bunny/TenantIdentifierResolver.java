package com.msv.bunny;

import static com.msv.bunny.MultiTenantConstants.CURRENT_TENANT_IDENTIFIER;
import static com.msv.bunny.MultiTenantConstants.DEFAULT_TENANT_ID;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.msv.bunny.core.config.DataSourceConfigSecurity;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	private static final String ANONYMOUS_USER = "anonymousUser";

	/**
	 * 
	 * AQUI ELE IDENTIFICA QUAL O BANCO QUE FOI CHAMADO. CASO NAO TENHA NENHUM IDENTIFICADO, 
	 * ELE USA O DEFAULT
	 * 
	 * 
	 */
	@Override
	public String resolveCurrentTenantIdentifier() {
		
		if( SecurityContextHolder.getContext().getAuthentication() == null || SecurityContextHolder.getContext().getAuthentication().getPrincipal()==null){
			return DEFAULT_TENANT_ID;
		}
		
		if( ANONYMOUS_USER.equalsIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString())){
			return DEFAULT_TENANT_ID;
		}
		
		DataSourceConfigSecurity userLogger = (DataSourceConfigSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	if( userLogger != null && userLogger.getDataSourceConfig() != null ){
    		return userLogger.getDataSourceConfig().getName();
    	}
    	
		/*RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			String tenantId = (String) requestAttributes.getAttribute(CURRENT_TENANT_IDENTIFIER, RequestAttributes.SCOPE_REQUEST);
			if (tenantId != null) {
				return tenantId;
			}
		}*/
    	
		return DEFAULT_TENANT_ID;
	}

	public void forceCurrentTenantIndetifier(String tenant) {
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			requestAttributes.setAttribute(CURRENT_TENANT_IDENTIFIER, tenant, RequestAttributes.SCOPE_REQUEST);
		}
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
