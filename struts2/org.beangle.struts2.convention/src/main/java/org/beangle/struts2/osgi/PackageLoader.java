package org.beangle.struts2.osgi;

import com.opensymphony.xwork2.FileManager;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import java.util.List;
import java.util.Map;

/**
 * Implementations of this interface can load packages from a Bundle
 */
public interface PackageLoader {

    List<PackageConfig> loadPackages(Bundle bundle, BundleContext bundleContext, ObjectFactory objectFactory, FileManager fileManager,
                                     Map<String, PackageConfig> map) throws ConfigurationException;

}
