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
package org.beangle.struts2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface Result {
  /**
   * @return The name of the result mapping. This is the value that is returned from the action
   *         method and is used to associate a location with a return value.
   */
  String name() default com.opensymphony.xwork2.Action.SUCCESS;

  /**
   * @return The location of the result within the web application or anywhere on disk. This
   *         location
   *         can be relative if the type of this result is one of the pre-defined relative result
   *         types (these default to dispatcher, velocity and freemarker). Or this location can be
   *         absolute relative to the root of the web application or the classpath (since velocity
   *         and freemarker templates can be loaded via the classpath).
   */
  String location() default "";

  /**
   * @return The type of the result. This is usually setup in the struts.xml or struts-plugin.xml
   *         and is a simple name that is mapped to a result Class.
   */
  String type() default "";

  /**
   * @return The parameters passed to the result. This is a list of strings that form a name/value
   *         pair chain since creating a Map for annotations is not possible. An example would be:
   *         <code>{"key", "value", "key2", "value2"}</code>.
   */
  String[] params() default {};
}
