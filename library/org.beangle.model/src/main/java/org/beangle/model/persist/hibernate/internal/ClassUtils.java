/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;
import org.osgi.framework.Bundle;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ObjectUtils;

public class ClassUtils {
	private static class ReadOnlySetFromMap<E> implements Set<E> {

		private final Set<E> keys;

		public ReadOnlySetFromMap(Map<E, ?> lookupMap) {
			keys = lookupMap.keySet();
		}

		public boolean add(Object o) {
			throw new UnsupportedOperationException();
		}

		public boolean addAll(Collection<? extends E> c) {
			throw new UnsupportedOperationException();
		}

		public void clear() {
			throw new UnsupportedOperationException();
		}

		public boolean contains(Object o) {
			return keys.contains(o);
		}

		public boolean containsAll(Collection<?> c) {
			return keys.containsAll(c);
		}

		public boolean isEmpty() {
			return keys.isEmpty();
		}

		public Iterator<E> iterator() {
			return keys.iterator();
		}

		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		public int size() {
			return keys.size();
		}

		public Object[] toArray() {
			return keys.toArray();
		}

		public <T> T[] toArray(T[] array) {
			return keys.toArray(array);
		}

		public String toString() {
			return keys.toString();
		}

		public int hashCode() {
			return keys.hashCode();
		}

		public boolean equals(Object o) {
			return o == this || keys.equals(o);
		}
	}

	public enum ClassSet {
		/**
		 * Include only the interfaces inherited from superclasses or
		 * implemented by the current class
		 */
		INTERFACES,
		/** Include only the class hierarchy (interfaces are excluded) */
		CLASS_HIERARCHY,
		/** Include all inherited classes (classes or interfaces) */
		ALL_CLASSES;
	}

	/**
	 * List of special class loaders, outside OSGi, that might be used by the
	 * user through boot delegation. read-only.
	 */
	public static final List<ClassLoader> knownNonOsgiLoaders;

	/**
	 * Set of special class loaders, outside OSGi, that might be used by the
	 * user through boot delegation. read-only.
	 */
	public static final Set<ClassLoader> knownNonOsgiLoadersSet;

	// add the known, non-OSGi class loaders
	// note that the order is important
	static {
		// start with the framework class loader
		// then get all its parents (normally the this should be fwk -> (*) ->
		// app -> ext -> boot)
		// where (*) represents some optional loaders for cases where the
		// framework is embedded

		final Map<ClassLoader, Boolean> lookupMap = new ConcurrentHashMap<ClassLoader, Boolean>(8);
		final List<ClassLoader> lookupList = Collections.synchronizedList(new ArrayList<ClassLoader>());

		final ClassLoader classLoader = getFwkClassLoader();

		if (System.getSecurityManager() != null) {
			AccessController.doPrivileged(new PrivilegedAction<Object>() {
				public Object run() {
					addNonOsgiClassLoader(classLoader, lookupList, lookupMap);
					// get the system class loader
					ClassLoader sysLoader = ClassLoader.getSystemClassLoader();
					addNonOsgiClassLoader(sysLoader, lookupList, lookupMap);
					return null;
				}
			});
		} else {
			addNonOsgiClassLoader(classLoader, lookupList, lookupMap);
			// get the system class loader
			ClassLoader sysLoader = ClassLoader.getSystemClassLoader();
			addNonOsgiClassLoader(sysLoader, lookupList, lookupMap);
		}

		// wrap the fields as read-only collections
		knownNonOsgiLoaders = Collections.unmodifiableList(lookupList);
		knownNonOsgiLoadersSet = new ReadOnlySetFromMap<ClassLoader>(lookupMap);

	}

