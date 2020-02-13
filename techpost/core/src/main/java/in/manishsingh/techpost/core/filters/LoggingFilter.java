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

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
 * Simple servlet filter component that logs incoming requests.
 */
@Component(service = Filter.class,
           property = {
                   EngineConstants.SLING_FILTER_SCOPE + "=" + EngineConstants.FILTER_SCOPE_REQUEST,
           })
@ServiceDescription("Demo to filter incoming requests")
@ServiceRanking(-700)
@ServiceVendor("Adobe")
public class LoggingFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
        logger.debug("request for {}, with selector {}", slingRequest
                .getRequestPathInfo().getResourcePath(), slingRequest
                .getRequestPathInfo().getSelectorString());
        
//        SlingHttpServletResponse sr = (SlingHttpServletResponse) response;
//        ResourceResolver rr = slingRequest.getResourceResolver();
//       
//        logger.info("MS: Location->"+rr.resolve(slingRequest.getRequestURI().trim()));
//        
//       Resource r1= rr.resolve(slingRequest.getRequestURI().trim());
//       logger.info("MS: Location1->"+r1.getValueMap()+"String->"+r1.getResourceType()+"String2->"+r1.getResourceMetadata());
//        if(r1.getResourceType().equalsIgnoreCase("sling:redirect")) {
//        	sr.setStatus(302);
//        	sr.setHeader("Location", r1.getValueMap().get("sling:target").toString().replaceFirst(".html", ""));
//        	logger.info("Inside if");
//        	
//        	logger.info("Redirect->"+r1.getValueMap().get("sling:target"));
//        	//sr.send
//        	//r1.getValueMap().get("sling:target").toString().replaceFirst(".html", "");
//        	//sr.sendRedirect(r1.getValueMap().get("sling:target").toString().replaceFirst(".html", ""));
//        	return;
//        }else {
//        	filterChain.doFilter(request, response);
//        }
//        
//     // Resource resource = slingRequest.getResourceResolver().resolve(slingRequest.getRequestURI());
////      
////      ResourceResolver rr = slingRequest.getResourceResolver();
//    logger.info("MS-Full path->"+rr.getResource(slingRequest.getRequestURI()));
////      logger.info("MS-Map"+rr.map(slingRequest.getRequestURI()));
////      
////      Session session=rr.adaptTo(Session.class);
////
////      
////      
////     try {
////		Node node = session.getNode(resource.getPath());
////		if(node.hasNode("/jcr:content")) {
////			logger.info("has jcr:content");
////			Node n1=node.getNode("/jcr:content");
////			Property p1=n1.getProperty("sling:vanityPath");
////			
////			if(p1!=null) {
////				logger.info("VanityFound");
////			}else {
////				logger.info("VanityNotFound");
////			}
////		}
////	} catch (RepositoryException e) {
////		// TODO Auto-generated catch block
////		e.printStackTrace();
////	}
////     
//
//       // filterChain.doFilter(request, response);
        
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}