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
package org.beangle.struts2.convention.config;

/**
 * <p>
 * This interface defines how the action configurations for the current web application can be
 * constructed. This must find all actions that are not specifically defined in the struts XML files
 * or any plugins. Furthermore, it must make every effort to locate all action results as well.
 * </p>
 */
public interface ActionConfigBuilder {
  /**
   * Builds all the action configurations and stores them into the XWork
   * configuration instance via XWork dependency injetion.
   */
  void buildActionConfigs();

  boolean needsReload();

  void destroy();
}
