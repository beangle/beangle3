package org.beangle.struts2.osgi;


import java.util.Map;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.config.PackageProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.util.ObjectFactoryDestroyable;

public class DelegatingObjectFactory extends ObjectFactory implements ObjectFactoryDestroyable {
    private ObjectFactory delegateObjectFactory;
    private BundleAccessor bundleResourceLoader;
    private OsgiConfigurationProvider osgiConfigurationProvider;

    @Inject
    public void setDelegateObjectFactory(@Inject Container container,
                                         @Inject("struts.objectFactory.delegate") String delegate) {
        if (delegate == null) {
            delegate = "struts";
        }
        delegateObjectFactory = container.getInstance(ObjectFactory.class, delegate);
    }

    @Inject
    public void setBundleResourceLoader(BundleAccessor rl) {
        this.bundleResourceLoader = rl;
    }


    public boolean isNoArgConstructorRequired() {
        return delegateObjectFactory.isNoArgConstructorRequired();
    }

    public Object buildBean(Class clazz, Map extraContext) throws Exception {
        return delegateObjectFactory.buildBean(clazz, extraContext);
    }

    public Object buildBean(String className, Map<String, Object> extraContext, boolean injectInternal) throws Exception {
        try {
            return delegateObjectFactory.buildBean(className, extraContext, injectInternal);
        } catch (Exception e) {
            Object object = bundleResourceLoader.loadClass(className).newInstance();
            if (injectInternal)
                injectInternalBeans(object);
            return object;
        }
    }

    @Override
    public Class getClassInstance(String className) throws ClassNotFoundException {
        try {
            return delegateObjectFactory.getClassInstance(className);
        }
        catch (Exception e) {
            return bundleResourceLoader.loadClass(className);
        }
    }

    public void destroy() {
        if (osgiConfigurationProvider != null) {
            osgiConfigurationProvider.destroy();
        }
    }

    @Inject("osgi")
    public void setOsgiConfigurationProvider(PackageProvider osgiConfigurationProvider) {
        this.osgiConfigurationProvider = (OsgiConfigurationProvider) osgiConfigurationProvider;
    }
}