	public static ClassLoader getFwkClassLoader() {
		if (System.getSecurityManager() != null) {
			return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
				public ClassLoader run() {
					return Bundle.class.getClassLoader();
				}
			});
		} else {
			return Bundle.class.getClassLoader();
		}
	}

	/**
	 * Special static method used during the class initialization.
	 * 
	 * @param classLoader
	 *            non OSGi class loader
	 */
	private static void addNonOsgiClassLoader(ClassLoader classLoader, List<ClassLoader> list,
			Map<ClassLoader, Boolean> map) {
		while (classLoader != null) {
			synchronized (list) {
				if (!map.containsKey(classLoader)) {
					list.add(classLoader);
					map.put(classLoader, Boolean.TRUE);
				}
			}
			classLoader = classLoader.getParent();
		}
	}

	/**
	 * Simple class loading abstraction working on both ClassLoader and Bundle
	 * classes.
	 * 
	 * @author Costin Leau
	 */
	@SuppressWarnings("unused")
	private static class ClassLoaderBridge {

		private final Bundle bundle;

		private final ClassLoader classLoader;

		public ClassLoaderBridge(Bundle bundle) {
			Validate.notNull(bundle);
			this.bundle = bundle;
			this.classLoader = null;
		}

		public ClassLoaderBridge(ClassLoader classLoader) {
			Validate.notNull(classLoader);
			this.classLoader = classLoader;
			this.bundle = null;
		}

		public Class<?> loadClass(String className) throws ClassNotFoundException {
			return (bundle == null ? classLoader.loadClass(className) : bundle.loadClass(className));
		}

		public boolean canSee(String className) {
			return (bundle == null ? org.springframework.util.ClassUtils.isPresent(className, classLoader)
					: isPresent(className, bundle));
		}
	}

	/**
	 * Checks the present of a class inside a bundle. This method returns true
	 * if the given bundle can load the given class or false otherwise.
	 * 
	 * @param className
	 * @param bundle
	 * @return
	 */
	public static boolean isPresent(String className, Bundle bundle) {
		Validate.notEmpty(className);
		Validate.notNull(bundle);
		try {
			bundle.loadClass(className);
			return true;
		} catch (Exception cnfe) {
			return false;
		}
	}

	/**
	 * Returns the classloader for the given class. This method deals with JDK
	 * classes which return by default, a null classloader.
	 * 
	 * @param clazz
	 * @return
	 */
	public static ClassLoader getClassLoader(Class<?> clazz) {
		Validate.notNull(clazz);
		ClassLoader loader = clazz.getClassLoader();
		return (loader == null ? ClassLoader.getSystemClassLoader() : loader);
	}

	/**
	 * Returns an array of class string names for the given classes.
	 * 
	 * @param array
	 * @return
	 */
	public static String[] toStringArray(Class<?>[] array) {
		if (ObjectUtils.isEmpty(array)) return new String[0];
		String[] strings = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			strings[i] = array[i].getName();
		}
		return strings;
	}

	/**
	 * Determines if multiple classes(not interfaces) are specified, without any
	 * relation to each other. Interfaces will simply be ignored.
	 * 
	 * @param classes
	 *            an array of classes
	 * @return true if at least two classes unrelated to each other are found,
	 *         false otherwise
	 */
	public static boolean containsUnrelatedClasses(Class<?>[] classes) {
		if (ObjectUtils.isEmpty(classes)) return false;

		Class<?> clazz = null;
		// check if is more then one class specified
		for (int i = 0; i < classes.length; i++) {
			if (!classes[i].isInterface()) {
				if (clazz == null) clazz = classes[i];
				// check relationship
				else {
					if (clazz.isAssignableFrom(classes[i]))
					// clazz is a parent, switch with the child
					clazz = classes[i];
					else if (!classes[i].isAssignableFrom(clazz)) return true;

				}
			}
		}

		// everything is in order
		return false;
	}

	/**
	 * Parses the given class array and eliminate parents of existing classes.
	 * Useful when creating proxies to minimize the number of implemented
	 * interfaces and redundant class information.
	 * 
	 * @see #containsUnrelatedClasses(Class[])
	 * @see #configureFactoryForClass(ProxyFactory, Class[])
	 * @param classes
	 *            array of classes
	 * @return a new array without superclasses
	 */
	public static Class<?>[] removeParents(Class<?>[] classes) {
		if (ObjectUtils.isEmpty(classes)) return new Class[0];

		List<Class<?>> clazz = new ArrayList<Class<?>>(classes.length);
		for (int i = 0; i < classes.length; i++) {
			clazz.add(classes[i]);
		}

		// remove null elements
		while (clazz.remove(null)) {
		}

		// only one class is allowed
		// there can be multiple interfaces
		// parents of classes inside the array are removed

		boolean dirty;
		do {
			dirty = false;
			for (int i = 0; i < clazz.size(); i++) {
				Class<?> currentClass = clazz.get(i);
				for (int j = 0; j < clazz.size(); j++) {
					if (i != j) {
						if (currentClass.isAssignableFrom(clazz.get(j))) {
							clazz.remove(i);
							i--;
							dirty = true;
							break;
						}
					}
				}
			}
		} while (dirty);

		return (Class[]) clazz.toArray(new Class[clazz.size()]);
	}

	/**
	 * Based on the given class, properly instructs the ProxyFactory proxies.
	 * For additional sanity checks on the passed classes, check the methods
	 * below.
	 * 
	 * @see #containsUnrelatedClasses(Class[])
	 * @see #removeParents(Class[])
	 * @param factory
	 * @param classes
	 */
	public static void configureFactoryForClass(ProxyFactory factory, Class<?>[] classes) {
		if (ObjectUtils.isEmpty(classes)) return;

		for (int i = 0; i < classes.length; i++) {
			Class<?> clazz = classes[i];

			if (clazz.isInterface()) {
				factory.addInterface(clazz);
			} else {
				factory.setTargetClass(clazz);
				factory.setProxyTargetClass(true);
			}
		}
	}

	/**
	 * Loads classes with the given name, using the given classloader.
	 * {@link ClassNotFoundException} exceptions are being ignored. The return
	 * class array will not contain duplicates.
	 * 
	 * @param classNames
	 *            array of classnames to load (in FQN format)
	 * @param classLoader
	 *            classloader used for loading the classes
	 * @return an array of classes (can be smaller then the array of class
	 *         names) w/o duplicates
	 */
	public static Class<?>[] loadClassesIfPossible(String[] classNames, ClassLoader classLoader) {
		if (ObjectUtils.isEmpty(classNames)) return new Class[0];

		Validate.notNull(classLoader, "classLoader is required");
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>(classNames.length);

		for (int i = 0; i < classNames.length; i++) {
			try {
				classes.add(classLoader.loadClass(classNames[i]));
			} catch (ClassNotFoundException ex) {
				// ignore
			}
		}

		return (Class[]) classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Loads and returns the classes given as names, using the given class
	 * loader. Throws IllegalArgument exception if the classes cannot be loaded.
	 * 
	 * @param classNames
	 *            array of class names
	 * @param classLoader
	 *            class loader for loading the classes
	 * @return the loaded classes
	 */
	public static Class<?>[] loadClasses(String[] classNames, ClassLoader classLoader) {
		if (ObjectUtils.isEmpty(classNames)) return new Class[0];

		Validate.notNull(classLoader, "classLoader is required");
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>(classNames.length);

		for (int i = 0; i < classNames.length; i++) {
			classes.add(org.springframework.util.ClassUtils.resolveClassName(classNames[i], classLoader));
		}

		return (Class[]) classes.toArray(new Class[classes.size()]);
	}

	/**
	 * Excludes classes from the given array, which match the given modifier.
	 * 
	 * @param classes
	 *            array of classes (can be null)
	 * @param modifier
	 *            class modifier
	 * @return array of classes (w/o duplicates) which does not have the given
	 *         modifier
	 */
	public static Class<?>[] excludeClassesWithModifier(Class<?>[] classes, int modifier) {
		if (ObjectUtils.isEmpty(classes)) return new Class[0];

		Set<Class<?>> clazzes = new LinkedHashSet<Class<?>>(classes.length);

		for (int i = 0; i < classes.length; i++) {
			if ((modifier & classes[i].getModifiers()) == 0) clazzes.add(classes[i]);
		}
		return (Class[]) clazzes.toArray(new Class[clazzes.size()]);
	}

	/**
	 * Returns the first matching class from the given array, that doens't
	 * belong to common libraries such as the JDK or OSGi API. Useful for
	 * filtering OSGi services by type to prevent class cast problems.
	 * <p/>
	 * No sanity checks are done on the given array class.
	 * 
	 * @param classes
	 *            array of classes
	 * @return a 'particular' (non JDK/OSGi) class if one is found. Else the
	 *         first available entry is returned.
	 */
	public static Class<?> getParticularClass(Class<?>[] classes) {
		boolean hasSecurity = (System.getSecurityManager() != null);
		for (int i = 0; i < classes.length; i++) {
			final Class<?> clazz = classes[i];
			ClassLoader loader = null;
			if (hasSecurity) {
				loader = AccessController.doPrivileged(new PrivilegedAction<ClassLoader>() {
					public ClassLoader run() {
						return clazz.getClassLoader();
					}
				});
			} else {
				loader = clazz.getClassLoader();
			}
			// quick boot/system check
			if (loader != null) {
				// consider known loaders
				if (!knownNonOsgiLoadersSet.contains(loader)) { return clazz; }
			}
		}

		return (ObjectUtils.isEmpty(classes) ? null : classes[0]);
	}
}
