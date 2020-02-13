/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package in.manishsingh.techpost.core.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.engine.EngineConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceRanking;
import org.osgi.service.component.propertytypes.ServiceVendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SSling filter to strip extension of a vanity redirect
 */
@Component(service = Filter.class, property = {
		EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST, })
@ServiceDescription("Demo to add the html extension to an incoming extension less url")
@ServiceRanking(-702)
@ServiceVendor("Techpost")
public class urlResolver implements Filter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain filterChain)
			throws IOException, ServletException {

		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;

		SlingHttpServletResponse sr = (SlingHttpServletResponse) response;
		ResourceResolver rr = slingRequest.getResourceResolver();
		if (!slingRequest.getRequestURI().contains(".html")) {
			logger.info("MS: Resolve->" + rr.resolve(slingRequest.getRequestURI().trim()));

			Resource resource = rr.resolve(slingRequest.getRequestURI().trim());

			if (resource.getResourceType().equalsIgnoreCase("cq:Page")) {
				logger.info("dispatching");
				RequestDispatcher rd = slingRequest.getRequestDispatcher(slingRequest.getRequestURI().trim() + ".html");
				rd.forward(slingRequest, sr);

				return;
			}
		}

		filterChain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}

}