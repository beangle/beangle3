/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.inject;

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
   * getAllPaths.
   */
  public List<URL> getAllPaths() {
    List<URL> all = CollectUtils.newArrayList();
    if (null != global) all.add(global);
    if (null != locals) all.addAll(locals);
    if (null != user) all.add(user);
    return all;
  }

  /**
   * Return true is empty
   */
  public boolean isEmpty() {
    return null == global && null == user && (null == locals || locals.isEmpty());
  }

  /**
   * Getter for the field <code>global</code>.
   */
  public URL getGlobal() {
    return global;
  }

  /**
   * Setter for the field <code>global</code>.
   */
  public void setGlobal(URL first) {
    this.global = first;
  }

  /**
   * Getter for the field <code>locals</code>.
   */
  public List<URL> getLocals() {
    return locals;
  }

  /**
   * Setter for the field <code>locals</code>.
   */
  public void setLocals(List<URL> paths) {
    this.locals = paths;
  }

  /**
   * Getter for the field <code>user</code>.
   */
  public URL getUser() {
    return user;
  }

  /**
   * Setter for the field <code>user</code>.
   */
  public void setUser(URL last) {
    this.user = last;
  }
}
