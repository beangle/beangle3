package org.beangle.struts2.osgi;


import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.util.ClassLoaderUtil;
import com.opensymphony.xwork2.inject.Inject;
import org.osgi.framework.ServiceReference;

import java.util.Map;

/**
 * This Object factory uses the ActionContext(s) published by Spring OSGi
 * to lookup beans
 */
public class SpringOsgiObjectFactory extends ObjectFactory {
    private final static String SPRING_SERVICE_NAME = "org.springframework.context.ApplicationContext";

    private BundleAccessor bundleAccessor;

    public Object buildBean(String className, Map<String, Object> extraContext, boolean injectInternal) throws Exception {
        if (containsBean(className))
            return getBean(className);
        else {
            Class clazz = ClassLoaderUtil.loadClass(className, SpringOsgiObjectFactory.class);
            Object object = clazz.newInstance();
            if (injectInternal)
                injectInternalBeans(object);
            return object;
        }

    }

    public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
        return clazz.newInstance();
    }

    public Class getClassInstance(String className) throws ClassNotFoundException {
        return containsBean(className) ? getBean(className).getClass() :  ClassLoaderUtil.loadClass(className, SpringOsgiObjectFactory.class);
    }

    protected Object getBean(String beanName) {
        ServiceReference[] refs = bundleAccessor.getAllServiceReferences(SPRING_SERVICE_NAME);
        if (refs != null) {
            for (ServiceReference ref : refs) {
                Object context = bundleAccessor.getService(ref);
                if (OsgiUtil.containsBean(context, beanName))
                    return OsgiUtil.getBean(context, beanName);
            }
        }

        return null;
    }

    protected boolean containsBean(String beanName) {
        ServiceReference[] refs = bundleAccessor.getAllServiceReferences(SPRING_SERVICE_NAME);
        if (refs != null) {
            for (ServiceReference ref : refs) {
                Object context = bundleAccessor.getService(ref);
                if (OsgiUtil.containsBean(context, beanName))
                    return true;
            }
        }

        return false;
    }

    @Inject
    public void setBundleAccessor(BundleAccessor bundleAccessor) {
        this.bundleAccessor = bundleAccessor;
    }
}
