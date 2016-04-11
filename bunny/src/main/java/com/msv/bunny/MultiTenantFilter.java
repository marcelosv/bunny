package com.msv.bunny;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import static com.msv.bunny.MultiTenantConstants.*;
/**
 * Verifies if the request has the "tenant" header and configure it as attribute.
 * It will be used to Hibernate the multi-tenant support to define which datasource needs to be loaded.
 */
public class MultiTenantFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getHeader(TENANT_KEY) != null) {
            req.setAttribute(CURRENT_TENANT_IDENTIFIER, req.getHeader(TENANT_KEY));
        } else {
            req.setAttribute(CURRENT_TENANT_IDENTIFIER, DEFAULT_TENANT_ID);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
