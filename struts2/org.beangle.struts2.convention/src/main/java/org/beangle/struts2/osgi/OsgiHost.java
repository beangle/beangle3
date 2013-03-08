package org.beangle.struts2.osgi;


import java.util.Map;

import javax.servlet.ServletContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * Implementations of this class start an OSGi container. They must also add the BundleContext to
 * the ServletContext under the key  OsgiHost.OSGI_BUNDLE_CONTEXT;
 */
public interface OsgiHost {
    String OSGI_BUNDLE_CONTEXT = "__struts_osgi_bundle_context"; 
    String OSGI_HEADER_STRUTS_ENABLED = "Struts2-Enabled";

    void destroy() throws Exception;
    void init(ServletContext servletContext);
    Map<String, Bundle> getBundles();
    Map<String, Bundle> getActiveBundles();
    BundleContext getBundleContext();
}
