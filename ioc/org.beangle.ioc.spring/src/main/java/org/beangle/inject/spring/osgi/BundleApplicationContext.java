package org.beangle.inject.spring.osgi;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.beangle.inject.spring.web.XmlWebApplicationContext;
import org.osgi.framework.Bundle;
import org.springframework.context.ApplicationContext;

public class BundleApplicationContext extends XmlWebApplicationContext {

  public BundleApplicationContext(ApplicationContext parent, Bundle bundle) {
    super();
    this.setParent(parent);
    ClassLoader current = Thread.currentThread().getContextClassLoader();
    ClassLoader bundleLoader = new BundleClassLoader(this.getClassLoader(), bundle);
    Thread.currentThread().setContextClassLoader(bundleLoader);
    this.setClassLoader(bundleLoader);
    this.setConfigLocation("classpath:spring-bundle.xml");
    this.refresh();
    Thread.currentThread().setContextClassLoader(current);
  }
}

class BundleClassLoader extends ClassLoader {

  public BundleClassLoader(ClassLoader parent, Bundle bundle) {
    super(parent);
    this.bundle = bundle;
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    return bundle.loadClass(name);
  }

  @Override
  protected URL findResource(String name) {
    URL url = bundle.getResource(name);
    return url;
  }

  @SuppressWarnings("unchecked")
  @Override
  protected Enumeration<URL> findResources(String name) throws IOException {
    return bundle.getResources(name);
  }

  final Bundle bundle;

}
