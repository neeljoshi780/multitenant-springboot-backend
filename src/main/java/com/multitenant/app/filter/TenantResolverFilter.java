package com.multitenant.app.filter;

import com.multitenant.app.context.TenantContext;
import com.multitenant.app.master.service.TenantLookupService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * TenantResolverFilter
 *
 * Intercepts incoming HTTP requests and resolves
 * tenant database identifier using request header.
 *
 * This filter executes before controller processing
 * and sets tenant context for dynamic datasource routing.
 */
@Component
@RequiredArgsConstructor
public class TenantResolverFilter implements Filter {

	// Service used to fetch tenant database configuration
	private final TenantLookupService tenantLookupService;

	/**
	 * Resolves tenant information and stores tenant database
	 * identifier in TenantContext for the current request.
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		// Extract tenant identifier from request header
		String companyCode = httpRequest.getHeader("X-COMPANY-CODE");

		try{
			if (companyCode != null && !companyCode.isBlank()) {
				var config = tenantLookupService.findTenantDatabase(companyCode);
				if (config == null)
					throw new RuntimeException("Invalid tenant code");
				// Set tenant database for routing
				TenantContext.setTenantDb(config.getDbName());
			}
			// Continue filter chain
			chain.doFilter(request, response);
		} finally {
			// Always clear tenant context to avoid cross-tenant leaks
			TenantContext.clear();
		}
	}

}