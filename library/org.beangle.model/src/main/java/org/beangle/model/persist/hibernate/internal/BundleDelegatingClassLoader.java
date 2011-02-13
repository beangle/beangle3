/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.internal;

import java.io.IOException;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Enumeration;

import org.apache.commons.lang.Validate;
import org.osgi.framework.Bundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BundleDelegatingClassLoader extends ClassLoader {

	/** use degradable logger */
	private static final Logger log = LoggerFactory.getLogger(BundleDelegatingClassLoader.class);

	private final ClassLoader bridge;

	private final Bundle backingBundle;

	/**
	 * Factory method for creating a class loader over the given bundle.
	 * 
	 * @param aBundle
	 *            bundle to use for class loading and resource acquisition
	 * @return class loader adapter over the given bundle
	 */
	public static BundleDelegatingClassLoader createBundleClassLoaderFor(Bundle aBundle) {
		return createBundleClassLoaderFor(aBundle, null);
	}

	/**
	 * Factory method for creating a class loader over the given bundle and with
	 * a given class loader as fall-back. In case the bundle cannot find a class
	 * or locate a resource, the given class loader will be used as fall back.
	 * 
	 * @param bundle
	 *            bundle used for class loading and resource acquisition
	 * @param bridge
	 *            class loader used as fall back in case the bundle cannot load
	 *            a class or find a resource. Can be <code>null</code>
	 * @return class loader adapter over the given bundle and class loader
	 */
	public static BundleDelegatingClassLoader createBundleClassLoaderFor(final Bundle bundle,
			final ClassLoader bridge) {
		return AccessController.doPrivileged(new PrivilegedAction<BundleDelegatingClassLoader>() {

			public BundleDelegatingClassLoader run() {
				return new BundleDelegatingClassLoader(bundle, bridge);
			}
		});
	}

	/**
	 * Private constructor. Constructs a new <code>BundleDelegatingClassLoader</code> instance.
	 * 
	 * @param bundle
	 * @param bridgeLoader
	 */
	protected BundleDelegatingClassLoader(Bundle bundle, ClassLoader bridgeLoader) {
		super(null);
		Validate.notNull(bundle, "bundle should be non-null");
		this.backingBundle = bundle;
		this.bridge = bridgeLoader;
	}

	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			return this.backingBundle.loadClass(name);
		} catch (ClassNotFoundException cnfe) {
			// DebugUtils.debugClassLoading(backingBundle, name, null);
			throw new ClassNotFoundException(name + " not found from bundle ["
					+ backingBundle.getSymbolicName() + "]", cnfe);
		} catch (NoClassDefFoundError ncdfe) {
			// This is almost always an error
			// This is caused by a dependent class failure,
			// so make sure we search for the right one.
			String cname = ncdfe.getMessage().replace('/', '.');
			// DebugUtils.debugClassLoading(backingBundle, cname, name);
			NoClassDefFoundError e = new NoClassDefFoundError(cname + " not found from bundle ["
					+ backingBundle + "]");
			e.initCause(ncdfe);
			throw e;
		}
	}

	protected URL findResource(String name) {
		boolean trace = log.isTraceEnabled();

		if (trace) log.trace("Looking for resource " + name);
		URL url = this.backingBundle.getResource(name);

		if (trace && url != null) log.trace("Found resource " + name + " at " + url);
		return url;
	}

	@SuppressWarnings("unchecked")
	protected Enumeration<URL> findResources(String name) throws IOException {
		boolean trace = log.isTraceEnabled();

		if (trace) log.trace("Looking for resources " + name);

		Enumeration<URL> enm = this.backingBundle.getResources(name);

		if (trace && enm != null && enm.hasMoreElements()) log.trace("Found resource " + name + " at "
				+ this.backingBundle.getLocation());

		return enm;
	}

	public URL getResource(String name) {
		URL resource = findResource(name);
		if (bridge != null && resource == null) {
			resource = bridge.getResource(name);
		}
		return resource;
	}

	protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = null;
		try {
			clazz = findClass(name);
		} catch (ClassNotFoundException cnfe) {
			if (bridge != null) clazz = bridge.loadClass(name);
			else throw cnfe;
		}
		if (resolve) {
			resolveClass(clazz);
		}
		return clazz;
	}

	public String toString() {
		return "BundleDelegatingClassLoader for [" + backingBundle + "]";
	}

	/**
	 * Returns the bundle to which this class loader delegates calls to.
	 * 
	 * @return the backing bundle
	 */
	public Bundle getBundle() {
		return backingBundle;
	}
}
