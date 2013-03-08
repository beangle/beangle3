package org.beangle.struts2.osgi;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public interface BundleAccessor {

    String CURRENT_BUNDLE_NAME = "__bundle_name__";

    Class loadClass(String name) throws ClassNotFoundException;

    InputStream loadResourceAsStream(String name) throws IOException;

    InputStream loadResourceFromAllBundlesAsStream(String name) throws IOException;

    URL loadResourceFromAllBundles(String name) throws IOException;

    Set<String> getPackagesByBundle(Bundle bundle);

    Object getService(ServiceReference ref);

    ServiceReference getServiceReference(String className);

    ServiceReference[] getServiceReferences(String className, String params) throws InvalidSyntaxException;

    public ServiceReference[] getAllServiceReferences(String className);

    void addPackageFromBundle(Bundle bundle, String packageName);

    void setBundleContext(BundleContext bundleContext);

    void setOsgiHost(OsgiHost osgiHost);
}