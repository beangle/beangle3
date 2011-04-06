/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.internal;

import java.net.URL;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.hibernate.support.TableNameConfig;
import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.hibernate.SessionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.SynchronousBundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

public class DynamicSessionFactoryBean extends LocalSessionFactoryBean implements SynchronousBundleListener,
		BundleContextAware {

	private static Logger logger = LoggerFactory.getLogger(DynamicSessionFactoryBean.class);

	private BundleContext context;

	private Set<URL> configs = CollectUtils.newHashSet();

	private ChainedClassLoader loader = null;

	private TableNameConfig tableNameConfig;

	@Override
	protected SessionFactory wrapSessionFactoryIfNecessary(SessionFactory rawSf) {
		return new SessionFactoryWrapper(rawSf);
	}

	protected void refreshSessionFactory() {
		Set<Resource> cfgs = CollectUtils.newHashSet();
		for (URL config : configs) {
			cfgs.add(new UrlResource(config));
		}
		Resource[] configLocations = new Resource[cfgs.size()];
		cfgs.toArray(configLocations);
		setConfigLocations(configLocations);
		try {
			logger.debug("buid session from locations {}", cfgs);
			SessionFactoryWrapper wrapper = (SessionFactoryWrapper) getSessionFactory();
			// FIXME Thread.currentThread().setContextClassLoader(loader);
			setBeanClassLoader(loader);
			wrapper.setWrapped(buildSessionFactory());
			logger.debug("build new session factory success");
		} catch (Exception e) {
			throw new RuntimeException("cannot update sessionFactory ", e);
		}
	}

	public void bundleChanged(BundleEvent bundleEvent) {
		try {
			boolean changed = false;
			if (bundleEvent.getType() == BundleEvent.STARTED) {
				changed = addBundle(bundleEvent.getBundle());
			} else if (bundleEvent.getType() == BundleEvent.STOPPED) {
				changed = removeBundle(bundleEvent.getBundle());
			}
			if (changed) refreshSessionFactory();
		} catch (RuntimeException re) {
			logger.error("Error processing bundle event", re);
			throw re;
		}
	}

	private URL getConfigLocation(Bundle bundle) {
		return bundle.getResource("META-INF/hibernate.cfg.xml");
	}

	private Set<URL> getTableConfigLocation(Bundle bundle) {
		Set<URL> urls = CollectUtils.newHashSet();
		URL url = bundle.getResource("META-INF/table.properties");
		if (null != url) {
			urls.add(url);
		}
		url = bundle.getResource("table.properties");
		if (null != url) {
			urls.add(url);
		}
		return urls;
	}

	private boolean addBundle(Bundle bundle) {
		boolean added = false;
		URL location = getConfigLocation(bundle);
		if (null != location) {
			configs.add(location);
			added = true;
			logger.debug("Add hibernate configuration: {}", location);
		}

		Set<URL> locations = getTableConfigLocation(bundle);
		if (!locations.isEmpty()) {
			for (URL url : locations) {
				tableNameConfig.addConfig(url);
			}
		}
		loader.addClassLoader(BundleDelegatingClassLoader.createBundleClassLoaderFor(bundle));
		return added;
	}

	private boolean removeBundle(Bundle bundle) {
		boolean removed = false;
		URL location = getConfigLocation(bundle);
		if (null != location) {
			configs.remove(location);
			removed = true;
			logger.debug("Remove hibernate configuration: {}", location);
		}
		Set<URL> locations = getTableConfigLocation(bundle);
		if (!locations.isEmpty()) {
			// for (URL url : locations) {
			// tableNameConfig.addConfig(url);
			// }
		}
		return removed;
	}

	public void logService() {
		for (Bundle bundle : context.getBundles()) {
			if (null != bundle.getRegisteredServices() && bundle.getRegisteredServices().length > 0) {
				logger.debug("============================== in bundle {}", bundle.getSymbolicName());
				int i = 0;
				for (ServiceReference sr : bundle.getRegisteredServices()) {
					// Object s = context.getService(sr);
					logger.debug("service{} is {}", i++, sr);
				}
				logger.debug("==============================");
			}
		}
	}

	public void stop(BundleContext context) {
		context.removeBundleListener(this);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		if (null != context) {
			context.addBundleListener(this);
			loader = new ChainedClassLoader(
					new ClassLoader[] { BundleDelegatingClassLoader.createBundleClassLoaderFor(context
							.getBundle()) });

			boolean added = false;
			for (Bundle bundle : context.getBundles()) {
				if (bundle.getState() == Bundle.ACTIVE) {
					added |= addBundle(bundle);
					loader.addClassLoader(BundleDelegatingClassLoader.createBundleClassLoaderFor(bundle));
				}
			}
			if (added) {
				refreshSessionFactory();
			}
		}
	}

	public void setBundleContext(BundleContext bundleContext) {
		context = bundleContext;
	}

	public void setTableNameConfig(TableNameConfig tableNameConfig) {
		this.tableNameConfig = tableNameConfig;
	}

}
