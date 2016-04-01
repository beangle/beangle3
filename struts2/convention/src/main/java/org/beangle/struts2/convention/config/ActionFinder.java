/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.convention.config;

import java.util.List;
import java.util.Map;

import org.beangle.commons.lang.functor.Predicate;
import org.beangle.struts2.convention.route.Profile;

/**
 * Find Action from ObjectFactory
 * 
 * @author chaostone
 * @since 3.0.0
 */
public interface ActionFinder {

  /**
   * Find action's name and class
   * 
   * @param test
   */
  Map<Class<?>, String> getActions(ActionTest test);

  /**
   * Test whether the class is a action class.
   * <ul>
   * <li>Ends with suffix</li>
   * <li>In one of given packages</li>
   * </ul>
   * 
   * @author chaostone
   */
  static class ActionTest implements Predicate<String> {

    final String suffix;
    final List<String> packages;

    ActionTest(String suffix, List<String> packages) {
      super();
      this.suffix = suffix;
      this.packages = packages;
    }

    public Boolean apply(String name) {
      boolean isAction = name.endsWith(suffix);
      if (isAction) {
        boolean inPackage = false;
        final String classPackageName = name.indexOf(".") > 0 ? name.substring(0, name.lastIndexOf(".")) : "";
        for (String packageName : packages) {
          if (Profile.isInPackage(packageName, classPackageName)) {
            inPackage = true;
            break;
          }
        }
        return inPackage;
      } else {
        return false;
      }
    }
  }
}
