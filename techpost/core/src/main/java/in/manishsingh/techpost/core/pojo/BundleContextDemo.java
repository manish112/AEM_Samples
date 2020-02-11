package in.manishsingh.techpost.core.pojo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.jcr.Node;
import javax.jcr.Session;

import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import com.adobe.cq.sightly.WCMUsePojo;

public class BundleContextDemo extends WCMUsePojo {
	
	Map<String, Object> auth_info= Collections.singletonMap(ResourceResolverFactory.SUBSERVICE, "demo-service");
	ResourceResolver resourceResolver;
	@Override
	public void activate() throws Exception {
		BundleContext bundleContext=FrameworkUtil.getBundle(BundleContextDemo.class).getBundleContext();
		ServiceReference serviceRef= bundleContext.getServiceReference(ResourceResolverFactory.class.getName());
		ResourceResolverFactory resourceResolverFactory=(ResourceResolverFactory) bundleContext.getService(serviceRef);
		try {
			
		    resourceResolver=resourceResolverFactory.getServiceResourceResolver(auth_info);
			Session session=resourceResolver.adaptTo(Session.class);
			Node node = session.getNode("/content/ms");
			node.setProperty("jcr:title", "Manish");
			session.save();
			
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally {
			resourceResolver.close();
		}

	}

}
