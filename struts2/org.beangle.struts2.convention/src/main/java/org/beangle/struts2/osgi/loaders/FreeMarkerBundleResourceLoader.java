package org.beangle.struts2.osgi.loaders;

import java.net.URL;

import org.beangle.struts2.osgi.DefaultBundleAccessor;

import freemarker.cache.URLTemplateLoader;

/**
 * Finds FreeMarker templates in bundles
 */
public class FreeMarkerBundleResourceLoader extends URLTemplateLoader {

    @Override
    protected URL getURL(String name) {
        return DefaultBundleAccessor.getInstance().loadResource(name);
    }

}
