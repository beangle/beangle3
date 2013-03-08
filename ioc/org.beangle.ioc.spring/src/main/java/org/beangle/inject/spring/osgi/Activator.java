package org.beangle.inject.spring.osgi;

import org.beangle.commons.inject.Containers;
import org.beangle.inject.spring.SpringContainer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.SynchronousBundleListener;
import org.springframework.context.ApplicationContext;

public class Activator implements SynchronousBundleListener, BundleActivator {

  ApplicationContext root;

  public ApplicationContext load(Bundle bundle) {
    ApplicationContext newContext = new BundleApplicationContext(root, bundle);
    Containers.register(bundle.getBundleId(), new SpringContainer(newContext));
    return newContext;
  }

  @Override
  public void start(BundleContext context) throws Exception {
    for (Bundle bundle : context.getBundles()) {
      if (null != bundle.getEntry("/META-INF/spring") || null != bundle.getEntry("META-INF/spring")) {
        load(bundle);
      }
    }
    context.addBundleListener(this);
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    for (Bundle bundle : context.getBundles())
      Containers.remove(bundle.getBundleId());
  }

  @Override
  public void bundleChanged(BundleEvent event) {
    Bundle bundle = event.getBundle();
    switch (event.getType()) {
    case BundleEvent.INSTALLED:
      load(bundle);
      break;
    case BundleEvent.UNINSTALLED:
      Containers.remove(event.getBundle().getBundleId());
      break;
    }
  }

}
