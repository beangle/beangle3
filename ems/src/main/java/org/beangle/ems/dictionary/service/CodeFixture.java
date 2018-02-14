/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.dictionary.service;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;

/**
 * @author chaostone
 * @version $Id: CodeFixture.java May 5, 2011 3:48:37 PM chaostone $
 */
public class CodeFixture {

  private Map<String, Object> params = CollectUtils.newHashMap();

  private String script;

  private Object entity;

  public CodeFixture(Object entity) {
    super();
    this.entity = entity;
  }

  public CodeFixture(Object entity, String script) {
    super();
    this.entity = entity;
    this.script = script;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public String getScript() {
    return script;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }

  public void setScript(String script) {
    this.script = script;
  }

  public final Object getEntity() {
    return entity;
  }

}
