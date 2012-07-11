/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * <p>
 * ConfigResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Resources {

  protected URL global;

  protected List<URL> locals = CollectUtils.newArrayList();

  protected URL user;

  /**
   * <p>
   * getAllPaths.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<URL> getAllPaths() {
    List<URL> all = CollectUtils.newArrayList();
    if (null != global) {
      all.add(global);
    }
    if (null != locals) {
      all.addAll(locals);
    }
    if (null != user) {
      all.add(user);
    }
    return all;
  }

  /**
   * <p>
   * isEmpty.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isEmpty() {
    return null == global && null == user && (null == locals || locals.isEmpty());
  }

  /**
   * <p>
   * Getter for the field <code>global</code>.
   * </p>
   * 
   * @return a {@link java.net.URL} object.
   */
  public URL getGlobal() {
    return global;
  }

  /**
   * <p>
   * Setter for the field <code>global</code>.
   * </p>
   * 
   * @param first a {@link java.net.URL} object.
   */
  public void setGlobal(URL first) {
    this.global = first;
  }

  /**
   * <p>
   * Getter for the field <code>locals</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<URL> getLocals() {
    return locals;
  }

  /**
   * <p>
   * Setter for the field <code>locals</code>.
   * </p>
   * 
   * @param paths a {@link java.util.List} object.
   */
  public void setLocals(List<URL> paths) {
    this.locals = paths;
  }

  /**
   * <p>
   * Getter for the field <code>user</code>.
   * </p>
   * 
   * @return a {@link java.net.URL} object.
   */
  public URL getUser() {
    return user;
  }

  /**
   * <p>
   * Setter for the field <code>user</code>.
   * </p>
   * 
   * @param last a {@link java.net.URL} object.
   */
  public void setUser(URL last) {
    this.user = last;
  }
}
